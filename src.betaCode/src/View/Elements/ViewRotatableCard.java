package View.Elements;

import Model.Cards.Card;

/**
 * Customize the {@link ViewCard} to provide the rotation needed by the {@link ViewCard} when displayed on screen.
 * Primarily used to know how to rotate the image based on which hand on screen it has to be drawn.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ViewRotatableCard extends ViewCard
{
    private int rotation;

    /**
     * Creates a new {@link ViewRotatableCard} with the default BACK_CARD image.
     */
    public ViewRotatableCard() { super(); }

    /**
     * Creates a new {@link ViewRotatableCard} of the given {@link Card} and no rotation
     * @param c
     */
    public ViewRotatableCard(Card c){ this(c, 0); }

    /**
     * Creates a new {@link ViewRotatableCard} of the given {@link Card} and the given rotation
     * If the rotation is not 0, then the painted image is chosen between the rotated back_card images based on the rotation.
     * @param c
     * @param rotation
     */
    public ViewRotatableCard(Card c, int rotation)
    {
        super(c);
        this.rotation = rotation;

        switch (rotation){
            case 90 -> paintedImage = BACK_ROTATED_90;
            case 180 -> paintedImage = BACK_CARD;
            case 270 -> paintedImage = BACK_ROTATED_270;
            default -> paintedImage = cardImage;
        }
    }

    //GETTER
    public int getRotation(){ return rotation; }
}
