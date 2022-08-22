package Controller;

import Utilities.Config;
import View.DeckColor;
import View.GraphicQuality;
import View.SettingsPanel;

import javax.swing.*;
import java.awt.event.*;

public class SettingsController {

    private final SettingsPanel view;

    //MainFrame.Dimensions dimesionChanges;
    private DeckColor deckChanges;
    private boolean graphicsChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController(MainFrameController mainFrame){
        view = new SettingsPanel();

        view.getEffectsVolumeSlider().setValue(mainFrame.soundEffects.getVolume());
        view.getMusicVolumeSlider().setValue(mainFrame.soundEffects.getVolume());
        //dimesionChanges = mainFrame.getCurrentDimension();

        view.getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.backMusic.setVolume(view.getMusicVolumeSlider().getValue());
                //mainFrame.backMusic.setFloatControlVolume();
                mainFrame.soundEffects.setVolume(view.getEffectsVolumeSlider().getValue());
                //mainFrame.soundEffects.setFloatControlVolume();
                Config.defaultColor = deckChanges;
                Config.highGraphics = graphicsChanges;
                if (returnPanel == MainFrameController.Panels.GAME) mainFrame.resumeGame();
                mainFrame.getConfig().saveConfig();
                mainFrame.setVisiblePanel(returnPanel);
            }
        });
        view.getCloseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainFrame.setVisiblePanel(MainFrameController.Panels.STARTMENU);
                mainFrame.setVisiblePanel(returnPanel);
            }
        });

        view.getQuitButton().addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.quitGame();
            }
        }));

        view.getWhiteDeck().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeDeckBack(DeckColor.WHITE);
            }
        });

        view.getDarkDeck().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeDeckBack(DeckColor.BLACK);
            }
        });

        view.getQualityCombo().addActionListener(e -> {
            String selectedQuality = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
            if (selectedQuality != null) graphicsChanges = selectedQuality.equals(GraphicQuality.HIGH.VALUE);
        });
        view.getQualityCombo().setSelectedIndex(Config.highGraphics ? 0 : 1);

        changeDeckBack(Config.defaultColor);
    }

    void changeDeckBack(DeckColor c){
        deckChanges = c;
        view.getDarkDeck().setPaintBackground( c==DeckColor.BLACK);
        view.getWhiteDeck().setPaintBackground(c==DeckColor.WHITE);
    }

    public void setReturnPanel(MainFrameController.Panels returnPanel) {
        this.returnPanel = returnPanel;
        view.getQuitButton().setVisible(returnPanel == MainFrameController.Panels.GAME);
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }

    public SettingsPanel getView() {
        return view;
    }
}
