package model.Interfaces;

import model.TurnManager;

public interface SkipAction {
    default void skipTurn(TurnManager turnManager)
    {
        turnManager.passTurn();
    }
}
