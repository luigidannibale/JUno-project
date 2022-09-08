package Model.Player;

import Model.Cards.Card;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Class used to model a generic Uno player, <br/>
 * provides methods to make basic plays during the game.
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public abstract class Player 
{
    protected String name;
    protected Stack<Card> hand;
    protected int points;
    protected boolean saidOne;
    protected boolean drew;
    protected boolean played;
    protected boolean isBlocked;

    /**
     * Creates a {@link Player} with empty hand, 0 points and all other attributes false.
     * @param name
     */
    protected Player(String name)
    {
        this.name = name;
        resetPlayer();
    }

    /**
     * Adds the drawn {@link Card} to the hand of the {@link Player}
     * @param drawnCards
     */
    public void drawCard(Card drawnCards)
    {
        hand.push(drawnCards);
        saidOne = false;
    }

    /**
     * Adds the drawn {@link Card} to the hand of the {@link Player}
     * @param drawnCards
     */
    public void drawCards(List<Card> drawnCards)
    {
        hand.addAll(drawnCards);
        saidOne = false;
    }

    /**
     * Removes the played {@link Card} from the hand of the {@link Player}
     * @param played
     */
    public void playCard(Card played)
    {
        hand.remove(played);
        this.played = true;
    }

    /**
     *
     * @return the hand of the {@link Player}
     */
    public Stack<Card> getHand(){ return hand; }

    /**
     * Takes in a new hand and returns the old one, can be used as a setter by ignoring the returned value.
     * @param newHand
     * @return oldHand
     */
    public Stack<Card> swapHand(Stack<Card> newHand)
    {
        var oldHand = hand;
        hand = newHand;
        return oldHand;
    }

    /**
     *
     * @param check
     * @return all the valid {@link Card} in the hand, (validity concept is described in {@link Card} class)
     */
    public List<Card> getValidCards(Card check) { return hand.stream().filter(card -> card.isValid(check)).collect(Collectors.toList()); }

    /**
     * Resets the {@link Player} attributes to creation, empty hand, 0 points and all other attributes false.
     */
    public void resetPlayer()
    {
        hand = new Stack<>();
        drew = false;
        isBlocked = false;
        saidOne = false;
        points = 0;
    }

    public String getName(){ return name; }

    public void setName(String name) {this.name = name;}

    public void shoutUno() { saidOne = true; }

    public boolean hasSaidOne(){ return saidOne; }

    public boolean hasDrew(){ return drew; }

    public void setDrew(boolean drew) {this.drew = drew;}

    public void setPlayed(boolean played) { this.played = played; }

    public boolean hasPlayed() { return played; }

    /**
     * @return true if the player has only one {@link Card} left
     */
    public boolean hasOne(){ return hand.size() == 1; }

    public void setBlocked(boolean isBlocked) {this.isBlocked = isBlocked;}

    public boolean isBlocked() {return isBlocked;}

    public int getPoints() { return points; }

    public void updatePoints(int points) { this.points += points; }

    /**
     * @return name - points
     */
    @Override
    public String toString(){ return name + "-" + points; }

}
