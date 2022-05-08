package model;

import model.Cards.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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

    public List<Card> getPlayableCards(Card card){
        List<Card> playableCard = new ArrayList<>(hand);

        /*
        Iterator<Card> i = playableCard.iterator();
        while(i.hasNext()){
            Card c = i.next();
            if (c.getColor() != card.getColor()) i.remove();
        }*/
        playableCard.removeIf(c -> c.getColor() != card.getColor());

        return playableCard;
    }

    public abstract void shoutUno();
}
