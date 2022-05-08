package model;

import model.Cards.Card;
import model.Enumerations.CardColor;

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

    public List<Card> getPlayableCards(Card check){
        //List<Card> playableCard = hand.stream().filter(card -> card.isPlayable(check)).collect(Collectors.toList());
        List<Card> playableCard = new ArrayList<>();

        //in teoria il +4 si può giocare solo se non si ha nessuna carta dello stesso colore
        for (Card card : hand) {
            if (card.isPlayable(check)) playableCard.add(card);
        }
        return playableCard;
    }

    public abstract void shoutUno();
}
