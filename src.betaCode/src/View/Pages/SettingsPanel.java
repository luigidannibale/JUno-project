package View.Pages;

import View.Utils;
import View.Elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class SettingsPanel extends ResizablePanel
{
    private final String pathImages = MainFrame.IMAGE_PATH +"SettingsPanel/";
    private final Color green = new Color(0, 231, 172);
    private final Font font = new Font("Digital-7", Font.BOLD, 18);

    private GifComponent saveButton;
    private GifComponent closeButton;
    private ImageComponent gameOver;
    private ChangebleIcon effectsLabel;
    private ViewSlider effectsViewSlider;
    private ChangebleIcon musicLabel;
    private ViewSlider musicViewSlider;
    private ImageComponent deckStyleLabel;
    private DeckRectangle darkDeck;
    private DeckRectangle whiteDeck;
    private ChangebleIcon qualityLabel;
    private JComboBox<GraphicQuality> qualityCombo;

    public SettingsPanel()
    {
        super(900, 600,0);
        setLayout(new GridBagLayout());
        setOpaque(false);
        setVisible(false);
        InitializeComponents();
        resizeComponents();
    }

    /**
     * Initializes and adds the components of the {@link SettingsPanel}
     */
    private void InitializeComponents()
    {
        //LABELS
        effectsLabel = new ChangebleIcon(pathImages +"EffectsVolume/",new String[]{"off","low","high"},".png");
        effectsLabel.setName("effectsLabel");
        musicLabel = new ChangebleIcon(pathImages +"MusicVolume/",new String[]{"off","on"},".png");
        musicLabel.setName("musicLabel");
        deckStyleLabel = new ImageComponent(pathImages +"DeckStyle.png");
        qualityLabel = new ChangebleIcon(pathImages +"GraphicQuality/",new String[]{"low","high"},".png");
        qualityLabel.setName("qualityLabel");

        //BUTTONS
        gameOver = new ImageComponent(pathImages + "exit_game.png", 118, 118);
        saveButton = new GifComponent(pathImages + "save");
        closeButton =new GifComponent(pathImages + "discard");

        //VOLUME SLIDER
        effectsViewSlider = new ViewSlider();
        effectsViewSlider.addMouseListener();
        musicViewSlider = new ViewSlider();
        musicViewSlider.addMouseListener();

        //DECK RECTANGLE
        whiteDeck = new DeckRectangle(pathImages +"white.png", "White Deck", DeckColor.WHITE);
        darkDeck = new DeckRectangle(pathImages +"black.png", "Dark Deck", DeckColor.BLACK);

        //COMBO BOX
        qualityCombo = new JComboBox<>();
        Arrays.stream(GraphicQuality.values()).forEach(g -> qualityCombo.addItem(g));
        qualityCombo.setPreferredSize(new Dimension(190, 28));
        qualityCombo.setFont(font);
        ((JLabel) qualityCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------First line
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        add(effectsLabel, gbc);

        gbc.gridx = 2;      gbc.gridy = 0;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(effectsViewSlider, gbc);

        //------------Second line
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.2;  gbc.weighty = 0.5;
        add(musicLabel, gbc);

        gbc.gridx = 2;      gbc.gridy = 1;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(musicViewSlider, gbc);

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
        gbc.gridx = 0;      gbc.gridy = 4;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(gameOver, gbc);

        gbc.gridx = 2;      gbc.gridy = 4;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        gbc.gridx = 3;      gbc.gridy = 4;
        gbc.weightx = 0.05; gbc.weighty = 0.1;
        add(closeButton, gbc);
    }

    //GETTERS
    public JLabel getSaveButton(){ return saveButton; }
    public JLabel getCloseButton(){ return closeButton; }
    public JLabel getGameOverButton(){ return gameOver; }
    public ViewSlider getEffectsVolumeSlider(){ return effectsViewSlider; }
    public ViewSlider getMusicVolumeSlider(){ return musicViewSlider; }
    public DeckRectangle getWhiteDeck() { return whiteDeck; }
    public DeckRectangle getDarkDeck() { return darkDeck; }
    public JComboBox<GraphicQuality> getQualityCombo() { return qualityCombo; }
    public ChangebleIcon getEffectsLabel() { return effectsLabel; }
    public ChangebleIcon getMusicLabel() { return musicLabel; }
    public ChangebleIcon getQualityLabel() { return qualityLabel; }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(green);
        g.fillRoundRect(0, 0, panelWidth, panelHeight, 15, 15);
    }

    /**
     * Custom JComponent
     * @author D'annibale Luigi, Venturini Daniele
     */
    public class DeckRectangle extends JComponent
    {
        private static int width = 114;
        private static int height = 114;
        private static int radius = 42;

        private final BufferedImage image;
        private final String title;
        private final DeckColor deckColor;
        private Color deckRectangleBorder = Color.BLACK;
        private boolean paintBackground = false;

        /**
         * Initializes the {@link DeckRectangle} given the image to add at the center, the title of the component, and the related {@link DeckColor}.
         * It gets highlighted when hovered.
         * @param imagePath
         * @param title
         * @param deckColor
         */
        public DeckRectangle(String imagePath, String title, DeckColor deckColor)
        {
            this.title = title;
            this.deckColor = deckColor;
            image = Utils.getBufferedImage(imagePath);
            setSize(width, height);
            setPreferredSize(new Dimension(width, height));
            setOpaque(false);

            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    deckRectangleBorder = Color.ORANGE;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    deckRectangleBorder = Color.BLACK;
                    repaint();
                }
            });
        }

        /**
         * Sets the flag related to the color of the background.
         * The background is green if true, otherwise transparent
         * @param paintBackground
         */
        public void setPaintBackground(boolean paintBackground)
        {
            this.paintBackground = paintBackground;
            repaint();
        }

        public DeckColor getDeckColor() { return deckColor; }

        @Override
        public void setPreferredSize(Dimension preferredSize) {
            super.setPreferredSize(preferredSize);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            Utils.applyQualityRenderingHints(g2);

            if (paintBackground)
            {
                g.setColor(Color.GREEN);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            }

            g2.setColor(deckRectangleBorder);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth(), getHeight(),radius, radius);

            int immWidth = getWidth() * 37 / 100;
            int immHeight = getHeight() * 55 / 100;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            g2.drawImage(image, centerX - (immWidth / 2), centerY - (immHeight / 4), immWidth, immHeight, null);

            g2.setFont(font.deriveFont(getWidth() * 16 / 100.0f));
            int fontWidth = g.getFontMetrics().stringWidth(title);
            g2.drawString(title, (getWidth() - fontWidth) / 2, 25);
        }
    }
}

