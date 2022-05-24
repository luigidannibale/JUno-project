package View;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GamePanel extends ResizablePanel{

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";

    public GamePanel(MainFrame mainFrame){
        super(null, mainFrame);

        setLayout(new BorderLayout());
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{1.0, 1.0});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{1.0, 1.0});
            put(MainFrame.Dimensions.HD, new Double[]{1.0, 1.0});
        }};
        addScalingListener();

        //debug
        setBackground(Color.GREEN);

        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents(){
        /*
        forse quasi meglio creare una classe per i panel dei player
                        top player
        left player     deckPanel        right player
                        bot player --> human player
         */
        JPanel topPlayer = new JPanel();
        JPanel leftPlayer = new JPanel();
        JPanel deckPanel = new JPanel();
        JPanel rightPlayer = new JPanel();
        JPanel botPlayer = new JPanel();

        add(topPlayer, BorderLayout.NORTH);
        add(leftPlayer, BorderLayout.WEST);
        add(deckPanel, BorderLayout.CENTER);
        add(rightPlayer, BorderLayout.EAST);
        add(botPlayer, BorderLayout.SOUTH);
    }

    public class CardImage extends JPanel{

         

        public CardImage(CardColor color, CardValue value){

        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

        }
    }
}
