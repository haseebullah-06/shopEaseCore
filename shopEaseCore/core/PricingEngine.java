package core;

import java.util.ArrayList;
import discount.CartItem;
import discount.DiscountPolicy;

public class PricingEngine {
    
    private static final double TAX_RATE = 0.17;
    private static final double FREE_SHIP_ABOVE = 5000;

    public double calculateSubTotal (ArrayList<CartItem> items) {
        double total = 0;
        for (int i=0; i < items.size(); i++) {
            total += items.get(i).getLineTotal();
        }
        return total;
    }

    public double applyDiscount (double price, DiscountPolicy policy, ArrayList<CartItem> items) {
        if (policy == null || !policy.isActive()) {
            return price;
        } 
        return policy.applyDiscount(price, items);
    }

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }

    public double calculateShipping (ArrayList<CartItem> items) {
        double subtotal = calculateSubTotal(items);
        if (subtotal >= FREE_SHIP_ABOVE) {
            System.out.println("[Pricing] Free Shipping! Order above Rs." + FREE_SHIP_ABOVE );
            return 0;
        }
        double shipping = 0;
        for (int i=0; i < items.size(); i++) {
            CartItem item = items.get(i);
            shipping += item.getProduct().calculateShippingCost() * item.getLineTotal();
        }
        return shipping;
    }

    public void printBill(double subtotal, double afterDiscount,
            double tax, double shipping, double grandTotal,
            String discountName) {
        System.out.println("================================");
        System.out.println("     SHOPEASE ELECTRONICS       ");
        System.out.println("================================");
        System.out.println("Subtotal        : Rs." + subtotal);
        System.out.println("Discount (" + discountName + ")");
        System.out.println("After Discount  : Rs." + afterDiscount);
        System.out.println("GST (17%)       : Rs." + tax);
        System.out.println("Shipping        : Rs." + shipping);
        System.out.println("--------------------------------");
        System.out.println("GRAND TOTAL     : Rs." + grandTotal);
        System.out.println("================================");
    }
    
}
