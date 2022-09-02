package Controller;

import Utilities.Config;
import View.Elements.CustomMouseAdapter;
import View.Pages.StartingMenuPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class StartingMenuController {

    private StartingMenuPanel view;

    public StartingMenuController(MainFrameController mfc){
        view = new StartingMenuPanel();

        //GameChoicePanel
        view.getGameChoiceIcon().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mfc.setVisiblePanel(MainFrameController.Panels.GAMECHOICE);
            }
        });

        //SettingPanel
        view.getSettingIcon().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mfc.setSettingsReturnPanel();
                mfc.setVisiblePanel(MainFrameController.Panels.SETTINGS);
            }
        });

        //Quit
        view.getQuitIcon().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
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
