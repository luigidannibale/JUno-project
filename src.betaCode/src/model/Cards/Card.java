package model.Cards;

import model.Enumerations.CardColor;
import model.Enumerations.CardValue;

public class Card {

    protected final CardColor color;
    protected final CardValue value;

    public Card(CardColor color, CardValue value)
    {
        this.color = color;
        this.value = value;
    }

    public CardValue getValue() {
        return value;
    }

    public CardColor getColor() { return color; }

    public boolean isPlayable(Card check){
        return getColor() == check.getColor() || getValue() == check.getValue() || getColor() == CardColor.WILD;
    }

    @Override
    public String toString(){
        return value.name() + "-"+ color.name();
    }
}
