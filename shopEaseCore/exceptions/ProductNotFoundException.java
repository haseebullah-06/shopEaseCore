package exceptions;

public class ProductNotFoundException extends Exception {
    
    public ProductNotFoundException(String productId) {
        super("Product not found: " + productId);
    }
    
}