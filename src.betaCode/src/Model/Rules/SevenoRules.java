package Model.Rules;

import Model.Cards.Card;
import Model.Cards.Color;
import Model.Cards.Value;
import Model.DeckManager;
import Model.Players.AIPlayer;
import Model.Players.HumanPlayer;
import Model.Players.Player;
import Model.TurnManager;

import java.util.*;
import java.util.stream.IntStream;
/**
 * This class specializes {@link UnoGameRules} to provide the methods of the seven-o game mode of UNO. <br/>
 * In this game mode when someone plays a 7, he chooses a {@link Player} to swap hands with, <br/>
 * while when someone plays a 0, the players swap their hand with the next player following the direction of the turn
 * @author D'annibale Luigi, Venturini Daniele
 */
public class SevenoRules extends UnoGameRules
{
    /**
     * Creates a new {@link SevenoRules} with the classic cards per player and cards playable, <br>
     * while the deck is made of the classic deck composition (spicified in {@link ClassicRules}) with 1 more zero and 2 more seven for each {@link Color}.
     */
    public SevenoRules()
    {
        super(7,1, new HashMap<>()
        {{
            putAll(DeckManager.CLASSIC_RULES_CARD_DISTRIBUTION);
            put(Value.ZERO,3);
            put(Value.SEVEN,4);
        }});
    }

    /**
     * Performs the default action associated with the first {@link Card} put on the discards after shuffling the deck
     * @param parameters
     * @return the {@link ActionPerformResult} of the first card
     */
    @Override
    public ActionPerformResult performFirstCardAction(Options parameters) { return super.cardActionPerformance(parameters); }

    /**
     * All valid cards are playable.
     * @param playerPlayableHand
     * @param discardsPick
     * @return all the playable {@link Card} cards in the hand
     */
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick) { return playerPlayableHand; }

    /**
     * Performs the classic actions associated with the {@link Card}, adding the action of the 7 and 0 {@link Card}: <br>
     * - with a 7, the current {@link Player} chooses a player to swap hands with;
     * - with a 0, the players swap their hand with the next player following the direction of the turn.
     * If the {@link ActionPerformResult} is SUCCESSFUL, then the turn is skipped, because the {@link Model.Players.Player} can't play other cards.
     * @param parameters
     * @return the {@link ActionPerformResult} of the operation
     */
    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Player currentPlayer = players[parameters.getCurrentPlayer()];
        Card lastCard = turnManager.getLastCardPlayed();

        var actionPerformResult = super.cardActionPerformance(parameters);

        if (lastCard.getValue() == Value.SEVEN)
        {//who playes 7 swaps the cards with another player

            Player playerToSwap = parameters.getPlayerToSwapCards();
            if (playerToSwap == null)
            {// no player to swap the hand with ahs been provided
                if (currentPlayer instanceof HumanPlayer) return ActionPerformResult.NO_PLAYER_PROVIDED;
                else if (currentPlayer instanceof AIPlayer aiCurrentPLayer) playerToSwap = aiCurrentPLayer.chooseBestPlayerToSwap(players, turnManager.getPlayer());
            }
            currentPlayer.swapHand(swapHand(currentPlayer.getHand(), playerToSwap));
        }
        if (lastCard.getValue() == Value.ZERO)
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

    /**
     * Gives the hand to the {@link Player} to swap cards with, and returns his now old hand
     * @param handToGiveAway the hand to give
     * @param playerToSwapWith the player to receive the new hand
     * @return the old hand of the player
     */
    private Stack<Card> swapHand(Stack<Card> handToGiveAway, Player playerToSwapWith) { return playerToSwapWith.swapHand(handToGiveAway); }
}
