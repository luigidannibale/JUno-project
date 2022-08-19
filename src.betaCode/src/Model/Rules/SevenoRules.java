package Model.Rules;

import Model.Cards.Card;
import Model.Deck;
import Model.Cards.CardValue;
import Model.Player.Player;
import Model.TurnManager;
import Model.UnoGameTable;

import java.util.HashMap;
import java.util.List;

public class SevenoRules extends UnoGameRules{

    public SevenoRules()
    {
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
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    { return playerPlayableHand; }
    @Override
    public void cardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {}

    @Override
    public void cardActionPerformance(UnoGameTable gameTable) {

    }
}
