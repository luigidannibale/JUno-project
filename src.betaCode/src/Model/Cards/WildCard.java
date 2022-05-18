package Model.Cards;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Interfaces.WildAction;

public class WildCard extends Card implements WildAction {

    public WildCard()
    {
        super(CardColor.WILD,CardValue.WILD);
    }
}
