package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Player.Player;
import Model.TurnManager;
import Model.UnoGameTable;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SevenoRules extends UnoGameRules{

    public SevenoRules()
    {
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.ZERO,2);
            put(CardValue.SEVEN,3);
        }});
        stackableCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    { return playerPlayableHand; }

    public void cardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {}
    public void cardActionPerformance(UnoGameTable gameTable,CardColor color, Player chosen)
    {
        TurnManager turnManager = gameTable.getTurnManager();
        Player[] players = gameTable.getPlayers();
        Deck deck = gameTable.getDeck();

        Card lastCard = turnManager.getLastCardPlayed();

        switch (lastCard.getValue())
        {
            case ZERO:
            {//all players pass their hands to the next player in direction of play
                Stack<Card> temp=players[players.length-1].getHand();
                for (Player p: players)
                    temp = p.swapHand(temp);
                break;
            }
            case SEVEN:
            {//the player who played the “7” card must trade their hand with another player of their choice.
                chosen.swapHand(players[turnManager.getPlayer()].swapHand(chosen.getHand()));
                break;
            }
            case REVERSE :
            {//if discard discard.peek == reverse -> il primo gioca e poi cambia giro
                ((ReverseCard) lastCard).performReverseAction(turnManager,players);
                break;
            }
            case WILD_DRAW:
            case DRAW:
            {//if discard discard.peek == draw -> il primo pesca due carte
                players[turnManager.getPlayer()].drawCards(deck.draw(((DrawCard)lastCard).getNumberOfCardsToDraw()));
                ((DrawCard) lastCard).performSkipAction(turnManager);
                break;
            }
            case SKIP :
            {//if discard discard.peek == skip -> il primo viene skippato
                ((SkipCard) lastCard).performSkipAction(turnManager);
                break;
            }
            /*i wild li gestisco a parte, uso lo switch perchè devo definire una regola specifica e non che prenda tutte
             le ereditarietà, si puo gestire il wild draw in altra maniera a che serve il cardvalue wild draw*/
        }
        turnManager.passTurn();
    }
    public void cardActionPerformance(UnoGameTable gameTable,CardColor color)
    {
        TurnManager turnManager = gameTable.getTurnManager();
        Player[] players = gameTable.getPlayers();
        Deck deck = gameTable.getDeck();

        Card lastCard = turnManager.getLastCardPlayed();
        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
            ((WildAction) lastCard).changeColor(turnManager, color);         
    }
}
