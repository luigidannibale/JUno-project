package Model.Interfaces;

import Model.TurnManager;

public interface SkipAction {
    default void performSkipAction(TurnManager turnManager)
    {
        turnManager.passTurn();
    }

}
