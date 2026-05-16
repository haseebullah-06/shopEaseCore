package payment;

import exceptions.InvalidAmountException;
import exceptions.PaymentFailureException;

public class CreditCardPayment extends PaymentMethod {

    private String cardNumber;
    private String cardHolder;
    private String expiry;

    public CreditCardPayment(String paymentId, String cardNumber,
            String cardHolder, String expiry) throws PaymentFailureException {
        super(paymentId, "Credit Card");
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            throw new PaymentFailureException("Card number must be exactly 16 digits.");
        }
        if (cardHolder == null || cardHolder.trim().isEmpty()) {
            throw new PaymentFailureException("Card holder name is required.");
        }
        if (expiry == null || !expiry.matches("\\d{2}/\\d{2}")) {
            throw new PaymentFailureException("Expiry must be in MM/YY format.");
        }
        // Store only last 4 digits for security
        this.cardNumber = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        this.cardHolder = cardHolder.trim();
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
