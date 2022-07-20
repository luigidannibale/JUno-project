package Model.Rules;

import Model.Cards.Card;
import Model.Cards.CardBuilder;
import Model.Cards.DrawCard;
import Model.Cards.ReverseCard;
import Model.Deck;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Interfaces.SkipAction;
import Model.Interfaces.WildAction;
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
    public abstract void cardActionPerformance(TurnManager turnManager,Player[] players, Deck deck);


}
