import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private JPanel UserPanel;
    private JPanel StartingPanel;
    private final int frameWidth = 1400;
    private final int frameHeight = 900;
    private final String pathImages = "resources/images/";

    public MainFrame(){
        super("J Uno");
        setLayout(new BorderLayout());
        setContentPane(new JLabel(getImageIcon("background.png")));
        setLayout(new GridBagLayout()); //Divide il frame in righe e colonne
        InitializeComponents();
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);    //centra il frame
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(getImageIcon("icon2.png").getImage());
        setVisible(true);



        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                UIManager.put("OptionPane.background", new ColorUIResource(255,255,255));
                UIManager.put("Panel.background", new ColorUIResource(255,255,255));
                String[] options = new String[]{"Yes", "No"};
                int confirm = JOptionPane.showOptionDialog(
                        null,
                        "Do you REALLY want to exit?",
                        "Probably you just miss-clicked",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        getImageIcon("block.png"),
                        options, options[1]);
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
            }
        });
    }

    private ImageIcon getImageIcon(String filename){
        return new ImageIcon(pathImages + filename);
    }

    private void InitializeComponents(){
        //Components
        //label = new JLabel("Prova");

        //image = new JLabel(new ImageIcon(pathImages + "White_deck/01.png"));

        //button = new JButton("Cliccami");
        //button.setFocusPainted(false);

        //Listener
        /*button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("riuscita");
            }
        });*/
        //button.addActionListener(e -> AudioManager.getInstance().play("resources/audio/rick roll.wav"));

        //Layout
        /*
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
        add(image, layout);*/
    }
}
