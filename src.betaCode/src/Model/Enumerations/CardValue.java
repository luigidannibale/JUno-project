package Model.Enumerations;

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

    private final int value;

    private CardValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
