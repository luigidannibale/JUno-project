package Model;

import Model.Enumerations.CardValue;

import java.util.HashMap;

public class UnoGameRules {

    HashMap<CardValue, Integer> cardsDistribution;
    boolean stackableActionCards;
    int numberOfPlayableCards;
    int numberOfCardsPerPlayer;

    public UnoGameRules()
    {
        cardsDistribution = Deck.classicRules;
        stackableActionCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;

    }


    public int getNumberOfCardsPerPlayer() {
        return numberOfCardsPerPlayer;
    }

    public void setNumberOfCardsPerPlayer(int numberOfCardsPerPlayer) {
        this.numberOfCardsPerPlayer = numberOfCardsPerPlayer;
    }

    public HashMap<CardValue, Integer> getCardsDistribution() {
        return cardsDistribution;
    }

    public void setCardsDistribution(HashMap<CardValue, Integer> cardsDistribution) {
        this.cardsDistribution = cardsDistribution;
    }

    public boolean isStackableActionCards() {
        return stackableActionCards;
    }

    public void setStackableActionCards(boolean stackableActionCards) {
        this.stackableActionCards = stackableActionCards;
    }

    public int getNumberOfPlayableCards() {
        return numberOfPlayableCards;
    }

    public void setNumberOfPlayableCards(int numberOfPlayableCards) {
        this.numberOfPlayableCards = numberOfPlayableCards;
    }


}
