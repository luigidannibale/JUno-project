package Cards;

import Enumerations.*;
import Interfaces.*;

import java.awt.*;

public class ReverseCard extends Card implements ActionCard {

    public ReverseCard(CardColor color, Image icon)
    {
        super(color, icon, CardValue.REVERSE);
    }

    public void action(){}
}
