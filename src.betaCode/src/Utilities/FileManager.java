package Utilities;

import View.DeckColor;
import View.GraphicQuality;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileManager
{
    public boolean writeSingleJson(HashMap<String,Object> data, String file)
    {
        JSONObject dataJson = new JSONObject();
        data.forEach(dataJson::put);
        return write(dataJson.toString(), file);
    }
    public boolean writeJson(HashMap<String,Object> datas[], String file)
    {
        JSONArray array = new JSONArray();
        for (HashMap<String,Object> data:datas)
        {
            JSONObject dataJson = new JSONObject();
            data.forEach(dataJson::put);
            array.put(dataJson);
        }
        return write(array.toString(), file);
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
    public HashMap<String,Object>  readJson(String file) throws IOException
    {
        JSONArray datasJson = read(file);
        HashMap<String,Object> data = new HashMap<>();
        for (JSONObject o:datasJson)
        {

        }
        return null;
    }
    private JSONArray read(String file) throws IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            return new JSONArray(br.lines());
            //br.lines().forEach(data::put);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
