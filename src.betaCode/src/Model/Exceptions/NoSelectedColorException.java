package Model.Exceptions;

public class NoSelectedColorException extends UnoGameException{
    public NoSelectedColorException() { super("Selected color not provided"); }
}
