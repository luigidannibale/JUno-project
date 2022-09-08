package Controller.Utilities;

import Model.Player.HumanPlayer;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;

import java.awt.*;
import java.util.HashMap;


public class Config
{
    public enum KEYS
    {
        EFFECTS_VOLUME("effectsVolume"),
        MUSIC_VOLUME("musicVolume"),
        DECK_STYLE("deckStyle"),
        GRAPHIC_QUALITY("graphicQuality"),
        SAVED_ICON_PATH("savedIconPath"),
        LOGGED_PLAYER("player"),
        CONFIG("config");

        String value;

        KEYS(String val) {value = val;}
        @Override
        public String toString() { return value; }
    }
    public static int effectsVolume;
    public static int musicVolume;
    public static DeckColor deckStyle;
    public static GraphicQuality graphicQuality;
    public static HumanPlayer loggedPlayer;
    public static final String DEFAULT_ICON_PATH = "resources/images/MainFrame/ProfilePanel/anonymous.png";
    public static String savedIconPath;
    public static double scalingPercentage;

    public static void assignDefaultValues()
    {
        loggedPlayer = new DataAccessManager().getModelProfile("");
        try { setHashMap(getDefaultSettings()); }
        catch (Exception e){}
        refreshScalingPercentage();
    }

    public static void  refreshScalingPercentage() { scalingPercentage = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1920; }

    /**
     * Represent into a hashmap the player and his settings
     * @param player
     * @return
     */
    public static HashMap<Object, Object> getPlayerConfig(HumanPlayer player)
    {
        return  new HashMap<>(){{
            put(KEYS.CONFIG.toString(),getSettings());
            put(KEYS.LOGGED_PLAYER.toString(), player.getHashmapNamePassword());
        }};
    }

    private static HashMap<Object, Object> getHashMap(int effectsVolume, int musicVolume, DeckColor deckStyle, GraphicQuality graphicQuality, String savedIconPath) {
        return new HashMap<>(){{
            put(KEYS.EFFECTS_VOLUME.toString(), effectsVolume);
            put(KEYS.MUSIC_VOLUME.toString(), musicVolume);
            put(KEYS.DECK_STYLE.toString(), deckStyle.name());
            put(KEYS.GRAPHIC_QUALITY.toString(), graphicQuality.name());
            put(KEYS.SAVED_ICON_PATH.toString(), savedIconPath);
        }};
    }

    public static void setHashMap(HashMap<Object, Object> config) throws Exception
    {
        Object effectsVolume = config.get(KEYS.EFFECTS_VOLUME.toString()),
                musicVolume = config.get(KEYS.MUSIC_VOLUME.toString()),
                graphicQuality = config.get(KEYS.GRAPHIC_QUALITY.toString()),
                deckStyle = config.get(KEYS.DECK_STYLE.toString()),
                savedIconPath = config.get(KEYS.SAVED_ICON_PATH.toString());

        if (effectsVolume == null) throw new Exception("Effects volume not found");
        if (musicVolume == null) throw new Exception("Music volume not found");
        if (graphicQuality == null) throw new Exception("Graphic quality not found");
        if (deckStyle == null) throw new Exception("Deck style not found");
        if (savedIconPath == null ) throw new Exception("Saved icon path not found");

        Config.effectsVolume  =  (int) effectsVolume;
        Config.musicVolume    =  (int) musicVolume;
        Config.graphicQuality =  GraphicQuality.valueOf((String) graphicQuality);
        Config.deckStyle      =  DeckColor.valueOf((String) deckStyle);
        Config.savedIconPath  =  (String) savedIconPath;
    }

    public static HashMap<Object, Object> getDefaultSettings()
    { return getHashMap(50, 50, DeckColor.WHITE, GraphicQuality.LOW,DEFAULT_ICON_PATH); }

    public static HashMap<Object, Object> getSettings()
    { return getHashMap(effectsVolume,musicVolume,deckStyle,graphicQuality,savedIconPath); }

    public static void storeConfig()
    {
        var DAM = new DataAccessManager();
        DAM.saveProfile(Config.loggedPlayer);
    }

}
