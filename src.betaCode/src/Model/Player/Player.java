package Model.Player;

import Model.Cards.Card;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class Player 
{
    protected String name;
    protected Stack<Card> hand;
    protected boolean saidOne = false;
    protected boolean drew = false;
    protected boolean isBlocked;

    protected Player(String name)
    {
        this.name = name;
        hand = new Stack<>();
    }

    /**
     * Adds the drew card to the hand of the player
     * @param drewCard
     */
    public void drawCard(Card drewCard) { hand.push(drewCard); }

    /**
     * Adds the drew cards to the hand of the player
     * @param drewCards
     */
    public void drawCards(List<Card> drewCards){ hand.addAll(drewCards); }

    /**
     * Removes the played card from the hand of the player
     * @param played
     */
    public void playCard(Card played){ hand.remove(played); }

    /**
     *
     * @return the hand of the player
     */
    public Stack<Card> getHand(){ return hand; }

    /**
     * Takes in the new hand and returns the old one
     * @param newHand
     * @return oldHand
     */
    public Stack<Card> swapHand(Stack<Card> newHand)
    {
        var oldHand = hand;
        hand = newHand;
        return oldHand;
    }

    public List<Card> getValidCards(Card check) { return hand.stream().filter(card -> card.isValid(check)).collect(Collectors.toList()); }

    public String getName(){ return name; }

    public void setName(String name) {this.name = name;}

    public void shoutUno() { saidOne = true; }

    public void setDrew(boolean drew) {this.drew = drew;}

    public void setBlocked(boolean isBlocked) {this.isBlocked = isBlocked;}

    public void falseSaidOne(){ saidOne = false; }

    public boolean hasDrew(){ return drew; }

    public boolean hasOne(){ return hand.size() == 1; }

    public boolean hasSaidOne(){ return saidOne; }

    public boolean isBlocked() {return isBlocked;}


    @Override
    public String toString(){ return name; }
}
