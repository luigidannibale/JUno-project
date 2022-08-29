package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Exceptions.NoSelectedColorException;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.TurnManager;

import java.util.ArrayList;
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
        if(playerPlayableHand.stream().anyMatch(card -> card.getColor()!= CardColor.WILD) && playerPlayableHand.stream().anyMatch(card -> card.getColor()==CardColor.WILD))
            //if there is at least one not wild card and one wild card all the wild cards are not playables
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!=CardColor.WILD).toList();
        return playerPlayableHand;
    }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        var actionPerformResult = super.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) parameters.getTurnManager().passTurn();
        return actionPerformResult;
    }
}
