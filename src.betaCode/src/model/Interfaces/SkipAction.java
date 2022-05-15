package model.Interfaces;

import model.UnoGameTable;

public interface SkipAction {
    default void skipturn()
    {
        UnoGameTable.TurnManager.skipTurn();
    }
}
