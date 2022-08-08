package Model.Cards.Interfaces;

import Model.Cards.Enumerations.CardColor;
import Model.TurnManager;

public interface WildAction {
    default void changeColor(TurnManager turnManager,CardColor  color)
    {
        turnManager.updateLastCardPlayed(turnManager.getLastCardPlayed().getValue(),color);
    }
}
