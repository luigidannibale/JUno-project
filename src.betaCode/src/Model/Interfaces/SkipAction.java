package Model.Interfaces;

import Model.TurnManager;

public interface SkipAction {
    default void skipTurn(TurnManager turnManager)
    {
        turnManager.passTurn();
    }
}
