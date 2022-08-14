package Model.Cards;

/**
 * Class used to model a Uno card : Skip <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class SkipCard extends ActionCard implements SkipAction
{
    /**
     * Creates a skip card
     * @param color
     */
    public SkipCard(CardColor color) {super(color, CardValue.SKIP);}
}
