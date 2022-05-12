package model;

import model.Cards.Card;

import java.util.Comparator;
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

    public void drawCard(Card drawedCard){  hand.push(drawedCard);  }

    public Card playCard(){  return hand.pop();  }

    public Stack<Card> getHand(){
        return hand;
    }

    public List<Card> getPlayableCards(Card check)
    {
        return hand.stream().filter(card -> card.isPlayable(check)).collect(Collectors.toList());
    }

    public abstract void shoutUno();
}
