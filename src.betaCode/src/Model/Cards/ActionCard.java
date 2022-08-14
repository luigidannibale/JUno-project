package Model.Cards;

/**
 * Class used to model a generic Uno action card
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public abstract class ActionCard extends Card
{
    protected ActionCard(CardColor color, CardValue value) { super(color, value); }
}
