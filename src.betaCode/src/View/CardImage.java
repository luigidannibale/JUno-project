package View;

import Controller.MainFrameController;
import Model.Cards.Card;
import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
import Utilities.Config;
import Utilities.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.spec.ECField;

public class CardImage extends JComponent {
    public static final BufferedImage backCard = Utils.getBufferedImage("resources/images/Back_card.png");
    public static final BufferedImage backLeft = Utils.rotateImage(backCard, 90);
    public static final BufferedImage backRight = Utils.rotateImage(backCard, 270);
    private static final String path = "resources/images/";
    //private static DeckColor deck = Config.defaultColor;
    //private final Image img;
    private BufferedImage img;

    //private boolean covered = false;

    public static final int width = 120;
    public static final int height = 180;

    private int x;
    private int y;
    private Rectangle position;
    private int offsetY = 0;

    private Card card;

    public CardImage(){
        img = null;
    }

    public CardImage(Card card) {
        this(card, 0);
    }

    public CardImage(Card card, int rotation) {
        this.card = card;

        if (rotation == 90) img = backLeft;
        else if (rotation == 270) img = backRight;
        else newGetCard();
    }

    /*
    public Image getImage() {
        return img;
    }
     */

    public BufferedImage getImage() {
        return img;
    }

    public BufferedImage getBackCard(){return backCard;}

    public void setPosition(int x, int y, int width){
        setPosition(x, y, width, false);
    }

    public void setPosition(int x, int y, int width, boolean rotated){
        this.x = x;
        this.y = y;
        position = rotated ? new Rectangle(x, y, height, width) : new Rectangle(x, y, width, height);
    }

    public Rectangle getPosition() {return position;}

    public boolean isInMouse(int x, int y){
        return position != null && position.contains(x, y);
    }

    @Override
    public String toString() {
        return card.toString();
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public Card getCard(){
        return card;
    }
    /*
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            Utils.applyQualityRenderingHints(g2);
            g2.drawImage(covered ? img : backCard, 0, 0, width, height, null);
        }

         */

    private void oldGetCard(){
        int num = 0;
        if (card.getColor() != CardColor.WILD) num = card.getColor().VALUE * 14 + card.getValue().ordinal() + 1;
        else num = card.getValue() == CardValue.WILD ? 14 : 14 * 5;
        String numero = String.format("%02d", num) + ".png";

        img = Utils.getBufferedImage(path + Config.defaultColor + "/" + numero);
    }

    private void newGetCard(){
        String cardName = card.getValue().name() + card.getColor().name() + ".png";
        img = Utils.getBufferedImage(path + Config.defaultColor + "/" + cardName);
    }
}
