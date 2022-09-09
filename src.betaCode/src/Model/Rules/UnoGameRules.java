package Model.Rules;

import Model.Cards.*;
import Model.DeckManager;
import Model.Players.*;
import Model.TurnManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to model the possible rules a Uno game, more specifically the aspects the various game-modes can change. <br/>
 * The main aspects to define to be well-designed Uno rules are :
 * <table>
 *     <tr>
 *         <th>Setup</th>
 *     </tr>
 *     <tr>
 *          <td>-the number of cards that can be played in a single turn</td>
 *     </tr>
 *     <tr>
 *          <td>-the number of cards distributed to each player at the start </td>
 *     </tr>
 *     <tr>
 *          <td>-the cards with which a deck is composed by</td>
 *     </tr>
 *      <tr>
 *         <th>In game</th>
 *     </tr>
 *     <tr>
 *          <td>-card playability</td>
 *     </tr>
 *     <tr>
 *          <td>-game win condition</td>
 *     </tr>
 *     <tr>
 *          <td>-card actions performances</td>
 *     </tr>
 *     <tr>
 *          <td>-point scoring</td>
 *     </tr>
 * </table>
 * @author D'annibale Luigi, Venturini Daniele
 */
public abstract class UnoGameRules
{
    public static final int WIN_POINTS_THRESHOLD = 500;

    /**
     * @see DeckManager
     */
    protected final HashMap<Value, Integer> cardsDistribution;

    /**
     * How many cards can be played in a single turn,<br/>
     * to play more than a card in a single turn cards must be the same value.
     */
    protected final int numberOfPlayableCards;

    /**
     * How many cards are distributed to each player at the start of the game.
     */
    protected final int numberOfCardsPerPlayer;

    /**
     * Creates a {@link UnoGameRules}
     * @param numberOfCardsPerPlayer
     * @param numberOfPlayableCards
     * @param cardsDistribution
     */
    protected UnoGameRules(int numberOfCardsPerPlayer, int numberOfPlayableCards, HashMap<Value, Integer> cardsDistribution)
    {
        this.numberOfCardsPerPlayer = numberOfCardsPerPlayer;
        this.numberOfPlayableCards = numberOfPlayableCards;
        this.cardsDistribution = cardsDistribution;
    }

    /**
     * Checks the win of a single game
     * @param currentPlayer
     * @return true if the currentPlayer won, false otherwise
     */
    public boolean checkGameWin(Player currentPlayer){ return currentPlayer.getHand().size() == 0; }

    /**
     * Checks the win of an entire match
     * @param players
     * @param player
     * @return true if the player won the entire match, false otherwise
     */
    public boolean checkWin(Player[] players, Player player)
    {
        boolean win = player.getPoints() + countPoints(players,player) >= WIN_POINTS_THRESHOLD;
        if (win)
        {
            for (Player p: players)
                if (p instanceof HumanPlayer humanPlayer)
                {
                    double exp = humanPlayer.getPoints()/ 1000.0;
                    if (humanPlayer.equals(player))
                        humanPlayer.updateStats(1,0,exp);
                    else
                        humanPlayer.updateStats(0,1,exp);

                    PlayerManager.updatePlayer(humanPlayer);
                    break;
                }
        }
        return win;
    }

    /**
     * @return if cards are stackable i.e. a {@link Player} can play more than a {@link Card} in a single turn.
     */
    public boolean isStackableCards() { return numberOfPlayableCards > 1; }

    public int getNumberOfCardsPerPlayer() { return numberOfCardsPerPlayer; }

    public HashMap<Value, Integer> getCardsDistribution() { return cardsDistribution; }

    public int getNumberOfPlayableCards() { return numberOfPlayableCards; }

    /**
     * How the first card must be performed is delegated to inheritors.
     * @param parameters
     * @return the {@link ActionPerformResult} of the first card
     */
    public abstract ActionPerformResult performFirstCardAction(Options parameters);

    /**
     * The playability must be defined in the rules for each game mode
     * @param playerHand
     * @param discardsPick
     * @return all the playable {@link Card} cards in the hand
     */
    public abstract List<Card> getPlayableCards(List<Card> playerHand, Card discardsPick);

    /**
     * Performs the actions associated with the {@link Card}
     * @param parameters
     * @return the {@link ActionPerformResult} of the operation
     */
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Card lastCard = turnManager.getLastCardPlayed();
        ActionPerformResult actionPerformResult;

        if (lastCard instanceof WildAction && lastCard.getColor() == Color.WILD)
        {
            actionPerformResult = _WildAction(parameters);
            if (actionPerformResult != ActionPerformResult.SUCCESSFUL) return actionPerformResult;
        }
        if(lastCard instanceof DrawCard)
        {
            actionPerformResult = _DrawAction(parameters, (DrawCard) lastCard);
            if (actionPerformResult != ActionPerformResult.SUCCESSFUL) return actionPerformResult;
        }
        if(lastCard instanceof ReverseCard)
        {
            actionPerformResult = _ReverseAction(turnManager);
            if (actionPerformResult != ActionPerformResult.SUCCESSFUL) return actionPerformResult;
        }
        if(lastCard instanceof SkipAction)
        {
            actionPerformResult = _SkipAction(turnManager, parameters.getPlayers(), parameters.getNextPlayer());
            return actionPerformResult;
        }
        return ActionPerformResult.SUCCESSFUL;
    }

    /**
     * Perform the classic wild action
     * @param parameters needs a color in parameters
     * @return the {@link ActionPerformResult} of the operation, if no color has been provided returns NO_COLOR_PROVIDED
     * @see WildAction
     */
    protected ActionPerformResult _WildAction(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player current = parameters.getPlayers()[parameters.getCurrentPlayer()];
        Color color = parameters.getColor();
        if (color == null)
        {
            if (current instanceof HumanPlayer) return ActionPerformResult.NO_COLOR_PROVIDED;
            else color = ((AIPlayer) current).chooseBestColor();
        }
        ((WildAction) turnManager.getLastCardPlayed()).changeColor(turnManager, color);
        return ActionPerformResult.SUCCESSFUL;
    }

    /**
     * Perform the classic draw action
     * @param parameters
     * @return the {@link ActionPerformResult} of the operation
     * @see DrawCard
     */
    protected ActionPerformResult _DrawAction(Options parameters, DrawCard lastCard)
    {
        ((DrawCard) parameters.getTurnManager().getLastCardPlayed()).performDrawAction(parameters.getPlayers()[parameters.getNextPlayer()],lastCard.getNumberOfCardsToDraw(),parameters.getDeck());
        return ActionPerformResult.SUCCESSFUL;
    }

    /**
     * Perform the classic reverse action
     * @return the {@link ActionPerformResult} of the operation
     * @see ReverseCard
     */
    protected ActionPerformResult _ReverseAction(TurnManager turnManager)
    {
        ((ReverseCard) turnManager.getLastCardPlayed()).performReverseAction(turnManager);
        return ActionPerformResult.SUCCESSFUL;
    }

    /**
     * Performs the classic skip action
     * @param turnManager
     * @param players
     * @param playerToBlock
     * @return the {@link ActionPerformResult} of the operation
     * @see SkipAction
     */
    protected ActionPerformResult _SkipAction(TurnManager turnManager, Player[] players, int playerToBlock)
    {
        ((SkipAction) turnManager.getLastCardPlayed()).performSkipAction(turnManager, players, playerToBlock);
        return ActionPerformResult.SUCCESSFUL;
    }

    /**
     *
     * @param players
     * @param winner
     * @return the points of the winner
     */
    public int countPoints(Player[] players, Player winner)
    {
        int points = Arrays.stream(players).filter(p -> !p.equals(winner))
                .flatMap(p -> p.getHand().stream())
                .mapToInt(c -> c.getValue().VALUE)
                .sum();
        winner.updatePoints(points);
        return points;
    }

    /**
     * Passes the turn
     * @param turnManager
     * @param currentPlayer
     */
    public void passTurn(TurnManager turnManager, Player currentPlayer)
    {
        currentPlayer.setDrew(false);
        currentPlayer.setPlayed(false);
        turnManager.passTurn();
    }
}
