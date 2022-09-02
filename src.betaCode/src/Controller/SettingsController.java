package Controller;

import Utilities.Config;
import View.Elements.ChangebleIcon;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;
import View.Elements.VolumeSlider;
import View.Pages.SettingsPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class SettingsController
{
    private final SettingsPanel view;
    private DeckColor deckChanges;
    private MainFrameController.Panels returnPanel;

    public SettingsController(MainFrameController mainFrame)
    {
        view = new SettingsPanel();
        addButtonsListeners(mainFrame);
        addChangeableIconListeners();
        refreshSettings();
        Arrays.stream(view.getComponents()).forEach(component -> {
            if(component.getPreferredSize().height == 0) System.out.println(component);
            component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
        });
    }

    private void addButtonsListeners(MainFrameController mainFrame)
    {
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

    private void changeDeckBack(DeckColor c) {
        deckChanges = c;
        view.getDarkDeck().setPaintBackground( c==DeckColor.BLACK);
        view.getWhiteDeck().setPaintBackground(c==DeckColor.WHITE);
    }
    private void addChangeableIconListeners()
    {
        MouseAdapter deckColorStyleMouseAdapter = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) { changeDeckBack(((SettingsPanel.DeckRectangle) e.getComponent()).getDeckColor()); }
        };

        view.getWhiteDeck().addMouseListener(deckColorStyleMouseAdapter);
        view.getDarkDeck().addMouseListener(deckColorStyleMouseAdapter);

        VolumeSlider musicVolumeSlider = view.getMusicVolumeSlider();
        ChangebleIcon musicLabel = view.getMusicLabel();
        musicVolumeSlider.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("done");
                var val = musicVolumeSlider.getValue();
                if (val == 0) musicLabel.setIcon("off");
                else musicLabel.setIcon("on");
            }
        });
//        musicVolumeSlider.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                var val = musicVolumeSlider.getValue();
//                if (val == 0) musicLabel.setIcon("off");
//                else musicLabel.setIcon("on");
//            }
//        });

        JComboBox qualityCombo = view.getQualityCombo();
        ChangebleIcon qualityLabel = view.getQualityLabel();
        qualityCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var val = (GraphicQuality) qualityCombo.getSelectedItem();
                if (val == GraphicQuality.HIGH) qualityLabel.setIcon("high");
                else qualityLabel.setIcon("low");
            }
        });
        VolumeSlider effectsVolumeSlider = view.getEffectsVolumeSlider();
        ChangebleIcon effectsLabel = view.getEffectsLabel();
        effectsVolumeSlider.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("done");
                var val = effectsVolumeSlider.getValue();
                if (val == 0) effectsLabel.setIcon("off");
                else if (val < 40) effectsLabel.setIcon("low");
                else effectsLabel.setIcon("high");
            }
        });
        effectsVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                var val = effectsVolumeSlider.getValue();
                if (val == 0) effectsLabel.setIcon("off");
                else if (val < 40) effectsLabel.setIcon("low");
                else effectsLabel.setIcon("high");
            }
        });

    }
    public void setReturnPanel(MainFrameController.Panels returnPanel)
    {
        this.returnPanel = returnPanel;
        view.getQuitButton().setVisible(returnPanel == MainFrameController.Panels.GAME);
    }
    public void setVisible(boolean visible){ view.setVisible(visible); }
    public SettingsPanel getView() {
        return view;
    }
}
