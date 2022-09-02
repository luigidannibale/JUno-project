package View.Pages.ProfileManagement;

import Model.Player.HumanPlayer;
import Model.Player.PlayerManager;
import Utilities.Config;

import java.awt.*;
import java.util.function.Predicate;

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
        String name = txtInsertName.getText(),
               password = txtInsertPassword.getText();
        if (PlayerManager.findPlayerByNicknameOrDefault(name).getName().equals(name))
        {
            textFieldError(txtInsertName, InputMessages.NAME_ALREADY_EXISTING);
            return;
        }
        System.out.println("Registrato : "+ name + " con esito " + PlayerManager.savePlayer(new HumanPlayer(name,password)));
        ((ProfilePanel)getParent().getParent()).update();
    }




}
