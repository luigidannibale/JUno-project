package View.Pages.ProfileManagement;

import Controller.Utilities.Config;
import View.Elements.GifComponent;
import View.Pages.ResizablePanel;

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

public abstract class InputPanel extends ResizablePanel
{
    public enum InputMessages
    {
        NAME_INSERT("Insert your name"),
        NAME_NOT_VALID("Name is not valid"),
        NAME_ALREADY_EXISTS("This name is already taken"),
        CONFIRM_PASSWORD_INSERT("Confirm your password"),
        PASSWORD_INSERT("Insert your password"),
        PASSWORD_TOOLTIP("Password must have 6 character with 1 capital letter and 1 lowercase"),
        PASSWORD_ERROR("Password is not valid"),
        CONFIRM_PASSWORD("Confirm old password");
        private final String associatedMessage;
        InputMessages(String associatedMessage){this.associatedMessage = associatedMessage;}
        public String getMessage(){return associatedMessage;}
    }

    public static final String IMAGE_PATH = ProfilePanel.IMAGE_PATH+"UpdatePanel/";

    JTextField txtInsertName;
    JTextField txtInsertPassword;
    GifComponent saveButton;
    GifComponent closeButton;

    public InputPanel(Color backColor, Color borderColor)
    {
        super(300,200,1);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {e.getComponent().requestFocus();}
        });

        txtInsertName = new JTextField();
        txtInsertName.setName("name");
        txtInsertName.setBackground(backColor);
        txtInsertName.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertName.setPreferredSize(new Dimension(300,40));
        txtInsertName.setFont(new Font(getFont().getFontName(),getFont().getStyle(),25));
        txtInsertName.setText(InputMessages.NAME_INSERT.getMessage());
        txtInsertName.setToolTipText(InputMessages.NAME_INSERT.getMessage());
        txtInsertName.setHorizontalAlignment(JTextField.CENTER);

        txtInsertPassword = new JTextField();
        txtInsertPassword.setName("password");
        txtInsertPassword.setBackground(backColor);
        txtInsertPassword.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertPassword.setPreferredSize(new Dimension(300,40));
        txtInsertPassword.setFont(new Font(getFont().getFontName(),getFont().getStyle(),25));

        txtInsertPassword.setText(InputMessages.PASSWORD_INSERT.getMessage());
        txtInsertPassword.setToolTipText(InputMessages.PASSWORD_TOOLTIP.getMessage());
        txtInsertPassword.setHorizontalAlignment(JTextField.CENTER);

        saveButton = new GifComponent(IMAGE_PATH + "save",80,80);
        closeButton =new GifComponent(IMAGE_PATH + "discard",80,80);

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
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(txtInsertName, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(txtInsertPassword, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(saveButton, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(closeButton, gbc);

        setBackground(borderColor);
        setVisible(true);
    }

    @Override
    public void resizeComponents()
    {
        //System.out.println("putting the size");
        //super.resizeComponents();
        Arrays.stream(getComponents()).forEach(component ->
        {
            //if(component.getPreferredSize().height == 0) System.out.println(component);
            //System.out.println(component.getName()+component.getPreferredSize());
            component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
            if(component instanceof JTextField) {

                var font = component.getFont();
                //component.setFont(new Font(font.getFontName(), font.getStyle(),(int) (font.getSize()*Config.scalingPercentage)));
                component.setFont(font.deriveFont((float) (font.getSize()*Config.scalingPercentage)));
                //System.out.println(component.getName()+component.getPreferredSize()+" old "+font.getSize()+" new "+new Font(font.getFontName(), font.getStyle(), (int) (font.getSize()*Config.scalingPercentage)));
            }
            //System.out.println();
            component.repaint();
        });
    }

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
            public void focusLost(FocusEvent e)
            {
                JTextField textfield = ((JTextField) e.getComponent());
                if (textfield.getText().isEmpty())
                {
                    textfield.setForeground(Color.BLACK);
                    textfield.setText(InputMessages.valueOf(textfield.getName().toUpperCase()+"_INSERT").getMessage());
                }
            }};
    }

    public boolean verifyInput()
    {
        String name = txtInsertName.getText(), password = txtInsertPassword.getText();
        if (check.test(name) || name.length() > 12)
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

    public GifComponent getSaveButton(){return saveButton;}

    public JTextField getTxtInsertName() { return txtInsertName; }

    public JTextField getTxtInsertPassword() { return txtInsertPassword; }

    protected Predicate<String> ContainedInEnum = toCheck -> Arrays.stream(InputMessages.values()).anyMatch(v -> v.getMessage().equals(toCheck));

    protected Predicate<String> check = toCheck -> toCheck.isEmpty() || ContainedInEnum.test(toCheck);

    protected Predicate<String> isPasswordValid = password -> password.length() >= 6 && containsOnecapitalOnelower1digit(password);

    public  void textFieldError(JTextField textField, InputMessages error)
    {
        textField.setText(error.getMessage());
        textField.setForeground(Color.RED);
        textField.transferFocus();
    }
    public boolean containsOnecapitalOnelower1digit(String string){ return Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])").matcher(string).find(); }
}
