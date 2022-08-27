package Model.Cards;

/**
 * Class used to model a generic Uno card
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class Card //implements Comparable<Card>
{
    protected final CardColor color;
    protected final CardValue value;

    public Card(CardColor color, CardValue value)
    {
        this.color = color;
        this.value = value;
    }

    public CardValue getValue() { return value; }
    public CardColor getColor() { return color; }
    /**
     * Validity means basic Uno context playability,<br/>
     * so basing on the check a {@link Card} is valid whether <br/>
     * is the same {@link CardColor} or the same {@link CardValue} <br/>
     * or is a wild card. <br/>
     * To be valid is necessary but not sufficient for a {@link Card} to be playable: <br/>
     * it must be checked basing on the rules.
     * @param check
     * @return whether the card is valid or not
     */
    public boolean isValid(Card check)
    { return getColor() == check.getColor() || getValue() == check.getValue() || getColor() == CardColor.WILD; }

    /**
     * @return {@link CardValue} - {@link CardColor}
     */
    @Override
    public String toString(){ return value.name() + "-"+ color.name(); }

    /**
     * Compares the actual card to a given object, <br/>
     * returns whether they are equal or not
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    { return (obj instanceof Card && color.equals(((Card) obj).getColor()) && value.equals(((Card) obj).getValue())); }

}
//nousecode
//@Override
//public int compareTo(Card o) {
//    return getColor() == o.getColor() ? 0 : getValue() == o.getValue() ? -1 : 1;

