package View.Elements;

import javax.swing.*;
import java.util.HashMap;
import java.util.Observer;
import java.util.function.IntConsumer;

public class ChangebleIcon extends JLabel
{
    public HashMap<String, Icon> ICONS= new HashMap<>();
    public ChangebleIcon(String path, String[]fileNames,String fileExtension)
    {
        for(String fileName:fileNames)
            ICONS.put(fileName,new ImageIcon(path+fileName+fileExtension));
    }
    public void setIcon(String iconID) { super.setIcon(ICONS.get(iconID));}
}
