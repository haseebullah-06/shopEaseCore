package core;

import java.util.ArrayList;
import discount.CartItem;
import exceptions.EmptyCartException;
import exceptions.InvalidQuantityException;
import exceptions.OutOfStockException;

public class CartValidator {

    public void validate(Cart cart)
            throws EmptyCartException, InvalidQuantityException, OutOfStockException {
        cart.validateNotEmpty();

        ArrayList<CartItem> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getQuantity() <= 0) {
                throw new InvalidQuantityException(item.getQuantity());
            }
            if (!item.getProduct().hasStock(item.getQuantity())) {
                throw new OutOfStockException(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getStockQuantity());
            }
        }
        System.out.println("[Validator] Cart is valid. Proceeding to checkout.");
    }
}