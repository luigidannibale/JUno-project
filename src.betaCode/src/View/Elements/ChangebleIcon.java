package View.Elements;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Class used to store more images in one {@link JLabel}.
 * The displayed icon can be changed by calling setIcon with the related {@link String} id
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ChangebleIcon extends JLabel
{
    private HashMap<String, ImageComponent> ICONS = new HashMap<>();

    /**
     * Creates a new {@link ChangebleIcon} and loads the {@link ImageComponent} combining the path, their name and the extension
     * @param path
     * @param fileNames
     * @param fileExtension
     */
    public ChangebleIcon(String path, String[]fileNames,String fileExtension)
    {
        setSize(new ImageComponent(path+fileNames[0]+fileExtension).getSize());
        for(String fileName:fileNames)
            ICONS.put(fileName,new ImageComponent(path+fileName+fileExtension));

        super.setPreferredSize(ICONS.values().stream().toList().get(0).getSize());
    }

    /**
     * Sets the current displayed icon by passing its {@link String} id
     * @param iconID
     */
    public void setIcon(String iconID) { super.setIcon(ICONS.get(iconID).getIcon());}

    //needed to resize each {@link ImageComponent}
    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        ICONS.values().forEach(icon->icon.setPreferredSize(preferredSize));
    }
}
