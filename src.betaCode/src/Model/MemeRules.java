package Model;

import Model.Cards.Card;
import Model.Enumerations.CardValue;

import java.util.HashMap;

public class MemeRules extends UnoGameRules{

    public MemeRules(){
        cardsDistribution = Deck.classicRules;
                cardsDistribution = new HashMap<CardValue,Integer>(){
            {
                put(CardValue.ZERO,1);
                put(CardValue.ONE,2);
                put(CardValue.TWO,2);
                put(CardValue.THREE,2);
                put(CardValue.FOUR,2);
                put(CardValue.FIVE,2);
                put(CardValue.SIX,2);
                put(CardValue.SEVEN,2);
                put(CardValue.EIGHT,2);
                put(CardValue.NINE,2);
                put(CardValue.SKIP,4);
                put(CardValue.DRAW,4);
                put(CardValue.REVERSE,4);
                put(CardValue.WILD,4);
                put(CardValue.WILD_DRAW,8);
            }};

        stackableCards = true;
        numberOfPlayableCards = 5;
        numberOfCardsPerPlayer = 11;
    }

    @Override
    public Card[] getPlayableCards(Card[] playerHand, Card discardsPick) {
        return new Card[0];
    }
}
