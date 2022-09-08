package Controller;

import Controller.Utilities.AudioManager;
import Controller.Utilities.Config;
import View.Elements.*;
import View.Pages.SettingsPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsController extends Controller<SettingsPanel>
{
    private DeckColor deckChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController()
    {
        super(new SettingsPanel());

        MainFrameController mfc = MainFrameController.getInstance();
        addButtonsListeners(mfc);
        addChangeableIconListeners();
        refreshSettings();

    }

    private void addButtonsListeners(MainFrameController mainFrame)
    {
        view.getSaveButton().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                saveConfig();
                mainFrame.setVisiblePanel(returnPanel);
            }
        });

        view.getCloseButton().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mainFrame.setVisiblePanel(returnPanel);
            }
        });

        view.getGameOverButton().addMouseListener((new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mainFrame.quitGame();
            }
        }));

    }

    private void saveConfig()
    {
        Config.effectsVolume = view.getEffectsVolumeSlider().getValue();
        Config.musicVolume = view.getMusicVolumeSlider().getValue();
        Config.deckStyle = deckChanges;
        Config.graphicQuality = (GraphicQuality) view.getQualityCombo().getSelectedItem();
        //if (returnPanel == MainFrameController.Panels.GAME) mainFrame.resumeGame();
        Config.storeConfig();

        AudioManager.getInstance().setVolume(Config.musicVolume);
    }

    public void refreshSettings()
    {
        view.getEffectsVolumeSlider().setValue(Config.effectsVolume);
        view.getMusicVolumeSlider().setValue(Config.musicVolume);
        view.getQualityCombo().setSelectedItem(Config.graphicQuality);
        changeDeckBack(Config.deckStyle);
    }

    private void changeDeckBack(DeckColor c) {
        deckChanges = c;
        view.getDarkDeck().setPaintBackground( c==DeckColor.BLACK);
        view.getWhiteDeck().setPaintBackground(c==DeckColor.WHITE);
    }

    private void addChangeableIconListeners()
    {
        MouseAdapter deckColorStyleMouseAdapter = new CustomMouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeDeckBack(((SettingsPanel.DeckRectangle) e.getComponent()).getDeckColor());
            }
        };

        view.getWhiteDeck().addMouseListener(deckColorStyleMouseAdapter);
        view.getDarkDeck().addMouseListener(deckColorStyleMouseAdapter);

        ViewSlider musicViewSlider = view.getMusicVolumeSlider();
        ChangebleIcon musicLabel = view.getMusicLabel();
        musicViewSlider.addPropertyChangeListener(evt -> {
            var val = musicViewSlider.getValue();
            if (val == 0) musicLabel.setIcon("off");
            else musicLabel.setIcon("on");
        });
        musicViewSlider.addChangeListener(e -> {
            var val = musicViewSlider.getValue();
            if (val == 0) musicLabel.setIcon("off");
            else musicLabel.setIcon("on");
        });

        JComboBox<GraphicQuality> qualityCombo = view.getQualityCombo();
        ChangebleIcon qualityLabel = view.getQualityLabel();
        qualityCombo.addActionListener(e -> {
            var val = (GraphicQuality) qualityCombo.getSelectedItem();
            if (val == GraphicQuality.HIGH) qualityLabel.setIcon("high");
            else qualityLabel.setIcon("low");
        });

        ViewSlider effectsViewSlider = view.getEffectsVolumeSlider();
        ChangebleIcon effectsLabel = view.getEffectsLabel();
        effectsViewSlider.addPropertyChangeListener(evt -> {
            var val = effectsViewSlider.getValue();
            if (val == 0) effectsLabel.setIcon("off");
            else if (val < 40) effectsLabel.setIcon("low");
            else effectsLabel.setIcon("high");
        });
        effectsViewSlider.addChangeListener(e -> {
            var val = effectsViewSlider.getValue();
            if (val == 0) effectsLabel.setIcon("off");
            else if (val < 40) effectsLabel.setIcon("low");
            else effectsLabel.setIcon("high");
            if (view.isVisible()) AudioManager.getInstance().setEffect(AudioManager.Effect.AUDIO_TEST, val);
        });

    }
    public void setReturnPanel(MainFrameController.Panels returnPanel)
    {
        this.returnPanel = returnPanel;
        view.getGameOverButton().setVisible(returnPanel == MainFrameController.Panels.GAME);
    }
}
