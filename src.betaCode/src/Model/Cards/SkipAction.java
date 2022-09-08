package Model.Cards;

import Model.Players.Player;
import Model.TurnManager;

/**
 * Provides the default void performSkipAction, that performs skip action <br/>
 * multiple players can be skipped at the same time too.
 * @author D'annibale Luigi, Venturini Daniele
 */
public interface SkipAction
{
    /**
     * Skips the specified amount of players, working along the {@link TurnManager}.
     * @param turnManager
     * @param players
     * @param playerToBlock the index of the {@link Player} to block on the {@link TurnManager}
     */
    default void performSkipAction(TurnManager turnManager, Player[] players, int playerToBlock)
    {
        if (turnManager.getPlayer() == playerToBlock) players[playerToBlock].setBlocked(true);
        else recursiveSkipper(turnManager, players, playerToBlock);
    }

    /**
     * Recursively blocks the first player that isn't blocked starting from the given index. <br/>
     * Ends when it founds himself or it blocks someone.
     * @param turnManager
     * @param players
     * @param playerToBlock
     */
    private void recursiveSkipper(TurnManager turnManager, Player[] players, int playerToBlock)
    {
        Player playerToCheck = players[playerToBlock];
        if (playerToCheck.equals(players[turnManager.getPlayer()])) return;
        if (playerToCheck.isBlocked())
            recursiveSkipper(turnManager, players, turnManager.next(playerToBlock));
        else
            playerToCheck.setBlocked(true);

    }

}
