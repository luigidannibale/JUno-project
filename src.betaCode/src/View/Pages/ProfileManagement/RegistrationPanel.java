package View.Pages.ProfileManagement;

import Model.Player.HumanPlayer;
import Model.Player.PlayerManager;
import Utilities.Config;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends InputPanel
{

    public RegistrationPanel(Color backColor, Color borderColor)
    {
        super(backColor, borderColor);
        setName("RegistrationPanel");
    }

    @Override
    protected void save()
    {
        if(!verifyInput()) return;
        String name = txtInsertName.getText(), password = txtInsertPassword.getText();

        boolean nicknameRegistered = PlayerManager.findPlayerByNicknameOrDefault(name).getName() == name;
        if (!nicknameRegistered)
            Config.savedPlayer = new HumanPlayer(name,password);
        else textFieldError(txtInsertName,placeholders.get("nameAlreadyExists"));
    }

}
