import javax.swing.*;
import java.awt.*;

public class PrimoFrame extends JFrame {

    private JLabel label;
    private JLabel image;

    private JButton button;

    public PrimoFrame(){
        super("Prima finestra");
        //Divide il frame in righe e colonne
        setLayout(new GridBagLayout());

        InitializeComponents();

        setSize(800, 800);
        setLocationRelativeTo(null);            //centra il frame
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setIconImage(new ImageIcon("resources/images/icon2.png").getImage());
    }

    private void InitializeComponents(){
        //Components
        label = new JLabel("Prova");

        image = new JLabel(new ImageIcon("resources/images/White_deck/01.png"));

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
