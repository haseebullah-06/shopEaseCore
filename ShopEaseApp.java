import core.Cart;
import core.InventoryManager;
import core.PricingEngine;
import discount.BulkDiscount;
import discount.CartItem;
import discount.SeasonalDiscount;
import exceptions.EmptyCartException;
import exceptions.InvalidAmountException;
import exceptions.InvalidLoginException;
import exceptions.InvalidQuantityException;
import exceptions.OutOfStockException;
import exceptions.PaymentFailureException;
import exceptions.ProductNotFoundException;
import exceptions.UnauthorizedAccessException;
import extras.LoyaltyProgram;
import extras.ReviewSystem;
import payment.CashOnDelivery;
import payment.CreditCardPayment;
import payment.PaymentMethod;
import products.DigitalProduct;
import products.PhysicalProduct;
import products.Product;
import products.ServiceProduct;
import users.Admin;
import users.AuthService;
import users.Customer;
import users.User;
import java.util.Scanner;

public class ShopEaseApp {

    static AuthService auth           = new AuthService();
    static InventoryManager inventory = new InventoryManager(auth);
    static PricingEngine pricing      = new PricingEngine();
    static LoyaltyProgram loyalty     = new LoyaltyProgram();
    static ReviewSystem reviews       = new ReviewSystem();
    static Scanner scanner            = new Scanner(System.in);

    static Cart currentCart = null;
    static int orderCounter = 1;

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("     Welcome to ShopEase Electronics Store      ");
        System.out.println("================================================");

        // Pre-load some products (admin does this at start)
        preloadData();

        boolean running = true;
        while (running) {
            if (auth.getCurrentUser() == null) {
                showGuestMenu();
                int choice = getInt();
                switch (choice) {
                    case 1: registerMenu(); break;
                    case 2: loginMenu();    break;
                    case 3:
                        System.out.println("Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } else if (auth.getCurrentUser().getRole().equals("ADMIN")) {
                showAdminMenu();
                int choice = getInt();
                switch (choice) {
                    case 1: adminAddProduct();    break;
                    case 2: adminRemoveProduct(); break;
                    case 3: adminRestockProduct();break;
                    case 4: adminUpdatePrice();   break;
                    case 5: inventory.printInventory(); break;
                    case 6: adminApproveReview(); break;
                    case 7: auth.logOut();        break;
                    default: System.out.println("Invalid option.");
                }
            } else {
                // CUSTOMER
                showCustomerMenu();
                int choice = getInt();
                switch (choice) {
                    case 1: browseProducts();     break;
                    case 2: addToCart();          break;
                    case 3: removeFromCart();     break;
                    case 4: updateCartQty();      break;
                    case 5: viewCart();           break;
                    case 6: applyDiscountMenu();  break;
                    case 7: checkout();           break;
                    case 8: viewLoyaltyStatus();  break;
                    case 9: redeemLoyaltyPoints();break;
                    case 10: leaveReview();       break;
                    case 11: viewReviews();       break;
                    case 12: auth.logOut(); currentCart = null; break;
                    default: System.out.println("Invalid option.");
                }
            }
        }
        scanner.close();
    }

    // ================================================================
    //  MENUS
    // ================================================================

    static void showGuestMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    static void showAdminMenu() {
        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("Logged in as: " + auth.getCurrentUser().getUsername());
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Restock Product");
        System.out.println("4. Update Price");
        System.out.println("5. View Inventory");
        System.out.println("6. Approve Review");
        System.out.println("7. Logout");
        System.out.print("Enter choice: ");
    }

    static void showCustomerMenu() {
        System.out.println("\n========== CUSTOMER MENU ==========");
        System.out.println("Logged in as: " + auth.getCurrentUser().getUsername());
        System.out.println("1.  Browse Products");
        System.out.println("2.  Add Item to Cart");
        System.out.println("3.  Remove Item from Cart");
        System.out.println("4.  Update Item Quantity");
        System.out.println("5.  View Cart");
        System.out.println("6.  Apply Discount");
        System.out.println("7.  Checkout");
        System.out.println("8.  View Loyalty Points");
        System.out.println("9.  Redeem Loyalty Points");
        System.out.println("10. Leave a Review");
        System.out.println("11. View Product Reviews");
        System.out.println("12. Logout");
        System.out.print("Enter choice: ");
    }

    // ================================================================
    //  GUEST ACTIONS
    // ================================================================

    static void registerMenu() {
        System.out.println("\n--- Register ---");
        System.out.println("Register as: 1. Customer   2. Admin");
        System.out.print("Choice: ");
        int type = getInt();

        System.out.print("Enter User ID   : ");
        String uid = scanner.nextLine().trim();
        System.out.print("Enter Username  : ");
        String uname = scanner.nextLine().trim();
        System.out.print("Enter Password  : ");
        String pass = scanner.nextLine().trim();
        System.out.print("Enter Email     : ");
        String email = scanner.nextLine().trim();

        if (type == 1) {
            System.out.print("Full Name       : ");
            String name = scanner.nextLine().trim();
            System.out.print("Address         : ");
            String addr = scanner.nextLine().trim();
            System.out.print("Phone           : ");
            String phone = scanner.nextLine().trim();
            Customer c = new Customer(uid, uname, pass, email, name, addr, phone);
            auth.registerUser(c);
        } else if (type == 2) {
            System.out.print("Admin Level (SUPER_ADMIN / MANAGER): ");
            String level = scanner.nextLine().trim();
            System.out.print("Department      : ");
            String dept = scanner.nextLine().trim();
            Admin a = new Admin(uid, uname, pass, email, level, dept);
            auth.registerUser(a);
        } else {
            System.out.println("Invalid type.");
        }
    }

    static void loginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Username : ");
        String uname = scanner.nextLine().trim();
        System.out.print("Password : ");
        String pass = scanner.nextLine().trim();
        try {
            User u = auth.login(uname, pass);
            u.showDashboard();
            // Create a fresh cart for customer on login
            if (u.getRole().equals("CUSTOMER")) {
                currentCart = new Cart("CART-" + u.getUserId(), u.getUserId());
                System.out.println("[Cart] New cart created for you.");
            }
        } catch (InvalidLoginException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // ================================================================
    //  ADMIN ACTIONS
    // ================================================================

    static void adminAddProduct() {
        System.out.println("\n--- Add Product ---");
        System.out.println("Type: 1. Physical   2. Digital   3. Service");
        System.out.print("Choice: ");
        int type = getInt();

        System.out.print("Product ID   : ");
        String id = scanner.nextLine().trim();
        System.out.print("Name         : ");
        String name = scanner.nextLine().trim();
        System.out.print("Brand        : ");
        String brand = scanner.nextLine().trim();
        System.out.print("Base Price   : ");
        double price = getDouble();
        System.out.print("Stock Qty    : ");
        int stock = getInt();
        System.out.print("Category     : ");
        String cat = scanner.nextLine().trim();

        try {
            if (type == 1) {
                System.out.print("Weight (kg)  : ");
                double weight = getDouble();
                System.out.print("Dimensions   : ");
                String dim = scanner.nextLine().trim();
                System.out.print("Warranty? (y/n): ");
                boolean warranty = scanner.nextLine().trim().equalsIgnoreCase("y");
                int months = 0;
                if (warranty) {
                    System.out.print("Warranty Months: ");
                    months = getInt();
                }
                inventory.addProduct(new PhysicalProduct(id, name, brand, price, stock, cat,
                                                         weight, dim, warranty, months));
            } else if (type == 2) {
                System.out.print("License Key  : ");
                String key = scanner.nextLine().trim();
                System.out.print("Download URL : ");
                String url = scanner.nextLine().trim();
                System.out.print("Valid Days   : ");
                int days = getInt();
                inventory.addProduct(new DigitalProduct(id, name, brand, price, stock, cat,
                                                        key, url, days));
            } else if (type == 3) {
                System.out.print("Service Type : ");
                String stype = scanner.nextLine().trim();
                System.out.print("Duration (hrs): ");
                int hrs = getInt();
                System.out.print("Home Service? (y/n): ");
                boolean home = scanner.nextLine().trim().equalsIgnoreCase("y");
                inventory.addProduct(new ServiceProduct(id, name, brand, price, stock, cat,
                                                        stype, hrs, home));
            } else {
                System.out.println("Invalid type.");
            }
        } catch (UnauthorizedAccessException e) {
            System.out.println("ACCESS ERROR: " + e.getMessage());
        }
    }

    static void adminRemoveProduct() {
        System.out.println("\n--- Remove Product ---");
        System.out.print("Enter Product ID to remove: ");
        String id = scanner.nextLine().trim();
        try {
            inventory.removeProduct(id);
        } catch (UnauthorizedAccessException e) {
            System.out.println("ACCESS ERROR: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void adminRestockProduct() {
        System.out.println("\n--- Restock Product ---");
        inventory.printInventory();
        System.out.print("Enter Product ID : ");
        String id = scanner.nextLine().trim();
        System.out.print("Quantity to add  : ");
        int qty = getInt();
        try {
            inventory.restockProduct(id, qty);
        } catch (UnauthorizedAccessException e) {
            System.out.println("ACCESS ERROR: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void adminUpdatePrice() {
        System.out.println("\n--- Update Price ---");
        inventory.printInventory();
        System.out.print("Enter Product ID : ");
        String id = scanner.nextLine().trim();
        System.out.print("New Price        : ");
        double price = getDouble();
        try {
            inventory.updatePrice(id, price);
        } catch (UnauthorizedAccessException e) {
            System.out.println("ACCESS ERROR: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void adminApproveReview() {
        System.out.println("\n--- Approve Review ---");
        System.out.print("Enter Product ID : ");
        String pid = scanner.nextLine().trim();
        reviews.printProductReviews(pid);
        System.out.print("Enter Review ID to approve: ");
        String rid = scanner.nextLine().trim();
        reviews.approveReview(rid);
    }

    // ================================================================
    //  CUSTOMER ACTIONS
    // ================================================================

    static void browseProducts() {
        System.out.println("\n--- Browse Products ---");
        inventory.printInventory();
    }

    static void addToCart() {
        System.out.println("\n--- Add Item to Cart ---");
        inventory.printInventory();
        System.out.print("Enter Product ID : ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Quantity   : ");
        int qty = getInt();
        try {
            Product p = inventory.findById(id);
            currentCart.addItem(p, qty);
        } catch (ProductNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (OutOfStockException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void removeFromCart() {
        System.out.println("\n--- Remove Item from Cart ---");
        currentCart.printCart();
        System.out.print("Enter Product ID to remove: ");
        String id = scanner.nextLine().trim();
        try {
            // Using overloaded removeItem(String productId)
            currentCart.removeItem(id);
        } catch (ProductNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void updateCartQty() {
        System.out.println("\n--- Update Item Quantity ---");
        currentCart.printCart();
        System.out.print("Enter Product ID  : ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter New Quantity : ");
        int qty = getInt();
        try {
            // Using overloaded updateQuantity(String productId, int qty)
            currentCart.updateQuantity(id, qty);
        } catch (ProductNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (OutOfStockException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void viewCart() {
        System.out.println("\n--- Your Cart ---");
        currentCart.printCart();
    }

    static void applyDiscountMenu() {
        System.out.println("\n--- Apply Discount ---");
        System.out.println("1. Seasonal Discount");
        System.out.println("2. Bulk Discount");
        System.out.println("3. Promo Code");
        System.out.print("Choice: ");
        int choice = getInt();

        if (choice == 1) {
            System.out.print("Season Name (e.g. Eid Sale): ");
            String season = scanner.nextLine().trim();
            System.out.print("Discount %: ");
            double pct = getDouble();
            currentCart.applyDiscount(new SeasonalDiscount(season, pct));

        } else if (choice == 2) {
            System.out.print("Minimum quantity for discount: ");
            int minQty = getInt();
            System.out.print("Discount %: ");
            double pct = getDouble();
            currentCart.applyDiscount(new BulkDiscount(minQty, pct));

        } else if (choice == 3) {
            System.out.print("Enter Promo Code: ");
            String code = scanner.nextLine().trim();
            // Example promo: SAVE10 gives 10% off, 5 uses
            if (code.equalsIgnoreCase("SAVE10")) {
                currentCart.applyDiscount(new SeasonalDiscount("Promo SAVE10", 10));
                System.out.println("Promo code applied!");
            } else {
                System.out.println("Invalid or expired promo code.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void checkout() {
        System.out.println("\n--- Checkout ---");
        currentCart.printCart();

        try {
            currentCart.validateNotEmpty();

            // Compute bill
            double subtotal      = pricing.calculateSubTotal(currentCart.getItems());
            double afterDiscount = pricing.applyDiscount(subtotal,
                                   currentCart.getActiveDiscount(), currentCart.getItems());
            double tax           = pricing.calculateTax(afterDiscount);
            double shipping      = pricing.calculateShipping(currentCart.getItems());
            double grandTotal    = afterDiscount + tax + shipping;

            String discountName = currentCart.getActiveDiscount() != null
                                ? currentCart.getActiveDiscount().getDiscountName()
                                : "None";
            pricing.printBill(subtotal, afterDiscount, tax, shipping, grandTotal, discountName);

            // Choose payment method
            System.out.println("\nSelect Payment Method:");
            System.out.println("1. Credit Card");
            System.out.println("2. Cash on Delivery");
            System.out.print("Choice: ");
            int payChoice = getInt();

            PaymentMethod payment = null;

            if (payChoice == 1) {
                System.out.print("Card Number (16 digits): ");
                String cardNum = scanner.nextLine().trim();
                System.out.print("Card Holder Name       : ");
                String holder = scanner.nextLine().trim();
                System.out.print("Expiry (MM/YY)         : ");
                String expiry = scanner.nextLine().trim();
                payment = new CreditCardPayment("PAY-" + orderCounter, cardNum, holder, expiry);

            } else if (payChoice == 2) {
                Customer c = (Customer) auth.getCurrentUser();
                payment = new CashOnDelivery("PAY-" + orderCounter, c.getAddress());

            } else {
                System.out.println("Invalid payment choice. Checkout cancelled.");
                return;
            }

            String receipt = payment.processPayment(grandTotal);

            // Deduct stock
            for (int i = 0; i < currentCart.getItems().size(); i++) {
                CartItem item = currentCart.getItems().get(i);
                item.getProduct().reduceStock(item.getQuantity());
            }

            // Print order summary
            System.out.println("\n========== ORDER SUMMARY ==========");
            System.out.println("Order ID   : ORD-" + orderCounter);
            System.out.println("Customer ID: " + auth.getCurrentUser().getUserId());
            System.out.println("Payment    : " + payment.getPaymentInfo());
            System.out.println("Receipt    : " + receipt);
            System.out.println("Total      : Rs." + grandTotal);
            System.out.println("===================================");
            orderCounter++;

            // Award loyalty points
            loyalty.earnPoints(auth.getCurrentUser().getUserId(), grandTotal);
            loyalty.printStatus(auth.getCurrentUser().getUserId());

            // Clear cart
            currentCart.clearCart();

        } catch (EmptyCartException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (PaymentFailureException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void viewLoyaltyStatus() {
        System.out.println();
        loyalty.printStatus(auth.getCurrentUser().getUserId());
    }

    static void redeemLoyaltyPoints() {
        System.out.println("\n--- Redeem Loyalty Points ---");
        loyalty.printStatus(auth.getCurrentUser().getUserId());
        System.out.print("Enter points to redeem (multiples of 100): ");
        int pts = getInt();
        double value = loyalty.redeemPoints(auth.getCurrentUser().getUserId(), pts);
        if (value > 0) {
            System.out.println("Rs." + value + " discount will apply on your next order.");
        }
    }

    static void leaveReview() {
        System.out.println("\n--- Leave a Review ---");
        inventory.printInventory();
        System.out.print("Enter Product ID : ");
        String pid = scanner.nextLine().trim();
        System.out.print("Rating (1-5)     : ");
        int rating = getInt();
        System.out.print("Your Comment     : ");
        String comment = scanner.nextLine().trim();
        try {
            reviews.submitReview(pid, auth.getCurrentUser().getUserId(), rating, comment);
            System.out.println("Review submitted! Waiting for admin approval.");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    static void viewReviews() {
        System.out.println("\n--- View Reviews ---");
        inventory.printInventory();
        System.out.print("Enter Product ID : ");
        String pid = scanner.nextLine().trim();
        reviews.printProductReviews(pid);
    }

    // ================================================================
    //  PRELOAD - seeds inventory so store isn't empty on startup
    // ================================================================
    static void preloadData() {
        // Temporarily register and login a default admin to seed products
        Admin defaultAdmin = new Admin("ADM-000", "admin", "admin123",
                                       "admin@shopease.pk", "SUPER_ADMIN", "Electronics");
        auth.registerUser(defaultAdmin);
        try {
            auth.login("admin", "admin123");
            inventory.addProduct(new PhysicalProduct(
                "PROD-001", "ThinkPad X1", "Lenovo",
                249999, 10, "Laptops",
                1.12, "31x22x2 cm", true, 24));
            inventory.addProduct(new PhysicalProduct(
                "PROD-002", "Galaxy S25", "Samsung",
                199999, 20, "Phones",
                0.18, "15x7x0.8 cm", true, 12));
            inventory.addProduct(new DigitalProduct(
                "PROD-003", "Internet Security", "Kaspersky",
                3500, 50, "Software",
                "KS-2026-XXXXX", "https://kaspersky.com/download", 365));
            inventory.addProduct(new ServiceProduct(
                "PROD-004", "Laptop Setup", "ShopEase",
                2000, 999, "Services",
                "Installation", 2, true));
            auth.logOut();
            System.out.println("[System] Store loaded with sample products.\n");
        } catch (Exception e) {
            System.out.println("Preload error: " + e.getMessage());
        }
    }

    // ================================================================
    //  HELPERS - clean input reading
    // ================================================================
    static int getInt() {
        int val = 0;
        try {
            val = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to 0.");
        }
        return val;
    }

    static double getDouble() {
        double val = 0;
        try {
            val = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to 0.");
        }
        return val;
    }
}
