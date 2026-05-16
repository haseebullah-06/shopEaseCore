package discount;

import products.Product;

public class CartItem {
    
    private Product product;
    private int quantity;
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return product.getBasePrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public String toString() {
        return product.getBrand() + " " + product.getName() +
                " x" + quantity +
                " @ Rs." + product.getBasePrice() +
                " = Rs." + getLineTotal();
    }
}
