package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class StartingMenuPanel extends JPanel {
    private String ImagesPath = "resources/images/MainFrame/panel/";
    private enum compSizes{
        Small(48),
        Medium(56),
        Big(64),
        Large(80);

        compSizes (int size){}

        public int getSize(){
            switch (super.ordinal()){
                case 0 ->{ return 48;}
                case 1 ->{ return 56;}
                case 2 ->{ return 64;}
                case 3 ->{ return 80;}
                default -> {return -1;}
            }
        }
        public int getBiggerSize(compSizes size){
            switch (size){
                case Small -> {return Medium.getSize();}
                case Medium -> {return Big.getSize();}
                case Big -> {return Large.getSize();}
                default -> {return -1;}
            }
        }
        public int getSmallerSize(compSizes size) {
            switch (size) {
                case Medium -> {return Small.getSize();}
                case Big -> {return Medium.getSize();}
                case Large -> {return Big.getSize();}
                default -> {return -1;}
            }
        }

    }
    public enum comp{
        StartGame("Startgame"),
        Settings("Settings"),
        Quit("Quit");

        private String ImagesPath = "resources/images/MainFrame/panel/";
        comp(String s) {}
        @Override
        public String toString() {
            return super.toString();
        }
        public String getPath(int num){
            return  ImagesPath+super.toString()+Integer.toString(num)+".png";
        }

    }
    private JLabel[] icons;
    private JLabel[] options;

    public StartingMenuPanel(){
        setBackground(Color.BLUE);
        setLayout(new GridBagLayout());
        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents(){
        //Components
        icons = new JLabel[]{
                new JLabel(new ImageIcon(comp.StartGame.getPath(compSizes.Small.getSize()))),
                new JLabel(new ImageIcon(comp.Settings.getPath(48))),
                new JLabel(new ImageIcon(comp.Quit.getPath(48)))
        };
        GridBagConstraints layout = new GridBagConstraints();
        for (int i = 0 ; i<3;i++)
        {
            layout.gridx = 0;
            layout.gridy = i;
            layout.insets = new Insets(20, 0, 0, 10);
            layout.anchor = GridBagConstraints.LINE_START;
            add(icons[i], layout);
        }
        /*options = new JLabel[]{
                new JLabel(new ImageIcon("resources/images/MainFrame/new_game.png")),
                new JLabel(new ImageIcon("resources/images/MainFrame/settings.png")),
                new JLabel(new ImageIcon("resources/images/MainFrame/quit.png"))
        };*/

        //Listener
        Arrays.stream(icons).forEach(op -> op.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                //op.setSize(op.getWidth() + 15, op.getHeight() + 15);
                //System.out.println(op.getWidth() + " " + op.getHeight());
                //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                //è da fare con due immagini separate
                if (e.getComponent() instanceof JLabel) {
                    String icona = ((JLabel) e.getComponent()).getIcon().toString().replace(ImagesPath, "").replace(".png", "");
                    System.out.println(((JLabel) e.getComponent()).getIcon().toString().replace(ImagesPath, "").replace(".png", ""));

                    String name = icona.substring(0,icona.length()-2);
                    int size = Integer.getInteger(icona.substring(icona.length()-2,icona.length()));

                    //finiamo dopo 
                    comp.valueOf(name)

                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //op.setSize(op.getWidth() - 15, op.getHeight() - 15);
                //System.out.println(op.getWidth() + " " + op.getHeight());
                Image i = ((ImageIcon) op.getIcon()).getImage();
                i = i.getScaledInstance(op.getWidth() - 15, op.getHeight() - 5, Image.SCALE_SMOOTH);
                op.setIcon(new ImageIcon(i));
            }
        }));

        //Layout

    }
}
