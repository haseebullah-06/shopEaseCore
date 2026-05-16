package exceptions;

public class OutOfStockException extends Exception {

    public OutOfStockException(String productName, int requested, int available) {
        super("Out of stock: " + productName +
                " | Requested: " + requested +
                " | Available: " + available);
    }
}
