package Utilities;

import org.json.*;
import java.io.*;
import java.util.*;

/**
 * Class used to read and write on files
 * <h6>For JSON management provides:</h6>
 * <ul>
 *      <li>wirteJson</li>
 *      <li>readJson</li>
 * </ul>
 *
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class JsonFileManager
{
    public boolean writeJson(List<HashMap<Object,Object>> datas, String file) throws IOException
    {
        ArrayList<HashMap<Object,Object>> actualData = readJson(file);
        actualData.addAll(datas);
        return overWriteJson(actualData,file);
    }
    public boolean overWriteJson(List<HashMap<Object,Object>> datas, String file)
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
    public ArrayList<HashMap<Object,Object>>  readJson(String file) throws IOException
    {
        JSONArray datasJson;
        try
        {
            datasJson = read(file);
        }
        catch (Exception e)
        {
            throw e;
        }
        ArrayList<HashMap<Object, Object>> data = new ArrayList<>();

        for (Object object : datasJson)
        {
            JSONObject jsonObject = (JSONObject) object;
            HashMap<Object, Object> hashMapObject = new HashMap<>();
            jsonObject.keys().forEachRemaining(key -> hashMapObject.put(key, jsonObject.get(key)instanceof JSONObject? jsonToHashMap((JSONObject) jsonObject.get(key)): jsonObject.get(key)));
            data.add(hashMapObject);
        }
        return data;
    }
    private HashMap<Object,Object> jsonToHashMap(JSONObject json)
    {
        HashMap<Object,Object> hash = new HashMap<>();
        json.keys().forEachRemaining(k-> hash.put(k,json.get(k) instanceof JSONObject?jsonToHashMap((JSONObject) json.get(k)):json.get(k)));
        return hash;
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