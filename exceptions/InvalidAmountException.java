package exceptions;

public class InvalidAmountException extends Exception {
    
    public InvalidAmountException(double amount) {
        super("Invalid amount: " + amount + ". Must be greater than zero.");
    }
    
}