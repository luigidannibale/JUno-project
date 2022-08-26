package Model.Player;

import Model.Cards.Card;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class Player 
{
    protected String name;
    protected Stack<Card> hand;

    //ogni inizio turno deve tornare ad essre false
    protected boolean saidOne = false;
    protected boolean hasDrew = false;
    protected boolean hasOne = false;

    protected Player(String name){
        this.name = name;
        hand = new Stack<>();
    }

    public void drawCard(Card drewCard){
        hand.push(drewCard);
        hasOne = false;
        saidOne = false;
    }

    public void drawCards(List<Card> cards){
        hand.addAll(cards);
        hasOne = false;
        saidOne = false;
    }

    public void playCard(Card played){
        hand.remove(played);
        if (hand.size() == 1) hasOne = true;
    }

    public Stack<Card> getHand(){
        return hand;
    }

    public void setHand(Stack<Card> newHand){
        hand = newHand;
        hasOne = hand.size() == 1;
        saidOne = false;
    }

    public Stack<Card> swapHand(Stack<Card> newHand)
    {
        var oldHand = hand;
        hand = newHand;
        return oldHand;
    }

    public List<Card> getValidCards(Card check)
    {
        return hand.stream().filter(card -> card.isValid(check)).collect(Collectors.toList());
    }

    public String getName(){ return name; }

    public void shoutUno()
    { saidOne = true; }

    public void setHasDrew(boolean hasDrew) {this.hasDrew = hasDrew;}
    public void setSaidOne(boolean saidOne) {this.saidOne = saidOne;}

    public boolean HasDrew(){ return hasDrew; }
    public boolean HasOne(){ return hasOne; }
    public boolean HasSaidOne(){ return saidOne; }

    @Override
    public String toString(){
        return name;
    }
}
