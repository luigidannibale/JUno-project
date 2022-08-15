package View.Animations;

import View.CardImage;

import java.awt.*;

public class PlayAnimation extends Animation{
    //1 attemp
    //double direction;
    //double speed;

    //2 attemp
    int m;


    int x;
    int y;
    double startX;
    double startY;
    double endX;
    double endY;

    CardImage card;

    public PlayAnimation(int startX, int startY, int endX, int endY, CardImage card) {
        this.card = card;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        System.out.println(startX + ":" + startY + " -> " + endX + ":" + endY);

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

        int deltaX = endX - startX;
        int deltaY = endY - startY;
        double speed = 40.0;
        double vX = deltaX / speed;
        double vY = deltaY / speed;
        System.out.println(vX + " " + vY);

        timer.addActionListener(e ->{
            move(vX, vY);

            if (this.startX == this.endX && this.startY == this.endY) timer.stop();
        });
        timer.start();
    }

    private void move(double vX, double vY){
        startX += vX;
        startY += vY;
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
        System.out.println(startX + " " + startY);
        g.drawImage(card.getImage(), (int)startX, (int)startY, CardImage.width, CardImage.height, null);
    }
}
