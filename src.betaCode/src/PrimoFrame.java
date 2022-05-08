import javax.swing.*;
import java.awt.*;

public class PrimoFrame extends JFrame {

    private JLabel label;
    private JLabel image;

    private JButton button;

    private final int frameWidth = 1400;
    private final int frameHeight = 900;
    private final String pathImages = "resources/images/";

    public PrimoFrame(){
        super("Prima finestra");
        setLayout(new BorderLayout());
        //HMMM è da sistemare decisamente
        setContentPane(new JLabel(new ImageIcon((pathImages + "background.png"))));
        //Divide il frame in righe e colonne
        setLayout(new GridBagLayout());

        InitializeComponents();

        setSize(frameWidth, frameHeight);
        //centra il frame
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(pathImages + "icon2.png").getImage());
        setVisible(true);
    }

    private void InitializeComponents(){
        //Components
        label = new JLabel("Prova");

        image = new JLabel(new ImageIcon(pathImages + "White_deck/01.png"));

        button = new JButton("Cliccami");
        button.setFocusPainted(false);

        //Listener
        /*button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("riuscita");
            }
        });*/
        button.addActionListener(e -> AudioManager.getInstance().play("resources/audio/rick roll.wav"));

        //Layout
        GridBagConstraints layout = new GridBagConstraints();
        layout.gridx = 0;
        layout.gridy = 0;
        layout.weightx = 0;
        layout.weighty = 0.01;
        add(label, layout);

        layout.gridx = 1;
        layout.gridy = 0;
        layout.weightx = 0;
        layout.weighty = 0.01;
        add(button, layout);

        layout.gridx = 0;
        layout.gridy = 1;
        layout.weightx = 0;
        layout.weighty = 1.0;
        layout.gridwidth = 2;
        layout.gridheight = 1;
        add(image, layout);
    }
}
