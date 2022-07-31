package View;

import Controller.MainFrameController;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends ResizablePanel {

    public ProfilePanel(MainFrameController mfc){
        super(200, 100, 0);
        imagePath = "images/MainFrame/StartingMenuPanel/ProfilePanel/";

        setLayout(new GridBagLayout());
        InitializeComponents();
    }
    private void InitializeComponents()
    {
        /*
        icons = new ImageComponent[]{
                new ImageComponent(imagePath + "1"),
                new ImageComponent(imagePath + "2"),
                new ImageComponent(imagePath + "3"),
                new ImageComponent(imagePath + "4")
        };
        add(icons[0]);
        add(icons[1]);
        add(icons[2]);
        add(icons[3]);
         */

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0,0, getWidth(), getHeight());
    }
}
