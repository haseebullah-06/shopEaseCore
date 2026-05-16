package discount;

import java.util.ArrayList;

public class BulkDiscount extends DiscountPolicy {
    
    private int minQuantity;
    private double discountPercent;
    
    public BulkDiscount(int minQuantity, double discountPercent) {
        super("Bulk Discount", "Buy " + minQuantity + "+ items, get " + discountPercent + "% off");
        this.minQuantity = minQuantity;
        this.discountPercent = discountPercent;
    }

    public double applyDiscount (double orignalPrice, ArrayList<CartItem> items) {
        if (!isActive) {
            return orignalPrice;
        }

        int totalQty = 0;
        for (int i=0 ; i < items.size() ; i++) {
            totalQty += items.get(i).getQuantity();
        }

        if (totalQty >= minQuantity) {
            double saving = orignalPrice * (discountPercent / 100);
            System.out.println("[Discount] Bull discount applied (qty=" + totalQty + "): -Rs" + saving);
            return orignalPrice - saving;
        }

        System.out.println("[Discount] Bulk discount not met. Need " + minQuantity +  " items, have " + totalQty);
        return orignalPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
    
}
