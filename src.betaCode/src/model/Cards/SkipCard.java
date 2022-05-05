package model.Cards;

import model.Enumerations.CardColor;
import model.Enumerations.CardValue;
import model.Interfaces.SkipAction;

public class SkipCard extends Card implements SkipAction {

    public SkipCard(CardColor color)
    {
        super(color, CardValue.SKIP);
    }
}
