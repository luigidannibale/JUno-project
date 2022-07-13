package Model;

import Model.Cards.Card;

public class ClassicRules extends UnoGameRules{

    public ClassicRules()
    {
        cardsDistribution = Deck.classicRules;
        stackableCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }

    @Override
    public Card[] getPlayableCards(Card[] playerHand, Card discardsPick) {
        return new Card[0];
    }
}
