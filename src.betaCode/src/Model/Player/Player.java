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
    protected boolean drew = false;
    protected boolean incepped = false;

    protected Player(String name){
        this.name = name;
        hand = new Stack<>();
    }

    public void drawCard(Card drewCard){
        hand.push(drewCard);
        saidOne = false;
    }

    public void drawCards(List<Card> cards){
        hand.addAll(cards);
        saidOne = false;
    }

    public void playCard(Card played){ hand.remove(played); }

    public Stack<Card> getHand(){ return hand; }

    public void setHand(Stack<Card> newHand){
        hand = newHand;
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
    public void setName(String name) {this.name = name;}
    public void shoutUno() { saidOne = true; }
    public void setDrew(boolean drew) {this.drew = drew;}
    public void setIncepped(boolean incepped) {this.incepped = incepped;}

    public boolean hasDrew(){ return drew; }
    public boolean hasOne(){ return hand.size() == 1; }
    public boolean hasSaidOne(){ return saidOne; }
    public boolean isIncepped() {return incepped;}
    @Override
    public String toString(){
        return name;
    }
}
