package products;

public class DigitalProduct extends Product {
    
    private String licenseKey;
    private String downloadUrl;
    private int validityDays;
    
    public DigitalProduct(String productId, String name, String brand, double basePrice, int stockQuantity,
            String category, String licenseKey, String downloadUrl, int validityDays) {
        super(productId, name, brand, basePrice, stockQuantity, category);
        this.licenseKey = licenseKey;
        this.downloadUrl = downloadUrl;
        this.validityDays = validityDays;
    }

    public String getProductType(){
        return "Digital Product";
    }

    public double calculateShippingCost() {
        return 0;
    }

    public String getProductDetails() {
        return "Product     : " + getBrand() + " " + getName() +
                "\nCategory    : " + getCategory() +
                "\nPrice       : Rs." + getBasePrice() +
                "\nLicense Key : " + licenseKey +
                "\nDownload    : " + downloadUrl +
                "\nValid For   : " + validityDays + " days" +
                "\nShipping    : Free (Digital)";
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public int getValidityDays() {
        return validityDays;
    }

}
