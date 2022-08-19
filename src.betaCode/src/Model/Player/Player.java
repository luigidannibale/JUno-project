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
    protected boolean saidOne=false;

    protected Player(String name){
        this.name = name;
        hand = new Stack<>();
    }

    public void drawCard(Card drawedCard){
        hand.push(drawedCard);
    }

    public void drawCards(List<Card> cards){
        hand.addAll(cards);
    }

    public void playCard(Card played){
        hand.remove(played);
    }

    public Stack<Card> getHand(){
        return hand;
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


    @Override
    public String toString(){
        return name;
    }
}
