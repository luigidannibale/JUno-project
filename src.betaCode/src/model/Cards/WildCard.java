package model.Cards;

import model.Enumerations.CardColor;
import model.Enumerations.CardValue;
import model.Interfaces.WildAction;

public class WildCard extends Card implements WildAction {

    public WildCard()
    {
        super(CardColor.WILD,CardValue.WILD);
    }
}
