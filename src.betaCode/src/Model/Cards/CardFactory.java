package Model.Cards;

import java.util.function.Predicate;

/**
 * Class used to provide methods to build cards
 * @author D'annibale Luigi, Venturini Daniele
 */
public class CardFactory
{
    /**
     * Creates a classic Uno {@link Card}, with a {@link Color} and a {@link Value}
     * @param color
     * @param value
     * @return the created {@link Card}
     * @throws IllegalArgumentException if the syntax of a Uno normal card wouldn't be respected. <br/>
     * I.e. a red draw 4 isn't syntactically valid. <br/>
     */
    public static Card createCard(Color color, Value value) throws IllegalArgumentException
    {
        if(!systaxValidityControl(color, value))  throw new IllegalArgumentException("this card combo is not accepted");
        return createFlowCard(color, value);
    }

    /**
     *
     * @param color
     * @param value
     * @return true if {@link Color} - {@link Value} combination is accepted, false otherwise
     */
    private static boolean systaxValidityControl(Color color, Value value)
    { return !(color == Color.WILD && value != Value.WILD && value != Value.WILD_DRAW || color != Color.WILD && (value == Value.WILD || value == Value.WILD_DRAW)); }

    /**
     * Creates a flow card, with {@link Color} and a {@link Value},
     * the purpose of flow cards is help keeping trace of last played cards
     * without dirtying the discards.
     * @param color
     * @param value
     * @return the created {@link Card}
     */
    public static Card createFlowCard(Color color, Value value)
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
