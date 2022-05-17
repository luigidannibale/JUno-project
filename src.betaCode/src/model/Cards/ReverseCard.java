package model.Cards;

import model.Enumerations.CardColor;
import model.Enumerations.CardValue;
import model.Player.Player;
import model.TurnManager;

import java.util.Arrays;
import java.util.Collections;

public class ReverseCard extends Card {

    public ReverseCard(CardColor color)
    {
        super(color, CardValue.REVERSE);
    }


    public void reverse(TurnManager turnManager, Player[] players){
        Collections.reverse(Arrays.asList(players));
        turnManager.setPlayer(players.length - 1 - turnManager.getPlayer());
    }
}
