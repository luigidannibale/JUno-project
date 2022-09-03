package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SevenoRules extends UnoGameRules
{

    public SevenoRules()
    {
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.ZERO,3);
            put(CardValue.SEVEN,4);
        }});
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        return playerPlayableHand;
    }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Player currentPlayer = players[turnManager.getPlayer()];
        Card lastCard = turnManager.getLastCardPlayed();

        var actionPerformResult = super.cardActionPerformance(parameters);

        if (lastCard.getValue() == CardValue.SEVEN)
        {//who playes 7 swaps the cards with another player

            Player playerToSwap = parameters.getPlayerToSwapCards();
            if (playerToSwap == null)
            {// no player to swap the hand with ahs been provided
                if (currentPlayer instanceof HumanPlayer) return ActionPerformResult.NO_PLAYER_PROVIDED;
                else playerToSwap = getBestPlayer(players);
            }

            currentPlayer.swapHand(swapHand(currentPlayer.getHand(), playerToSwap));
        }
        if (lastCard.getValue() == CardValue.ZERO){
            //i giocatori si scambiano le carte in base al senso del turno

            Stack<Card> newHand = players[0].getHand();
            for (int i = 0; i < players.length; i++){
                var nextPlayer = players[turnManager.next(i)];
                newHand = swapHand(newHand, nextPlayer);
            }
        }
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) turnManager.passTurn();
        return actionPerformResult;
    }

    private Player getBestPlayer(Player[] players) {
        var min = players[0].getHand().size();
        var bestPlayer = players[0];
        for (Player p: players) if(p.getHand().size()<min) bestPlayer = p;
        return bestPlayer;
    }

    private Stack<Card> swapHand(Stack<Card> handToGiveAway, Player playerToSwapWith) { return playerToSwapWith.swapHand(handToGiveAway); }
}
