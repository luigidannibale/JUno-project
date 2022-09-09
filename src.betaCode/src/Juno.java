import Controller.MainFrameController;
import Model.Cards.Card;
import Model.Cards.Value;
import Model.DeckManager;
import View.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Juno
{
    public static void main(String[] args)
    {
        MainFrameController game;

        JWindow loadingWindow = new JWindow();
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
}
