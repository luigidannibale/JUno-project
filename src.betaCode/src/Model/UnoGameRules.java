package Model;

import Model.Cards.Card;
import Model.Enumerations.CardValue;

import java.util.HashMap;

public abstract class UnoGameRules {

    protected HashMap<CardValue, Integer> cardsDistribution;
    protected boolean stackableActionCards;
    protected int numberOfPlayableCards;
    protected int numberOfCardsPerPlayer;


    public int getNumberOfCardsPerPlayer() {
        return numberOfCardsPerPlayer;
    }

    public HashMap<CardValue, Integer> getCardsDistribution() {
        return cardsDistribution;
    }

    public boolean isStackableActionCards() {
        return stackableActionCards;
    }

    public int getNumberOfPlayableCards() {
        return numberOfPlayableCards;
    }

    public abstract Card[] getPlayableCards(Card[] playerHand, Card discardsPick);
    //public abstract void playCard(); //da vedere se e come fare





}
