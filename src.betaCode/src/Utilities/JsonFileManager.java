package Utilities;

import org.json.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    //READ DATA
    /**
     *
     * @param file
     * @return the read data
     * @throws Exception
     */
    public ArrayList<HashMap<Object,Object>>  readJson(String file) throws Exception
    {
        JSONArray datasJson;
        try
        {
            datasJson = read(file);
        }
        catch (Exception e)
        { throw e; }

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
    //---------

    //WRITE DATA
    /**
     * Takes the data as {@link HashMap} and the file path, and writes the data as {@link JSONArray} on the file, appending them to the data that already are written there.
     * @param datas
     * @param file
     * @return true if the operation is successful, false otherwise
     * @throws Exception
     */
    public boolean writeJson(List<HashMap<Object,Object>> datas, String file) throws Exception
    {
        ArrayList<HashMap<Object,Object>> actualData = readJson(file);
        actualData.addAll(datas);
        return overWriteJson(actualData,file);
    }

    /**
     * Takes the data as {@link HashMap} and the file path, and writes the data as {@link JSONArray} on the file overwriting the existing content.
     * @param datas
     * @param file
     * @return true if the operation is successful, false otherwise
     */
    public boolean overWriteJson(List<HashMap<Object,Object>> datas, String file)
    {
        JSONArray datasJson = new JSONArray(datas);
        return write(datasJson, file);
    }
    //----------



    /**
     * Writes a {@link JSONArray} on a file.
     * @param data
     * @param file
     * @return true if the operation is successful, false otherwise
     */
    private boolean write(JSONArray data, String file)
    {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file)))
        {
            data.write(writer,1,1);
        }
        catch (Exception e)
        { return false; }
        return true;
    }

    /**
     * Converts from {@link JSONObject} to {@link HashMap}
     * @param json
     * @return the corresponding {@link HashMap}
     */
    private HashMap<Object,Object> jsonToHashMap(JSONObject json)
    {
        HashMap<Object,Object> hash = new HashMap<>();
        json.keys().forEachRemaining(k-> hash.put(k,json.get(k) instanceof JSONObject?jsonToHashMap((JSONObject) json.get(k)):json.get(k)));
        return hash;
    }

    /**
     * Reads a {@link JSONArray} from a file.
     * @param file
     * @return
     * @throws Exception
     */
    private JSONArray read(String file) throws Exception
    {
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            JSONTokener tokener = new JSONTokener(br);
            JSONArray jsonArray  =  new JSONArray(tokener);
            return jsonArray;
        }
        catch (JSONException e)
        {if (e.getMessage().equals("A JSONArray text must start with '[' at 0 [character 1 line 1]")) write(new JSONArray(){}, file);throw e;}
        catch (Exception e)
        { throw e; }
    }
}