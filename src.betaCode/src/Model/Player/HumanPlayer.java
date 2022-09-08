package Model.Player;
import java.util.HashMap;

/**
 * This class specializes {@link Player} to profile a human player
 * @author D'annibale Luigi, Venturini Daniele
 */
public class HumanPlayer extends Player
{
    private Stats stats;
    private String password;

    /**
     * Creates a new {@link HumanPlayer} with the name and the password, with default {@link Stats}
     * @param name
     * @param password
     */
    public HumanPlayer(String name,String password)
    {
        super(name);
        this.password = password;
        this.stats = new Stats();
    }

    /**
     * Creates a new {@link HumanPlayer} by the name, the password and the {@link Stats}
     * @param nickname
     * @param password
     * @param stats
     */
    public HumanPlayer(String nickname,String password, HashMap<String,Object> stats)
    {
        super(nickname);
        this.password = password;
        this.stats = new Stats(stats);
    }

    /**
     * Creates a new {@link HumanPlayer} by a {@link HashMap} representation.
     * @param hashMap a {@link HashMap} representation that uses {@link Model.Player.PlayerManager.KEYS}.VALUE as keys.
     */
    protected HumanPlayer(HashMap<Object,Object> hashMap)
    { this((String) hashMap.get(PlayerManager.KEYS.NAME.VALUE), (String) hashMap.get(PlayerManager.KEYS.PASSWORD.VALUE), (HashMap<String, Object>) hashMap.get("stats")); }

    /**
     * A {@link HashMap} with values: name, password.
     * @return a {@link HashMap} representation that uses {@link Model.Player.PlayerManager.KEYS}.VALUE as keys.
     */
    public HashMap<Object,Object> getHashmapNamePassword()
    {
        return new HashMap<>(){{
            put(PlayerManager.KEYS.NAME.VALUE,name);
            put(PlayerManager.KEYS.PASSWORD,password);
        }};
    }

    /**
     * @param defeatsAccumulated to sum to the already saved defeats
     * @param winAccumulated to sum to the already saved victories
     * @param expGained to sum to the already saved level
     */
    public void updateStats (int winAccumulated,int defeatsAccumulated, double expGained) {stats.updateStats(winAccumulated, defeatsAccumulated, expGained);}

    public void setStats(Stats stats) { this.stats = stats; }

    public Stats getStats() { return stats; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
