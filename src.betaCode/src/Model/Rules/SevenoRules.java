package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Exceptions.NoSelectedPlayerToSwapException;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;
import Model.UnoGameTable;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SevenoRules extends UnoGameRules
{

    public SevenoRules()
    {
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.ZERO,2);
            put(CardValue.SEVEN,700);
        }});
        stackableCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    { return playerPlayableHand; }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
//        assert(parameters.getTurnManager() != null);
//        assert(parameters.getPlayers() != null);
//        assert(parameters.getDeck() != null);
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Card lastCard = turnManager.getLastCardPlayed();

        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
        {
            Player current = parameters.getPlayers()[turnManager.getPlayer()];
            CardColor color = parameters.getColor();
            if (color == null) {
                if (current instanceof HumanPlayer) return ActionPerformResult.NO_COLOR_PROVIDED;
                else
                    color = ((AIPlayer) current).chooseBestColor();
            }
            ((WildAction) lastCard).changeColor(turnManager, color);
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(parameters.getDeck().draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).performReverseAction(turnManager);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).performSkipAction(turnManager, players);
        if (lastCard.getValue() == CardValue.SEVEN)
        {
            //il giocatore che ha giocato deve scambiare le carte con un altro player di sua scelta
            //----> i nomi dei player devono essere cliccabili
            if (parameters.getPlayerToSwapCards() == null) return ActionPerformResult.NO_PLAYER_PROVIDED;
            Player currentPlayer = players[turnManager.getPlayer()];
            currentPlayer.setHand(swapHand(currentPlayer.getHand(),parameters.getPlayerToSwapCards()));
            return ActionPerformResult.SUCCESSFUL;
        }
        if (lastCard.getValue() == CardValue.ZERO){
            //i giocatori si scambiano le carte in base al senso del turno

            var newHand = players[0].getHand();
            for (int i = 0; i < players.length; i++){
                var nextPlayer = players[turnManager.next(i)];
                newHand = swapHand(newHand, nextPlayer);
            }
        }
        turnManager.passTurn();
        return ActionPerformResult.SUCCESSFUL;
    }
    @Override
    public void oldCardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {
        /*
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
            ((ReverseCard) lastCard).performReverseAction(turnManager);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).performSkipAction(turnManager);

        if (lastCard.getValue() == CardValue.SEVEN){
            //il giocatore che ha giocato deve scambiare le carte con un altro player di sua scelta
            //----> i nomi dei player devono essere cliccabili
        }
        if (lastCard.getValue() == CardValue.ZERO){
            //i giocatori si scambiano le carte in base al senso del turno

            var newHand = players[0].getHand();
            for (int i = 0; i < players.length; i++){
                var nextPlayer = players[turnManager.next(i)];
                newHand = swapHand(newHand, nextPlayer);
            }
        }
        turnManager.passTurn();

         */
    }

    private Stack<Card> swapHand(Stack<Card> handToGiveAway, Player playerToSwapWith){
        Stack<Card> handToGet = playerToSwapWith.getHand();
        playerToSwapWith.setHand(handToGiveAway);
        return handToGet;
    }

}
