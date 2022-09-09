package View.Elements;

import Controller.Utilities.Config;
import Model.Cards.Card;
import View.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class used to provide the {@link Card} a graphic representation. It contains the default images for the normal and rotated BACK_CARD.
 * The class has two images, one for the painted image, that can be the BACK_CARD image, and one for the card image, related to the {@link Card}
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class ViewCard
{
    private static final String path = "resources/images/";
    public static final BufferedImage BACK_CARD = Utils.getBufferedImage(path + "Back_card.png");
    public static final BufferedImage BACK_ROTATED_270 = Utils.rotateImage(BACK_CARD, 270);
    public static final BufferedImage BACK_ROTATED_90 = Utils.rotateImage(BACK_CARD, 90);

    private final Rectangle position = new Rectangle();

    protected BufferedImage cardImage;
    protected BufferedImage paintedImage;

    public static int width = 120;
    public static int height = 180;

    private Card card;

    //debug
    boolean carteScoperte = true;

    /**
     * Creates a new {@link ViewCard} with the default BACK_CARD image.
     */
    public ViewCard(){ cardImage = BACK_CARD; }


    /**
     * Creates a new {@link ViewCard} of the given {@link Card}
     * @param card
     */
    public ViewCard(Card card)
    {
        this.card = card;
        buildCard();
    }

    /**
     * Gets the related image of the {@link Card} with the combination of the default path, the deck style, the value of the card + the color of the card and the extension .png. <br>
     * For example, DRAWRED.png
     */
    private void buildCard() { cardImage = Utils.getBufferedImage(path + Config.deckStyle + "/" + card.getValue().name() + card.getColor().name() + ".png"); }

    /**
     * Checks if the clicked point is in the card position
     * @param point the point clicked
     * @return true if the card has been clicked, false otherwise
     */
    public boolean contains(Point point){ return position.contains(point); }

    //GETTERS
    public Card getCard(){ return card; }
    public BufferedImage getCardImage() { return cardImage; }
    public BufferedImage getPaintedImage() { return paintedImage; }
    public Rectangle getPosition() {return position;}

    //SETTERS
    public void setCard(Card card){
        this.card = card;
        buildCard();
    }
    public void setPosition(int x, int y, int width){ setPosition(x, y, width, false); }
    public void setPosition(int x, int y, int width, boolean rotated){ position.setRect(rotated ? new Rectangle(x, y, height, width) : new Rectangle(x, y, width, height)); }
    public void setPaintedImage(BufferedImage drawedImage) {this.paintedImage = drawedImage;}

    @Override
    public String toString() { return card.toString(); }
}
