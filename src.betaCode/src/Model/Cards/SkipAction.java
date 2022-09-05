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
    default void performSkipAction(TurnManager turnManager, Player[] players, int playerToBlock)
    {
        if (turnManager.getPlayer() == playerToBlock) players[playerToBlock].setBlocked(true);
        else recursiveSkipper(turnManager, players, playerToBlock - 1);
    }
    default void performSkipAction(TurnManager turnManager, Player[] players) { performSkipAction(turnManager, players, turnManager.getPlayer()); }
    private void recursiveSkipper(TurnManager turnManager, Player[] players, int playerToBlock)
    {
        Player next = players[turnManager.next(playerToBlock)];
        if (next.equals(players[turnManager.getPlayer()])) return;
        if (next.isBlocked()) recursiveSkipper(turnManager, players, playerToBlock+1);
        else next.setBlocked(true);
    }

}
