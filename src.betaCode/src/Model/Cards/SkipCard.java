package Model.Cards;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Interfaces.SkipAction;

public class SkipCard extends ActionCard implements SkipAction {

    public SkipCard(CardColor color) {super(color, CardValue.SKIP);}
}
