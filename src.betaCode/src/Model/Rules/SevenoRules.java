package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Player.Player;
import Model.TurnManager;
import Model.UnoGameTable;

import java.util.HashMap;
import java.util.List;

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
    @Override
    public void cardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {
        Card lastCard = turnManager.getLastCardPlayed();

        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
        {
            //color deve essere preso come scelta dell'utente
            CardColor color = CardColor.RED;
            ((WildAction) lastCard).changeColor(turnManager, color);
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).performReverseAction(turnManager, players);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).performSkipAction(turnManager);

        if (lastCard.getValue() == CardValue.SEVEN){
            //il giocatore che ha giocato deve scambiare le carte con un altro player di sua scelta
            //----> i nomi dei player devono essere cliccabili
        }
        if (lastCard.getValue() == CardValue.ZERO){
            //i giocatori si scambiano le carte in base al senso del turno
            /*
            for (int i = 0; i < players.length; i++){
                var nextPlayer = players[turnManager.next(i)];
                var nextHand = nextPlayer.getHand();
                nextPlayer.
            }
             */
        }
    }

    @Override
    public void cardActionPerformance(UnoGameTable gameTable) {

    }
}
