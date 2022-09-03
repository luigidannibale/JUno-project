package Controller;

import View.Elements.CustomMouseAdapter;
import View.Pages.StartingMenuPanel;

import java.awt.event.MouseEvent;

public class StartingMenuController extends Controller<StartingMenuPanel>
{
    public StartingMenuController(MainFrameController mfc)
    {
        super(new StartingMenuPanel());

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
}
