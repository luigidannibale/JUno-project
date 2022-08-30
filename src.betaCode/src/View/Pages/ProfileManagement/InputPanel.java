package View.Pages.ProfileManagement;

import Model.Player.PlayerManager;
import View.Elements.GifComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

public abstract class InputPanel extends JPanel
{
    HashMap<String,String> placeholders = new HashMap<>(){{
        put("nameToolTip","Insert your name");
        put("passwordToolTip","Insert your password");
        put("nameError","Name is not valid");
        put("passwordError","Password is not valid");
        put("nameInsert","Insert your name");
        put("passwordInsert","Insert your password");
        put("nameAlreadyExists","This nickname already exists");
    }} ;
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

        txtInsertName = new JTextField();
        txtInsertName.setBackground(backColor);
        txtInsertName.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertName.setPreferredSize(new Dimension(220,30));
        txtInsertName.setText(placeholders.get("nameInsert"));
        txtInsertName.setToolTipText(placeholders.get("nameToolTip"));
        txtInsertName.setHorizontalAlignment(JTextField.CENTER);

        txtInsertPassword = new JTextField();
        txtInsertPassword.setBackground(backColor);
        txtInsertPassword.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertPassword.setPreferredSize(new Dimension(220,30));
        txtInsertPassword.setText(placeholders.get("passwordInsert"));
        txtInsertPassword.setToolTipText(placeholders.get("passwordToolTip"));
        txtInsertPassword.setHorizontalAlignment(JTextField.CENTER);

        saveButton = new GifComponent(imagePath + "save",50,50);
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                save();
            }
        });
        closeButton =new GifComponent(imagePath + "discard",50,50);


        FocusListener onClickCancelText = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField textfield = ((JTextField) e.getComponent());
                if (placeholders.values().contains(textfield.getText()))
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
                    textfield.setForeground(Color.GRAY);
                    textfield.setText(placeholders.get("nameInsert"));
                }
            }};



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
    protected boolean verifyInput()
    {

        String name = txtInsertName.getText(), password = txtInsertPassword.getText();

        if (check.test(name))
        {
            textFieldError(txtInsertName, "nameError");
            return false;
        }
        if (check.test(password))
        {
            textFieldError(txtInsertPassword, "passwordError");
            return false;
        }

        var nicknameRegistered = PlayerManager.findPlayerByNickname(name);
        if (nicknameRegistered == null) System.out.println("Il nickname non è registrato");
        else System.out.println("Il nickname è registrato");
        return false;
    }

    public GifComponent getCloseButton(){return closeButton;}
    protected Predicate<String> check = toCheck -> toCheck.isEmpty() || placeholders.containsValue(toCheck);

    protected  void textFieldError(JTextField textField, String error)
    {
        textField.setText(placeholders.get(error));
        textField.setForeground(Color.RED);
    }
}
