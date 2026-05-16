package discount;

import java.util.ArrayList;

public abstract class DiscountPolicy {
    
    protected String discountName;
    protected String description;
    protected boolean isActive;
    
    public DiscountPolicy(String discountName, String description) {
        this.discountName = discountName;
        this.description = description;
        this.isActive = true;
    }

    public abstract double applyDiscount (double orignalPrice, ArrayList<CartItem> items );
    public abstract double getDiscountPercent();
    
    public String getDiscountName() {
        return discountName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean v) {
        this.isActive = v;
    }
    
}
