package Model.Cards;

import Model.Cards.CardColor;
import Model.TurnManager;

/**
 * Provides the default void changeColor, that performs wild action
 * @author D'annibale Luigi, Venturini Daniele
 */
public interface WildAction
{
    /**
     * Changes the color in a Uno Game match
     * @param turnManager
     */
    default void changeColor(TurnManager turnManager,CardColor  color) { turnManager.updateLastCardPlayed(turnManager.getLastCardPlayed().getValue(),color); }
}
