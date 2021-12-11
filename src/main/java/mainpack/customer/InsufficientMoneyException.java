package mainpack.customer;

public class InsufficientMoneyException extends Exception {
    public InsufficientMoneyException(String message) {
        super(message);
    }
}
