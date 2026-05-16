package exceptions;

public class InvalidQuantityException extends Exception {
    
    public InvalidQuantityException(int qty) {
        super("Invalid quantity: " + qty + ". Must be greater than zero.");
    }
    
}