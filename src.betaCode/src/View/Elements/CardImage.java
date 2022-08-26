package View.Elements;

import Model.Cards.Card;
import Model.Cards.CardColor;
import Model.Cards.CardValue;
import Utilities.Config;
import Utilities.ConfigDeprecated;
import Utilities.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CardImage {
    public static final BufferedImage backCard = Utils.getBufferedImage("resources/images/Back_card.png");
    public static final BufferedImage backLeft = Utils.rotateImage(backCard, 270);
    public static final BufferedImage backRight = Utils.rotateImage(backCard, 90);
    private static final String path = "resources/images/";
    //private static DeckColor deck = Config.defaultColor;
    //private final Image img;
    private BufferedImage img;
    private BufferedImage drawImage;

    //private boolean covered = false;

    public static int width = 120;
    public static int height = 180;

    private Rectangle position;
    private int offsetY = 0;
    private int rotation;
    private Card card;

    public CardImage(){
        img = backCard;
    }

    public CardImage(Card card) {
        this(card, 0);
    }

    public CardImage(Card card, int rotation)
    {
        this.card = card;
        this.rotation = rotation;
        newGetCard();

        switch (rotation){
            case 90 -> drawImage = backRight;
            case 180 -> drawImage = backCard;
            case 270 -> drawImage = backLeft;
            default -> drawImage = img;
        }
        //position = new Rectangle();
    }

    /*
    public Image getImage() {
        return img;
    }
     */

    public BufferedImage getCardImage() {
        return img;
    }

    public BufferedImage getDrawImage() {
        return drawImage;
    }

    public void setDrawImage(BufferedImage drawedImage) {this.drawImage = drawedImage;}

    public BufferedImage getBackCard(){return backCard;}

    public void setPosition(int x, int y, int width){
        setPosition(x, y, width, false);
    }

    public void setPosition(int x, int y, int width, boolean rotated){
        position = rotated ? new Rectangle(x, y, height, width) : new Rectangle(x, y, width, height);
        //if (rotated) position.setRect(x, y, height, width);
        //else position.setRect(x, y, width, height);
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

    public int getRotation(){
        return rotation;
    }

    @Deprecated
    private void oldGetCard(){
        int num = 0;
        if (card.getColor() != CardColor.WILD) num = card.getColor().VALUE * 14 + card.getValue().ordinal() + 1;
        else num = card.getValue() == CardValue.WILD ? 14 : 14 * 5;
        String numero = String.format("%02d", num) + ".png";

        img = Utils.getBufferedImage(path + Config.deckStyle + "/" + numero);
    }

    private void newGetCard(){
        String cardName = card.getValue().name() + card.getColor().name() + ".png";
        img = Utils.getBufferedImage(path + Config.deckStyle + "/" + cardName);
    }
}
