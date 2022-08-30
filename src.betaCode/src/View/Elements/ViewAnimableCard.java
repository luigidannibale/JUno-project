package View.Elements;

import Model.Cards.Card;

import java.awt.*;

public class ViewAnimableCard extends ViewRotatableImage
{
    private Dimension shiftAmount = new Dimension(0,0);

    public ViewAnimableCard(Card c, int rotation) { super(c,rotation); }

    public double getShiftHeight() { return shiftAmount.getHeight(); }

    public double getShiftWidth() { return shiftAmount.getWidth(); }

    public void setShiftHeight(double shiftHeight) { shiftAmount.setSize(shiftAmount.getWidth(),shiftHeight); }

    public void setShiftWidth(double shiftWidth) { shiftAmount.setSize(shiftWidth,shiftAmount.getHeight()); }

}
