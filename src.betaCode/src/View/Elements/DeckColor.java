package View.Elements;

/**
 * <table>
 *     <tr>
 *         <th>DeckColor</th>
 *         <th>VALUE</th>
 *     </tr>
 *     <tr>
 *         <td>BLACK</td>
 *         <td>"black"</td>
 *     </tr>
 *     <tr>
 *         <td>WHITE</td>
 *         <td>"white"</td>
 *     </tr>
 * </table>
 * @author D'annibale Luigi, Venturini Daniele
 */
public enum DeckColor
{
    BLACK("black"),
    WHITE("white");
    public final String VALUE;
    DeckColor(String value){ this.VALUE = value; }

}