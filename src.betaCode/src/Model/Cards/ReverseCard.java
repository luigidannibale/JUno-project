package Model.Cards;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
import Model.Player.Player;
import Model.TurnManager;
import java.util.Arrays;
import java.util.Collections;

public class ReverseCard extends ActionCard {

    public ReverseCard(CardColor color)
    {
        super(color, CardValue.REVERSE);
    }

    public void performReverseAction(TurnManager turnManager, Player[] players){
        Player player = players[turnManager.getPlayer()];
        Collections.reverse(Arrays.asList(players));
        Arrays.stream(players).filter(item -> player == item).forEach(item -> turnManager.passTurn());
    }
}
