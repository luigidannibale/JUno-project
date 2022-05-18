package Model.Enumerations;

public enum CardColor {
    RED(0),
    YELLOW(1),
    GREEN(2),
    BLUE(3),
    WILD(4);

    private final int numericValue;
    private CardColor(int num){
        this.numericValue = num;
    }
    public int getIntValue(){ return numericValue; }
}
