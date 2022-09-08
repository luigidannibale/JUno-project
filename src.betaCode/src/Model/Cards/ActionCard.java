package Model.Cards;

/**
 * Class used to model a generic Uno action {@link Card}
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public abstract class ActionCard extends Card
{
    /**
     * Creates an {@link ActionCard} by a {@link Color} and a {@link Value}. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     * @param value
     */
    protected ActionCard(Color color, Value value) { super(color, value); }
}
