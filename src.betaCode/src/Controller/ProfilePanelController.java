package Controller;

import Model.Player.HumanPlayer;
import Model.Player.PlayerManager;
import Utilities.Config;
import Utilities.DataAccessManager;
import View.Elements.CircularImage;
import View.Elements.CustomMouseAdapter;
import View.Elements.ViewPlayer;
import View.Pages.ProfileManagement.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.Random;

public class ProfilePanelController extends Controller<ProfilePanel>
{

    private MainFrameController.Panels returnPanel;

    public ProfilePanelController()
    {
        super(new ProfilePanel());

        MainFrameController mfc = MainFrameController.getInstance();

        JLabel name = view.getLabelName();
        name.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                mfc.setVisiblePanel(MainFrameController.Panels.PROFILE);
                Color c;
                switch (new Random().nextInt(1,10))
                {
                    case 1 -> c = Color.BLACK;
                    case 2 -> c = Color.RED;
                    case 3 -> c = Color.BLUE;
                    case 4 -> c = Color.YELLOW;
                    case 5 -> c = Color.GREEN;
                    default -> c = Color.BLACK;
                }
                name.setForeground(c);}
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setFont(0);
                name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) { setFont(-1); }

            //UNDERLINE 0 = sottolineato
            //UNDERLINE -1 = non sottolineato
            private void setFont(int onOff)
            {
                Font font = name.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, onOff);
                name.setFont(font.deriveFont(attributes));
            }
        });

        MouseAdapter exitFromProfilePanel = new CustomMouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                mfc.setVisiblePanel(returnPanel);
            }
        };

        view.getPlayerInputTabbedPanel().getPanels().forEach(inputPanel -> {
            inputPanel.getCloseButton().addMouseListener(exitFromProfilePanel);
            inputPanel.getSaveButton().addMouseListener(getSave(inputPanel, mfc));

        });
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png"));
        view.getLblChangeIcon().addMouseListener(new CustomMouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    mfc.getViewPlayer().getProfilePicture().setCircleImage(Config.savedIconPath = chooser.getSelectedFile().getAbsolutePath());
                    DataAccessManager DAM = new DataAccessManager();
                    DAM.saveProfile(Config.loggedPlayer);
                }
            }
        });
    }

    private CustomMouseAdapter getSave(InputPanel inputPanel, MainFrameController mfc)
    {
        return new CustomMouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JTextField txtInsertName = inputPanel.getTxtInsertName(),
                        txtInsertPassword = inputPanel.getTxtInsertPassword();

                if (inputPanel instanceof UpdatePanel)
                {//have to put also the "delete me" button
                    update((UpdatePanel) inputPanel, txtInsertName, txtInsertPassword);
                    view.update();
                    return;
                }

                if(!inputPanel.verifyInput()) return;

                if (inputPanel instanceof LoginPanel)
                    login((LoginPanel) inputPanel, txtInsertName, txtInsertPassword, mfc);
                else if (inputPanel instanceof RegistrationPanel)
                    registrate((RegistrationPanel) inputPanel, txtInsertName, txtInsertPassword);
                view.update();
            }
        };
    }

    private void update(UpdatePanel panel, JTextField txtInsertName, JTextField txtInsertPassword)
    {
        JTextField txtConfirmPassword = panel.getTxtConfirmPassword();
        
        if(txtConfirmPassword.isVisible())
        {//pass confirmation
            if(!txtConfirmPassword.getText().equals(Config.loggedPlayer.getPassword()))
            {//pass not valid
                panel.textFieldError(txtConfirmPassword, InputPanel.InputMessages.PASSWORD_ERROR);
                return;
            }
            //view.getPlayerInputTabbedPanel().clearTextField();
            panel.setTxtVisibility(false);
        }
        else if (panel.verifyInput())
        {//profile update
            String oldName = Config.loggedPlayer.getName();
            String oldPasword = Config.loggedPlayer.getPassword();
            DataAccessManager DAM = new DataAccessManager();
            Config.loggedPlayer.setName(txtInsertName.getText());
            Config.loggedPlayer.setPassword(txtInsertPassword.getText());
            //view.getPlayerInputTabbedPanel().clearTextField();
            panel.setTxtVisibility(DAM.updateProfile(Config.loggedPlayer,oldName, oldPasword));
        }
    }

    private void login(LoginPanel panel, JTextField txtInsertName, JTextField txtInsertPassword, MainFrameController mfc)
    {
        String name = txtInsertName.getText(),
                password = txtInsertPassword.getText();

        DataAccessManager DAM = new DataAccessManager();
        HumanPlayer optionalPlayer = DAM.getModelProfile(name);

        if (!optionalPlayer.getName().equals(name))
        {
            panel.textFieldError(txtInsertName, InputPanel.InputMessages.NAME_NOT_VALID);
            return;
        }

        if (!optionalPlayer.getPassword().equals(password))
        {
            panel.textFieldError(txtInsertName, InputPanel.InputMessages.PASSWORD_ERROR);
            return;
        }

        Config.loggedPlayer = optionalPlayer;
        if (DAM.loadPlayerProfile(optionalPlayer.getName())) MainFrameController.getInstance().setViewPlayer(new ViewPlayer(optionalPlayer, new CircularImage(Config.savedIconPath)));
    }

    private void registrate(RegistrationPanel panel, JTextField txtInsertName, JTextField txtInsertPassword)
    {
        String name = txtInsertName.getText(),
                password = txtInsertPassword.getText();
        HumanPlayer player = PlayerManager.findPlayerByNicknameOrDefault(name);
        if (player.getName().equals(name))
        {
            panel.textFieldError(txtInsertName, InputPanel.InputMessages.NAME_ALREADY_EXISTS);
            return;
        }
        player = new HumanPlayer(name, password);
        DataAccessManager DAM = new DataAccessManager();
        System.out.println("Registrato : "+ name + " con esito " + DAM.saveModelProfile(player));
        Config.assignDefaultValues();
        Config.loggedPlayer = player;
        if (DAM.saveProfile(player)) MainFrameController.getInstance().setViewPlayer(new ViewPlayer(player, new CircularImage(Config.savedIconPath)));
    }

    public JPanel getSmallPanel(){ return view.getSmallPanel(); }

    @Override
    public void setVisible(boolean visible)
    {
        view.setVisible(visible);
        view.getSmallPanel().setVisible(!visible);
        if (visible) view.initializeMainPanel();
        else view.initializeSmallPanel();
    }

    public void setReturnPanel(MainFrameController.Panels returnPanel)
    { if (returnPanel != MainFrameController.Panels.PROFILE && returnPanel != MainFrameController.Panels.SETTINGS) this.returnPanel = returnPanel; }
}
