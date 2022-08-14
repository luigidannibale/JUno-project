package Model.Cards;
/**
 * All the values of Uno {@link Card},<br/>
 * values are associate to their points value<br/>
 * and the scheme is like so:
 * <table>
 *     <tr>
 *         <th>Values</th>
 *         <th>Points</th>
 *     </tr>
 *     <tr>
 *         <td>ZERO to NINE</td>
 *         <td>0-9</td>
 *     </tr>
 *     <tr>
 *         <td>SKIP</td>
 *         <td>20</td>
 *     </tr>
 *     <tr>
 *         <td>REVERSE</td>
 *         <td>20</td>
 *     </tr>
 *     <tr>
 *         <td>DRAW</td>
 *         <td>20</td>
 *     </tr>
 *     <tr>
 *         <td>WILD</td>
 *         <td>50</td>
 *     </tr>
 *     <tr>
 *         <td>WILD DRAW</td>
 *         <td>50</td>
 *     </tr>
 * </table>
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
// * <ul>
// *     <li>ZERO</li>
//        *     <li>ONE</li>
//        *     <li>TWO</li>
//        *     <li>THREE</li>
//        *     <li>FOUR</li>
//        *     <li>FIVE</li>
//        *     <li>SIX</li>
//        *     <li>SEVEN</li>
//        *     <li>EIGHT</li>
//        *     <li>NINE</li>
//        *     <li>SKIP</li>
//        *     <li>REVERSE</li>
//        *     <li>DRAW</li>
//        *     <li>WILD</li>
//        *     <li>WILD_DRAW</li>
//        * </ul>
public enum CardValue
{
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    SKIP(20),
    REVERSE(20),
    DRAW(20),
    WILD(50),
    WILD_DRAW(50);
    public final int VALUE;
    private CardValue(int value){
        this.VALUE = value;
    }
}
