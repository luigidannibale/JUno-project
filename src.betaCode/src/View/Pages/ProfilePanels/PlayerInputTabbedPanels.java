package View.Pages.ProfilePanels;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PlayerInputTabbedPanels extends JTabbedPane
{
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private UpdatePanel updatePanel;

    /**
     * Initialize the {@link PlayerInputTabbedPanels} and the {@link InputPanel}s with the given back color and border color
     * @param backColor
     * @param borderColor
     */
    public PlayerInputTabbedPanels(Color backColor, Color borderColor)
    {

        loginPanel = new LoginPanel(backColor,borderColor);
        registrationPanel = new RegistrationPanel(backColor,borderColor);
        updatePanel = new UpdatePanel(backColor,borderColor);
        setPreferredSize(new Dimension(370,300));
        addChangeListener(e -> clearTextField());

        getPanels().forEach(this::add);
        this.setTitleAt(0,"Log in");
        this.setTitleAt(1,"Registration");
        this.setTitleAt(2,"Update Profile");
        setTabLayoutPolicy(WRAP_TAB_LAYOUT);
        setSelectedIndex(0);
    }

    /**
     * Grabs transfers the focus for each {@link JTextField} in the panel and resets their text when the tabbed pane is changed
     */
    public void clearTextField()
    {
        InputPanel panel = getPanels().get(getSelectedIndex());
        Arrays.stream(panel.getComponents()).filter(c -> c instanceof JTextField).forEach(c -> {
            var text = (JTextField) c;
            text.grabFocus();
            text.setText("");
            requestFocusInWindow();
        });
    }

    //GETTER
    public List<InputPanel> getPanels(){ return List.of(loginPanel, registrationPanel, updatePanel); }

    //needed to resize the components of the child panels
    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        loginPanel.resizeComponents();
        updatePanel.resizeComponents();
        registrationPanel.resizeComponents();
    }


}
