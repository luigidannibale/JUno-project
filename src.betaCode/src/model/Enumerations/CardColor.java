package model.Enumerations;

public enum CardColor {
    RED(0),
    YELLOW(1),
    GREEN(2),
    BLUE(3),
    WILD(4);

    private final int num;
    private CardColor(int num){
        this.num = num;
    }
    public int getIntValue(){ return num; }
}
