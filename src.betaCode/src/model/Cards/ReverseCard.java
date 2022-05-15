package model.Cards;

import model.Enumerations.CardColor;
import model.Enumerations.CardValue;
import model.Interfaces.ActionCard;

public class ReverseCard extends Card implements ActionCard {

    public ReverseCard(CardColor color)
    {
        super(color, CardValue.REVERSE);
    }

    @Override
    public void action(){}
}
