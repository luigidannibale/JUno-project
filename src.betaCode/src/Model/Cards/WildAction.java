package Model.Cards;

import Model.TurnManager;

/**
 * Provides the default void changeColor, that performs wild action
 * @author D'annibale Luigi, Venturini Daniele
 */
public interface WildAction
{
    /**
     * Changes the {@link Color} in the {@link TurnManager}.
     * @param turnManager
     * @param color
     */
    default void changeColor(TurnManager turnManager, Color color)
    { turnManager.updateLastCardPlayed(turnManager.getLastCardPlayed().getValue(),color); }
}
