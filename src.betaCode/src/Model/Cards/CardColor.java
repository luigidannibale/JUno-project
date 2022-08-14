package Model.Cards;

/**
 * All the colors of Uno {@link Card},<br/>
 * colors are thought to be comparable, <br/>
 * and the order goes top-down like this:
 * <ul>
 *     <li>RED</li>
 *     <li>YELLOW</li>
 *     <li>GREEN</li>
 *     <li>BLUE</li>
 *     <li>WILD</li>
 * </ul>
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public enum CardColor implements Comparable<CardColor>{
    RED(0),
    YELLOW(1),
    GREEN(2),
    BLUE(3),
    WILD(4);
    public final int VALUE;
    private CardColor(int num){
        this.VALUE = num;
    }
}
