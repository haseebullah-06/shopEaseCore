package exceptions;

public class PaymentFailureException extends Exception {
    
    public PaymentFailureException(String reason) {
        super("Payment failed: " + reason);
    }
    
}