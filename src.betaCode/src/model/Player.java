package model;

import model.Cards.Card;

import java.util.Stack;

public abstract class Player {

    //protected boolean saidOne=false;
    protected String name;
    protected Stack<Card> hand;

    public void drawCard(Card drawedCard){  hand.push(drawedCard);  };
    public Card playCard(){  return hand.pop();  };
    public abstract void shoutUno();
}
