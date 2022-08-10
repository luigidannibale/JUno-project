package Controller;

import Utilities.Config;
import View.DeckColor;
import View.MainFrame;
import View.SettingsPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsController {

    private SettingsPanel view;

    //MainFrame.Dimensions dimesionChanges;
    private DeckColor deckChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController(MainFrameController mainFrame){
        view = new SettingsPanel(mainFrame);

        view.getEffectsVolumeSlider().setValue(mainFrame.soundEffects.getVolume());
        view.getMusicVolumeSlider().setValue(mainFrame.soundEffects.getVolume());
        //dimesionChanges = mainFrame.getCurrentDimension();

        view.getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainFrame.updateSize(dimesionChanges);
                mainFrame.backMusic.setVolume(view.getMusicVolumeSlider().getValue());
                mainFrame.backMusic.setFloatControlVolume();
                mainFrame.soundEffects.setVolume(view.getEffectsVolumeSlider().getValue());
                mainFrame.soundEffects.setFloatControlVolume();
                //mainFrame.deckColor = deckChanges;
                Config.defaultColor = deckChanges;
                if (returnPanel == MainFrameController.Panels.GAME) mainFrame.changeGameDeck();
                mainFrame.getConfig().saveConfig();
                //AudioManager.getInstance().setEffectVolume((effectsVolumeSlider.getValue()));
            }
        });
        view.getCloseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainFrame.setVisiblePanel(MainFrameController.Panels.STARTMENU);
                mainFrame.setVisiblePanel(returnPanel);
                view.setVisible(false);
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
