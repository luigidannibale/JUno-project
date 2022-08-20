package View;

import Utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SettingsPanel extends ResizablePanel
{
    private static final String deckPath = "resources/images/";

    private final Color green = new Color(0, 231, 172);
    private final Font font = new Font("Digital-7", Font.BOLD, 18);

    private GifComponent saveButton;
    private GifComponent closeButton;
    private JLabel quit;
    private ImageComponent effectsLabel;
    private VolumeSlider effectsVolumeSlider;
    private ImageComponent musicLabel;
    private VolumeSlider musicVolumeSlider;
    private ImageComponent deckStyleLabel;
    private DeckRectangle darkDeck;
    private DeckRectangle whiteDeck;
    private ImageComponent qualityLabel;
    private JComboBox<String> qualityCombo;

    public SettingsPanel()
    {
        super(900, 600, 6);
        imagePath = "resources/images/MainFrame/SettingsPanel/";
        setLayout(new GridBagLayout());
        setOpaque(false);
        setVisible(false);

        InitializeComponents();
    }

    private void InitializeComponents(){
        //Components
        effectsLabel = new ImageComponent(imagePath+"EffectsVolume/high.png", -1, -1, false);
        musicLabel = new ImageComponent(imagePath+"MusicVolume.png", -1, -1, false);
        deckStyleLabel = new ImageComponent(imagePath+"DeckStyle.png", -1, -1, false);
        qualityLabel = new ImageComponent(imagePath+"GraphicQuality/high.png", -1, -1, false);

        quit = new JLabel("ESCI DA QUA SALVATI");
        saveButton = new GifComponent(imagePath + "save");
        closeButton =new GifComponent(imagePath + "discard");

        effectsVolumeSlider = new VolumeSlider();
        effectsVolumeSlider.setChangebleIcon(effectsLabel,imagePath+"EffectsVolume/", new String[] {"off.png", "low.png", "high.png"});
        musicVolumeSlider = new VolumeSlider();

        whiteDeck = new DeckRectangle(deckPath + "white/ZERORED.png", "White Deck");
        darkDeck = new DeckRectangle(deckPath + "black/ZERORED.png", "Dark Deck");

        qualityCombo = new JComboBox<>();
        Arrays.stream(GraphicQuality.values()).forEach(g -> qualityCombo.addItem(g.VALUE));
        qualityCombo.setPreferredSize(new Dimension(190, 28));
        qualityCombo.setFont(font);
        ((JLabel)qualityCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

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
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        add(deckStyleLabel, gbc);

        gbc.gridx = 2;      gbc.gridy = 2;
        gbc.weightx = 0.05;  gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        add(whiteDeck, gbc);

        gbc.gridx = 3;      gbc.gridy = 2;
        gbc.weightx = 0.05;  gbc.weighty = 0.5;
        add(darkDeck, gbc);

        //------------Fourth line
        gbc.gridx = 0;      gbc.gridy = 3;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        add(qualityLabel, gbc);

        gbc.gridx = 2;      gbc.gridy = 3;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(qualityCombo, gbc);

        //------------Fifth line
        gbc.gridx = 1;      gbc.gridy = 4;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(quit, gbc);

        gbc.gridx = 2;      gbc.gridy = 4;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        gbc.gridx = 3;      gbc.gridy = 4;
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

    public DeckRectangle getWhiteDeck() {
        return whiteDeck;
    }

    public DeckRectangle getDarkDeck() {
        return darkDeck;
    }
    public JComboBox getQualityCombo() {
        return qualityCombo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(green);
        g.fillRoundRect(0, 0, panelWidth, panelHeight, 15, 15);
    }

    public class DeckRectangle extends JPanel{
        private static int width = 114;
        private static int height = 114;
        private static int radius = 42;

        private final BufferedImage image;
        private final String title;
        private Color deckRectangleBorder = Color.BLACK;
        private boolean paintBackground = false;

        public DeckRectangle(String imagePath, String title){
            this.title = title;
            image = Utils.getBufferedImage(imagePath);
            setSize(width, height);
            setPreferredSize(new Dimension(width, height));
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    deckRectangleBorder = Color.ORANGE;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    deckRectangleBorder = Color.BLACK;
                    repaint();
                }
            });
        }

        public void setPaintBackground(boolean paintBackground) {
            this.paintBackground = paintBackground;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            Utils.applyQualityRenderingHints(g2);

            if (paintBackground) {
                g.setColor(Color.GREEN);
                g.fillRoundRect(0, 0, width, height, radius, radius);
            }

            g2.setColor(deckRectangleBorder);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, width, height,radius, radius);

            int immWidth = 42;
            int immHeight = 63;
            int centerX = width / 2;
            int centerY = height / 2;
            g2.drawImage(image, centerX - (immWidth / 2), centerY - (immHeight / 4), immWidth, immHeight, null);

            g2.setFont(font);
            int fontWidth = g.getFontMetrics().stringWidth(title);
            g2.drawString(title, (width - fontWidth) / 2, 25);
        }
    }
}

