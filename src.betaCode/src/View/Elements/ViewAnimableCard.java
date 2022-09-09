package View.Elements;

import Model.Cards.Card;

import java.awt.*;

/**
 * Customize the {@link ViewRotatableCard} to add a shift {@link Dimension} used when drawing.
 * Primarily used to lift the cards of the user when hovered.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ViewAnimableCard extends ViewRotatableCard
{
    private Dimension shiftAmount = new Dimension(0,0);

    /**
     * Creates the {@link ViewAnimableCard} of the given {@link Card} with its rotation
     * @param c
     * @param rotation
     */
    public ViewAnimableCard(Card c, int rotation) { super(c,rotation); }

    //GETTERS
    public double getShiftHeight() { return shiftAmount.getHeight(); }
    public double getShiftWidth() { return shiftAmount.getWidth(); }

    //SETTERS
    public void setShiftHeight(double shiftHeight) { shiftAmount.setSize(shiftAmount.getWidth(),shiftHeight); }
    public void setShiftWidth(double shiftWidth) { shiftAmount.setSize(shiftWidth,shiftAmount.getHeight()); }

}
