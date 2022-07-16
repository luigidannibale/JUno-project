package Model.Rules;

import Model.Cards.Card;
import Model.Deck;
import Model.Enumerations.CardValue;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;

public abstract class UnoGameRules {

    protected HashMap<CardValue, Integer> cardsDistribution;
    protected boolean stackableCards;
    protected int numberOfPlayableCards;
    protected int numberOfCardsPerPlayer;


    public int getNumberOfCardsPerPlayer() {
        return numberOfCardsPerPlayer;
    }

    public HashMap<CardValue, Integer> getCardsDistribution() {
        return cardsDistribution;
    }

    public boolean isStackableCards() {
        return stackableCards;
    }

    public int getNumberOfPlayableCards() {
        return numberOfPlayableCards;
    }

    public abstract List<Card> getPlayableCards(List<Card> playerHand, Card discardsPick);
    public abstract void cardActionPerformance(TurnManager turnManager, Card cardToPlay,Player[] players, Deck deck);





}
