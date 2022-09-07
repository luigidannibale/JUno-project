package Model.Rules;

import Model.Cards.Card;
import Model.Cards.CardColor;
import Model.DeckManager;

import java.util.List;

public class ClassicRules extends UnoGameRules
{
    public ClassicRules()  { super(7,1,DeckManager.classicRules); }

    @Override
    public ActionPerformResult performFirstCardAction(Options parameters)  { return super.cardActionPerformance(parameters); }

    /**
     * if there is at least one not wild card and one wild card all the wild cards are not playables
     * @param playerPlayableHand
     * @param discardsPick
     * @return
     */
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        if(playerPlayableHand.stream().anyMatch(card -> card.getColor()!= CardColor.WILD) && playerPlayableHand.stream().anyMatch(card -> card.getColor()==CardColor.WILD))
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!=CardColor.WILD).toList();
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
