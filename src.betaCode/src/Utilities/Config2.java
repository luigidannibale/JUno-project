package Utilities;

import View.DeckColor;
import View.GraphicQuality;

import java.io.*;


public class Config2 {

    private int effectsVolume;
    private int musicVolume;
    private DeckColor deckStyle;
    private GraphicQuality graphicQuality;

    private final String fileName = "config.txt";

    /**
     * Assigns default values :
     * <ul>
     *  <li>effectsVolume = 50;</li>
     *  <li>musicVolume = 50;</li>
     *  <li>deckStyle = DeckColor.WHITE;</li>
     *  <li>graphicQuality = GraphicQuality.LOW;</li>
     * </ul>
     */
    public Config2()
    {
        assignDefaultValues();

    }
    public void assignDefaultValues()
    {
        this.effectsVolume = 50;
        this.musicVolume = 50;
        this.deckStyle = DeckColor.WHITE;
        this.graphicQuality = GraphicQuality.LOW;
    }

    public Config2(int effectsVolume, int musicVolume, DeckColor deckStyle,GraphicQuality graphicQuality)
    {
        this.effectsVolume = effectsVolume;
        this.musicVolume = musicVolume;
        this.deckStyle = deckStyle;
        this.graphicQuality = graphicQuality;
    }

    public boolean writeOnFile()
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
        {
            //Music volume
            bw.write(effectsVolume);
            bw.newLine();
            //Effects volume
            bw.write(musicVolume);
            bw.newLine();
            //DeckColor
            bw.write(deckStyle.VALUE);
            bw.newLine();
            //Graphics
            bw.write(graphicQuality.VALUE);
            bw.newLine();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean readFromFile()
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            effectsVolume = Integer.parseInt(br.readLine());
            musicVolume = Integer.parseInt(br.readLine());
            deckStyle = DeckColor.valueOf(br.readLine());
            graphicQuality = GraphicQuality.valueOf(br.readLine());
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }


    
    //#region ---getter and setters

    public int getEffectsVolume() {
        return effectsVolume;
    }

    public void setEffectsVolume(int effectsVolume) {
        this.effectsVolume = effectsVolume;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public DeckColor getDeckStyle() {
        return deckStyle;
    }

    public void setDeckStyle(DeckColor deckStyle)
    {
        this.deckStyle = deckStyle;
    }

    public GraphicQuality getGraphicQuality() {
        return graphicQuality;
    }

    public void setGraphicQuality(GraphicQuality graphicQuality) {
        this.graphicQuality = graphicQuality;
    }

}
