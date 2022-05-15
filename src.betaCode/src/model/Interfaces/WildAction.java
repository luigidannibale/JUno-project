package model.Interfaces;

import model.Cards.Card;
import model.Enumerations.CardColor;
import model.Enumerations.CardValue;
import model.UnoGameTable;

import java.util.Random;

public interface WildAction {
    default void changeColor(CardValue value)
    {
        Random r = new Random();
        var randomColor = CardColor.values()[r.nextInt(4)];
        System.out.println("New color: " + randomColor);
        UnoGameTable.TurnManager.setCard(new Card(randomColor, value));
    }
}
