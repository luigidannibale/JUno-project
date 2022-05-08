package model;

import model.Cards.*;
import model.Enumerations.CardColor;
import model.Enumerations.CardValue;

import java.util.*;

public class Deck {
    Stack<Card> deck;

    public Deck()
    {
        deck = new Stack<>();
        /*
        * creates a classic deck, with 108 cards, described in data organization
        * */
        for (CardColor c : CardColor.values())
        {//deck creation starts
            if (c == CardColor.WILD) continue;
            for (CardValue v : CardValue.values()) {
                switch (v) {
                    case DRAW -> deck.addAll(Arrays.asList(new DrawCard(c), new DrawCard(c)));
                    case SKIP -> deck.addAll(Arrays.asList(new SkipCard(c), new SkipCard(c)));
                    case REVERSE -> deck.addAll(Arrays.asList(new ReverseCard(c), new ReverseCard(c)));
                    case WILD -> deck.add(new WildCard());
                    case WILD_DRAW -> deck.add(new DrawCard(CardColor.WILD));
                    case ZERO -> deck.add(new Card(c, v));
                    default -> deck.addAll(Arrays.asList(new Card(c, v), new Card(c, v)));
                }
            }
        }//deck creation ends
        shuffle();
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

    public Stack<Card> getDeck() {return deck; }
}
