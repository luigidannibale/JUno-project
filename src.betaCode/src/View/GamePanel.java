package View;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;

public class GamePanel extends ResizablePanel{

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";

    public GamePanel(MainFrame mainFrame){
        super(new ImageIcon(imagePath+"background.png").getImage(), mainFrame);

        setLayout(new BorderLayout());
        Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{1.0, 1.0});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.97, 0.95});
            put(MainFrame.Dimensions.HD, new Double[]{1.0, 1.0});
        }};
        offset = 0;
        addScalingListener();
        setOpaque(true);
        //debug
        setBackground(Color.GREEN);

        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents(){
        /*
        forse quasi meglio creare una classe per i panel dei player
                        top player
        left player     deckPanel        right player
                        bot player --> human player
         */
        /*
        JPanel topPlayer = new JPanel();
        JPanel leftPlayer = new JPanel();

        JPanel rightPlayer = new JPanel();
        JPanel botPlayer = new JPanel();*/
        JPanel[] panels = new JPanel[4];
        String[] b = new String[]{BorderLayout.NORTH, BorderLayout.WEST, BorderLayout.EAST, BorderLayout.SOUTH};
        final int[] i = {0};
        Arrays.stream(panels).forEach(p -> {
            p = new JPanel();
            p.setBorder(new LineBorder(Color.BLACK));
            p.setOpaque(false);
            add(p, b[i[0]]);
            i[0] += 1;
        });

        JPanel deckPanel = new JPanel();
        deckPanel.setBorder(new LineBorder(Color.BLACK));
        deckPanel.setOpaque(false);


        //Arrays.stream(panels).forEach(this::add);
        /*
        add(topPlayer, BorderLayout.NORTH);
        add(leftPlayer, BorderLayout.WEST);
        add(deckPanel, BorderLayout.CENTER);
        add(rightPlayer, BorderLayout.EAST);
        add(botPlayer, BorderLayout.SOUTH);*/
        var a = new CardImage(CardColor.BLUE, CardValue.THREE);
        var c = new CardImage(CardColor.RED, CardValue.EIGHT);
        var d = new CardImage(CardColor.GREEN, CardValue.FOUR);
        var e = new CardImage(CardColor.YELLOW, CardValue.FIVE);
        var f = new CardImage(CardColor.WILD, CardValue.WILD);
        var g = new CardImage(CardColor.WILD, CardValue.WILD_DRAW);

        deckPanel.add(a);
        deckPanel.add(c);
        deckPanel.add(d);
        deckPanel.add(e);
        deckPanel.add(f);
        deckPanel.add(g);

        add(deckPanel, BorderLayout.CENTER);
    }

    public class CardImage extends JLabel{
        private static final Image backCard = new ImageIcon("resources/images/Back_card.png").getImage();
        private static final String pathDeck = "resources/images/White_deck/";
        private final Image img;

        private boolean covered = false;

        private final int width;
        private final int height;

        //o ruotiamo tutte le immagini o ruotiamo l'intero panel
        //che non sarebbe male
        private int rotation;

        public CardImage(CardColor color, CardValue value){
            int num = 0 ;
            if (color != CardColor.WILD) num = color.getIntValue() * 14 + value.ordinal() + 1;
            else num = value == CardValue.WILD ? 14 : 14 * 5;
            String numero = String.format("%02d", num) + ".png";
            System.out.println(pathDeck + numero);

            img = new ImageIcon(pathDeck + numero).getImage();
            width = img.getWidth(null) * 50 / 100;
            height = img.getHeight(null) * 50 / 100;

            setPreferredSize(new Dimension(width, height));
            setSize(new Dimension(width, height));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    covered = !covered;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(covered ? img : backCard, 0, 0, width, height, null);
        }

        @Override
        public Dimension getPreferredSize() { return (new Dimension(width, height)); }
    }
}
