package Model.Interfaces;

import Model.Enumerations.CardColor;
import Model.TurnManager;

import java.util.Random;

public interface WildAction {
    default void changeColor(TurnManager turnManager,CardColor  color)
    {
        turnManager.updateLastCardPlayed(turnManager.getLastCardPlayed().getValue(),color);
    }
}
