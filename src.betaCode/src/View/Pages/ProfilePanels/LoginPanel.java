package View.Pages.ProfilePanels;

import java.awt.*;

/**
 * Class used to provide the {@link InputPanel} where the user can log in an already created account.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class LoginPanel extends InputPanel
{
    /**
     * Instantiate the {@link LoginPanel} with the given back color and border color
     * @param backColor
     * @param borderColor
     */
    public LoginPanel(Color backColor, Color borderColor)
    {
        super(backColor, borderColor);
        setName("LoginPanel");
    }
}

