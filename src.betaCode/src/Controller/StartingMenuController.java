package Controller;

import View.MainFrame;
import View.StartingMenuPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class StartingMenuController {

    StartingMenuPanel view;

    public StartingMenuController(MainFrameController mfc){
        view = new StartingMenuPanel(mfc);
        //view.setSize();

        view.getGameChoiceIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mfc.setVisiblePanel(MainFrameController.Panels.GAMECHOICE);
                view.setVisible(false);
            }
        });

        //SettingPanel
        view.getSettingIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mfc.setVisiblePanel(MainFrameController.Panels.SETTINGS);
                view.setVisible(false);
            }
        });
        //Quit
        view.getQuitIcon().addMouseListener(new MouseAdapter() { // Quit button
            @Override
            public void mouseClicked(MouseEvent e) {
                mfc.closeWindow();
            }
        });
    }

    public StartingMenuPanel getView() {
        return view;
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
