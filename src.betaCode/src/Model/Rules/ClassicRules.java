package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Exceptions.NoSelectedColorException;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;

import java.util.List;

public class ClassicRules extends UnoGameRules
{
    public ClassicRules()
    {
        cardsDistribution = Deck.classicRules;
        stackableCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        //if there is at least one not wild card and one wild card all the wild cards are not playables
        if(playerPlayableHand.stream().anyMatch(card -> card.getColor()!= CardColor.WILD) && playerPlayableHand.stream().anyMatch(card -> card.getColor()==CardColor.WILD))
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!=CardColor.WILD).toList();
        return playerPlayableHand;
    }
    @Override
    public void cardActionPerformance(Options parameters) throws NoSelectedColorException {

        TurnManager turnManager = parameters.getTurnManager();
        Card lastCard = turnManager.getLastCardPlayed();

        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
        {
            //assert (parameters.getColor() != null):"No color provided";
            Player current = parameters.getPlayers()[turnManager.getPlayer()];
            CardColor color = parameters.getColor();
            if (color == null) {
                if (current instanceof HumanPlayer) throw new NoSelectedColorException();
                else
                    color = ((AIPlayer) current).chooseBestColor();
            }
            ((WildAction) lastCard).changeColor(turnManager, color);
        }
        if(lastCard instanceof DrawCard)
            parameters.getPlayers()[turnManager.next()].drawCards(parameters.getDeck().draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).performReverseAction(turnManager);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).performSkipAction(turnManager);

        turnManager.passTurn();
    }

    @Override
    public void oldCardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {
        Card lastCard = turnManager.getLastCardPlayed();

        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
        {
            //color deve essere preso come scelta dell'utente
            CardColor color = CardColor.RED;
            ((WildAction) lastCard).changeColor(turnManager, color);
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).performReverseAction(turnManager);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).performSkipAction(turnManager);

        turnManager.passTurn();
    }

}
