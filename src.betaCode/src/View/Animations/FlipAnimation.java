package View.Animations;

import View.CardImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlipAnimation extends Animation
{
    int x;
    int y;
    int increase = 3;
    private int delay = 750;

    public FlipAnimation(CardImage card, Rectangle position){
        x = (int) position.getX();
        y = (int) position.getY();

        image = card.getBackCard();

        timer.addActionListener(e -> {
            x += increase;
            width -= 2*increase;

            if (width == 0)
            {
                image = card.getCardImage();
                increase = -increase;
            }

            if(width == CardImage.width) {
                new Thread(() -> {
                    try
                    {
                        increase = 0;
                        Thread.sleep(delay);
                        timer.stop();
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                }).start();
            }
        });
        timer.start();
    }

    public void paint(Graphics2D g){
        g.drawImage(image, x, y, width, height, null);
    }

    //public boolean isRunning(){
    //    return timer.isRunning();
    //}

}
