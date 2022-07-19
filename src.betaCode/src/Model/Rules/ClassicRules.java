package Model.Rules;

import Model.Cards.Card;
import Model.Cards.CardBuilder;
import Model.Cards.DrawCard;
import Model.Cards.ReverseCard;
import Model.Deck;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Interfaces.SkipAction;
import Model.Interfaces.WildAction;
import Model.Player.Player;
import Model.TurnManager;

import java.util.List;

public class ClassicRules extends UnoGameRules{

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
        {//if there is at least one not wild card and one wild card all the wild cards are not playables
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!=CardColor.WILD).toList();
        }
        return playerPlayableHand;
    }

    @Override
    public void cardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {
        Card lastCard = turnManager.getLastCardPlayed();
        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD) {
            CardColor c = ((WildAction) lastCard).changeColor();
            turnManager.updateLastCardPlayed(CardBuilder.createFlowCard(c, CardValue.WILD));
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        else if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).reverse(turnManager, players);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).skipTurn(turnManager);
    };

}
