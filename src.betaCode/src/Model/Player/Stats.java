package Model.Player;

import java.util.HashMap;

/**
 * Class used to store the stats of a {@link Player} <br/>
 * Stores victories, defeats, and level.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class Stats
{
    /**
     * KEYS for stats
     * <table>
     *     <tr>
     *         <th>Key</th>
     *         <th>String representation</th>
     *     </tr>
     *     <tr>
     *         <td>VICTORIES</td>
     *         <td>"victories"</td>
     *     </tr>
     *     <tr>
     *         <td>DEFEATS</td>
     *         <td>"defeats"</td>
     *     </tr>
     *     <tr>
     *         <td>LEVEL</td>
     *         <td>"level"</td>
     *     </tr>
     * </table>
     *
     * @author D'annibale Luigi, Venturini Daniele
     */
    public enum KEYS
    {
        VICTORIES("victories"),
        DEFEATS("defeats"),
        LEVEL("level");
        public final String VALUE;

        KEYS(String val) { VALUE = val;}
    }
    private int victories;
    private int defeats;
    private double level; //int part is the actual level and the decimal part

    /**
     * Creates {@link Stats} with the given values
     * @param stats  a hashmap that uses {@link Stats.KEYS}.VALUE as keys
     */
    public Stats(HashMap<String, Object> stats) { setStatsFromHashMap(stats); }

    /**
     * Creates {@link Stats} with default values
     */
    public Stats()  { defaultValues(); }

    /**
     * @return default values contained into a {@link HashMap} that uses {@link Stats.KEYS}.VALUE as keys
     */
    public HashMap<String, Object> defaultValues() { return getHashMapValues(0, 0, 0); }

     /**
     * @return the values contained into a {@link HashMap} that uses {@link Stats.KEYS}.VALUE as keys
     */
    public HashMap<String, Object> getValues() { return getHashMapValues(victories, defeats, level); }

    /**
     * @param victories
     * @param defeats
     * @param level
     * @return an {@link HashMap} representation of the given values that  uses {@link Stats.KEYS}.VALUE as keys
     */
    private HashMap<String, Object> getHashMapValues(int victories, int defeats, double level)
    {
        return new HashMap<>()
        {{
            put(KEYS.VICTORIES.VALUE, victories);
            put(KEYS.DEFEATS.VALUE, defeats);
            put(KEYS.LEVEL.VALUE, level);
        }};
    }

    /**
     *
     * @param stats a hashmap that uses {@link Stats.KEYS}.VALUE as keys
     */
    public void setStatsFromHashMap(HashMap<String, Object> stats)
    {
        this.defeats = (int) stats.get(KEYS.DEFEATS.VALUE);
        this.victories = (int) stats.get(KEYS.VICTORIES.VALUE);
        this.level = Double.parseDouble(stats.get(KEYS.LEVEL.VALUE).toString());
    }

    /**
     * @param defeats to sum to the already saved defeats
     * @param victories to sum to the already saved victories
     * @param exp to sum to the already saved level
     */
    public void updateStats(int defeats, int victories, double exp)
    {
        this.defeats += defeats;
        this.victories += victories;
        this.level += exp;
    }

    public int getVictories() { return victories; }

    public void setVictories(int victories) { this.victories = victories; }

    public int getDefeats() { return defeats; }

    public void setDefeats(int defeats) { this.defeats = defeats; }

    public double getLevel() { return level; }

    public void setLevel(float level) { this.level = level; }
}

