package model.Cards;

import model.Enumerations.*;
import model.Interfaces.*;

public class DrawCard extends Card implements ActionCard, WildAction, SkipAction {

    public DrawCard(CardColor color)
    {
        super(color, color == CardColor.WILD ? CardValue.WILD_DRAW : CardValue.DRAW);
    }

    @Override
    public void action() {}


}
