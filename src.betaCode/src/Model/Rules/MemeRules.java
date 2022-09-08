package Model.Rules;

import Model.Cards.*;
import Model.DeckManager;
import Model.Players.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class MemeRules extends UnoGameRules
{
    private final int pointsPerGame = 1000;
    private boolean isStacking;
    private int stackedCardsToDraw;
    private int playersToBlock;
    private int cardsPlayed;
    private ActionPerformResult lastAction;

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

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        return isStacking ? filterByValue(playerPlayableHand.stream(), discardsPick).toList() : playerPlayableHand;
    }

    private Stream<Card> getStackableCards(Stream<Card> stream, Card card) { return filterByValue(stream,card).filter(c -> !c.isWild.test(c)); }

    private Stream<Card> filterByValue(Stream<Card> stream, Card card){ return stream.filter(card.isValueValid); }

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

    @Override
    public int countPoints(Player[] players, Player winner)
    {
        winner.updatePoints(pointsPerGame);
        return pointsPerGame;
    }

    @Override
    public void passTurn(TurnManager turnManager, Player currentPlayer)
    {
        super.passTurn(turnManager, currentPlayer);
        cardsPlayed = 0;
    }
}
