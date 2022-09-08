package View.Elements;

import Model.Cards.Card;
import Controller.Utilities.Config;
import View.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewCard
{
    public static final BufferedImage BACK_CARD = Utils.getBufferedImage("resources/images/Back_card.png");
    public static final BufferedImage BACK_ROTATED_270 = Utils.rotateImage(BACK_CARD, 270);
    public static final BufferedImage BACK_ROTATED_90 = Utils.rotateImage(BACK_CARD, 90);
    private static final String path = "resources/images/";

    private BufferedImage cardImage;
    private BufferedImage paintedImage;

    public static int width = 120;

    public static int height = 180;

    private Rectangle position = new Rectangle();
    //private int offsetY = 0;

    private int rotation;
    private Card card;

    //debug
    boolean carteScoperte = true;


    public ViewCard(){ cardImage = BACK_CARD; }

    public ViewCard(Card card) { this(card, 0); }

    public ViewCard(Card card, int rotation)
    {
        this.card = card;
        this.rotation = rotation;
        buildCard();

        if (carteScoperte){ rotation = 0; }
        switch (rotation){
            case 90 -> paintedImage = BACK_ROTATED_90;
            case 180 -> paintedImage = BACK_CARD;
            case 270 -> paintedImage = BACK_ROTATED_270;
            default -> paintedImage = cardImage;
        }

    }

    public BufferedImage getCardImage() { return cardImage; }

    public BufferedImage getPaintedImage() { return paintedImage; }

    public void setPaintedImage(BufferedImage drawedImage) {this.paintedImage = drawedImage;}

    public BufferedImage getBackCard(){return BACK_CARD;}

    public void setPosition(int x, int y, int width){ setPosition(x, y, width, false); }

    public void setPosition(int x, int y, int width, boolean rotated){ position.setRect(rotated ? new Rectangle(x, y, height, width) : new Rectangle(x, y, width, height)); }

    public Rectangle getPosition() {return position;}

    public boolean contains(Point point){ return position != null && position.contains(point); }

    @Override
    public String toString() { return card.toString(); }

    public Card getCard(){ return card; }

    public void setCard(Card card){
        this.card = card;
        buildCard();
    }

    public int getRotation(){ return rotation; }

    private void buildCard() { cardImage = Utils.getBufferedImage(path + Config.deckStyle + "/" + card.getValue().name() + card.getColor().name() + ".png"); }
}
