package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class SettingsPanel extends ResizablePanel
{
    private static final String imagePath = "resources/images/MainFrame/SettingsPanel/";

    JComboBox<MainFrame.Dimensions> combo;
    JLabel saveButton;
    JLabel closeButton;
    VolumeSlider effectsVolumeSlider;
    VolumeSlider musicVolumeSlider;

    public SettingsPanel(MainFrame mainFrame)
    {
        super(mainFrame);
        //setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setLayout(new GridBagLayout());
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.57,0.60});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.70,0.55});
            put(MainFrame.Dimensions.HD, new Double[]{0.60,0.55});
        }};
        offset = 6;
        addScalingListener();
        setOpaque(false);
        InitializeComponents();
    }

    private void InitializeComponents(){
        //Components
        JLabel effectsLabel = new JLabel(new ImageIcon(imagePath+"Effectsvolumeon.png"));
        JLabel musicLabel = new JLabel(new ImageIcon(imagePath+"Musicvolume.png"));
        JLabel screenLabel = new JLabel(new ImageIcon(imagePath+"Screensize.png"));

        saveButton = new JLabel(new ImageIcon(imagePath+"save.png"));
        closeButton = new JLabel(new ImageIcon(imagePath+"discard.png"));

        effectsVolumeSlider = new VolumeSlider(mainFrame.soundEffects.getVolume());
        effectsVolumeSlider.setChangebleIcon(effectsLabel, new String[] {"Effectsvolumeoff.png", "Effectsvolumelow.png", "Effectsvolumeon.png"});
        musicVolumeSlider = new VolumeSlider(mainFrame.backMusic.getVolume());

        combo = new JComboBox<>(MainFrame.Dimensions.values());

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------First line
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(effectsLabel, gbc);

        gbc.gridx = 1;      gbc.gridy = 0;
        gbc.weightx = 0.1;  gbc.weighty = 0.5;
        add(effectsVolumeSlider, gbc);

        //------------Second line
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(musicLabel, gbc);

        gbc.gridx = 1;      gbc.gridy = 1;
        gbc.weightx = 0.1;  gbc.weighty = 0.5;
        add(musicVolumeSlider, gbc);

        //------------Third line
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(screenLabel, gbc);

        gbc.gridx = 1;      gbc.gridy = 2;
        gbc.weightx = 0.1;  gbc.weighty = 0.5;
        add(combo, gbc);

        //------------Fourth line
        gbc.gridx = 1;      gbc.gridy = 3;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        add(saveButton, gbc);

        gbc.gridx = 2;      gbc.gridy = 3;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        add(closeButton, gbc);
    }

    public JComboBox<MainFrame.Dimensions> getCombobox(){
        return combo;
    }

    public JLabel getSaveButton(){
        return saveButton;
    }

    public JLabel getCloseButton(){
        return closeButton;
    }

    public VolumeSlider getEffectsVolumeSlider(){
        return effectsVolumeSlider;
    }

    public VolumeSlider getMusicVolumeSlider(){
        return musicVolumeSlider;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color verde = new Color(0, 231, 172);
        g.setColor(verde);
        g.fillRoundRect(0, 0, panelWidth, panelHeight, 15, 15);
    }
}

