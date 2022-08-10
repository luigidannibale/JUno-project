package View;

public enum DeckColor{
    BLACK("black"),
    WHITE("white");
    private final String VALUE;
    private DeckColor(String value){
        this.VALUE = value;
    }

    @Override
    public String toString() {
        return VALUE;
    }
}