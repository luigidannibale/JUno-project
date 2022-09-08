package View.Elements;

import Model.Cards.Card;

public class ViewRotatableCard extends ViewCard
{
    private int rotation;

    public ViewRotatableCard() { super(); }

    public ViewRotatableCard(Card c){ this(c, 0); }

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

    public int getRotation(){ return rotation; }
}
