package Model.Exceptions;

public class NoMoreDeckException extends UnoGameException{

    public NoMoreDeckException() {
        super("Il deck è finito");
    }
}
