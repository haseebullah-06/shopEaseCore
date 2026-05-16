package discount;

import java.util.ArrayList;

public class SeasonalDiscount extends DiscountPolicy {

    private double discountPercent;
    private String seasonName;
    
    public SeasonalDiscount(String seasonName, double discountPercent) {
        super("Seasonal Discount", seasonName + " - " + discountPercent + "% off");
        this.seasonName = seasonName;
        this.discountPercent = discountPercent;
    }

    public double applyDiscount (double orignalPrice, ArrayList<CartItem> items) {
        if (!isActive) {
            return orignalPrice;
        }
        double saving = orignalPrice * (discountPercent / 100);
        System.out.println("[Discount] " + seasonName + " applied: -Rs" + saving);
        return orignalPrice - saving;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
}
