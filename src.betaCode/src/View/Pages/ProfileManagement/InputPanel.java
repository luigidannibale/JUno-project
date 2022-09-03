package View.Pages.ProfileManagement;

import View.Elements.CustomMouseAdapter;
import View.Elements.GifComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public abstract class InputPanel extends JPanel
{
    //da fare con enum
    public enum InputMessages
    {
        NAME_INSERT("Insert your name"),
        NAME_NOT_VALID("Name is not valid"),
        NAME_ALREADY_EXISTING("This name is already taken"),
        CONFIRM_PASSWORD_INSERT("Confirm your password"),
        PASSWORD_INSERT("Insert your password"),
        PASSWORD_TOOLTIP("Password must have 6 character with 1 capital letter and 1 lowercase"),
        PASSWORD_ERROR("Password is not valid"),
        CONFIRM_PASSWORD("Confirm old password");
        private String associatedMessage;
        InputMessages(String associatedMessage){this.associatedMessage = associatedMessage;}
        public String getMessage(){return associatedMessage;}
    }

    JTextField txtInsertName;
    JTextField txtInsertPassword;
    GifComponent saveButton;
    GifComponent closeButton;
    private static final String imagePath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/UpdatePanel/";

    public InputPanel(Color backColor, Color borderColor)
    {
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//
//                System.out.println(jtabbedpane.getSelectedComponent());
//            }
//        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {e.getComponent().requestFocus();}
        });

        txtInsertName = new JTextField();
        txtInsertName.setName("name");
        txtInsertName.setBackground(backColor);
        txtInsertName.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertName.setPreferredSize(new Dimension(220,30));
        txtInsertName.setText(InputMessages.NAME_INSERT.getMessage());
        txtInsertName.setToolTipText(InputMessages.NAME_INSERT.getMessage());
        txtInsertName.setHorizontalAlignment(JTextField.CENTER);

        txtInsertPassword = new JTextField();
        txtInsertPassword.setName("password");
        txtInsertPassword.setBackground(backColor);
        txtInsertPassword.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertPassword.setPreferredSize(new Dimension(220,30));
        txtInsertPassword.setText(InputMessages.PASSWORD_INSERT.getMessage());
        txtInsertPassword.setToolTipText(InputMessages.PASSWORD_TOOLTIP.getMessage());
        txtInsertPassword.setHorizontalAlignment(JTextField.CENTER);

        saveButton = new GifComponent(imagePath + "save",50,50);
        saveButton.addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                save();
            }
        });
        closeButton =new GifComponent(imagePath + "discard",50,50);


        FocusListener onClickCancelText = onClickCancelTextFocusListener();
        txtInsertName.addFocusListener(onClickCancelText);
        txtInsertPassword.addFocusListener(onClickCancelText);

        setPreferredSize(new Dimension(300,200));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(txtInsertName, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(txtInsertPassword, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(saveButton, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(closeButton, gbc);

        //updatePanel.setLayout(gbc);

        setBackground(borderColor);
        setVisible(true);
        //roba del tabbed pane
//        this.add(updatePanel);
//        this.add(new JPanel());
//        this.add(new JPanel());
//        this.setTitleAt(0,"Registration");
//        this.setTitleAt(1,"Log in");
//        this.setTitleAt(2,"Update Profile");

    }
    protected abstract void save();

    protected FocusListener onClickCancelTextFocusListener()
    {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField textfield = ((JTextField) e.getComponent());
                if (ContainedInEnum.test(textfield.getText()))
                {
                    textfield.setText("");
                    textfield.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                JTextField textfield = ((JTextField) e.getComponent());
                if (textfield.getText().isEmpty())
                {
                    textfield.setForeground(Color.BLACK);
                    textfield.setText(InputMessages.valueOf(textfield.getName().toUpperCase()+"_INSERT").getMessage());
                }
            }};
    }
    protected boolean verifyInput()
    {
        String name = txtInsertName.getText(), password = txtInsertPassword.getText();
        if (check.test(name))
        {
            textFieldError(txtInsertName, InputMessages.NAME_NOT_VALID);
            return false;
        }
        if (check.test(password) || !isPasswordValid.test(password))
        {
            textFieldError(txtInsertPassword, InputMessages.PASSWORD_ERROR);
            return false;
        }
        return true;
    }

    public GifComponent getCloseButton(){return closeButton;}

    protected Predicate<String> ContainedInEnum = toCheck -> Arrays.stream(InputMessages.values()).anyMatch(v -> v.getMessage().equals(toCheck));

    protected Predicate<String> check = toCheck -> toCheck.isEmpty() || ContainedInEnum.test(toCheck);

    protected Predicate<String> isPasswordValid = password -> password.length() >= 6 && contains1capital1lower1digit(password);

    protected  void textFieldError(JTextField textField, InputMessages error)
    {
        textField.setText(error.getMessage());
        textField.setForeground(Color.RED);
        textField.transferFocus();
    }
    public boolean contains1capital1lower1digit (String string){ return Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])").matcher(string).find(); }
}
