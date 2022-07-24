package Model.Cards;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

public class CardBuilder {

    public static Card createCard(CardColor color, CardValue value)
    {

        if (color == CardColor.WILD && value != CardValue.WILD && value != CardValue.WILD_DRAW ||
                color != CardColor.WILD && (value == CardValue.WILD || value == CardValue.WILD_DRAW) )
            throw new IllegalArgumentException("this card combo is not accepted");
        switch (value)
        {
            case DRAW,WILD_DRAW -> { return new DrawCard(color); }
            case SKIP -> { return new SkipCard(color); }
            case REVERSE -> { return new ReverseCard(color); }
            case WILD -> { return new WildCard(); }
            default -> { return new Card(color, value); }
        }
    }
    public static Card createFlowCard(CardColor color, CardValue value) { return new Card(color,value); }
}
