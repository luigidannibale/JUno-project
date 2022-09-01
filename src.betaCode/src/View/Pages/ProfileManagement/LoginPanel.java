package View.Pages.ProfileManagement;

import Model.Player.PlayerManager;
import Utilities.Config;

import java.awt.*;

public class LoginPanel extends InputPanel
{

    public LoginPanel(Color backColor, Color borderColor) {
        super(backColor, borderColor);
        setName("LoginPanel");
    }

    @Override
    protected void save() {
        if(!verifyInput()) return;
        String name = txtInsertName.getText(),
               password = txtInsertPassword.getText();

        var optionalPlayer = PlayerManager.findPlayerByNicknameOrDefault(name);
        if (!optionalPlayer.getName().equals(name)){
            textFieldError(txtInsertName,InputMessages.NAME_NOT_VALID);
            return;
        }

        if (!optionalPlayer.getPassword().equals(password)){
            textFieldError(txtInsertName,InputMessages.PASSWORD_ERROR);
            return;
        }

        Config.loggedPlayer = optionalPlayer;
    }

}
