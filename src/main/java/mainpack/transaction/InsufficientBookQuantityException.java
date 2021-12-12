package mainpack.transaction;

public class InsufficientBookQuantityException extends Exception {
    public InsufficientBookQuantityException(String message) {
        super(message);
    }
}
