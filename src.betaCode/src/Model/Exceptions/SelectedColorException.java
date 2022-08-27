package Model.Exceptions;

public class SelectedColorException extends UnoGameException{
    public SelectedColorException() {
        super("Colore scelto non trovato");
    }
}
