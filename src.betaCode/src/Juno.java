import Controller.MainFrameController;
import Model.Cards.Card;
import Model.Deck;
import Model.Cards.CardValue;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Juno {
    public static void main(String[] args){
        //System.setProperty("sun.java2d.d3d", "false");
        //System.setProperty("sun.java2d.noddraw", "true");

        MainFrameController table;

        //provaDeck();
        //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
        //UnoGameTable uno = new UnoGameTable(new UnoBasicGame(new Player[] {gigi, d, w, wa}));
        //uno.startGame();

        var g = new JWindow();
        g.getContentPane().add(new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                Utils.applyQualityRenderingHints(g2);
                g2.drawImage((new ImageIcon("resources/images/icon3.png")).getImage(), 0,0, 550, 487, null);
            }
        }, SwingConstants.CENTER);
        g.setSize(new Dimension(550,487));
        g.setBackground(new Color(0,0,0,0));
        g.setLocationRelativeTo(null);
        g.setVisible(true);
        table = new MainFrameController();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.setVisible(false);
        table.setVisible();
        g.dispose();
    }


    public static void provaDeck()
    {
        Deck deck = new Deck();
        System.out.print("I'm shuffling deck");
        for(int casualNumberOfTimes = 1; casualNumberOfTimes<(new Random()).nextInt(4,7); casualNumberOfTimes++)
        {
            System.out.print(".");
            deck.shuffle();
        }
        System.out.println();
        int i = 0;
        int wild = 0;
        int drawWild = 0;
        int draw = 0;
        int reverse = 0;
        int skip = 0;
        int normal = 0;
        for (Card c : deck.getDeck()){

            System.out.print(i + " " + c + " ");
            i++;

            System.out.println();
            if (c.getValue() == CardValue.WILD) wild ++;
            if (c.getValue() == CardValue.WILD_DRAW) drawWild ++;
            if (c.getValue() == CardValue.DRAW) draw ++;
            if (c.getValue() == CardValue.SKIP) skip ++;
            if (c.getValue() == CardValue.REVERSE) reverse ++;
            if (c.getClass() == Card.class) normal ++;
        }

        System.out.println("wild:"+wild);
        System.out.println("draw wild:"+drawWild);
        System.out.println("draw:"+draw);
        System.out.println("skip:"+skip);
        System.out.println("reverse:"+reverse);
        System.out.println("normal:"+normal);
    }
}
