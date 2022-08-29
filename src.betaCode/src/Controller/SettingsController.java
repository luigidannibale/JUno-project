package Controller;

import Utilities.Config;
import Utilities.ConfigDeprecated;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;
import View.Pages.SettingsPanel;

import javax.swing.*;
import java.awt.event.*;

public class SettingsController {

    private final SettingsPanel view;

    //MainFrame.Dimensions dimesionChanges;
    private DeckColor deckChanges;
    private GraphicQuality graphicsChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController(MainFrameController mainFrame){
        view = new SettingsPanel();

        view.getEffectsVolumeSlider().setValue(mainFrame.soundEffects.getVolume());
        view.getMusicVolumeSlider().setValue(mainFrame.soundEffects.getVolume());
        //dimesionChanges = mainFrame.getCurrentDimension();

        view.getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainFrame.backMusic.setVolume(view.getMusicVolumeSlider().getValue());
                //mainFrame.backMusic.setFloatControlVolume();
                //mainFrame.soundEffects.setVolume(view.getEffectsVolumeSlider().getValue());
                //mainFrame.soundEffects.setFloatControlVolume();
                Config.effectsVolume = view.getEffectsVolumeSlider().getValue();
                Config.musicVolume = view.getMusicVolumeSlider().getValue();
                Config.deckStyle = deckChanges;
                Config.graphicQuality = graphicsChanges;
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
            GraphicQuality selectedQuality = GraphicQuality.valueOf( ((String) ((JComboBox<String>) e.getSource()).getSelectedItem()).toUpperCase());
            if (selectedQuality != null) graphicsChanges = selectedQuality;
        });
        view.getQualityCombo().setSelectedIndex(ConfigDeprecated.highGraphics ? 0 : 1);

        changeDeckBack(ConfigDeprecated.defaultColor);
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
