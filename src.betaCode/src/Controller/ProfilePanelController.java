package Controller;

import View.Elements.ViewPlayer;
import View.Pages.ProfileManagement.InputPanel;
import View.Pages.ProfileManagement.ProfilePanel;
import View.Pages.ProfileManagement.UpdatePlayerPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public class ProfilePanelController {

    private ProfilePanel view;

    private MainFrameController.Panels returnPanel;

    public ProfilePanelController(MainFrameController mfc){
        ViewPlayer player = mfc.getViewPlayer();
        view = new ProfilePanel(player);

        JLabel name = view.getLabelName();
        name.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                mfc.setVisiblePanel(MainFrameController.Panels.PROFILE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(0);
                name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(-1);
            }

            //UNDERLINE 0 = sottolineato
            //UNDERLINE -1 = non sottolineato
            void setFont(int onOff){
                Font font = name.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, onOff);
                name.setFont(font.deriveFont(attributes));
        }});

        MouseAdapter exitFromProfilePanel = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) { mfc.setVisiblePanel(returnPanel); }
        };

        view.getUpdateTabbedPanel().getPanels().forEach(pane -> pane.getCloseButton().addMouseListener(exitFromProfilePanel));


        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png"));
        view.getLblChangeIcon().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    player.getProfilePicture().setCircleImage(chooser.getSelectedFile().getAbsolutePath());
                    //view.setProfilePicture(player.getProfilePicture());
            }
        });
    }

    public ProfilePanel getView() { return view; }

    public JPanel getSmallPanel(){ return view.getSmallPanel(); }

    public void setVisible(boolean visible)
    {
        view.setVisible(visible);
        view.getSmallPanel().setVisible(!visible);
        if (visible) view.InitializeMainPanel();
        else view.InitializeSmallPanel();
    }

    public void setReturnPanel(MainFrameController.Panels returnPanel)
    { if (returnPanel != MainFrameController.Panels.PROFILE && returnPanel != MainFrameController.Panels.SETTINGS) this.returnPanel = returnPanel; }
}
