package Model.Cards;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
import Model.Cards.Interfaces.WildAction;

public class WildCard extends ActionCard implements WildAction {

    public WildCard()
    {
        super(CardColor.WILD,CardValue.WILD);
    }

}
