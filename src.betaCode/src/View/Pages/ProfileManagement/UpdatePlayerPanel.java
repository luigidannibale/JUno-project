package View.Pages.ProfileManagement;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;



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
        setPreferredSize(new Dimension(370,300));
        addChangeListener(e -> clearTextField());

        getPanels().forEach(this::add);
        this.setTitleAt(0,"Log in");
        this.setTitleAt(1,"Registration");
        this.setTitleAt(2,"Update Profile");
        setTabLayoutPolicy(WRAP_TAB_LAYOUT);
        setSelectedIndex(0);
    }

    public List<InputPanel> getPanels(){ return List.of(loginPanel, registrationPanel, updatePanel); }

    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        loginPanel.resizeComponents();
        updatePanel.resizeComponents();
        registrationPanel.resizeComponents();
    }

    public void clearTextField()
    {
        InputPanel panel = getPanels().get(getSelectedIndex());
        Arrays.stream(panel.getComponents()).filter(c -> c instanceof JTextField).forEach(c -> {
            var text = (JTextField)c;
            text.grabFocus();
            text.setText("");
            panel.requestFocusInWindow();
        });
    }
}
