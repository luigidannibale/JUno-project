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
        Player player = players[turnManager.getPlayer()];
        Collections.reverse(Arrays.asList(players));
        for(int i = 0;i< players.length;i++)
            if(player == players[i])
                while(i+1> players.length?turnManager.getPlayer()!=0:turnManager.getPlayer()!=i+1)
                    turnManager.passTurn();
        //turnManager.setPlayer(players.length - 1 - turnManager.getPlayer());
    }
}
