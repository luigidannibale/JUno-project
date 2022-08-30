package View.Pages.ProfileManagement;

import Model.Player.Player;
import Model.Player.PlayerManager;
import View.Elements.GifComponent;

import javax.swing.*;
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

        this.add(loginPanel);
        this.add(registrationPanel);
        this.add(updatePanel);
        this.setTitleAt(0,"Log in");
        this.setTitleAt(1,"Registration");
        this.setTitleAt(2,"Update Profile");

    }

    public List<InputPanel> getPanels(){
        return List.of(loginPanel, registrationPanel, updatePanel);
    }

}
