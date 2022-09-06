package Model.Player;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.naming.Name;
import java.util.HashMap;
import java.util.logging.Level;


public class Stats
{
    public enum KEYS
    {
        VICTORIES("victories"),
        DEFEATS("defeats"),
        LEVEL("level");
        String value;

        KEYS(String val) {value = val;}
        @Override
        public String toString() { return value; }
    }
    private int victories;
    private int defeats;
    private double level; //int part is the actual level and the decimal part

    public Stats(HashMap<String, Object> stats) { setStatsFromHashMap(stats); }

    public Stats()  { defaultValues(); }

    /**
     * Values are contained into an hashmap the keys are string specified in {@link KEYS}.
     * @return saved player values
     */
    public HashMap<String, Object> defaultValues() { return getHashMapValues(0, 0, 0); }
    /**
     * Values are contained into an hashmap the keys are string specified in {@link KEYS}.
     * @return saved player values
     */
    public HashMap<String, Object> getValues() { return getHashMapValues(victories, defeats, level); }

    private HashMap<String, Object> getHashMapValues(int victories, int defeats, double level)
    {
        return new HashMap<>()
        {{
            put(KEYS.VICTORIES.toString(), victories);
            put(KEYS.DEFEATS.toString(), defeats);
            put(KEYS.LEVEL.toString(), level);
        }};
    }

    public void setStatsFromHashMap(HashMap<String, Object> stats)
    {
        this.defeats = (int) stats.get(KEYS.DEFEATS.toString());
        this.victories = (int) stats.get(KEYS.VICTORIES.toString());
        this.level = Double.parseDouble(stats.get(KEYS.LEVEL.toString()).toString());
    }

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

