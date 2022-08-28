package Model.Cards;

/**
 * Class used to provide methods to build cards
 * @author D'annibale Luigi, Venturini Daniele
 */
public class CardFactory
{
    /**
     * Creates a classic Uno card, with {@link CardColor} and a {@link CardValue}
     * @param color
     * @param value
     * @return the card
     * @throws IllegalArgumentException if the syntax of a Uno normal card wouldn't be respected. <br/>
     * I.e. a red draw 4 isn't syntactically valid. <br/>
     */
    public static Card createCard(CardColor color, CardValue value)
    {
        //--Syntax validity control--
        if (color == CardColor.WILD && value != CardValue.WILD && value != CardValue.WILD_DRAW ||
                color != CardColor.WILD && (value == CardValue.WILD || value == CardValue.WILD_DRAW) )
            throw new IllegalArgumentException("this card combo is not accepted");
        //---------------------------
        //--Card creation------------
        return createFlowCard(color, value);
        //---------------------------
    }
    /**
     * Creates a flow card, with {@link CardColor} and a {@link CardValue},
     * the purpose of flow cards is help keeping trace of last played cards
     * without dirtying the discards.
     * @param color
     * @param value
     * @return the card
     */
    public static Card createFlowCard(CardColor color, CardValue value)
    {
        switch (value)
        {
            case DRAW -> { return new DrawCard(color); }
            case WILD_DRAW -> { return new DrawCard(color,value); }
            case SKIP -> { return new SkipCard(color); }
            case REVERSE -> { return new ReverseCard(color); }
            case WILD -> { return new WildCard(color); }
            default -> { return new Card(color, value); }
        }
    }
}
