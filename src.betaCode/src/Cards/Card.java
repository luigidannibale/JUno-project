package Cards;

import Enumerations.*;

import java.awt.*;

public abstract class Card {

    protected final CardColor color;
    protected final CardValue value;
    protected final Image icon;

    protected Card(CardColor color, Image icon, CardValue value)
    {
        this.color = color;
        this.icon = icon;
        this.value = value;
    }

    public CardValue getValue() {
        return value;
    }
}
