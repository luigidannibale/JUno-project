package Model.Cards;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Player.Player;
import Model.TurnManager;

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
