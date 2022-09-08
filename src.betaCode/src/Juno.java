import Controller.MainFrameController;
import Model.Cards.Card;
import Model.Cards.Value;
import Model.DeckManager;
import View.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Juno {
    public static void main(String[] args)
    {
        //System.setProperty("sun.java2d.d3d", "false");
        //System.setProperty("sun.java2d.noddraw", "true");
        MainFrameController game;

        //provaDeck();
        //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
        //UnoGameTable uno = new UnoGameTable(new UnoBasicGame(new Player[] {gigi, d, w, wa}));
        //uno.startGame();

        var loadingWindow = new JWindow();
        loadingWindow.getContentPane().add(new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                Utils.applyQualityRenderingHints(g2);
                g2.drawImage(Utils.getImage("resources/images/startingIcon.png"), 0,0, 550, 487, null);
            }
        }, SwingConstants.CENTER);
        loadingWindow.setSize(new Dimension(550,487));
        loadingWindow.setBackground(new Color(0,0,0,0));
        loadingWindow.setLocationRelativeTo(null);
        loadingWindow.setVisible(true);
        game = MainFrameController.getInstance();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadingWindow.setVisible(false);
        game.setVisible(true);
        loadingWindow.dispose();
    }


    public static void provaDeck()
    {
        DeckManager deckManager = new DeckManager();
        System.out.print("I'm shuffling deck");
        for(int casualNumberOfTimes = 1; casualNumberOfTimes<(new Random()).nextInt(4,7); casualNumberOfTimes++)
        {
            System.out.print(".");
            deckManager.shuffle();
        }
        System.out.println();
        int i = 0;
        int wild = 0;
        int drawWild = 0;
        int draw = 0;
        int reverse = 0;
        int skip = 0;
        int normal = 0;
        for (Card c : deckManager.getDeck()){

            System.out.print(i + " " + c + " ");
            i++;

            System.out.println();
            if (c.getValue() == Value.WILD) wild ++;
            if (c.getValue() == Value.WILD_DRAW) drawWild ++;
            if (c.getValue() == Value.DRAW) draw ++;
            if (c.getValue() == Value.SKIP) skip ++;
            if (c.getValue() == Value.REVERSE) reverse ++;
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
