package Model.Cards;

import java.util.function.Predicate;

/**
 * Class used to model a generic Uno card
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class Card
{
    protected final Color color;
    protected final Value value;

    /**
     * Creates a {@link Card} by a {@link Color} and a {@link Value}. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     * @param value
     */
    public Card(Color color, Value value)
    {
        this.color = color;
        this.value = value;
    }

    /**
     *
     * @return the face {@link Value} of the card
     */
    public Value getValue() { return value; }

    /**
     *
     * @return the {@link Color} of the card
     */
    public Color getColor() { return color; }

    /**
     * Validity means basic Uno context playability,<br/>
     * so basing on the check a {@link Card} is valid whether <br/>
     * is the same {@link Color} or the same {@link Value} or it is a wild card. <br/>
     * To be valid is necessary but not sufficient for a {@link Card} to be playable: <br/>
     * it must be checked basing on the rules.
     * For more info about playability see {@link Model.Rules.UnoGameRules}
     * @param check
     * @return true if the card is valid , false otherwise
     */
    public boolean isValid(Card check)
    { return isColorValid.test(check) || isValueValid.test(check) || isWild.test(this); }

    /**
     * Used to check if a {@link Color} is valid
     */
    public final Predicate<Card> isColorValid = c -> c.getColor() == getColor();

    /**
     * Used to check if a {@link Value} is valid
     */
    public final Predicate<Card> isValueValid = c -> c.getValue() == getValue();

    /**
     * Used to check if a {@link Color} is Wild
     */
    public final Predicate<Card> isWild = c -> c.getColor() == Color.WILD;

    /**
     * @return {@link Value} - {@link Color}
     */
    @Override
    public String toString(){ return value.name() + "-"+ color.name(); }

    /**
     * Compares the actual {@link Card} to a given object, <br/>
     * a card equals another card when they match by {@link Color} and {@link Value}
     *
     * @param obj
     * @return true if they are eqaul, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    { return (obj instanceof Card objCard && color.equals(objCard.getColor()) && value.equals(objCard.getValue())); }

}
