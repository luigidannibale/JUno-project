package View;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
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
    private static final String pathDeck = "resources/images/White_deck/";
    //private final Image img;
    private BufferedImage img;

    private boolean covered = false;

    public static final int width = 120;
    public static final int height = 180;

    private int x;
    private int y;
    private Rectangle position;
    private int offsetY = 0;

    private CardColor color;
    private CardValue value;

    public CardImage(){
        img = null;
    }

    public CardImage(CardColor color, CardValue value) {
        this(color, value, 0);
    }

    public CardImage(CardColor color, CardValue value, int rotation) {
        this.color = color;
        this.value = value;
        int num = 0;
        if (color != CardColor.WILD) num = color.VALUE * 14 + value.ordinal() + 1;
        else num = value == CardValue.WILD ? 14 : 14 * 5;
        String numero = String.format("%02d", num) + ".png";

        //img = Utils.getImage(pathDeck + numero);
        img = Utils.getBufferedImage(pathDeck + numero);
        if(rotation != 0) {
            img = Utils.rotateImage(backCard, rotation);

        }
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

    public boolean isInMouse(int x, int y){
        return position != null && position.contains(x, y);
    }

    @Override
    public String toString() {
        return value + " " + color;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
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
}
