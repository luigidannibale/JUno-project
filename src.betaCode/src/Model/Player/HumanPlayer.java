package Model.Player;

import Utilities.FileManager;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Stats getStats() { return stats; }

    public void setStats(Stats stats) { this.stats = stats; }
}
