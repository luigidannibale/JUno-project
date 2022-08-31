package Model.Player;

import Utilities.FileManager;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.util.HashMap.*;


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
    public HumanPlayer(String nickname,String password, JSONObject stats)
    {
        super(nickname);
        this.password = password;
        this.stats = new Stats(stats);
    }
    public HashMap<Object,Object> getHashmap()
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
