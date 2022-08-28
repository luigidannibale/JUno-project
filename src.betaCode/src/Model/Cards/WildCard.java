package Model.Cards;

/**
 * Class used to model a Uno card : Wild             <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class WildCard extends ActionCard implements WildAction
{
    /**
     * Class used to model a Uno card : Wild
     */
    public WildCard() { super(CardColor.WILD,CardValue.WILD); }

    public WildCard(CardColor color) {super(color, CardValue.WILD);}
}
