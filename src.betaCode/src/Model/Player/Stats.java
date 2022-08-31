package Model.Player;

import Utilities.FileManager;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Stats
{
    private int victories;
    private int defeats;
    private float level; //int part is the actual level and the decimal part

    public Stats(JSONObject stats)  { updateStatsFromJson(stats); }

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
        return new HashMap<Object, Object>()
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
        return new HashMap<Object, Object>()
        {{
            put("victories",victories);
            put("defeats",defeats);
            put("level",level);
        }};
    }

    public JSONObject getJsonStats()  { return new JSONObject(values()); }
    public void updateStatsFromJson(JSONObject stats)
    {
        this.defeats = (int) stats.get("defeats");
        this.victories = (int) stats.get("victories");
        this.level = Float.parseFloat(stats.get("level").toString());
    }

    public int getVictories() { return victories; }

    public void setVictories(int victories) { this.victories = victories; }

    public int getDefeats() { return defeats; }

    public void setDefeats(int defeats) { this.defeats = defeats; }

    public float getLevel() { return level; }

    public void setLevel(float level) { this.level = level; }
}

