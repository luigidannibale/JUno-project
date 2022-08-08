package Model;

import Model.Cards.*;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Exceptions.NoMoreDeckException;

import java.util.*;

public class Deck {
    /**
     * creates a classic deck, with 108 cards, described in data organization
     *
     * -4 wild
     * -4 wild draw
     * then for each color:
     *   -1 zero card
     *   -2 cards for each value 1 to 9
     *   -2 card draw
     *   -2 card skip
     *   -2 card reverse
     * */
    public Deck() {
        deck = new Stack<>();
        createDeck(classicRules);
        shuffle();
    }
    Stack<Card> deck;
    public static final HashMap<CardValue,Integer> classicRules = new HashMap<>(){
        {
            put(CardValue.ZERO,1);
            put(CardValue.ONE,2);
            put(CardValue.TWO,2);
            put(CardValue.THREE,2);
            put(CardValue.FOUR,2);
            put(CardValue.FIVE,2);
            put(CardValue.SIX,2);
            put(CardValue.SEVEN,2);
            put(CardValue.EIGHT,2);
            put(CardValue.NINE,2);
            put(CardValue.SKIP,2);
            put(CardValue.DRAW,2);
            put(CardValue.REVERSE,2);
            put(CardValue.WILD,4);
            put(CardValue.WILD_DRAW,4);
        }};

    /**
     *
     * @param numberOfCards:
     *                     for each card value it must be specified how many instances of that card the deck has to contain
     *
     */

    public Deck(HashMap<CardValue,Integer> numberOfCards)
    {
        if(numberOfCards.size() != 15) throw new IllegalArgumentException("Specify one number for each of the cards value in the deck creation");
        deck = new Stack<>();
        createDeck(numberOfCards);
        shuffle();
    }

    public void createDeck(HashMap<CardValue,Integer> rules)
    {
        for (CardColor color : CardColor.values())
        {
            if (color == CardColor.WILD)
            {
                addManyCards(color,CardValue.WILD,rules.get(CardValue.WILD));
                addManyCards(color,CardValue.WILD_DRAW,rules.get(CardValue.WILD_DRAW));
            }
            else
            {
                for (CardValue value : Arrays.stream(CardValue.values()).filter(cardValue ->
                    !(cardValue == CardValue.WILD || cardValue == CardValue.WILD_DRAW)).toList())
                    addManyCards(color, value, rules.get(value));
            }
        }
    }


    private void addManyCards(CardColor color, CardValue value, int numberOfCards)
    {
        for (int howMany = numberOfCards; howMany > 0; howMany--)
            deck.add(CardBuilder.createCard(color, value));
    }

    public Stack<Card> getDeck() {return deck; }

    public Card draw() {
        if(deck.size() > 1) return deck.pop();
        //else throw new NoMoreDeckException();
        else return null;
    }

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
     /*for (CardColor c : CardColor.values())
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
        }//deck creation ends*/



}
