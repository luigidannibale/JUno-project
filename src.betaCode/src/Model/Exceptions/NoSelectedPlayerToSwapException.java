package Model.Exceptions;

public class NoSelectedPlayerToSwapException extends UnoGameException {
    public NoSelectedPlayerToSwapException() { super("Selected player not provided"); }
}
