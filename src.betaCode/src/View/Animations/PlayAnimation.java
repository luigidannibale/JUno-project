package View.Animations;

import Model.Cards.Card;
import Utilities.Utils;
import View.CardImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayAnimation extends Animation{
    //1 attemp
    //double direction;
    //double speed;

    //2 attemp
    int m;


    int width = CardImage.width;
    int height = CardImage.height;
    double startX;
    double startY;
    double endX;
    double endY;
    final double speed = 40.0;      //più alta è meno veloce va

    BufferedImage image;

    public PlayAnimation(double startX, double startY, double endX, double endY, CardImage card) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        //System.out.println(startX + ":" + startY + " -> " + endX + ":" + endY);

        /* 1 attemp
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        direction = Math.atan(deltaY / deltaX);
        speed = 5.0;

        timer.addActionListener(e ->{
            x = (int) (x + (speed * Math.cos(direction)));
            y = (int) (y + (speed * Math.sin(direction)));

            if (x == endX && y == endY) timer.stop();
        });
        timer.start();
         */

        //2 attemp
        /*
        m = (endY - startY) / (endX - startX);
        timer.addActionListener(e ->{
            x++;
            y = m * x;
        });
        timer.start();
         */
        if (card.getRotation() == 90 || card.getRotation() == 270){
            width = CardImage.height;
            height = CardImage.width;
            image = Utils.rotateImage(card.getCardImage(), card.getRotation());
        }
        else image = card.getCardImage();


        double deltaX = endX - startX;
        double deltaY = endY - startY;
        double vX = deltaX / speed;
        double vY = deltaY / speed;
        //System.out.println(vX + " " + vY);

        timer.addActionListener(e ->{
            move(vX, vY);

            if (checkEnd()) timer.stop();
        });
        timer.start();
    }

    private void move(double vX, double vY){
        startX += vX;
        startY += vY;
    }

    private boolean checkEnd(){
        return Math.round(startX) == endX || Math.round(startY) == endY;
    }

    @Override
    public void paint(Graphics2D g) {
        //debug
        //g.drawLine((int)startX, (int)startY, (int)endX, (int)endY);

        // 1 attemp
        //System.out.println((startX + x) + " " + (startY + y));
        //g.drawImage(card.getImage(), startX + x, startY + y, CardImage.width, CardImage.height, null);

        // 2 attemp
        //System.out.println((startX + x) + " " + (startY + y));
        //g.drawImage(card.getImage(), startX + x, startY + y, CardImage.width, CardImage.height, null);

        //3 attemp
        //System.out.println(startX + " " + startY);
        g.drawImage(image, (int)startX, (int)startY, width, height, null);
    }
}
