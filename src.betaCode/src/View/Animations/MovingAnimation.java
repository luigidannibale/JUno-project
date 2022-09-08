package View.Animations;

import View.Utils;
import View.Elements.ViewCard;

import java.awt.*;

/**
 * Class used to move the {@link ViewCard} from one point to another.
 * Used for the draw and play action.
 * Specialize the abstract class {@link Animation}
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class MovingAnimation extends Animation
{
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private final double speed = 40.0;      //higher = less speed

    /**
     * Creates a new {@link MovingAnimation} with the given {@link ViewCard} from the start point to the end point.
     * If the card is rotated, then the image in {@link ViewCard} is rotated too
     * @param startX the x of the start point
     * @param startY the y of the end point
     * @param endX the x of the start point
     * @param endY the y of the end point
     * @param card the card to draw
     */
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

    /**
     * Calculates the increment to add at the x and y coordinates of the image by dividing the difference in x and y
     * between the start and end point by the speed.
     * Then adds the increment until the card reaches the end point.
     */
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

    /**
     * Adds the increment to x and y position of the card
     * @param vX
     * @param vY
     */
    private void move(double vX, double vY)
    {
        startX += vX;
        startY += vY;
    }

    /**
     * Checks if the card has reached the end point
     * @return
     */
    private boolean hasReachedEnd()
    { return Math.round(startX) == endX || Math.round(startY) == endY; }

    /**
     * Paints the card
     * @param g2
     */
    @Override
    public void paint(Graphics2D g2) { g2.drawImage(image, (int)startX, (int)startY, width, height, null); }
}
