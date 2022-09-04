package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class MemeRules extends UnoGameRules
{
    private boolean isStacking;
    private int stackedCardsToDraw;
    private int playersToBlock;
    private int cardsPlayed;
    private ActionPerformResult lastAction;

    public MemeRules()
    {
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.SKIP,4);
            put(CardValue.DRAW,4);
            put(CardValue.REVERSE,4);
            put(CardValue.WILD,4);
            put(CardValue.WILD_DRAW,8);
        }});
        numberOfPlayableCards = 3;
        numberOfCardsPerPlayer = 11;
    }

    @Override
    public ActionPerformResult performFirstCardAction(Options parameters)
    {
        return cardActionPerformance(parameters);
    }

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    { return isStacking ? filterByValue(playerPlayableHand.stream(), discardsPick).toList() : playerPlayableHand; }

    private Stream<Card> getStackableCards(Stream<Card> stream, Card card) { return filterByValue(stream,card).filter(c -> !c.isWild.test(c)); }

    private Stream<Card> filterByValue(Stream<Card> stream, Card card){ return stream.filter(card.isValueValid); }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Player currentPlayer = players[turnManager.getPlayer()];
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
            cardsPlayed = 0;
            turnManager.passTurn();
            return ActionPerformResult.SUCCESSFUL;
        }

        //no more stackable cards, need to perform card action
        while(playersToBlock > 1)
        {//players to be blocked become actually blocked, 1 must be left the cycle because last action will take care of it
            _SkipAction(turnManager, players);
            playersToBlock --;
        }

        lastAction = super.cardActionPerformance(parameters);

        if (lastAction == ActionPerformResult.SUCCESSFUL)
        {//action perfomed successfully
            turnManager.passTurn();
            isStacking = false;
            stackedCardsToDraw = 0;
            playersToBlock = 0;
            cardsPlayed = 0;
        }
        return lastAction;
    }

}
