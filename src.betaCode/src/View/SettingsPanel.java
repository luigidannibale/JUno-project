package View;

import Controller.MainFrameController;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SettingsPanel extends ResizablePanel
{
    private static final String imagePath = "resources/images/MainFrame/SettingsPanel/";
    private static final String deckPath = "resources/images/";

    private final Color verde = new Color(0, 231, 172);

    //JComboBox<MainFrame.Dimensions> combo;
    JLabel saveButton;
    JLabel closeButton;
    JLabel quit;
    ImageComponent effectsLabel;
    VolumeSlider effectsVolumeSlider;
    ImageComponent musicLabel;
    VolumeSlider musicVolumeSlider;

    private boolean whiteOn = true;
    private final BufferedImage whiteImage = Utils.getBufferedImage(deckPath + "White_deck/01.png");
    private final BufferedImage darkImage = Utils.getBufferedImage(deckPath + "Dark_deck/01.png");

    public SettingsPanel(MainFrameController mainFrame)
    {
        super(1008, 506, 6);
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());

        setOpaque(false);
        InitializeComponents();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
    }

    private void InitializeComponents(){
        //Components
        effectsLabel = new ImageComponent(imagePath+"Effectsvolumeon.png", -1, -1, false);
        musicLabel = new ImageComponent(imagePath+"Musicvolume.png", -1, -1, false);

        quit = new JLabel("ESCI DA QUA SALVATI");
        saveButton = new JLabel(new ImageIcon(imagePath+"save.png"));
        closeButton = new JLabel(new ImageIcon(imagePath+"discard.png"));

        effectsVolumeSlider = new VolumeSlider(mainFrame.soundEffects.getVolume());
        effectsVolumeSlider.setChangebleIcon(effectsLabel,imagePath+"EffectsVolume/", new String[] {"off.png", "low.png", "high.png"});
        musicVolumeSlider = new VolumeSlider(mainFrame.backMusic.getVolume());

        DeckRectangle whiteDeck = new DeckRectangle(whiteImage, "White Deck");
        DeckRectangle darkDeck = new DeckRectangle(darkImage, "Dark Deck");

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------First line
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        add(effectsLabel, gbc);

        gbc.gridx = 2;      gbc.gridy = 0;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(effectsVolumeSlider, gbc);

        //------------Second line
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(musicLabel, gbc);

        gbc.gridx = 2;      gbc.gridy = 1;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(musicVolumeSlider, gbc);

        //------------Third line
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(whiteDeck, gbc);

        gbc.gridx = 1;      gbc.gridy = 2;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(darkDeck, gbc);

        //------------Fourth line
        gbc.gridx = 1;      gbc.gridy = 3;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(quit, gbc);

        gbc.gridx = 2;      gbc.gridy = 3;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        add(saveButton, gbc);

        gbc.gridx = 3;      gbc.gridy = 3;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        add(closeButton, gbc);
    }

    public JLabel getSaveButton(){
        return saveButton;
    }

    public JLabel getCloseButton(){
        return closeButton;
    }

    public JLabel getQuitButton(){
        return quit;
    }

    public VolumeSlider getEffectsVolumeSlider(){
        return effectsVolumeSlider;
    }

    public VolumeSlider getMusicVolumeSlider(){
        return musicVolumeSlider;
    }

    public boolean isWhiteOn() {
        return whiteOn;
    }

    public void setWhiteOn(boolean whiteOn) {
        this.whiteOn = whiteOn;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(verde);
        g.fillRoundRect(0, 0, panelWidth, panelHeight, 15, 15);

        //Graphics2D g2 = (Graphics2D) g;
        //Utils.applyQualityRenderingHints(g2);
        //int y = musicLabel.getY() + musicLabel.getHeight() + 30;
        //paintDeckRectangle(g2, whiteImage, effectsLabel.getX(), y, "White Deck", whiteOn);
        //paintDeckRectangle(g2, darkImage, effectsLabel.getX() + 150, y, "Dark Deck", !whiteOn);
    }

    /*
    private void paintDeckRectangle(Graphics2D g, BufferedImage image, int x, int y, String title, boolean whiteOn){
        int width = 100;
        int height = 100;

        if (whiteOn) {
            g.setColor(Color.green);
            g.fillRect(x, y, width, height);
        }

        g.setColor(Color.black);
        g.setStroke(new BasicStroke(5));
        g.drawRect(x, y, width, height);

        int immWidth = 36;
        int immHeight = 54;
        int centerX = width / 2;
        int centerY = height / 2;
        g.drawImage(image, x + centerX - (immWidth / 2), y + centerY - (immHeight / 4), immWidth, immHeight, null);

        g.setFont(font);
        int fontWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, x + ((width - fontWidth) / 2), y + 20);
    }

     */

    private class DeckRectangle extends JPanel{
        private static int width = 100;
        private static int height = 100;

        private final BufferedImage image;
        private final String title;
        private final Font font = new Font("Digital-7", Font.BOLD, 15);
        private Color deckRectangleBorder = Color.black;

        public DeckRectangle(BufferedImage image, String title){
            this.image = image;
            this.title = title;
            setSize(width, height);
            setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            Utils.applyQualityRenderingHints(g2);
            if (whiteOn) {
                g.setColor(Color.green);
                g.fillRect(0, 0, width, height);
            }

            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(0, 0, width, height);

            int immWidth = 36;
            int immHeight = 54;
            int centerX = width / 2;
            int centerY = height / 2;
            g2.drawImage(image, centerX - (immWidth / 2), centerY - (immHeight / 4), immWidth, immHeight, null);

            g2.setFont(font);
            int fontWidth = g.getFontMetrics().stringWidth(title);
            g2.drawString(title, (width - fontWidth) / 2, 20);
        }
    }
}

