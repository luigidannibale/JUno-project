package Utilities;

import Controller.MainFrameController;

import java.io.*;

public class Config {

    MainFrameController mfc;
    final static String fileName = "config.txt";

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

            //Music volume
            String line = br.readLine();
            mfc.backMusic.setVolume(Integer.parseInt(line));

            //Effects volume
            line = br.readLine();
            mfc.soundEffects.setVolume(Integer.parseInt(line));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
