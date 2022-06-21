package View;

import java.io.*;

public class Config {

    MainFrame mf;

    public Config(MainFrame mf){
        this.mf = mf;
    }

    public void saveConfig(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"))){

            //Screen Dimension
            MainFrame.Dimensions dim = mf.getDimension();
            bw.write(dim.toString());
            bw.newLine();

            //Music volume
            bw.write(String.valueOf(mf.backMusic.getVolume()));
            bw.newLine();

            //Effects volume
            bw.write(String.valueOf(mf.soundEffects.getVolume()));
            bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try(BufferedReader br = new BufferedReader(new FileReader("config.txt"))){

            //Screen dimension
            String line = br.readLine();
            if (line == null) return;
            mf.updateSize(line);

            //Music volume
            line = br.readLine();
            mf.backMusic.setVolume(Integer.parseInt(line));

            //Effects volume
            line = br.readLine();
            mf.soundEffects.setVolume(Integer.parseInt(line));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
