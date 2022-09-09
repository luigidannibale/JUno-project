package Controller.Utilities;

import Model.Cards.Card;
import Model.Players.HumanPlayer;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;

import java.awt.*;
import java.util.HashMap;

/**
 * This class is a container for static values that represent the configuration of all the application settings.
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class Config
{

    /**
     * Keys for configs hashmap representation:
     * <table>
     *     <tr>
     *         <th>KEY</th>
     *         <th>VALUE</th>
     *     </tr>
     *     <tr>
     *         <td>EFFECTS_VOLUME</td>
     *         <td>"effectsVolume"/td>
     *     </tr>
     *     <tr>
     *         <td>MUSIC_VOLUME</td>
     *         <td>"musicVolume"</td>
     *     </tr>
     *     <tr>
     *         <td>DECK_STYLE</td>
     *         <td>"deckStyle"</td>
     *     </tr>
     *     <tr>
     *         <td>GRAPHIC_QUALITY</td>
     *         <td>"graphicQuality"</td>
     *     </tr>
     *     <tr>
     *         <td>SAVED_ICON_PATH</td>
     *         <td>"savedIconPath"</td>
     *     </tr>
     *     <tr>
     *         <td>LOGGED_PLAYER</td>
     *         <td>"player"</td>
     *     </tr>
     *     <tr>
     *         <td>CONFIG</td>
     *         <td>"config"</td>
     *     </tr>
     * </table>
     */
    public enum KEYS
    {
        EFFECTS_VOLUME("effectsVolume"),
        MUSIC_VOLUME("musicVolume"),
        DECK_STYLE("deckStyle"),
        GRAPHIC_QUALITY("graphicQuality"),
        SAVED_ICON_PATH("savedIconPath"),
        LOGGED_PLAYER("player"),
        CONFIG("config");

        public final String VALUE;
        KEYS(String val) { VALUE = val;}
    }

    public static int effectsVolume;
    public static int musicVolume;
    public static DeckColor deckStyle;
    public static GraphicQuality graphicQuality;
    public static HumanPlayer loggedPlayer;
    public static final String DEFAULT_ICON_PATH = "resources/images/MainFrame/ProfilePanel/anonymous.png";
    public static String savedIconPath;
    public static double scalingPercentage;

    /**
     * Sets the default player, with associated default settings values:
     *  <table>
     *     <tr>
     *         <th>Value</th>
     *         <th>default value associated</th>
     *     </tr>
     *     <tr>
     *         <td>effectsVolume</td>
     *         <td>50</td>
     *     </tr>
     *     <tr>
     *         <td>musicVolume</td>
     *         <td>50</td>
     *     </tr>
     *     <tr>
     *         <td>deckStyle</td>
     *         <td>DeckColor.WHITE</td>
     *     </tr>
     *     <tr>
     *         <td>graphicQuality</td>
     *         <td>GraphicQuality.LOW</td>
     *     </tr>
     *     <tr>
     *         <td>savedIconPath</td>
     *         <td>DEFAULT_ICON_PATH</td>
     *     </tr>
     *  </table>
     */
    public static void assignDefaultValues()
    {
        loggedPlayer = new DataAccessManager().getModelProfile("");
        try { setHashMap(getDefaultSettings()); }
        catch (Exception e){}
        refreshScalingPercentage();
    }

    /**
     * Calculates the actual scaling percentage basing on current window dimensions
     */
    public static void  refreshScalingPercentage() { scalingPercentage = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1920; }

    /**
     * @param player
     * @return an {@link HashMap} representation of the players personal {@link Config} that uses {@link KEYS}.VALUE as keys
     */
    public static HashMap<Object, Object> getPlayerConfig(HumanPlayer player)
    {
        return  new HashMap<>(){{
            put(KEYS.CONFIG.VALUE,getSettings());
            put(KEYS.LOGGED_PLAYER.VALUE, player.getHashmapNamePassword());
        }};
    }

    /**
     *
     * @param effectsVolume
     * @param musicVolume
     * @param deckStyle
     * @param graphicQuality
     * @param savedIconPath
     * @return an {@link HashMap} representation of the given values that uses {@link KEYS}.VALUE as keys
     */
    private static HashMap<Object, Object> getHashMap(int effectsVolume, int musicVolume, DeckColor deckStyle, GraphicQuality graphicQuality, String savedIconPath) {
        return new HashMap<>(){{
            put(KEYS.EFFECTS_VOLUME.VALUE, effectsVolume);
            put(KEYS.MUSIC_VOLUME.VALUE, musicVolume);
            put(KEYS.DECK_STYLE.VALUE, deckStyle.name());
            put(KEYS.GRAPHIC_QUALITY.VALUE, graphicQuality.name());
            put(KEYS.SAVED_ICON_PATH.VALUE, savedIconPath);
        }};
    }

    /**
     * @param config
     * Sets the stats basing on a given {@link HashMap} that uses {@link KEYS}.VALUE as keys. <br/>
     * The expected attributes are:
     * <ul>
     *     <li>EFFECTS_VOLUME</li>
     *     <li>MUSIC_VOLUME</li>
     *     <li>GRAPHIC_QUALITY</li>
     *     <li>DECK_STYLE</li>
     *     <li>SAVED_ICON_PATH</li>
     * </ul>
     * @see KEYS
     * @throws Exception if it doesn't find any attribute throws
     */
    public static void setHashMap(HashMap<Object, Object> config) throws Exception
    {
        Object effectsVolume = config.get(KEYS.EFFECTS_VOLUME.VALUE),
                musicVolume = config.get(KEYS.MUSIC_VOLUME.VALUE),
                graphicQuality = config.get(KEYS.GRAPHIC_QUALITY.VALUE),
                deckStyle = config.get(KEYS.DECK_STYLE.VALUE),
                savedIconPath = config.get(KEYS.SAVED_ICON_PATH.VALUE);

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

    /**
     * Default settings values:
     *  <table>
     *     <tr>
     *         <th>Value</th>
     *         <th>default value associated</th>
     *     </tr>
     *     <tr>
     *         <td>"effectsVolume"</td>
     *         <td>50</td>
     *     </tr>
     *     <tr>
     *         <td>"musicVolume"</td>
     *         <td>50</td>
     *     </tr>
     *     <tr>
     *         <td>"deckStyle"</td>
     *         <td>DeckColor.WHITE</td>
     *     </tr>
     *     <tr>
     *         <td>"graphicQuality"</td>
     *         <td>GraphicQuality.LOW</td>
     *     </tr>
     *     <tr>
     *         <td>"savedIconPath"</td>
     *         <td>DEFAULT_ICON_PATH</td>
     *     </tr>
     *  </table>
     * @return the {@link HashMap} representation, that uses {@link KEYS}.VALUE as keys
     */
    public static HashMap<Object, Object> getDefaultSettings()
    { return getHashMap(50, 50, DeckColor.WHITE, GraphicQuality.LOW,DEFAULT_ICON_PATH); }

    /**
     * @return the {@link HashMap} representation of the actual settings, that uses {@link KEYS}.VALUE as keys
     */
    public static HashMap<Object, Object> getSettings()
    { return getHashMap(effectsVolume,musicVolume,deckStyle,graphicQuality,savedIconPath); }

    /**
     * Delegates the storing of the actual {@link Config} into the database.
     * @see DataAccessManager
     */
    public static void storeConfig()
    {
        var DAM = new DataAccessManager();
        DAM.saveProfile(Config.loggedPlayer);
    }

}
