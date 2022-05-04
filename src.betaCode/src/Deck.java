import Cards.*;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Stack;

public class Deck {
    Stack<Card> deck;

    public Deck()
    {
        deck = new Stack<>();
        //implementazione
        //ma vogliamo leggere i file qui?
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
