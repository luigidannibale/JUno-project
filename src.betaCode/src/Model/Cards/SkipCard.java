package Model.Cards;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
import Model.Cards.Interfaces.SkipAction;

public class SkipCard extends ActionCard implements SkipAction {

    public SkipCard(CardColor color) {super(color, CardValue.SKIP);}
}
