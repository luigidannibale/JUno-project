package Model.Exceptions;

public abstract class UnoGameException extends Exception{

    public UnoGameException(String message){
        System.out.println(message);
    }

}
