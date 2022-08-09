package Utilities;

import Controller.MainFrameController;
import View.DeckColor;

import java.io.*;

public class Config {
    int defaultVolume =50;
    DeckColor defaultColor = DeckColor.WHITE;
    MainFrameController mfc;
    final String fileName = "config.txt";

    public Config(MainFrameController mfc){
        this.mfc = mfc;
    }

    public void saveConfig(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){

            /*
            //Screen Dimension
            MainFrame.Dimensions dim = mfc.getCurrentDimension();
            bw.write(dim.toString());
            bw.newLine();
             */

            //Music volume
            bw.write(String.valueOf(mfc.backMusic.getVolume()));
            bw.newLine();

            //Effects volume
            bw.write(String.valueOf(mfc.soundEffects.getVolume()));
            bw.newLine();

            //WhiteDeck
            bw.write(String.valueOf(mfc.deckColor.VALUE));
            bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){

            /*
            //Screen dimension
            String line = br.readLine();
            if (line == null) return;
            mfc.updateSize(line);
             */
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
            try
            {
                mfc.soundEffects.setVolume(Integer.parseInt(br.readLine()));
            }
            catch (Exception e){
                mfc.soundEffects.setVolume(defaultVolume);
                exceptionOccurred++;
            }
            try
            {
                mfc.deckColor = DeckColor.valueOf(br.readLine());
            }
            catch (Exception e){
                mfc.deckColor = defaultColor;
                exceptionOccurred++;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
