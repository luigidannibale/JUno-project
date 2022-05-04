package Cards;

import Enumerations.*;
import Interfaces.*;

import java.awt.*;

public class WildCard extends Card implements WildAction {

    public WildCard(Image icon)
    {
        super(CardColor.WILD,icon, CardValue.WILD);
    }
}
