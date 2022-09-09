package View.Pages.ProfilePanels;

import java.awt.*;

/**
 * Class used to provide the {@link InputPanel} where the user can create a new account.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class RegistrationPanel extends InputPanel
{
    /**
     * Instantiate the {@link RegistrationPanel} with the given back color and border color
     * @param backColor
     * @param borderColor
     */
    public RegistrationPanel(Color backColor, Color borderColor)
    {
        super(backColor, borderColor);
        setName("RegistrationPanel");
    }
}
