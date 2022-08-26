package View.Animations;

import View.Elements.CardImage;

import java.awt.*;

public class FlipAnimation extends Animation
{
    int x;
    int y;
    int increase = 3;
    private int delay = 750;

    CardImage card;
    Rectangle position;

    public FlipAnimation(CardImage card, Rectangle position)
    {
        this.card = card;
        this.position = position;

        start();
    }

    @Override
    public void run()
    {
        x = (int) position.getX();
        y = (int) position.getY();

        image = card.getBackCard();

        while(isAlive()){
            x += increase;
            width -= 2*increase;

            if (width == 0)
            {
                image = card.getCardImage();
                increase = -increase;
            }

            if(width >= CardImage.width) {
                try
                {
                    increase = 0;
                    Thread.sleep(delay);
                    return;
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }

            super.sleep();
        }

    }

    public void paint(Graphics2D g){
        g.drawImage(image, x, y, width, height, null);
    }

    //public boolean isRunning(){
    //    return timer.isRunning();
    //}

}

 /*
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

         */
