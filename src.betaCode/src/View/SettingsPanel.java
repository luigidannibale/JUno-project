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

    private float effectVolumeChanges;
    private float musicVolumeChanges;
    private MainFrame.Dimensions dimesionChanges;

    public SettingsPanel(MainFrame mainFrame)
    {
        super(new ImageIcon(imagePath+"background.png").getImage(),mainFrame);
        //setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setLayout(new GridBagLayout());
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.57,0.60});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.70,0.55});
            put(MainFrame.Dimensions.HD, new Double[]{0.60,0.55});
        }};
        //setLayout(new BorderLayout());
        addScalingListener();
        setOpaque(true);

        dimesionChanges = mainFrame.getDimension();

        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents()
    {
        //Components
        JLabel imm = new JLabel(new ImageIcon(imagePath+"Effectsvolumeon.png"));
        JLabel imm2 = new JLabel(new ImageIcon(imagePath+"Musicvolume.png"));
        JLabel imm3= new JLabel(new ImageIcon(imagePath+"Screensize.png"));

        JLabel saveButton = new JLabel(new ImageIcon(imagePath+"save.png"));
        JLabel closeButton = new JLabel(new ImageIcon(imagePath+"discard.png"));

        VolumeSlider effectsVolumeSlider = new VolumeSlider();
        VolumeSlider musicVolumeSlider = new VolumeSlider();

        JComboBox<MainFrame.Dimensions> combo = new JComboBox<>(MainFrame.Dimensions.values());
        combo.setSelectedItem(mainFrame.getDimension());

        //Listener
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.updateSize(dimesionChanges);
                System.out.println(musicVolumeSlider.getValue());
                AudioManager.getInstance().setBackgroundVolume(musicVolumeSlider.getValue());
                //AudioManager.getInstance().setEffectVolume((effectsVolumeSlider.getValue()));
            }
        });
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(mainFrame.getStartingPanel());
            }
        });
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) { dimesionChanges = (MainFrame.Dimensions) e.getItem();}
        });

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------First line
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(imm, gbc);

        gbc.gridx = 1;      gbc.gridy = 0;
        gbc.weightx = 0.1;  gbc.weighty = 0.5;
        add(musicVolumeSlider, gbc);

        //------------Second line
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(imm2, gbc);

        gbc.gridx = 1;      gbc.gridy = 1;
        gbc.weightx = 0.1;  gbc.weighty = 0.5;
        add(effectsVolumeSlider, gbc);

        //------------Third line
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(imm3, gbc);

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
}

