package View.Animations;

import View.Elements.ViewCard;

/**
 * Customize the {@link Animation} to add the common default width and height of the {@link ViewCard} for the {@link CardAnimation}
 */
public abstract class CardAnimation extends Animation
{
    protected int width = ViewCard.width;
    protected int height = ViewCard.height;
}
