package Model.Rules;

import Model.Cards.*;
import Model.DeckManager;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class UnoGameRules
{
    /**
     * @see DeckManager
     */
    protected  HashMap<CardValue, Integer> CARDS_DISTRIBUTION;
    /**
     * How many cards can be played in a single turn,<br/>
     * to play more than a card in a single turn cards must be the same value.
     */
    protected  int NUMBER_OF_PLAYABLE_CARDS;
    /**
     * How many cards are distributed to each player at the start of the game.
     */
    protected  int numberOfCardsPerPlayer;

//    protected UnoGameRules(int numberOfCardsPerPlayer) {
//        this.numberOfCardsPerPlayer = numberOfCardsPerPlayer;
//    }

    public boolean checkGameWin(Player current){return current.getHand().size() == 0 && current.hasSaidOne();}
    public boolean checkWin(Player[] players, Player player){ return countPoints(players,player)>=500;}
    public int getNumberOfCardsPerPlayer() { return numberOfCardsPerPlayer; }

    public HashMap<CardValue, Integer> getCARDS_DISTRIBUTION() { return CARDS_DISTRIBUTION; }

    public boolean isStackableCards() { return NUMBER_OF_PLAYABLE_CARDS > 1; }

    public int getNUMBER_OF_PLAYABLE_CARDS() { return NUMBER_OF_PLAYABLE_CARDS; }

    public abstract ActionPerformResult performFirstCardAction(Options parameters);

    public abstract List<Card> getPlayableCards(List<Card> playerHand, Card discardsPick);

    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Card lastCard = turnManager.getLastCardPlayed();
        ActionPerformResult actionPerformResult;

        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
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
        CardColor color = parameters.getColor();
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
        parameters.getPlayers()[parameters.getNextPlayer()].drawCards(parameters.getDeck().draw(lastCard.getNumberOfCardsToDraw()));
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
