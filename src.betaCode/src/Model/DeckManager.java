package Model;

import Model.Cards.Card;
import Model.Cards.CardColor;
import Model.Cards.CardFactory;
import Model.Cards.CardValue;

import java.util.*;

/**
 * Class used to manage a deck of cards of a Uno Game. <br/>
 * Deck is composed basing on a card distribution,
 * each {@link CardValue} is associated with the number of instances for each {@link CardColor}.
 *
 * @see Stack<Card>
 * @author D'annibale Luigi, Venturini Daniele
 */
public class DeckManager
{
    private Stack<Card> deck;
    private Stack<Card> discards;
    /**
     * <strong>Classic card distrbution of Uno</strong> <br/>
     *108 cards distributed so:
     * <ul>
     *     <li>4 Cards Wild</li>
     *     <li>4 Cards Wild Draw</li>
     *     <li>
     *         Then for each color:
     *         <ul>
     *            <li>1 Zero Card</li>
     *            <li>2 Cards for each value from 1 to 9</li>
     *            <li>2 Cards draw</li>
     *            <li>2 Cards skip</li>
     *            <li>2 Cards reverse</li>
     *         </ul>
     *     </li>
     * </ul>
     * */
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
     * Creates a deck with classic Uno cards distribution
     * */
    public DeckManager() { this(classicRules); }
    /**
    *
    * @param numberOfCards:
    *                     it is an {@link HashMap} that maps a {@link CardValue} to an integer. <br/>
    *                     For each card value must be specified the corresponding
    *                     number of cards that have to assume that value.
    * @throws IllegalArgumentException : If the size of the numerOfCards is not 15.
    */
    public DeckManager(HashMap<CardValue,Integer> numberOfCards)
    {
        assert (numberOfCards.size() == 15):"Specify one number for each of the CardValue in the deck creation";
        deck = new Stack<>();
        discards = new Stack<>();
        createDeck(numberOfCards);
        shuffle();
    }
    /**
     * Actually performs the creation of the deck: <br/>
     * puts in the deck, all the cards, basing on the cards distribution
     *
     * @param cardsDistribution
     */
    private void createDeck(HashMap<CardValue,Integer> cardsDistribution)
    {
        //adds the wild cards
        Arrays.stream(CardColor.values()).forEach(color ->
        {
            if (color == CardColor.WILD)
            {
                addManyCards(color, CardValue.WILD, cardsDistribution.get(CardValue.WILD));
                addManyCards(color, CardValue.WILD_DRAW, cardsDistribution.get(CardValue.WILD_DRAW));
            }
            //adds the red, blue, green and yellow cards
            else
                Arrays.stream(CardValue.values()).filter(cardValue -> !(cardValue == CardValue.WILD || cardValue == CardValue.WILD_DRAW)).toList().forEach(value -> addManyCards(color, value, cardsDistribution.get(value)));
        });
    }
    /**
     *
     * Actually adds more instances of a card to the deck
     * @param color : the {@link CardColor} of the {@link Card}
     * @param value : the {@link CardValue} of the {@link Card}
     * @param numberOfCards : how many instances of the card have to be added
     */
    private void addManyCards(CardColor color, CardValue value, int numberOfCards)
    {
        for (int howMany = numberOfCards; howMany > 0; howMany--)
            deck.add(CardFactory.createCard(color, value));
    }
    /**
     * Pops the first card on the deck
     * @return Card : the {@link Card} drawed out
     */
    public Card draw()
    {
        if (deck.size() == 0) re_shuffle();
        return deck.pop();
    }
    /**
     * Pops the first cards on the deck: <br/>
     * the number of cards to pop is specified as param.
     * @param cardsToDraw: the number of cards to pop
     * @return {@link ArrayList} of {@link Card}
     */
    public ArrayList<Card> draw(int cardsToDraw)
    {
        ArrayList<Card> drawed = new ArrayList<>();
        for (int i = 0; i < cardsToDraw; i++)
        {
            Card drawedCard = draw();
            drawed.add(drawedCard);
        }
        return drawed;
    }
    public Stack<Card> getDeck() { return deck; }
    /**
     * @return the size of the Deck
     */
    public int size() { return deck.size(); }
    /**
     * @return {@link Card} the peek of the deck
     */
    public Card peek() { return deck.peek(); }
    /**
     * Puts a {@link Card} in the deck
     * @param card
     */
    public void push(Card card) { deck.push(card); }
    /**
     * Shuffles the deck
     */
    public void shuffle() { Collections.shuffle(deck); }
    /**
     * Builds the deck from the discards stack, <br/>
     * this method should be called when the deck goes out of bounds.
     */
    private void re_shuffle()
    {
        Card last = discards.pop();
        deck.addAll(discards);
        discards.removeAllElements();
        discards.push(last);
        shuffle();
    }
    public Stack<Card> getDiscards() { return discards; }
    public void pushDiscards(Card card) { discards.push(card); }
    public Card peekDiscards() { return discards.peek(); }

}
