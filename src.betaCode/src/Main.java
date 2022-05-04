import javax.swing.*;

public class Main extends JFrame {

    public Main(){
        setTitle("Test");
        setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);        //centra il frame
    }

    public static void main(String[] args){
        //nome da cambiare
        Main m = new Main();
    }
}
