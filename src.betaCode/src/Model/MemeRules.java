package Model;

import Model.Cards.Card;
import Model.Enumerations.CardValue;

import java.util.HashMap;

public class MemeRules extends UnoGameRules{

    public MemeRules(){
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.SKIP,4);
            put(CardValue.DRAW,4);
            put(CardValue.REVERSE,4);
            put(CardValue.WILD,4);
            put(CardValue.WILD_DRAW,8);
        }});
        stackableCards = true;
        numberOfPlayableCards = 5;
        numberOfCardsPerPlayer = 11;
    }

    @Override
    public Card[] getPlayableCards(Card[] playerPlayableHand, Card discardsPick) {

        return new Card[0];
    }
}
