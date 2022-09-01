package View.Pages.ProfileManagement;

import Utilities.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
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
        txtConfirmPassword.setPreferredSize(new Dimension(220,30));
        txtConfirmPassword.setText(InputMessages.CONFIRM_PASSWORD.getMessage());
        txtConfirmPassword.setToolTipText(InputMessages.CONFIRM_PASSWORD.getMessage());
        txtConfirmPassword.setHorizontalAlignment(JTextField.CENTER);

        txtConfirmPassword.addFocusListener(onClickCancelTextFocusListener());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(txtConfirmPassword, gbc);

        txtInsertName.setVisible(false);
        txtInsertPassword.setVisible(false);
    }

    @Override
    protected void save()
    {
        if(txtConfirmPassword.isVisible())
        {//pass confirmation
            if(!txtConfirmPassword.getText().equals(Config.loggedPlayer.getPassword()))
            {//pass not valid
                System.out.println("logged pass "+Config.loggedPlayer.getPassword());
                System.out.println("pass "+txtConfirmPassword.getText());
                System.out.println("equals "+txtConfirmPassword.getText().equals(Config.loggedPlayer.getPassword()));
                textFieldError(txtConfirmPassword,InputMessages.PASSWORD_ERROR);
                return;
            }

            txtInsertName.setVisible(true);
            txtInsertPassword.setVisible(true);
            txtConfirmPassword.setVisible(false);

        }
        else
        {//profile update

        }
    }



}
