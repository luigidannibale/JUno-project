package Cards;

import Enumerations.*;
import Interfaces.*;

import java.awt.*;

public class WildCard extends Card implements WildAction {

    public WildCard(CardColor color, Image icon)
    {
        super(color,icon, CardValue.WILD);
    }
}
