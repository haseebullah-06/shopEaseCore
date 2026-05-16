package core;

import java.util.ArrayList;

import discount.CartItem;

public class Order {
    
    private String orderId;
    private String customerId;
    private ArrayList<CartItem> items;
    private double grandTotal;
    private String receipt;
    private String paymentInfo;
    private String status;
    private String orderDate;

    public Order(String orderId, String customerId, ArrayList<CartItem> items,
            double grandTotal, String receipt, String paymentInfo) {
        this.orderId = orderId;
        this.customerId = customerId;
        // Copy the list so cart can be cleared after
        this.items = new ArrayList<CartItem>(items);
        this.grandTotal = grandTotal;
        this.receipt = receipt;
        this.paymentInfo = paymentInfo;
        this.status = "CONFIRMED";
        this.orderDate = java.time.LocalDate.now().toString();
    }

    public void printOrderSummary() {
        System.out.println("\n========= ORDER SUMMARY =========");
        System.out.println("Order ID   : " + orderId);
        System.out.println("Date       : " + orderDate);
        System.out.println("Customer   : " + customerId);
        System.out.println("Status     : " + status);
        System.out.println("----- Items -----");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  " + items.get(i));
        }
        System.out.println("----- Bill -----");
        System.out.println("Grand Total: Rs." + grandTotal);
        System.out.println("Payment    : " + paymentInfo);
        System.out.println("Receipt    : " + receipt);
        System.out.println("==================================\n");
    }


    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setStatus(String status) {
        System.out.println("[Order] " + orderId + " -> " + status);
        this.status = status;
    }
}
