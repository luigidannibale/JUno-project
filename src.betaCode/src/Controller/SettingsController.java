package Controller;

import Utilities.AudioManager;
import Utilities.Config;
import View.Elements.*;
import View.Pages.SettingsPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsController extends Controller<SettingsPanel>
{
    private DeckColor deckChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController(MainFrameController mainFrame)
    {
        super(new SettingsPanel());

        addButtonsListeners(mainFrame);
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
        Config.saveConfig();

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

        VolumeSlider musicVolumeSlider = view.getMusicVolumeSlider();
        ChangebleIcon musicLabel = view.getMusicLabel();
        musicVolumeSlider.addPropertyChangeListener(evt -> {
            var val = musicVolumeSlider.getValue();
            if (val == 0) musicLabel.setIcon("off");
            else musicLabel.setIcon("on");
        });
        musicVolumeSlider.addChangeListener(e -> {
            var val = musicVolumeSlider.getValue();
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

        VolumeSlider effectsVolumeSlider = view.getEffectsVolumeSlider();
        ChangebleIcon effectsLabel = view.getEffectsLabel();
        effectsVolumeSlider.addPropertyChangeListener(evt -> {
            var val = effectsVolumeSlider.getValue();
            if (val == 0) effectsLabel.setIcon("off");
            else if (val < 40) effectsLabel.setIcon("low");
            else effectsLabel.setIcon("high");
        });
        effectsVolumeSlider.addChangeListener(e -> {
            var val = effectsVolumeSlider.getValue();
            if (val == 0) effectsLabel.setIcon("off");
            else if (val < 40) effectsLabel.setIcon("low");
            else effectsLabel.setIcon("high");
            if (view.isVisible()){
                AudioManager.getInstance().setCommonFolder();
                AudioManager.getInstance().setEffects(AudioManager.Effects.AUDIO_TEST, val);
            }
        });

    }
    public void setReturnPanel(MainFrameController.Panels returnPanel)
    {
        this.returnPanel = returnPanel;
        view.getGameOverButton().setVisible(returnPanel == MainFrameController.Panels.GAME);
    }
}
