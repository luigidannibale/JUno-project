package Model.Exceptions;

public class DeckOutOfBuonds extends UnoGameException{

    public DeckOutOfBuonds() {
        super("Deck is finished");
    }
    public DeckOutOfBuonds(String msg) {
        super(msg);
    }
}
