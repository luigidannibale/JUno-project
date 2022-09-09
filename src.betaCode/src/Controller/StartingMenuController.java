package Controller;

import View.Elements.CustomMouseAdapter;
import View.Pages.GameChoicePanel;
import View.Pages.StartingMenuPanel;

import java.awt.event.MouseEvent;

/**
 * Specialize {@link Controller} on {@link StartingMenuPanel}.
 * Class used to get the inputs from the user in the {@link StartingMenuPanel}.
 * It lets the user navigate between the {@link View.Pages.SettingsPanel} and the {@link View.Pages.GameChoicePanel}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class StartingMenuController extends Controller<StartingMenuPanel>
{
    /**
     * Creates a new {@link StartingMenuController} with its associated view ({@link StartingMenuPanel}), and adds the listeners to the {@link View.Elements.ImageComponent} inside the {@link StartingMenuPanel}
     */
    public StartingMenuController()
    {
        super(new StartingMenuPanel());
        MainFrameController mfc = MainFrameController.getInstance();

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
