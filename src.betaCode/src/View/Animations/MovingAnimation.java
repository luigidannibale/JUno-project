package View.Animations;

import View.Utils;
import View.Elements.ViewCard;

import java.awt.*;

public class MovingAnimation extends Animation
{
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private final double speed = 40.0;      //più alta è meno veloce va

    public MovingAnimation(double startX, double startY, double endX, double endY, ViewCard card)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        if (card.getRotation() == 90 || card.getRotation() == 270)
        {
            width = ViewCard.height;
            height = ViewCard.width;
            image = Utils.rotateImage(card.getCardImage(), card.getRotation());
        }
        else
            image = card.getCardImage();
        start();
    }

    @Override
    public void run()
    {
        double shiftX = (endX - startX) / speed,
               shiftY = (endY - startY) / speed;
        while(running)
        {
            move(shiftX, shiftY);
            if (hasReachedEnd()) running = false;
            super.sleep();
        }
    }

    private void move(double vX, double vY)
    {
        startX += vX;
        startY += vY;
    }

    private boolean hasReachedEnd()
    { return Math.round(startX) == endX || Math.round(startY) == endY; }

    @Override
    public void paint(Graphics2D g2) { g2.drawImage(image, (int)startX, (int)startY, width, height, null); }
}
