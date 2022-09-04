package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;

public abstract class UnoGameRules
{
    /**
     * @see Deck
     */
    protected HashMap<CardValue, Integer> cardsDistribution;
    /**
     * How many cards can be played in a single turn,<br/>
     * to play more than a card in a single turn cards must be the same value.
     */
    protected int numberOfPlayableCards;
    /**
     * How many cards are distributed to each player at the start of the game.
     */
    protected int numberOfCardsPerPlayer;


    public ActionPerformResult startGame(Options parameters) {return performFirstCardAction(parameters);}
    public int getNumberOfCardsPerPlayer() { return numberOfCardsPerPlayer; }

    public HashMap<CardValue, Integer> getCardsDistribution() { return cardsDistribution; }

    public boolean isStackableCards() { return numberOfPlayableCards > 1; }

    public int getNumberOfPlayableCards() { return numberOfPlayableCards; }

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
            actionPerformResult = _SkipAction(turnManager, parameters.getPlayers());
            if (actionPerformResult != ActionPerformResult.SUCCESSFUL) return actionPerformResult;
        }
        return ActionPerformResult.SUCCESSFUL;
    }

    protected ActionPerformResult _WildAction(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player current = parameters.getPlayers()[turnManager.getPlayer()];
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
        TurnManager turnManager = parameters.getTurnManager();
        parameters.getPlayers()[turnManager.next()].drawCards(parameters.getDeck().draw(lastCard.getNumberOfCardsToDraw()));
        return ActionPerformResult.SUCCESSFUL;
    }
    protected ActionPerformResult _ReverseAction(TurnManager turnManager)
    {
        ((ReverseCard) turnManager.getLastCardPlayed()).performReverseAction(turnManager);
        return ActionPerformResult.SUCCESSFUL;
    }
    protected ActionPerformResult _SkipAction(TurnManager turnManager, Player[] players)
    {
        ((SkipAction) turnManager.getLastCardPlayed()).performSkipAction(turnManager, players);
        return ActionPerformResult.SUCCESSFUL;
    }

}
