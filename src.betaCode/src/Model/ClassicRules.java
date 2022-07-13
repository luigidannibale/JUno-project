package Model;

import Model.Cards.Card;
import Model.Enumerations.CardColor;

import java.util.Arrays;
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
    public Card[] getPlayableCards(Card[] playerPlayableHand, Card discardsPick) {
        //return playerPlayableHand;
        //List<Card> playableCards = Arrays.stream(playerPlayableHand).toList();
        if(Arrays.stream(playerPlayableHand).anyMatch(card -> card.getColor()!= CardColor.WILD) && Arrays.stream(playerPlayableHand).anyMatch(card -> card.getColor()==CardColor.WILD))
        {//if there is at least one not wild card and one wild card all the wild cards are not playables
            playerPlayableHand = (Card[]) Arrays.stream(playerPlayableHand).filter(card -> card.getColor()!=CardColor.WILD).toArray();
        }
        return playerPlayableHand;
    }
}
