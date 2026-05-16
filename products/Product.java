package products;

public abstract class Product {
    
    private String productId;
    private String name;
    private String brand;
    private double basePrice;
    private int stockQuantity;
    private String category;

    public Product(String productId, String name, String brand, double basePrice, int stockQuantity, String category) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.basePrice = basePrice;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    // Abstract Methods
    public abstract String getProductType();
    public abstract double calculateShippingCost();
    public abstract String getProductDetails();

    // Getters 
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public double getBasePrice() {
        return basePrice;
    }


    // Setters
    public void setBasePrice (double price) {
        try {
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            this.basePrice = price;
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void setStockQuantity(int qty) {
        try {
            if (qty < 0) {
                throw new IllegalArgumentException("Stock cannot be negative");
            }
            this.stockQuantity = qty;
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    // Other Methods 
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean hasStock (int requested) {
        return stockQuantity >= requested;
    }

    public void reduceStock (int qty) {
        this.stockQuantity -= qty;
    }

    public void addStock (int qty) {
        this.stockQuantity += qty;
    }

    public String toString() {
        return "[" + productId + "]" + brand + " " + name + 
                " | Price Rs." + basePrice +
                " | Stock: " + stockQuantity +
                " | Type: " + getProductType();
    }
    
}
