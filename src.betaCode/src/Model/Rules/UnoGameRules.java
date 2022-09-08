package Model.Rules;

import Model.Cards.*;
import Model.DeckManager;
import Model.Player.*;
import Model.TurnManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    protected UnoGameRules(int numberOfCardsPerPlayer, int numberOfPlayableCards, HashMap<Value, Integer> cardsDistribution)
    {
        this.numberOfCardsPerPlayer = numberOfCardsPerPlayer;
        this.numberOfPlayableCards = numberOfPlayableCards;
        this.cardsDistribution = cardsDistribution;
    }

    public boolean checkGameWin(Player current){ return current.getHand().size() == 0; }

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

    public int getNumberOfCardsPerPlayer() { return numberOfCardsPerPlayer; }

    public HashMap<Value, Integer> getCardsDistribution() { return cardsDistribution; }

    public boolean isStackableCards() { return numberOfPlayableCards > 1; }

    public int getNumberOfPlayableCards() { return numberOfPlayableCards; }

    public abstract ActionPerformResult performFirstCardAction(Options parameters);

    public abstract List<Card> getPlayableCards(List<Card> playerHand, Card discardsPick);

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
    protected ActionPerformResult _DrawAction(Options parameters, DrawCard lastCard)
    {
        ((DrawCard) parameters.getTurnManager().getLastCardPlayed()).performDrawAction(parameters.getPlayers()[parameters.getNextPlayer()],lastCard.getNumberOfCardsToDraw(),parameters.getDeck());
        return ActionPerformResult.SUCCESSFUL;
    }
    protected ActionPerformResult _ReverseAction(TurnManager turnManager)
    {
        ((ReverseCard) turnManager.getLastCardPlayed()).performReverseAction(turnManager);
        return ActionPerformResult.SUCCESSFUL;
    }
    protected ActionPerformResult _SkipAction(TurnManager turnManager, Player[] players, int playerToBlock)
    {
        ((SkipAction) turnManager.getLastCardPlayed()).performSkipAction(turnManager, players, playerToBlock);
        return ActionPerformResult.SUCCESSFUL;
    }


    public int countPoints(Player[] players, Player winner)
    {
        int points = Arrays.stream(players).filter(p -> !p.equals(winner))
                .flatMap(p -> p.getHand().stream())
                .mapToInt(c -> c.getValue().VALUE)
                .sum();
        winner.updatePoints(points);
        return points;
    }

    public void passTurn(TurnManager turnManager, Player currentPlayer)
    {
        currentPlayer.setDrew(false);
        currentPlayer.setPlayed(false);
        turnManager.passTurn();
    }
}
