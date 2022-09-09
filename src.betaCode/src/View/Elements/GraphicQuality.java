package View.Elements;

/**
 * <table>
 *     <tr>
 *         <th>GraphicQuality</th>
 *         <th>VALUE</th>
 *     </tr>
 *     <tr>
 *         <td>HIGH</td>
 *         <td>"High"</td>
 *     </tr>
 *     <tr>
 *         <td>LOW</td>
 *         <td>"Low"</td>
 *     </tr>
 * </table>
 * @author D'annibale Luigi, Venturini Daniele
 */
public enum GraphicQuality
{
    HIGH("High"),
    LOW("Low");

    public final String VALUE;
    GraphicQuality(String value){ this.VALUE = value; }
}