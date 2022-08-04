package View;

import Model.Cards.Card;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Player.Player;
import Model.UnoGame;
import Utilities.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer {

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";

    private final int maxCardsWidth = 1350;
    private final int maxCardsHeight = 800;

    private UnoGame model;

    private Player[] players;

    public GamePanel(UnoGame model){
        this.model = model;          //server per prendere dati
        //setLayout(new BorderLayout());
        /*
        Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.97, 0.95});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.97, 0.95});
            put(MainFrame.Dimensions.HD, new Double[]{1.0, 1.0});
        }};

         */
        /*
        percentX = 1;
        percentY = 1;
        offset = 0;
        addScalingListener();

         */
        setOpaque(true);
        //debug
        setBackground(Color.GREEN);

        InitializeComponents();
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
        /*
        JPanel[] panels = new JPanel[4];
        String[] b = new String[]{BorderLayout.NORTH, BorderLayout.WEST, BorderLayout.EAST, BorderLayout.SOUTH};
        for (int i = 0; i < 4; i++){
            panels[i] = new JPanel();
            panels[i].setBorder(new LineBorder(Color.BLACK));
            panels[i].setOpaque(false);
            add(panels[i], b[i]);
        }

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


        /*panels[0].add(a);
        deckPanel.add(c);
        deckPanel.add(d);
        deckPanel.add(e);
        deckPanel.add(f);
        deckPanel.add(g);

        add(deckPanel, BorderLayout.CENTER);
         */

        //add(a);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color verde = new Color(14, 209, 69);
        g.setColor(verde);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        Utils.applyQualityRenderingHints(g2);

        if (players != null){
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height);
            drawHorizontalHand(players[2], g2, 0);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.width);
            drawVerticalHand(players[3], g2, 0);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            var discard = model.getLastCard();
            g2.drawImage(new CardImage(discard.getColor(), discard.getValue()).getImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);
            g2.drawImage(new CardImage().getImage(), centerX - 25 - CardImage.width, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);


            g2.setColor(Color.black);
            g2.drawLine(centerX - 5, centerY, centerX + 5, centerY);
        }
    }

    //trying to generalize
    public void drawHand(Player player, int maxSpace, int fullSpace, Graphics2D g2, int y){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxSpace);
        int startX = (fullSpace - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();
    }

    public void drawHorizontalHand(Player player, Graphics2D g2, int y){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        for (Card card : player.getHand()) {
            var image = new CardImage(card.getColor(), card.getValue());
            if (y == 0) g2.drawImage(image.getImage(), startX + CardImage.width, y + CardImage.height, -CardImage.width, -CardImage.height, null);
            else        g2.drawImage(image.getImage(), startX, y, CardImage.width, CardImage.height, null);
            startX += cardsWidth;
        }
    }

    public void drawVerticalHand(Player player, Graphics2D g2, int x){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        for (Card card : player.getHand()) {
            var image = new CardImage(card.getColor(), card.getValue());

            /*
            double rotationRequired = Math.toRadians (x == 0 ? 90 : 270);
            //int locationX = image.getWidth() / 2;
            //int locationY = image.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, image.getWidth(), image.getHeight());
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            g2.drawImage(op.filter(Utils.toBufferedImage(image.getImage()), null), x, startY, CardImage.height, CardImage.width, null);
             */
            int rotationRequired = x == 0 ? 90 : 270;
            g2.drawImage(Utils.rotateImage(image.getImage(), rotationRequired), x, startY, CardImage.width, CardImage.height, null);

            startY += cardsWidth;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Piero");

        UnoGame model = (UnoGame)o;
        this.players = model.getPlayers();
    }
}

