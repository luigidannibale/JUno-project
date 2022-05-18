package Model;

import Model.Cards.*;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

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

    public Stack<Card> getDeck() {return deck; }

    public Card draw() { return deck.pop(); }

    public void push(Card card) { deck.push(card); }

    public ArrayList<Card> draw(int cardsToDraw)
    {
        ArrayList<Card> drawed = new ArrayList<>();
        for (int i = 0; i < cardsToDraw; i++)
            drawed.add(deck.pop());
        return drawed;
    }

    public int size(){
        return deck.size();
    }

    public Card peek(){
        return deck.peek();
    }

    public void shuffle() { Collections.shuffle(deck); }

    public void re_shuffle(Stack<Card> discards){
        deck = discards;
        shuffle();
    }
}
