package Cards;

import Enumerations.*;
import Interfaces.*;

import java.awt.*;

public class DrawCard extends Card implements ActionCard, WildAction, SkipAction {

    public DrawCard(CardColor color, Image icon)
    {
        super(color, icon, color == CardColor.WILD ? CardValue.WILD : CardValue.DRAW);
    }

    @Override
    public void action() {}


}
