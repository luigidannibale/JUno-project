package View.Pages.ProfileManagement;

import Model.Player.Player;
import Model.Player.PlayerManager;
import View.Elements.GifComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;


public class UpdatePlayerPanel extends JTabbedPane
{
    LoginPanel loginPanel;
    RegistrationPanel registrationPanel;
    UpdatePanel updatePanel;

    public UpdatePlayerPanel(Color backColor, Color borderColor)
    {
        loginPanel = new LoginPanel(backColor,borderColor);
        registrationPanel = new RegistrationPanel(backColor,borderColor);
        updatePanel = new UpdatePanel(backColor,borderColor);

        /*
        for (int i = 0; i < panels.size(); i++) {
            if (i == currentIndex) continue;
            InputPanel p = panels.get(i);
            Arrays.stream(p.getComponents()).filter(c -> c instanceof JTextField).forEach(c -> ((JTextField) c).grabFocus());
        }

         */
        addChangeListener(e -> clearTextField());

        this.add(loginPanel);
        this.add(registrationPanel);
        this.add(updatePanel);
        this.setTitleAt(0,"Log in");
        this.setTitleAt(1,"Registration");
        this.setTitleAt(2,"Update Profile");

        setSelectedIndex(0);
    }

    public List<InputPanel> getPanels(){
        return List.of(loginPanel, registrationPanel, updatePanel);
    }

    public void clearTextField(){
        int currentIndex = getSelectedIndex();
        InputPanel panel = getPanels().get(currentIndex);
        Arrays.stream(panel.getComponents()).filter(c -> c instanceof JTextField).forEach(c -> {
            var text = (JTextField)c;
            text.grabFocus();
            text.setText("");
            panel.requestFocusInWindow();
        });
    }
}
