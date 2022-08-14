package View.Animations;

import View.CardImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FlipAnimation extends Animation{
    //final Timer timer;

    int x;
    int y;

    int increase = 3;

    int width = CardImage.width;
    int height = CardImage.height;

    BufferedImage currentCard;

    public FlipAnimation(CardImage card, Rectangle position){
        int framesPerSecond = 140;
        int delay = 1000 / framesPerSecond;

        x = (int) position.getX();
        y = (int) position.getY();
        currentCard = card.getBackCard();

        timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            x += increase;
            width -= increase;
            width -= increase;

            //System.out.println("X :" + x);
            //System.out.println("Width :" + width);

            if (width == 0){
                currentCard = card.getImage();
                increase = -increase;
            }

            if(width == CardImage.width) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                timer.stop();
            }
        });
        timer.start();
    }

    public void paint(Graphics2D g){
        g.drawImage(currentCard, x, y, width, height, null);
    }

    //public boolean isRunning(){
    //    return timer.isRunning();
    //}

}
