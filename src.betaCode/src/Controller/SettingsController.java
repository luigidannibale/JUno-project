package Controller;

import View.MainFrame;
import View.SettingsPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsController {

    SettingsPanel view;

    MainFrame.Dimensions dimesionChanges;

    public SettingsController(MainFrameController mainFrame){
        view = new SettingsPanel(mainFrame);

        dimesionChanges = mainFrame.getCurrentDimension();

        view.getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.updateSize(dimesionChanges);
                mainFrame.backMusic.setVolume(view.getMusicVolumeSlider().getValue());
                mainFrame.backMusic.setFloatControlVolume();
                mainFrame.soundEffects.setVolume(view.getEffectsVolumeSlider().getValue());
                mainFrame.soundEffects.setFloatControlVolume();
                //AudioManager.getInstance().setEffectVolume((effectsVolumeSlider.getValue()));
            }
        });
        view.getCloseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.setVisiblePanel(MainFrameController.Panels.STARTMENU);
                view.setVisible(false);
            }
        });
        view.getCombobox().setSelectedItem(mainFrame.getCurrentDimension());
        view.getCombobox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) { dimesionChanges = (MainFrame.Dimensions) e.getItem();}
        });
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }

    public SettingsPanel getView() {
        return view;
    }
}
