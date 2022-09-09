package Model.Rules;

import Model.Cards.*;
import Model.DeckManager;
import Model.Players.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class specializes {@link UnoGameRules} to provide the methods of a game mode of UNO created by us
 * Uno meme rules are :
 * <table>
 *     <tr>
 *         <th>Setup</th>
 *     </tr>
 *     <tr>
 *          <td>-the number of cards that can be played in a single turn</td>
 *          <td>3</td>
 *     </tr>
 *     <tr>
 *          <td>-the number of cards distributed to each player at the start </td>
 *          <td>11</td>
 *     </tr>
 *     <tr>
 *          <td>-the cards with which a deck is composed by</td>
 *          <td>classic deck composition with 2 more skip,2 more draw2 ,2 more reverse for each color, 4 more wild and 4 more wild_draw4.(classic deck composition specified in {@link ClassicRules})</td>
 *     </tr>
 *      <tr>
 *         <th>In game</th>
 *     </tr>
 *     <tr>
 *          <td>-card playability</td>
 *          <td>same as classic</td>
 *     </tr>
 *     <tr>
 *          <td>-game win condition</td>
 *          <td>the first that remains with 0 card left wins the entire match</td>
 *     </tr>
 *     <tr>
 *          <td>-card actions performances</td>
 *          <td>same as classic but cards are stackable, and the effects sum up</td>
 *     </tr>
 *     <tr>
 *          <td>-point scoring</td>
 *          <td>there is not scoring, actually the first {@link Player} who wins earns 1000 points</td>
 *     </tr>
 * </table>
 *
 * @see ClassicRules
 * @author D'annibale Luigi, Venturini Daniele
 */
public class MemeRules extends UnoGameRules
{
    private final int pointsPerGame = 1000;
    private boolean isStacking;
    private int stackedCardsToDraw;
    private int playersToBlock;
    private int cardsPlayed;
    private ActionPerformResult lastAction;

    /**
     * Creates a new {@link MemeRules} with the eleven cards per player at the start and three cards playable per turn, <br>
     * while the deck has 2 more skip,2 more draw2 ,2 more reverse for each color, 4 more wild and 4 more wild_draw4.
     */
    public MemeRules()
    {
        super(11,3, new HashMap<>(){{
            putAll(DeckManager.CLASSIC_RULES_CARD_DISTRIBUTION);
            put(Value.SKIP,4);
            put(Value.DRAW,4);
            put(Value.REVERSE,4);
            put(Value.WILD,8);
            put(Value.WILD_DRAW,8);
        }});
    }

    /**
     * Performs the default action associated with the first {@link Card} put on the discards after shuffling the deck
     * @param parameters
     * @return the {@link ActionPerformResult} of the first card
     */
    @Override
    public ActionPerformResult performFirstCardAction(Options parameters)
    {
        cardsPlayed -= 1;
        var result = cardActionPerformance(parameters);
        var card = parameters.getTurnManager().getLastCardPlayed();
        if (!(card instanceof SkipAction)) isStacking = false;
        if (result == ActionPerformResult.SUCCESSFUL && !isStacking) parameters.getTurnManager().setPlayer(0);
        return result;
    }

    /**
     * All valid cards are playable, but if there is an {@link ActionCard} and it is stacking,only stackable cards can be played.
     * @param playerPlayableHand
     * @param discardsPick
     * @return all the playable {@link Card} cards in the hand
     */
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    { return isStacking ? filterByValue(playerPlayableHand.stream(), discardsPick).toList() : playerPlayableHand; }

    /**
     * Two cards are stackable when they have the same {@link Value}
     * @param cardStream
     * @param card
     * @return the stackable cards of the cardStream
     */
    private Stream<Card> getStackableCards(Stream<Card> cardStream, Card card) { return filterByValue(cardStream,card).filter(c -> !c.isWild.test(c)); }

    /**
     * @param stream
     * @param card
     * @return the stream filtered, in which are contained only the cards the match the {@link Value} of the passed card.
     */
    private Stream<Card> filterByValue(Stream<Card> stream, Card card){ return stream.filter(card.isValueValid); }

    /**
     * This method overrides the original, to integrate the performance of an {@link ActionCard} with the stackable cards
     * @param parameters
     * @return
     */
    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Player currentPlayer = players[parameters.getCurrentPlayer()];
        Card lastCardPlayed = turnManager.getLastCardPlayed();

        if(lastCardPlayed instanceof SkipCard) playersToBlock += 1;
        else if(lastCardPlayed instanceof DrawCard drawCard && lastAction != ActionPerformResult.NO_COLOR_PROVIDED)
        {
            stackedCardsToDraw += drawCard.getNumberOfCardsToDraw();
            drawCard.setNumberOfCardsToDraw(stackedCardsToDraw);
            turnManager.updateLastCardPlayed(drawCard);
        }

        if (cardsPlayed < numberOfPlayableCards - 1 && getStackableCards(currentPlayer.getHand().stream(), lastCardPlayed).toList().size() > 0)
        {//current player has more stackable cards
            if (lastCardPlayed instanceof ReverseCard) _ReverseAction(turnManager);
            isStacking = true;
            cardsPlayed += 1;
            return ActionPerformResult.SUCCESSFUL;
        }
        else if (lastCardPlayed instanceof SkipAction && filterByValue(players[turnManager.next()].getHand().stream(), lastCardPlayed).toList().size() > 0)
        {//current player has no more stackable cards, last card played can be stacked and next player has skip or draw cards to stack
            isStacking = true;
            passTurn(turnManager, currentPlayer);
            return ActionPerformResult.SUCCESSFUL;
        }

        //no more stackable cards, need to perform card action
        while(playersToBlock > 1)
        {//players to be blocked become actually blocked, 1 must be left the cycle because last action will take care of it
            _SkipAction(turnManager, players, parameters.getNextPlayer());
            playersToBlock --;
        }

        lastAction = super.cardActionPerformance(parameters);

        if (lastAction == ActionPerformResult.SUCCESSFUL)
        {//action perfomed successfully
            passTurn(turnManager, currentPlayer);
            isStacking = false;
            stackedCardsToDraw = 0;
            playersToBlock = 0;
        }
        return lastAction;
    }

    /**
     * This method overrides the original, because in this game mode each time someone wins, 1000 points are granted
     * @param players
     * @param winner
     * @return
     */
    @Override
    public int countPoints(Player[] players, Player winner)
    {
        winner.updatePoints(pointsPerGame);
        return pointsPerGame;
    }

    /**
     * Passes the turn
     * @param turnManager
     * @param currentPlayer
     */
    @Override
    public void passTurn(TurnManager turnManager, Player currentPlayer)
    {
        super.passTurn(turnManager, currentPlayer);
        cardsPlayed = 0;
    }
}
