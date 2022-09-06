package Model.Player;

import java.util.HashMap;


public class HumanPlayer extends Player
{
    private static final String filePath = "resources/info.json";

    private Stats stats;
    private String password;

    public HumanPlayer(String nickname,String password)
    {
        super(nickname);
        this.password = password;
        this.stats = new Stats();
    }
    public HumanPlayer(String nickname,String password, HashMap<String,Object> stats)
    {
        super(nickname);
        this.password = password;
        this.stats = new Stats(stats);
    }
    public HumanPlayer(HashMap<Object,Object> hashMap)
    {
        this((String) hashMap.get("name"), (String) hashMap.get("password"), (HashMap<String, Object>) hashMap.get("stats"));
    }

    public HashMap<Object,Object> getHashmapNamePassword()
    {
        return new HashMap<>(){{
            put("name",name);
            put("password",password);
        }};
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Stats getStats() { return stats; }

    public void setStats(Stats stats) { this.stats = stats; }
}
