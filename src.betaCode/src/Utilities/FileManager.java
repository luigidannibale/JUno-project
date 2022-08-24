package Utilities;

import View.DeckColor;
import View.GraphicQuality;
import org.json.*;
import java.io.*;
import java.util.*;

/**
 * Class used to write on files
 * <h6>For JSON management provides:</h6>
 * <ul>
 *      <li>wirteJson</li>
 *      <li>readJson</li>
 * </ul>
 *
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class FileManager
{
    public boolean writeJson(HashMap<String,Object> datas[], String file)
    {
        JSONArray datasJson = new JSONArray(datas);
        return write(datasJson.toString(), file);
    }
    private boolean write(String data, String file)
    {
        try (FileWriter fw = new FileWriter(file))
        {
            fw.write(data);
            fw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public ArrayList<HashMap<String,Object>>  readJson(String file) throws IOException {
        JSONArray datasJson = new JSONArray();
        try
        {
            datasJson = read(file);
        }
        catch (Exception e)
        {
            throw e;
        }
        ArrayList<HashMap<String,Object>> data = new ArrayList<>();

        for (Object object:datasJson)
        {
            assert(object instanceof JSONObject);
            JSONObject jsonObject= (JSONObject) object;
            HashMap<String,Object> hashMapObject = new HashMap<>();
            jsonObject.keys().forEachRemaining(key->hashMapObject.put(key,jsonObject.get(key)));
            data.add(hashMapObject);
        }
        return data;
    }
    private JSONArray read(String file) throws IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            JSONTokener tokener = new JSONTokener(br);
            JSONArray jsonArray  =  new JSONArray(tokener);
            return jsonArray;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}