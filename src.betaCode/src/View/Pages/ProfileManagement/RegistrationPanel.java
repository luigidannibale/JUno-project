package View.Pages.ProfileManagement;

import Model.Player.HumanPlayer;
import Model.Player.PlayerManager;
import Utilities.Config;

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
        {   // non worka perche fa il controllo nel file resources -> saves.json, il quale ancora non viene lavorato quindi quando lo si interpella non worka
            Config.savedPlayer = new HumanPlayer(name,password);
            System.out.println("lo salvo");
        }

        else textFieldError(txtInsertName, InputMessages.NAME_ALREADY_EXISTING.getMessage());
    }

}
