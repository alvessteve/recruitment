package model.restaurant;

public class IncorrectPriceException extends RuntimeException {
    public IncorrectPriceException(String message) {
        super(message);
    }
}
