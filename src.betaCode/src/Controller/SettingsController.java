package Controller;

import Utilities.Config;
import Utilities.ConfigDeprecated;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;
import View.Pages.SettingsPanel;

import javax.swing.*;
import java.awt.event.*;

public class SettingsController
{
    private final SettingsPanel view;
    private DeckColor deckChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController(MainFrameController mainFrame){
        view = new SettingsPanel();

        refreshSettings();

        view.getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveConfig();
                mainFrame.setVisiblePanel(returnPanel);
            }
        });

        view.getCloseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.setVisiblePanel(returnPanel);
            }
        });

        view.getQuitButton().addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.quitGame();
            }
        }));

        MouseAdapter deckColorStyleMouseAdapter = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) { changeDeckBack(((SettingsPanel.DeckRectangle) e.getComponent()).getDeckColor()); }
        };

        view.getWhiteDeck().addMouseListener(deckColorStyleMouseAdapter);
        view.getDarkDeck().addMouseListener(deckColorStyleMouseAdapter);

//        view.getEffectsVolumeSlider().addChangeListener(e -> {
//            if (icon != null){
//                String newImage = path;
//                if (getValue() == 0) newImage += fileNames[0];
//                else if (getValue() < 50) newImage += fileNames[1];
//                else newImage += fileNames[2];
//                var icona = new ImageIcon((newImage));
//                icon.setIcon(ScaleImage(icona, icona.getIconWidth(), icona.getIconHeight()));
//            }
//        });
    }

    private void saveConfig()
    {
        Config.effectsVolume = view.getEffectsVolumeSlider().getValue();
        Config.musicVolume = view.getMusicVolumeSlider().getValue();
        Config.deckStyle = deckChanges;
        Config.graphicQuality = (GraphicQuality) view.getQualityCombo().getSelectedItem();
        //if (returnPanel == MainFrameController.Panels.GAME) mainFrame.resumeGame();
        Config.saveConfig();
    }

    public void refreshSettings()
    {
        view.getEffectsVolumeSlider().setValue(Config.effectsVolume);
        view.getMusicVolumeSlider().setValue(Config.musicVolume);
        view.getQualityCombo().setSelectedItem(Config.graphicQuality);
        changeDeckBack(Config.deckStyle);
    }

    void changeDeckBack(DeckColor c) {
        deckChanges = c;
        view.getDarkDeck().setPaintBackground( c==DeckColor.BLACK);
        view.getWhiteDeck().setPaintBackground(c==DeckColor.WHITE);
    }

    public void setReturnPanel(MainFrameController.Panels returnPanel)
    {
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
