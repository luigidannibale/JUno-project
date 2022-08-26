package Model.Rules;

import Model.Cards.Card;
import Model.Deck;
import Model.Cards.CardValue;
import Model.Player.Player;
import Model.TurnManager;
import Model.UnoGameTable;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public abstract class UnoGameRules {

    /**
     * @see Deck
     */
    protected HashMap<CardValue, Integer> cardsDistribution;
    /**
     * If more cards of a same value can be stacked by a single player in a single turn.
     *///stackable card can be replaced controlling if number of playable cards is one or ore than one.
    protected boolean stackableCards;
    /**
     * How many cards can be played in a single turn,<br/>
     * to play more than a card in a single turn cards must be the same value.
     */
    protected int numberOfPlayableCards;
    /**
     * How many cards are distributed to each player at the start of the game.
     */
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

    public abstract void oldCardActionPerformance(TurnManager turnManager, Player[] players, Deck deck);
    public abstract void cardActionPerformance(Options parameters);
}
