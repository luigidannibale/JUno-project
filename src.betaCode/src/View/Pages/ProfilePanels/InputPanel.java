package View.Pages.ProfilePanels;

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

/**
 * Class used to provide the common {@link JComponent}s and method of the profile management {@link InputPanel}s.
 * @author D'annibale Luigi, Venturini Daniele
 */
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
        public final String ASSOCIATED_MESSAGE;
        InputMessages(String associatedMessage){this.ASSOCIATED_MESSAGE = associatedMessage;}
    }

    public static final String IMAGE_PATH = ProfilePanel.IMAGE_PATH+"UpdatePanel/";

    protected JTextField txtInsertName;
    protected JTextField txtInsertPassword;
    protected GifComponent saveButton;
    protected GifComponent closeButton;

    /**
     * Instantiate the {@link InputPanel} with the given back color and border color and
     * initialize the common components of the subclass
     * @param backColor
     * @param borderColor
     */
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
        txtInsertName.setPreferredSize(new Dimension(230,40));
        txtInsertName.setFont(new Font(getFont().getFontName(),getFont().getStyle(),25));
        txtInsertName.setText(InputMessages.NAME_INSERT.ASSOCIATED_MESSAGE);
        txtInsertName.setToolTipText(InputMessages.NAME_INSERT.ASSOCIATED_MESSAGE);
        txtInsertName.setHorizontalAlignment(JTextField.CENTER);

        txtInsertPassword = new JTextField();
        txtInsertPassword.setName("password");
        txtInsertPassword.setBackground(backColor);
        txtInsertPassword.setBorder(new EmptyBorder(0,0,0,0));
        txtInsertPassword.setPreferredSize(new Dimension(230,40));
        txtInsertPassword.setFont(new Font(getFont().getFontName(),getFont().getStyle(),25));

        txtInsertPassword.setText(InputMessages.PASSWORD_INSERT.ASSOCIATED_MESSAGE);
        txtInsertPassword.setToolTipText(InputMessages.PASSWORD_TOOLTIP.ASSOCIATED_MESSAGE);
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

    /**
     * Resizes every component inside based on the scaling percentage.
     * If the component is a {@link JTextField} even the font is scaled.
     */
    @Override
    public void resizeComponents()
    {
        Arrays.stream(getComponents()).forEach(component ->
        {
            if(component instanceof JTextField){
                int width = Math.max((int) (component.getPreferredSize().width * Config.scalingPercentage), getPreferredSize().width - 100);
                component.setPreferredSize(new Dimension(width, (int) (component.getPreferredSize().height * Config.scalingPercentage)));
                component.setFont(component.getFont().deriveFont((float) (component.getFont().getSize()*Config.scalingPercentage)));
            }
            else component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
            component.repaint();
        });
    }

    /**
     * {@link FocusListener} for the {@link JTextField} in the {@link InputPanel}s
     * On focus lost the text-field displays an info message, while on focus gained the text is emptied.
     * @return the focus listener
     */
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
                    textfield.setText(InputMessages.valueOf(textfield.getName().toUpperCase()+"_INSERT").ASSOCIATED_MESSAGE);
                }
            }};
    }

    /**
     * Checks if the name and password in the related {@link JTextField} are valid.
     * @return true if valid, false otherwise
     */
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

    /**
     * Sets in the given {@link JTextField} the given text {@link InputMessages} error.
     * @param textField
     * @param error
     */
    public  void textFieldError(JTextField textField, InputMessages error)
    {
        textField.setText(error.ASSOCIATED_MESSAGE);
        textField.setForeground(Color.RED);
        textField.transferFocus();
    }

    /**
     * Checks if the given string has one capital, lower letter and one number
     */
    protected boolean containsOnecapitalOnelower1digit(String string){ return Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])").matcher(string).find(); }

    //PREDICATES
    /**
     * Checks if the given string is contained in the {@link InputMessages} enum
     */
    protected Predicate<String> ContainedInEnum = toCheck -> Arrays.stream(InputMessages.values()).anyMatch(message -> message.ASSOCIATED_MESSAGE.equals(toCheck));
    /**
     * Checks if the given string is empty or contained in the enum {@link InputMessages}
     */
    protected Predicate<String> check = toCheck -> toCheck.isEmpty() || ContainedInEnum.test(toCheck);
    /**
     * Checks if the password has more than 6 letters and if it has one capital, lower letter and one number
     */
    protected Predicate<String> isPasswordValid = password -> password.length() >= 6 && containsOnecapitalOnelower1digit(password);

    //GETTERS
    public GifComponent getCloseButton(){return closeButton;}
    public GifComponent getSaveButton(){return saveButton;}
    public JTextField getTxtInsertName() { return txtInsertName; }
    public JTextField getTxtInsertPassword() { return txtInsertPassword; }

}
