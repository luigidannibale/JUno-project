package Model.Cards;

/**
 * Class used to model a Uno card : Skip <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class SkipCard extends ActionCard implements SkipAction
{
    /**
     * Creates a {@link SkipCard} by a {@link Color}. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     */
    public SkipCard(Color color) { super(color, Value.SKIP); }
}
