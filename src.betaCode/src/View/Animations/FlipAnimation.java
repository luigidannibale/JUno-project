package View.Animations;

import Utilities.Utils;
import View.CardImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class FlipAnimation extends Animation{

    int x;
    int y;

    int increase = 3;

    int width = CardImage.width;
    int height = CardImage.height;

    BufferedImage currentCard;

    public FlipAnimation(CardImage card, Rectangle position){
        x = (int) position.getX();
        y = (int) position.getY();
        currentCard = card.getBackCard();

        timer.addActionListener(e -> {
            x += increase;
            width -= increase;
            width -= increase;

            //System.out.println("X :" + x);
            //System.out.println("Width :" + width);

            if (width == 0){
                currentCard = card.getCardImage();
                increase = -increase;
            }

            if(width == CardImage.width) {
                Utils.wait(500);
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
