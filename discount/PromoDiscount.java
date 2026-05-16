package discount;

import java.util.ArrayList;

public class PromoDiscount extends DiscountPolicy {
    
    private String promoCode;
    private double discountPercent;
    private int usageLimit;
    private int usedCount;

    public PromoDiscount(String promoCode, double discountPercent, int usageLimit) {
        super("Promo Code", "Code: " + promoCode + " - " + discountPercent + "% off");
        this.promoCode = promoCode;
        this.discountPercent = discountPercent;
        this.usageLimit = usageLimit;
        this.usedCount = 0;
    }

    public boolean validateCode (String inputCode) {
        return isActive && promoCode.equalsIgnoreCase(inputCode) && usedCount < usageLimit;
    }

    public double applyDiscount (double orignalPrice, ArrayList<CartItem> items) {
        if (!isActive || usedCount >= usageLimit) {
            System.out.println("[Discount] Promo expired or limit reached.");
            return orignalPrice;
        }
        double saving = orignalPrice * (discountPercent / 100);
        usedCount ++;
        System.out.println("[Discount] Promo '" + promoCode + "' applied: -Rs." + saving +
                " | Uses left: " + (usageLimit - usedCount));
        return orignalPrice - saving;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public int getRemainingUses() {
        return usageLimit - usedCount;
    }
}
