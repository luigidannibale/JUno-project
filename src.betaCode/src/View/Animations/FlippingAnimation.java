package View.Animations;

import View.Elements.ViewCard;

import java.awt.*;

/**
 * Class used to draw the {@link ViewCard} flipping when drawn from the user.
 * Specialize the abstract class {@link Animation}
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class FlippingAnimation extends CardAnimation
{
    private int x;
    private int y;
    private int increase = 3;
    private ViewCard card;
    private Rectangle position;

    /**
     * Creates a new {@link FlippingAnimation} with the given {@link ViewCard} to draw and the Deck {@link Rectangle} position
     * @param card
     * @param position
     */
    public FlippingAnimation(ViewCard card, Rectangle position) {
        this.card = card;
        this.position = position;

        start();
    }

    /**
     * Decrements the width of the back image of the {@link ViewCard} while increasing the x.
     * When the width is less than 0 it changes the image with the card image and
     * starts increasing the width while decreasing the x, until the width is more than the width of the {@link ViewCard}
     */
    @Override
    public void run() {
        x = (int) position.getX();
        y = (int) position.getY();

        image = ViewCard.BACK_CARD;

        while (running) {
            x += increase;
            width -= 2 * increase;

            if (width <= 0) {
                image = card.getCardImage();
                increase = -increase;
            }

            if (width >= ViewCard.width) {
                increase = 0;
                delay = 750;
                sleep();
                running = false;
                return;
            }
            sleep();
        }
    }

    /**
     * Paints the image
     * @param g
     */
    public void paint(Graphics2D g) { g.drawImage(image, x + 5, y - 10, width, height, null); }
}