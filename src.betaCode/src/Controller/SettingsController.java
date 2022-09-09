package Controller;

import Controller.Utilities.AudioManager;
import Controller.Utilities.Config;
import View.Elements.*;
import View.Pages.SettingsPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Specialize {@link Controller} on {@link SettingsPanel}.
 * Class used to get the inputs from the user in the {@link SettingsPanel}.
 * It manages the values of the {@link Config} and updates the {@link SettingsPanel} when they are modified.
 * It also initializes the {@link ChangebleIcon} with the icons they need.
 * When the {@link SettingsPanel} is closed, the visible panel becomes the last one where the user was
 * @author D'annibale Luigi, Venturini Daniele
 */
public class SettingsController extends Controller<SettingsPanel>
{
    private DeckColor deckChanges;
    private MainFrameController.Panels returnPanel;

    /**
     * Creates a new {@link SettingsController} with its associated view ({@link SettingsPanel}), updating the displayed values and adding the listeners
     */
    public SettingsController()
    {
        super(new SettingsPanel());

        addButtonsListeners();
        addChangeableIconListeners();
        refreshSettings();
    }

    /**
     * Adds the listeners to the buttons
     */
    private void addButtonsListeners()
    {
        view.getSaveButton().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                saveConfig();
                MainFrameController.getInstance().setVisiblePanel(returnPanel);
            }
        });

        view.getCloseButton().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MainFrameController.getInstance().setVisiblePanel(returnPanel);
            }
        });

        view.getGameOverButton().addMouseListener((new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MainFrameController.getInstance().quitGame();
            }
        }));

    }

    /**
     * Saves the new {@link Config} values
     */
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

    /**
     * Refresh the displayed values with the ones in {@link Config}
     */
    public void refreshSettings()
    {
        view.getEffectsVolumeSlider().setValue(Config.effectsVolume);
        view.getMusicVolumeSlider().setValue(Config.musicVolume);
        view.getQualityCombo().setSelectedItem(Config.graphicQuality);
        changeDeckBack(Config.deckStyle);
    }

    /**
     * Changes the selected {@link View.Pages.SettingsPanel.DeckRectangle}
     * @param deckColor the related {@link DeckColor}
     */
    private void changeDeckBack(DeckColor deckColor)
    {
        deckChanges = deckColor;
        view.getDarkDeck().setPaintBackground( deckColor==DeckColor.BLACK);
        view.getWhiteDeck().setPaintBackground(deckColor==DeckColor.WHITE);
    }

    /**
     * Adds the listeners to the {@link View.Pages.SettingsPanel.DeckRectangle} and the {@link ChangebleIcon}
     */
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

    /**
     * Sets the {@link Controller.MainFrameController.Panels} to be visible when {@link SettingsPanel} is closed
     * @param returnPanel
     */
    public void setReturnPanel(MainFrameController.Panels returnPanel)
    {
        this.returnPanel = returnPanel;
        view.getGameOverButton().setVisible(returnPanel == MainFrameController.Panels.GAME);
    }
}
