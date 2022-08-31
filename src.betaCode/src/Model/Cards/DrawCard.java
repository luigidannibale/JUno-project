package Model.Cards;
import Model.Deck;
import Model.Player.Player;

/**
 * Class used to model a Uno card : Draw  <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class DrawCard extends ActionCard implements WildAction, SkipAction
{
    private int numberOfCardsToDraw;
    /**
     * Creates a draw card
     * @param color
     * @param numberOfCardsToDraw
     */
    public DrawCard(CardColor color, int numberOfCardsToDraw)
    {
        super(color, color == CardColor.WILD? CardValue.WILD_DRAW : CardValue.DRAW);
        setNumberOfCardsToDraw(numberOfCardsToDraw);
    }
    /**
     * Creates a draw card, automatically calculates the number of card to draw
     * @param color
     */
    public DrawCard(CardColor color) { this(color,color == CardColor.WILD ? 4 : 2); }
    public DrawCard(CardColor color,CardValue value)
    {
        //if !(value == CardValue.DRAW || value == CardValue.WILD_DRAW) throw new IllegalArgumentException("");
        super(color, value);
        setNumberOfCardsToDraw(value == CardValue.DRAW? 2 : 4);
    }
    public int getNumberOfCardsToDraw() { return numberOfCardsToDraw; }
    public void setNumberOfCardsToDraw(int numberOfCardsToDraw) { this.numberOfCardsToDraw = numberOfCardsToDraw; }
    public void performDrawAction(Player player, int cardsToDraw, Deck deck) { player.drawCards(deck.draw(cardsToDraw)); }
}
