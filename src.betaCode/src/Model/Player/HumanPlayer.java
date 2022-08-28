package Model.Player;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HumanPlayer extends Player
{
    private static final String filePath = "resources/file.json";
    private Stats stats;
    //------
    //non vado troppo oltre questo lo facciamo insieme su paint ci sono scelte progettuali da fare
    protected class Stats
    {
        private int victories;
        private int defeats;
        private float level; //int part is the actual level and the decimal part
        // is in a progress bar can be done with a specific class
        public Stats(String filePath,String password)
        {
            //pensavo di fare un file con pw per ogni user ora vediamo come gestirlo

            readStats(filePath);
        }

        public boolean readStats(String filePath)
        {
            try
            {
                //We can write any JSONArray or JSONObject instance to the file
                String text = Files.readString(Paths.get(filePath));
                System.out.println(text);
            }
            catch (IOException e)
            {
                return false;
            }
            return false;
        }
        public boolean writeStats(String filePath)
        {
            JSONObject player = getJsonStats();
//nousecode
//            JSONObject player2 = new JSONObject();
//
//            player2.put("victories", victories);
//            player2.put("defeats", defeats);
//            player2.put("level", level);
//            JSONObject employeeObject2 = new JSONObject();
//            employeeObject2.put(name, player);
//            employeeObject2.put("danno", player2);
//

            //Write JSON file
            try (FileWriter file = new FileWriter(filePath))
            {
                //We can write any JSONArray or JSONObject instance to the file
                file.write(player.toString());
                file.flush();
            }
            catch (IOException e)
            {
                return false;
            }
            return true;
        }

        private JSONObject getJsonStats()
        {
            JSONObject player = new JSONObject();

            player.put("victories", stats.victories);
            player.put("defeats", defeats);
            player.put("level", level);
            return player;
        }

        public int getvictories() { return victories; }
        public int getdefeats() { return defeats; }
        public float getLevel() { return level; }

    }

    /*questo non so se va nel model
    protected class PersonalData
    {
        private String userName;
        private String password;
        private String name;
        //tutte informazioni che riteniamo opportune

    }*/
    //----------
    public HumanPlayer(String nickname)
    {
        super(nickname);
        //if (!getProfile(filePath))
            //updateProfile(filePath);

    }
}
