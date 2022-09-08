package Model.Cards;

import Model.TurnManager;

/**
 * Class used to model a Uno card : Reverse   <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ReverseCard extends ActionCard
{
    /**
     * Creates a {@link ReverseCard} by a {@link Color}. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     */
    public ReverseCard(Color color) { super(color, Value.REVERSE); }

    /**
     * Performs the reverse action, the turn is reversed on the {@link TurnManager}
     * @param turnManager
     */
    public void performReverseAction(TurnManager turnManager){ turnManager.reverseTurn(); }
}

