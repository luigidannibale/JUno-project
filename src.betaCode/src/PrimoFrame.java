import javax.swing.*;

public class PrimoFrame extends JFrame {

    public PrimoFrame(){
        super("Prima finestra");
        setSize(800, 800);
        setLocationRelativeTo(null);            //centra il frame
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
