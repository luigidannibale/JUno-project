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
    public boolean writeSingleJson(HashMap<String,Object> data, String File)
    {
        JSONObject dataJson = new JSONObject();
        data.forEach(dataJson::put);
        return write(dataJson.toString(), File);
    }
    public boolean writeJson(HashMap<String,Object> datas[], String File)
    {
        JSONArray array = new JSONArray();
        for (HashMap<String,Object> data:datas)
        {
            JSONObject dataJson = new JSONObject();
            data.forEach(dataJson::put);
            array.put(dataJson);
        }
        return write(array.toString(), File);
    }
    private boolean write(String data, String File)
    {
        try (FileWriter file = new FileWriter(File))
        {
            file.write(data);
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean read(JSONArray data, String File)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(File)))
        {
            data = new JSONArray(br.lines());
            //br.lines().forEach(data::put);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
