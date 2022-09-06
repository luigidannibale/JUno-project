package Model.Rules;

import Model.Cards.*;
import Model.DeckManager;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;

import java.util.*;
import java.util.stream.IntStream;

public class SevenoRules extends UnoGameRules
{

    public SevenoRules()
    {
        super(7,1, new HashMap<>(){{
            putAll(DeckManager.classicRules);
            put(CardValue.ZERO,3);
            put(CardValue.SEVEN,4);
        }});
    }

    @Override
    public ActionPerformResult performFirstCardAction(Options parameters) { return super.cardActionPerformance(parameters); }
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick) { return playerPlayableHand; }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Player currentPlayer = players[parameters.getCurrentPlayer()];
        Card lastCard = turnManager.getLastCardPlayed();

        var actionPerformResult = super.cardActionPerformance(parameters);

        if (lastCard.getValue() == CardValue.SEVEN)
        {//who playes 7 swaps the cards with another player

            Player playerToSwap = parameters.getPlayerToSwapCards();
            if (playerToSwap == null)
            {// no player to swap the hand with ahs been provided
                if (currentPlayer instanceof HumanPlayer) return ActionPerformResult.NO_PLAYER_PROVIDED;
                else if (currentPlayer instanceof AIPlayer aiCurrentPLayer) playerToSwap = aiCurrentPLayer.chooseBestPlayerToSwap(players, turnManager.getPlayer());
            }

            currentPlayer.swapHand(swapHand(currentPlayer.getHand(), playerToSwap));
        }
        if (lastCard.getValue() == CardValue.ZERO)
        {//each player gets the hand of the previous player
            List<Stack<Card>> hands = new ArrayList<>(Arrays.stream(players).map(Player::getHand).toList());
            if (turnManager.antiClockwiseTurn())
                hands.add(0 , hands.remove(hands.size() -1));
            else
                hands.add(hands.remove(0));
            IntStream.range(0, players.length).forEach(i -> players[i].swapHand(hands.get(i)));
        }

        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) passTurn(turnManager, currentPlayer);
        return actionPerformResult;
    }

    private Stack<Card> swapHand(Stack<Card> handToGiveAway, Player playerToSwapWith) { return playerToSwapWith.swapHand(handToGiveAway); }
}
