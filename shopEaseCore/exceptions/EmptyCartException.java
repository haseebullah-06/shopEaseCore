package exceptions;

public class EmptyCartException extends Exception {
    
    public EmptyCartException() {
        super("Cart is empty. Add items before checkout.");
    }
    
}