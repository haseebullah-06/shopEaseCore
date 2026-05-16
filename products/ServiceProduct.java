package products;

public class ServiceProduct extends Product {
    
    private String serviceType;
    private int durationHours;
    private boolean homeService;
   
    public ServiceProduct(String productId, String name, String brand, double basePrice, int stockQuantity,
            String category, String serviceType, int durationHours, boolean homeService) {
        super(productId, name, brand, basePrice, stockQuantity, category);
        this.serviceType = serviceType;
        this.durationHours = durationHours;
        this.homeService = homeService;
    }

    public String getProductType() {
        return "Service";
    }

    public double calculateShippingCost() {
        if (homeService == true) {
            return 200;
        } else {
            return 0;
        }
    }

    public String getProductDetails() {

        String location;

        if (homeService == true) {
            location = "Home Visit (Rs.200 fee)";
        } else {
            location = "In-Store";
        }

        return "Service  : " + getBrand() + " " + getName() +
                "\nType     : " + serviceType +
                "\nDuration : " + durationHours + " hours" +
                "\nLocation : " + location +
                "\nPrice    : Rs." + getBasePrice();
    }

    public String getServiceType() {
        return serviceType;
    }

    public boolean isHomeService() {
        return homeService;
    }
}
