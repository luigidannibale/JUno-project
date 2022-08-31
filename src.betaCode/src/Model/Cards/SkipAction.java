package Model.Cards;

import Model.Player.Player;
import Model.TurnManager;

/**
 * Provides the default void performSkipAction, that performs skip action
 * @author D'annibale Luigi, Venturini Daniele
 */
public interface SkipAction
{
    /**
     * Skips the turn in a Uno Game match
     * @param turnManager
     */
    default void performSkipAction(TurnManager turnManager, Player[] players) {
        recursiveSkipper(turnManager, players, turnManager.getPlayer());
    }
    private void recursiveSkipper(TurnManager turnManager, Player[] players, int i)
    {
        Player next = players[turnManager.next(i)];
        if (next.equals(players[turnManager.getPlayer()])) return;
        if (next.isBlocked()) recursiveSkipper(turnManager, players, i+1);
        else next.setBlocked(true);

    }
}
