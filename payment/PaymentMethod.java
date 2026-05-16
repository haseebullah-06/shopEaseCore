package payment;

import exceptions.InvalidAmountException;
import exceptions.PaymentFailureException;

public abstract class PaymentMethod {
    
    protected String paymentId;
    protected String paymentType;

    public PaymentMethod(String paymentId, String paymentType) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
    }

    public abstract String processPayment (double amount)
        throws PaymentFailureException, InvalidAmountException;

    public abstract String getPaymentInfo();

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymemtType() {
        return paymentType;
    }
}
