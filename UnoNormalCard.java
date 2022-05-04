import java.awt.*;

public class UnoNormalCard extends UnoCard{
    private final UnoCardValue value;

    protected UnoNormalCard(UnoCardColor color, Image icon, UnoCardValue value) {
        super(color, icon);
        this.value = value;
    }

    public UnoCardValue getValue() {
        return value;
    }
}

