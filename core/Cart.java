package core;

import java.util.ArrayList;
import discount.CartItem;
import discount.DiscountPolicy;
import exceptions.EmptyCartException;
import exceptions.InvalidQuantityException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import products.Product;

// Polymorphisim
public class Cart {
    
    private String cartId;
    private String customerId;
    private ArrayList<CartItem> items;
    private DiscountPolicy activeDiscount;
    
    public Cart(String cartId, String customerId) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.items = new ArrayList<CartItem>();
        this.activeDiscount = null;
    }

    // Overloaded addItem (object and quantity)

    public void addItem(Product product, int quantity)
            throws InvalidQuantityException, OutOfStockException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
        if (!product.hasStock(quantity)) {
            throw new OutOfStockException(product.getName(), quantity, product.getStockQuantity());
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getProductId().equals(product.getProductId())) {
                int newQty = items.get(i).getQuantity() + quantity;
                if (!product.hasStock(newQty)) {
                    throw new OutOfStockException(product.getName(), newQty, product.getStockQuantity());
                }
                items.get(i).setQuantity(newQty);
                System.out.println("[Cart] Updated qty for: " + product.getName() + " -> " + newQty);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        System.out.println("[Cart] Added: " + product.getName() + " x" + quantity);
    }

    // add item method with quantity of 1
    public void addItem(Product product)
            throws InvalidQuantityException, OutOfStockException {
        addItem(product, 1);
    }

    // Remove by product object
    public void removeItem(Product product) throws ProductNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getProductId().equals(product.getProductId())) {
                items.remove(i);
                System.out.println("[Cart] Removed: " + product.getName());
                return;
            }
        }
        throw new ProductNotFoundException(product.getProductId());
    }

    // Remove by product id String
    public void removeItem(String productId) throws ProductNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getProductId().equals(productId)) {
                System.out.println("[Cart] Removed ID: " + productId);
                items.remove(i);
                return;
            }
        }
        throw new ProductNotFoundException(productId);
    }

    // Update by product object
    public void updateQuantity(Product product, int newQty)
            throws InvalidQuantityException, OutOfStockException, ProductNotFoundException {
        if (newQty <= 0)
            throw new InvalidQuantityException(newQty);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getProductId().equals(product.getProductId())) {
                if (!product.hasStock(newQty)) {
                    throw new OutOfStockException(product.getName(), newQty, product.getStockQuantity());
                }
                items.get(i).setQuantity(newQty);
                System.out.println("[Cart] Updated " + product.getName() + " qty -> " + newQty);
                return;
            }
        }
        throw new ProductNotFoundException(product.getProductId());
    }

    // Update by product id 
    public void updateQuantity(String productId, int newQty)
            throws InvalidQuantityException, OutOfStockException, ProductNotFoundException {
        if (newQty <= 0)
            throw new InvalidQuantityException(newQty);
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().getProductId().equals(productId)) {
                if (!item.getProduct().hasStock(newQty)) {
                    throw new OutOfStockException(item.getProduct().getName(),
                            newQty, item.getProduct().getStockQuantity());
                }
                item.setQuantity(newQty);
                System.out.println("[Cart] Updated ID " + productId + " qty -> " + newQty);
                return;
            }
        }
        throw new ProductNotFoundException(productId);
    }

    public void applyDiscount (DiscountPolicy policy) {
        this.activeDiscount = policy;
         System.out.println("[Cart] Discount set: " + policy.getDiscountName());
    }

    public void clearCart() {
        items.clear();
        activeDiscount = null;
        System.out.println("[Cart] Cart cleared.");
    }

    public void validateNotEmpty () throws EmptyCartException {
        if (items.isEmpty()) {
            throw new EmptyCartException();
        }
    }

    public void printCart() {
        System.out.println("--- Cart: " + cartId + " ---");
        if (items.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  " + items.get(i));
        }
        System.out.println("-------------------");
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public DiscountPolicy getActiveDiscount() {
        return activeDiscount;
    }

    public String getCartId() {
        return cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
