package payment;

import exceptions.InvalidAmountException;
import exceptions.PaymentFailureException;

public class CreditCardPayment extends PaymentMethod {

    private String cardNumber;
    private String cardHolder;
    private String expiry;

    public CreditCardPayment(String paymentId, String cardNumber,
            String cardHolder, String expiry) {
        super(paymentId, "Credit Card");
        // Store only last 4 digits for security
        this.cardNumber = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        this.cardHolder = cardHolder;
        this.expiry = expiry;
    }
    
    public String processPayment(double amount)
            throws PaymentFailureException, InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        System.out.println("[Payment] Processing Rs." + amount + " via " + cardNumber);
        System.out.println("[Payment] Card payment SUCCESS.");
        return "TXN-" + paymentId + " | " + cardNumber + " | Rs." + amount + " | APPROVED";
    }

    public String getPaymentInfo() {
        return "Credit Card | " + cardHolder + " | " + cardNumber + " | Exp: " + expiry;
    }
}
