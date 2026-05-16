package products;

public class PhysicalProduct extends Product {

    private double weightKg;
    private String dimensions;
    private boolean hasWarranty;
    private int warrantyMonths;
    
    public PhysicalProduct(String productId, String name, String brand, double basePrice, int stockQuantity,
            String catergory, double weightKg, String dimensions, boolean hasWarranty, int warrantyMonths) {
        super(productId, name, brand, basePrice, stockQuantity, catergory);
        this.weightKg = weightKg;
        this.dimensions = dimensions;
        this.hasWarranty = hasWarranty;
        this.warrantyMonths = warrantyMonths;
    }


    public String getProductType () {
        return "Physical Electronic";
    }

    public double calculateShippingCost() {
        return 50 + (weightKg * 30);
    }

    public String getProductDetails() {
        return "Product  : " + getBrand() + " " + getName() +
                "\nCategory : " + getCategory() +
                "\nPrice    : Rs." + getBasePrice() +
                "\nWeight   : " + weightKg + " kg" +
                "\nSize     : " + dimensions +
                "\nWarranty : " + (hasWarranty ? warrantyMonths + " months" : "No Warranty") +
                "\nStock    : " + getStockQuantity();
    }

    public double getWeighKg() {
        return weightKg;
    }

    public boolean isHasWarranty () {
        return hasWarranty;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }
    
    
}
