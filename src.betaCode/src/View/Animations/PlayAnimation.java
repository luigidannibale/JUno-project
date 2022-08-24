package View.Animations;

import Utilities.Utils;
import View.CardImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayAnimation extends Animation{
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private final double speed = 40.0;      //più alta è meno veloce va

    public PlayAnimation(double startX, double startY, double endX, double endY, CardImage card)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        if (card.getRotation() == 90 || card.getRotation() == 270)
        {
            width = CardImage.height;
            height = CardImage.width;
            image = Utils.rotateImage(card.getCardImage(), card.getRotation());
        }
        else
            image = card.getCardImage();

        double shiftX = (endX - startX) / speed,
               shiftY = (endY - startY) / speed;

        timer.addActionListener(e ->{
            move(shiftX, shiftY);
            if (hasReachedEnd()) timer.stop();
        });
        timer.start();
    }

    private void move(double vX, double vY)
    {
        startX += vX;
        startY += vY;
    }

    private boolean hasReachedEnd() { return Math.round(startX) == endX || Math.round(startY) == endY; }

    @Override
    public void paint(Graphics2D g) { g.drawImage(image, (int)startX, (int)startY, width, height, null); }
}
