package View.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ChangebleIcon extends JLabel
{
    public HashMap<String, ImageComponent> ICONS= new HashMap<>();
    public ChangebleIcon(String path, String[]fileNames,String fileExtension)
    {
        for(String fileName:fileNames)
            ICONS.put(fileName,new ImageComponent(path+fileName+fileExtension));

        super.setPreferredSize(ICONS.values().stream().toList().get(0).getPreferredSize());
    }
    public void setIcon(String iconID) { super.setIcon(ICONS.get(iconID).getIcon());}

    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        ICONS.values().forEach(icon->icon.setPreferredSize(preferredSize));
    }
}
