package Model.Rules;

import Model.Cards.Card;
import Model.Deck;
import Model.Enumerations.CardValue;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;

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
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick) {

        return playerPlayableHand;
    }

    public void cardActionPerformance(TurnManager turnManager, Card cardToPlay, Player[] players, Deck deck){

    }
}
