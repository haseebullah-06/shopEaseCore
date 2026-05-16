package payment;

import exceptions.InvalidAmountException;
import exceptions.PaymentFailureException;

public class CashOnDelivery extends PaymentMethod {

    private String deliveryAddress;
    private double codFee = 50;

    public CashOnDelivery(String paymentId, String deliveryAddress) {
        super(paymentId, "Cash on Delivery");
        this.deliveryAddress = deliveryAddress;
    }

    public String processPayment(double amount)
            throws PaymentFailureException, InvalidAmountException {
        if (amount <= 0)
            throw new InvalidAmountException(amount);
        double total = amount + codFee;
        System.out.println("[Payment] COD confirmed. Pay Rs." + total +
                " on delivery (includes Rs." + codFee + " COD fee).");
        return "TXN-" + paymentId + " | COD | Rs." + amount +
                " + Rs." + codFee + " fee | PENDING DELIVERY";
    }

    public String getPaymentInfo() {
        return "Cash on Delivery | Address: " + deliveryAddress + " | COD Fee: Rs." + codFee;
    }
}