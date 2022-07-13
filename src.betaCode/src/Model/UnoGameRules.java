package Model;

import Model.Cards.Card;
import Model.Enumerations.CardValue;

import java.util.HashMap;

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

    public abstract Card[] getPlayableCards(Card[] playerHand, Card discardsPick);
    //public abstract void playCard(); //da vedere se e come fare





}
