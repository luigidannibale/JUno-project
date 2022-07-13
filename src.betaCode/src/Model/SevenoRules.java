package Model;

import Model.Cards.Card;
import Model.Enumerations.CardValue;

import java.util.HashMap;

public class SevenoRules extends UnoGameRules{

    public SevenoRules(){
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.ZERO,2);
            put(CardValue.SEVEN,3);
        }});
        stackableCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }

    @Override
    public Card[] getPlayableCards(Card[] playerPlayableHand, Card discardsPick) {
        return new Card[0];
    }
}
