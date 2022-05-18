package Model.Player;

import Model.Cards.Card;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class Player {

    //protected boolean saidOne=false;
    protected String name;
    protected Stack<Card> hand;

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

    public List<Card> getPlayableCards(Card check)
    {
        return hand.stream().filter(card -> card.isPlayable(check)).collect(Collectors.toList());
    }

    public String getName(){ return name; }

    public abstract void shoutUno();

    @Override
    public String toString(){
        return name;
    }
}
