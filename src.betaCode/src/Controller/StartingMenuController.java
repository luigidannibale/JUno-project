package Controller;

import View.MainFrame;
import View.StartingMenuPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.Arrays;

public class StartingMenuController {

    StartingMenuPanel view;

    public StartingMenuController(MainFrame mf){
        view = new StartingMenuPanel(mf);

        view.getGameChoiceIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mf.setVisiblePanel(MainFrame.Panels.GAMECHOICE);
                view.setVisible(false);
            }
        });

        //SettingPanel
        view.getSettingIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mf.setVisiblePanel(MainFrame.Panels.SETTINGS);
                view.setVisible(false);
            }
        });
        //Quit
        view.getQuitIcon().addMouseListener(new MouseAdapter() { // Quit button
            @Override
            public void mouseClicked(MouseEvent e) {

                try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
                {
                    ((WindowAdapter) ((Arrays.stream(mf.getWindowListeners()).toArray())[0])).windowClosing(null);
                }
                catch(Exception ex) { mf.dispose(); }
            }
        });
    }
}
