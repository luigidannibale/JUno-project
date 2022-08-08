package Model.Cards;

import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

public class Card /*implements Comparable<Card>*/{

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

    public boolean isValid(Card check){
        return getColor() == check.getColor() || getValue() == check.getValue() || getColor() == CardColor.WILD;
    }

    @Override
    public String toString(){
        return value.name() + "-"+ color.name();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Card && color.equals(((Card) obj).getColor()) && value.equals(((Card) obj).getValue()));
    }

    /*
    @Override
    public int compareTo(Card o) {
        return getColor() == o.getColor() ? 0 : getValue() == o.getValue() ? -1 : 1;
    }*/
}
