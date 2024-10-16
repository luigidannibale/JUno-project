package Model.Rules;

import Model.Cards.Card;
import Model.Cards.Color;
import Model.DeckManager;

import java.util.List;

/**
 * This class specializes {@link UnoGameRules} to provide the methods of the classic game mode of UNO <br/>
 *
 * <strong>Classic deck composition of Uno</strong> <br/>
 *108 cards distributed so:
 * <ul>
 *     <li>4 Cards Wild</li>
 *     <li>4 Cards Wild Draw</li>
 *     <li>
 *         Then for each color:
 *         <ul>
 *            <li>1 Zero Card</li>
 *            <li>2 Cards for each value from 1 to 9</li>
 *            <li>2 Cards draw</li>
 *            <li>2 Cards skip</li>
 *            <li>2 Cards reverse</li>
 *         </ul>
 *     </li>
 * </ul>
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ClassicRules extends UnoGameRules
{
    /**
     * Creates a new {@link ClassicRules} with the classic cards per player, cards playable and deck composition
     */
    public ClassicRules()  { super(7,1,DeckManager.CLASSIC_RULES_CARD_DISTRIBUTION); }

    /**
     * Performs the default action associated with the first {@link Card} put on the discards after shuffling the deck
     * @param parameters
     * @return the {@link ActionPerformResult} of the first card
     */
    @Override
    public ActionPerformResult performFirstCardAction(Options parameters)  { return super.cardActionPerformance(parameters); }

    /**
     * The player can't play Wilds Cards if he has other playable cards
     * @param playerPlayableHand
     * @param discardsPick
     * @return all the playable {@link Card} cards in the hand
     */
    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        if(playerPlayableHand.stream().anyMatch(card -> card.getColor()!= Color.WILD) && playerPlayableHand.stream().anyMatch(card -> card.getColor()== Color.WILD))
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!= Color.WILD).toList();
        return playerPlayableHand;
    }
    /**
     * Performs the classic actions associated with the {@link Card}.
     * If the {@link ActionPerformResult} is SUCCESSFUL, then the turn is skipped, because the {@link Model.Players.Player} can't play other cards
     * @param parameters
     * @return the {@link ActionPerformResult} of the operation
     */
    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        var actionPerformResult = super.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) passTurn(parameters.getTurnManager(), parameters.getPlayers()[parameters.getCurrentPlayer()]);
        return actionPerformResult;
    }

}
