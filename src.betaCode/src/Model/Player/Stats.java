package Model.Player;

import org.json.JSONObject;

import java.util.HashMap;


public class Stats
{
    private int victories;
    private int defeats;
    private double level; //int part is the actual level and the decimal part

    public Stats(HashMap<Object, Object> stats)  { updateStatsFromJson(stats); }

    public Stats()  { defaultValues(); }

    /**
     * Default values is an hashmap with the following values:
     * <table>
     *     <tr>
     *          <td>"victories":</td>
     *          <td>0</td>,
     *          <td>"defeats":</td>
     *          <td>0</td>,
     *          <td>"level":</td>
     *          <td>0</td>
     *     </tr>
     * </table>
     * @return default values
     */
    public HashMap<Object, Object> defaultValues()
    {
        return new HashMap<>()
        {{
            put("victories",0);
            put("defeats",0);
            put("level",0);
        }};
    }
    /**
     * Values are contained into an hashmap with the following keys:
     * <table>
     *     <tr>
     *          <td>"victories"</td>,
     *          <td>"defeats"</td>,
     *          <td>"level"</td>
     *     </tr>
     * </table>
     * @return default values
     */
    public HashMap<Object, Object> values()
    {
        return new HashMap<>()
        {{
            put("victories",victories);
            put("defeats",defeats);
            put("level",level);
        }};
    }

    public JSONObject getJsonStats()  { return new JSONObject(values()); }

    public void updateStatsFromJson(HashMap<Object, Object> stats)
    {
        this.defeats = (int) stats.get("defeats");
        this.victories = (int) stats.get("victories");
        this.level = Double.parseDouble(stats.get("level").toString());
    }

    public int getVictories() { return victories; }

    public void setVictories(int victories) { this.victories = victories; }

    public int getDefeats() { return defeats; }

    public void setDefeats(int defeats) { this.defeats = defeats; }

    public double getLevel() { return level; }

    public void setLevel(float level) { this.level = level; }
}

