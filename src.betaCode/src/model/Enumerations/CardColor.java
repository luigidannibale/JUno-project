package model.Enumerations;

public enum CardColor {
    YELLOW(1),
    RED(0),
    GREEN(2),
    BLUE(3),
    WILD(5);

    private final int num;
    private CardColor(int num){
        this.num = num;
    }
    public int getIntValue(){ return num; }
}
