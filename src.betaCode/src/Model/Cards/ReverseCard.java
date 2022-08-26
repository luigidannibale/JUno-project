package Model.Cards;

import Model.Player.Player;
import Model.TurnManager;

/**
 * Class used to model a Uno card : Reverse   <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ReverseCard extends ActionCard
{
    /**
     * Creates a reverse card
     * @param color
     */
    public ReverseCard(CardColor color) { super(color, CardValue.REVERSE); }
    public void performReverseAction(TurnManager turnManager){ turnManager.reverseTurn(); }
}
//nousecode
//public void performReverseAction(TurnManager turnManager, Player[] players)
//{
//    //Player player = players[turnManager.getPlayer()];
//    //Collections.reverse(Arrays.asList(players));
//    //Arrays.stream(players).filter(item -> player == item).forEach(item -> turnManager.passTurn());
//}
