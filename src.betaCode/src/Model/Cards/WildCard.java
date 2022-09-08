package Model.Cards;

/**
 * Class used to model a Uno card : Wild             <br/>
 * Specialize the generic class {@link Card}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class WildCard extends ActionCard implements WildAction
{
    /**
     * Creates a {@link WildCard}. <br/>
     * It's recommendable to use {@link CardFactory}
     */
    public WildCard() { super(Color.WILD, Value.WILD); }

    /**
     * Creates a {@link WildCard} by a {@link Color}, used to create flow cards. <br/>
     * It's recommendable to use {@link CardFactory}
     * @param color
     */
    public WildCard(Color color) {super(color, Value.WILD);}
}
