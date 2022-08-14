package Model.Cards;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;

/**
 * Class used to model a generic action card
 */
public abstract class ActionCard extends Card
{
    protected ActionCard(CardColor color, CardValue value) { super(color, value); }
}
