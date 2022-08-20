package Controller;

import View.ProfilePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public class ProfilePanelController {

    private ProfilePanel view;

    public ProfilePanelController(MainFrameController mfc){
        view = new ProfilePanel(mfc);

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
    }

    public ProfilePanel getView() {
        return view;
    }

    public JPanel getSmallPanel(){
        return view.getSmallPanel();
    }
}
