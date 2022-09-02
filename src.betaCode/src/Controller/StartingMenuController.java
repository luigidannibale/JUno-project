package Controller;

import Utilities.Config;
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
        view.getGameChoiceIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mfc.setVisiblePanel(MainFrameController.Panels.GAMECHOICE);
            }
        });

        //SettingPanel
        view.getSettingIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mfc.setSettingsReturnPanel();
                mfc.setVisiblePanel(MainFrameController.Panels.SETTINGS);
            }
        });

        //Quit
        view.getQuitIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mfc.closeWindow();
            }
        });
        Arrays.stream(view.getComponents()).forEach(component -> {
                    component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
                    component.setSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
                    view.repaint();
                }
        );
    }

    public StartingMenuPanel getView() {
        return view;
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
