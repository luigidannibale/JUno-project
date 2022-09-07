package View.Pages.ProfileManagement;

import Model.Player.PlayerManager;
import Utilities.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class UpdatePanel extends InputPanel
{

    JTextField txtConfirmPassword;
    public UpdatePanel(Color backColor, Color borderColor) 
    {
        super(backColor, borderColor);

        setName("UpdatePanel");

        GridBagConstraints gbc = new GridBagConstraints();

        txtConfirmPassword = new JTextField();
        txtConfirmPassword.setName("confirm_password");
        txtConfirmPassword.setBackground(backColor);
        txtConfirmPassword.setBorder(new EmptyBorder(0,0,0,0));
        txtConfirmPassword.setPreferredSize(new Dimension(300,40));
        txtConfirmPassword.setFont(new Font(getFont().getFontName(),getFont().getStyle(),25));
        txtConfirmPassword.setText(InputMessages.CONFIRM_PASSWORD.getMessage());
        txtConfirmPassword.setToolTipText(InputMessages.CONFIRM_PASSWORD.getMessage());
        txtConfirmPassword.setHorizontalAlignment(JTextField.CENTER);

        txtConfirmPassword.addFocusListener(onClickCancelTextFocusListener());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(txtConfirmPassword, gbc);

        txtInsertName.setVisible(false);
        txtInsertPassword.setVisible(false);
    }

    public void setTxtVisibility(boolean ConfirmPassword)
    {
        txtConfirmPassword.setVisible(ConfirmPassword);
        txtInsertName.setVisible(!ConfirmPassword);
        txtInsertPassword.setVisible(!ConfirmPassword);

        if (!ConfirmPassword){
            resetTextField(txtInsertName, txtInsertPassword);
        }
        else{
            resetTextField(txtConfirmPassword);
        }
        //resetTextField( !ConfirmPassword ?new JTextField(){txtInsertName,txtInsertPassword} : txtConfirmPassword);

    }

    private void resetTextField(JTextField... textfields)
    {
        Arrays.stream(textfields).forEach(textfield -> {
            textfield.grabFocus();
            textfield.transferFocus();
        });

    }

    public JTextField getTxtConfirmPassword() { return txtConfirmPassword; }
}
