package Model.Cards.Enumerations;

public enum CardValue
{
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    SKIP(20),
    REVERSE(20),
    DRAW(20),
    WILD(50),
    WILD_DRAW(50);

    private final int VALUE;

    private CardValue(int value){
        this.VALUE = value;
    }

}
