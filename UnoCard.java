import java.awt.*;

public abstract class UnoCard {
    protected final UnoCardColor color;
    protected final Image icon;

    protected UnoCard(UnoCardColor color, Image icon) {
        this.color = color;
        this.icon = icon;
    }
}
