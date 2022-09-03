package Utilities;

import Model.Player.HumanPlayer;
import Model.Player.PlayerManager;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;

import java.awt.*;
import java.util.HashMap;


public class Config
{
    public static int effectsVolume;
    public static int musicVolume;
    public static DeckColor deckStyle;
    public static GraphicQuality graphicQuality;
    public static HumanPlayer loggedPlayer;
    public static String savedIconPath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/anonymous.png";
    public static double scalingPercentage;

    /**
     * Tries to read datas from file, if something goes wrong
     * assigns the folowing default values :
     * <ul>
     *  <li>effectsVolume = 50;</li>
     *  <li>musicVolume = 50;</li>
     *  <li>deckStyle = DeckColor.WHITE;</li>
     *  <li>graphicQuality = GraphicQuality.LOW;</li>
     * </ul>
     */
    public Config()
    {
        //var DAM = new DataAccessManager();
        //DAM.loadConfigOrDefault();
    }

    public static void assignDefaultValues()
    {
        loggedPlayer = PlayerManager.findPlayerByNicknameOrDefault("");
        effectsVolume = 50;
        musicVolume = 50;
        deckStyle = DeckColor.WHITE;
        graphicQuality = GraphicQuality.LOW;
        refreshScalingPercentage();
    }

    public static void  refreshScalingPercentage() { scalingPercentage = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1920; }

    public static HashMap<Object, Object> getPlayerConfig()
    {
        return  new HashMap<>(){{
            put("config",getPropertiesHashMapRapresentation());
            put("player", loggedPlayer.getHashmapNamePassword());
        }};
    }

    public static HashMap<Object, Object> getPropertiesHashMapRapresentation()
    {
        return new HashMap<>(){{
            put("effectsVolume", Config.effectsVolume);
            put("musicVolume", Config.musicVolume);
            put("deckStyle", Config.deckStyle.name());
            put("graphicQuality", Config.graphicQuality.name());
        }};
    }

    public static void saveConfig()
    {
        var DAM = new DataAccessManager();
        DAM.saveConfig();
    }



}
    //#region ---getters and setters
//    public int getEffectsVolume() { return effectsVolume; }
//    public int getMusicVolume() { return musicVolume; }
//    public DeckColor getDeckStyle() { return deckStyle; }
//    public GraphicQuality getGraphicQuality() { return graphicQuality; }
//    public void setMusicVolume(int musicVolume) { this.musicVolume = musicVolume; }
//    public void setEffectsVolume(int effectsVolume) { this.effectsVolume = effectsVolume; }
//    public void setDeckStyle(DeckColor deckStyle) { this.deckStyle = deckStyle; }
//    public void setGraphicQuality(GraphicQuality graphicQuality) { this.graphicQuality = graphicQuality; }
//    public boolean writeOnFile()
//    {
//        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
//        {
//            //Music volume
//            bw.write(effectsVolume);
//            bw.newLine();
//            //Effects volume
//            bw.write(musicVolume);
//            bw.newLine();
//            //DeckColor
//            bw.write(deckStyle.VALUE);
//            bw.newLine();
//            //Graphics
//            bw.write(graphicQuality.VALUE);
//            bw.newLine();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    public boolean readFromFile()
//    {
//        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
//        {
//            effectsVolume = Integer.parseInt(br.readLine());
//            musicVolume = Integer.parseInt(br.readLine());
//            deckStyle = DeckColor.valueOf(br.readLine());
//            graphicQuality = GraphicQuality.valueOf(br.readLine());
//            return true;
//        } catch (IOException e){
//            e.printStackTrace();
//            return false;
//        }
//    }
