package Model;

import Model.Cards.Card;
import Model.Cards.Color;
import Model.Cards.CardFactory;
import Model.Cards.Value;

import java.util.*;

/**
 * Class used to manage a deck of cards of a Uno Game. <br/>
 * Deck is composed basing on a card distribution,
 * each {@link Value} is associated with the number of instances for each {@link Color}.
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
    public static final HashMap<Value,Integer> CLASSIC_RULES_CARD_DISTRIBUTION = new HashMap<>()
    {
        {
            put(Value.ZERO,1);
            put(Value.ONE,2);
            put(Value.TWO,2);
            put(Value.THREE,2);
            put(Value.FOUR,2);
            put(Value.FIVE,2);
            put(Value.SIX,2);
            put(Value.SEVEN,2);
            put(Value.EIGHT,2);
            put(Value.NINE,2);
            put(Value.SKIP,2);
            put(Value.DRAW,2);
            put(Value.REVERSE,2);
            put(Value.WILD,4);
            put(Value.WILD_DRAW,4);
        }};

    /**
     * Creates a {@link DeckManager} with a deck with classic Uno cards distribution
     * */
    public DeckManager() { this(CLASSIC_RULES_CARD_DISTRIBUTION); }

    /**
    *
    * @param numberOfCards:
    *                     it is an {@link HashMap} that maps a {@link Value} to an integer. <br/>
    *                     For each card value must be specified the corresponding
    *                     number of cards that have to assume that value.
    * @throws IllegalArgumentException : If the size of the numerOfCards is not 15.
    */
    public DeckManager(HashMap<Value,Integer> numberOfCards) throws IllegalArgumentException
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
    private void createDeck(HashMap<Value,Integer> cardsDistribution)
    {
        Arrays.stream(Color.values()).forEach(color ->
        {
            if (color == Color.WILD)
            {//adds the wild cards
                addManyCards(color, Value.WILD, cardsDistribution.get(Value.WILD));
                addManyCards(color, Value.WILD_DRAW, cardsDistribution.get(Value.WILD_DRAW));
            }
            //adds the red, blue, green and yellow cards
            else
                Arrays.stream(Value.values()).filter(cardValue -> !(cardValue == Value.WILD || cardValue == Value.WILD_DRAW)).toList().forEach(value -> addManyCards(color, value, cardsDistribution.get(value)));
        });
    }

    /**
     *
     * Actually adds more instances of a card to the deck
     * @param color : the {@link Color} of the {@link Card}
     * @param value : the {@link Value} of the {@link Card}
     * @param numberOfCards : how many instances of the card have to be added
     */
    private void addManyCards(Color color, Value value, int numberOfCards)
    {
        for (int howMany = numberOfCards; howMany > 0; howMany--)
            deck.add(CardFactory.createCard(color, value));
    }

    /**
     * Pops the first card of the deck
     * @return Card : the {@link Card} drawn out
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
        ArrayList<Card> drawn = new ArrayList<>();
        for (int i = 0; i < cardsToDraw; i++)
        {
            Card drawedCard = draw();
            drawn.add(drawedCard);
        }
        return drawn;
    }

    /**
     *
     * @return the deck
     */
    public Stack<Card> getDeck() { return deck; }

    /**
     * @return the size of the Deck
     */
    public int size() { return deck.size(); }

    /**
     * @return the {@link Card} on top of the deck
     */
    public Card peekDeck() { return deck.peek(); }

    /**
     * Shuffles the deck
     */
    public void shuffle() { Collections.shuffle(deck); }

    /**
     * Builds the deck from the discards {@link Stack}, <br/>
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

    /**
     * Puts a {@link Card} on top on discards {@link Stack}
     * @param card
     */
    public void pushDiscards(Card card) { discards.push(card); }

    /**
     * @return the {@link Card} on top of the discards {@link Stack}
     */
    public Card peekDiscards() { return discards.peek(); }

}
