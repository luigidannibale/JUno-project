package Cards;

import Enumerations.*;
import Interfaces.*;

import java.awt.*;

public class SkipCard extends Card implements SkipAction {

    public SkipCard(CardColor color, Image icon)
    {
        super(color, icon, CardValue.SKIP);
    }
}
