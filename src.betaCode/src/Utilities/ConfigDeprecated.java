package Utilities;

import Controller.MainFrameController;
import View.Elements.DeckColor;

import java.io.*;

public class ConfigDeprecated {
    int defaultVolume =50;
    public static DeckColor defaultColor = DeckColor.WHITE;
    public static boolean highGraphics = false;
    MainFrameController mfc;
    final String fileName = "config.txt";

    public ConfigDeprecated(MainFrameController mfc){
        this.mfc = mfc;
    }

    public void saveConfig(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
        {
            //Music volume
            bw.write(String.valueOf(mfc.backMusic.getVolume()));
            bw.newLine();

            //Effects volume
            bw.write(String.valueOf(mfc.soundEffects.getVolume()));
            bw.newLine();

            //DeckColor
            bw.write(String.valueOf(defaultColor));
            bw.newLine();

            //Graphics
            bw.write(String.valueOf(highGraphics));
            bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            int exceptionOccurred = 0;

            //Music volume
            try
            {
                mfc.backMusic.setVolume(Integer.parseInt(br.readLine()));
            }
            catch (Exception e){
                mfc.backMusic.setVolume(defaultVolume);
                exceptionOccurred++;
            }

            //Effects volume
            try
            {
                mfc.soundEffects.setVolume(Integer.parseInt(br.readLine()));
            }
            catch (Exception e){
                mfc.soundEffects.setVolume(defaultVolume);
                exceptionOccurred++;
            }

            //DeckColor
            try
            {
                defaultColor = DeckColor.valueOf(br.readLine().toUpperCase());
            }
            catch (Exception e){
                //defaultColor = defaultColor;
                exceptionOccurred++;
            }
            try
            {
                highGraphics = Boolean.parseBoolean(br.readLine());
            }
            catch (Exception e){
                exceptionOccurred++;
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
