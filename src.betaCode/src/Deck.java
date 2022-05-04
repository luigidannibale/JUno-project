import Cards.*;
import Enumerations.CardColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.Stack;

public class Deck {
    Stack<Card> deck;

    public Deck(String deckName)
    {
        deck = new Stack<>();
        //implementazione
        //ma vogliamo leggere i file qui?
        for (CardColor c : CardColor.values()){
            if (c == CardColor.WILD) continue;
            int k = 14*c.getIntValue();
            int skip1=11+k,skip2=67+k;
            int rev1=12+k,rev2=68+k;
            int draw1=13+k,draw2=69+k;

            deck.addAll(Arrays.asList(new SkipCard(c, null), new SkipCard(c, null)));
            deck.addAll(Arrays.asList(new ReverseCard(c, null), new ReverseCard(c, null)));
            deck.addAll(Arrays.asList(new DrawCard(c, null), new DrawCard(c, null)));
        }

        for (int i = 0; i < 4; i++){
            deck.addAll(Arrays.asList(new WildCard(null), new DrawCard(CardColor.WILD, null)));
        }
    }

    public Card draw() { return deck.pop(); }

    public ArrayList<Card> draw(int cardsToDraw)
    {
        ArrayList<Card> cardsDrawed = new ArrayList<>();
        for (int i = 0; i < cardsToDraw; i++)
            cardsDrawed.add(deck.pop());
        return cardsDrawed;
    }

    public void shuffle() { Collections.shuffle(deck); }
}
