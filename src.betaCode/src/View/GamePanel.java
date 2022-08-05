package View;

import Model.Cards.Card;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Player.Player;
import Model.UnoGame;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

public class GamePanel extends JPanel implements Observer {

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";

    private final int maxCardsWidth = 1350;
    private final int maxCardsHeight = 800;

    private UnoGame model;

    private Player[] players;

    public GamePanel(UnoGame model){
        this.model = model;          //server per prendere dati
        setOpaque(true);
        //debug
        setBackground(Color.GREEN);

        InitializeComponents();
    }

    private void InitializeComponents(){
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
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height, false);
            drawHorizontalHand(players[2], g2, 0, true);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.height, true);
            drawVerticalHand(players[3], g2, 0, true);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            var discard = model.getLastCard();
            g2.drawImage(new CardImage(discard.getColor(), discard.getValue()).getImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);
            g2.drawImage(CardImage.backCard, centerX - 25 - CardImage.width, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);

            g2.setColor(Color.black);
            g2.drawLine(centerX - 5, centerY, centerX + 5, centerY);
        }

        g2.dispose();
    }

    //trying to generalize
    public void drawHand(Player player, int maxSpace, int fullSpace, Graphics2D g2, int y){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxSpace);
        int startX = (fullSpace - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();
    }

    Function<CardImage, Image> getCard = CardImage::getImage;
    Function<CardImage, Image> getBackCard = CardImage::getBackCard;

    private void drawHorizontalHand(Player player, Graphics2D g2, int y, boolean covered){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        Function<CardImage, Image> drawCard = covered ? getBackCard : getCard;

        for (Card card : player.getHand()) {
            var image = new CardImage(card.getColor(), card.getValue());
            if (y == 0) g2.drawImage(drawCard.apply(image), startX + CardImage.width, y + CardImage.height, -CardImage.width, -CardImage.height, this);
            else        g2.drawImage(drawCard.apply(image), startX, y, CardImage.width, CardImage.height, null);
            startX += cardsWidth;
        }

        drawNames(player.getName(), startX, y == 0 ? CardImage.height + 30 : y - 30, g2);
    }

    private  void drawVerticalHand(Player player, Graphics2D g2, int x, boolean covered){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        drawNames(player.getName(), x == 0 ? CardImage.width + 30 : x - 30, startY, g2);

        Function<CardImage, Image> drawCard = covered ? getBackCard : getCard;

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
            g2.drawImage(Utils.rotateImage(drawCard.apply(image), rotationRequired), x, startY, CardImage.height, CardImage.width, null);

            startY += cardsWidth;
        }
    }

    private void drawNames(String name, int x, int y, Graphics g2){
        g2.setFont(new Font("Digital-7", Font.PLAIN, 25));
        g2.setColor(Color.black);
        g2.drawString(name, x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Piero");

        UnoGame model = (UnoGame)o;
        this.players = model.getPlayers();
    }
}

