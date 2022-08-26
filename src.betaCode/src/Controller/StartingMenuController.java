package Controller;

import View.Pages.StartingMenuPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    }

    public StartingMenuPanel getView() {
        return view;
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
