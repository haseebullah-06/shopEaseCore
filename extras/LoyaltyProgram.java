package extras;

import java.util.ArrayList;

/*
 * ADDED CLASS 1: LoyaltyProgram
 * Justification:
 * A loyalty/rewards system is essential for any e-commerce platform to
 * retain customers. It adds vertical scope by creating a link between
 * the Customer and Order subsystems — every purchase earns points that
 * can be redeemed as discounts on future orders.
 * Rule: Earn 1 point per Rs.10 spent. Redeem 100 points = Rs.50 off.
 */


public class LoyaltyProgram {
    
    // store customer id and their points
    private ArrayList<String> customerIds;
    private ArrayList<Integer> pointsList;

    public LoyaltyProgram() {
        customerIds = new ArrayList<String>();
        pointsList = new ArrayList<Integer>();
    }

    private int getIndex (String customerId) {
        for (int i=0; i< customerIds.size(); i++) {
            if (customerIds.get(i).equals(customerId)) {
                return i;
            }
        }
        return -1;
    }

    public void registerCustomer (String customerId) {
        if (getIndex(customerId) == -1) {
            customerIds.add(customerId);
            pointsList.add(0);
            System.out.println("[Loyalty] Registered: " + customerId);
        }
    }

    public void earnPoints(String customerId, double amountSpent) {
        int idx = getIndex(customerId);
        if (idx == -1) {
            registerCustomer(customerId);
            idx = getIndex(customerId);
        }
        int earned = (int) (amountSpent / 10);
        pointsList.set(idx, pointsList.get(idx) + earned);
        System.out.println("[Loyalty] " + customerId + " earned " + earned +
                " points. Total: " + pointsList.get(idx));
    }

    public double redeemPoints(String customerId, int pointsToRedeem) {
        int idx = getIndex(customerId);
        if (idx == -1) {
            System.out.println("[Loyalty] Customer not found.");
            return 0;
        }
        int current = pointsList.get(idx);
        if (pointsToRedeem > current) {
            System.out.println("[Loyalty] Not enough points. Have: " + current);
            return 0;
        }
        if (pointsToRedeem % 100 != 0) {
            System.out.println("[Loyalty] Redeem in multiples of 100 points.");
            return 0;
        }
        pointsList.set(idx, current - pointsToRedeem);
        double value = (pointsToRedeem / 100) * 50.0;
        System.out.println("[Loyalty] Redeemed " + pointsToRedeem + " points = Rs." + value + " discount.");
        return value;
    }

    public int getPoints(String customerId) {
        int idx = getIndex(customerId);
        return (idx == -1) ? 0 : pointsList.get(idx);
    }


    public String getTier(String customerId) {
        int pts = getPoints(customerId);
        if (pts >= 5000)
            return "PLATINUM";
        if (pts >= 2000)
            return "GOLD";
        if (pts >= 500)
            return "SILVER";
        return "BRONZE";
    }

    public void printStatus(String customerId) {
        System.out.println("[Loyalty] " + customerId +
                " | Points: " + getPoints(customerId) +
                " | Tier: " + getTier(customerId) +
                " | Redeemable: Rs." + (getPoints(customerId) / 100) * 50.0);
    }
}
