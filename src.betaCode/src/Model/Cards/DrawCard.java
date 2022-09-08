package Model.Cards;
import Model.DeckManager;
import Model.Players.Player;

/**
 * Class used to model a Uno card : Draw  <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class DrawCard extends ActionCard implements WildAction, SkipAction
{
    private int numberOfCardsToDraw;
    /**
     * Creates a {@link DrawCard} with a {@link Color} and the number of cards to draw associated,<br/>
     * automatically detects the {@link Value}. <br/>
     *
     * It's recommendable to use {@link CardFactory}
     * @param color
     * @param numberOfCardsToDraw
     */
    public DrawCard(Color color, int numberOfCardsToDraw)
    {
        super(color, color == Color.WILD? Value.WILD_DRAW : Value.DRAW);
        setNumberOfCardsToDraw(numberOfCardsToDraw);
    }
    /**
     * Creates a {@link DrawCard} with a {@link Color} <br/>
     * automatically calculates the {@link Value}, but must assign number of cards to draw. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     */
    public DrawCard(Color color) { this(color,color == Color.WILD ? 4 : 2); }

    /**
     * Creates a {@link DrawCard} by a {@link Color} and a {@link Value}<br/>
     * automatically calculates the number of cards to draw. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     */
    public DrawCard(Color color, Value value)
    {
        super(color, value);
        setNumberOfCardsToDraw(value == Value.DRAW? 2 : 4);
    }

    public int getNumberOfCardsToDraw() { return numberOfCardsToDraw; }
    public void setNumberOfCardsToDraw(int numberOfCardsToDraw) { this.numberOfCardsToDraw = numberOfCardsToDraw; }

    /**
     * Performs the draw action, the {@link Player} draws the amount of cards specified from the {@link DeckManager}
     * @param player
     * @param cardsToDraw
     * @param deckManager
     */
    public void performDrawAction(Player player, int cardsToDraw, DeckManager deckManager)
    { player.drawCards(deckManager.draw(cardsToDraw)); }
}
