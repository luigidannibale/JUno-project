package Model.Rules;

import Model.Cards.Card;
import Model.Cards.Color;
import Model.DeckManager;

import java.util.List;

/**
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ClassicRules extends UnoGameRules
{
    public ClassicRules()  { super(7,1,DeckManager.CLASSIC_RULES_CARD_DISTRIBUTION); }

    @Override
    public ActionPerformResult performFirstCardAction(Options parameters)  { return super.cardActionPerformance(parameters); }


    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        if(playerPlayableHand.stream().anyMatch(card -> card.getColor()!= Color.WILD) && playerPlayableHand.stream().anyMatch(card -> card.getColor()== Color.WILD))
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!= Color.WILD).toList();
        return playerPlayableHand;
    }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        var actionPerformResult = super.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) passTurn(parameters.getTurnManager(), parameters.getPlayers()[parameters.getCurrentPlayer()]);
        return actionPerformResult;
    }

}
