package core;

import java.util.ArrayList;

import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import exceptions.UnauthorizedAccessException;
import products.Product;
import users.AuthService;

public class InventoryManager {
    
    private ArrayList<Product> products;
    private AuthService authService;

    public InventoryManager (AuthService authService) {
        this.products = new ArrayList<Product>();
        this.authService = authService;
    }

    // Admin Only 
    public void addProduct(Product p) throws UnauthorizedAccessException {
        authService.requireAdmin("Add Product");
        products.add(p);
        System.out.println("[Inventory] Added: " + p.getName());
    }

    public void removeProduct (String productId) 
        throws UnauthorizedAccessException, ProductNotFoundException {
            authService.requireAdmin("Remove Product");
            Product p = findById(productId);
            products.remove(p);
            System.out.println("[Inventory] Removed: " + p.getName());
        }

    public void restockProduct (String productId, int qty)
        throws UnauthorizedAccessException, ProductNotFoundException {
            authService.requireAdmin("Restock Product");
            Product p = findById(productId);
            p.addStock(qty);
            System.out.println("[Inventory] Restocked " + p.getName() + " by " + qty +
                    ". New stock: " + p.getStockQuantity());
        }

    public void updatePrice (String productId, double newPrice)
        throws UnauthorizedAccessException, ProductNotFoundException {
            authService.requireAdmin("Update Price");
            Product p = findById(productId);
            double old = p.getBasePrice();
            p.setBasePrice(newPrice);
            System.out.println("[Inventory] Price updated: " + p.getName() +
                    " Rs." + old + " -> Rs." + newPrice);
        }

    // Public - anyone can access

    public Product findById (String productId) throws ProductNotFoundException {
        for (int i=0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                return products.get(i);
            }
        }
        throw new ProductNotFoundException(productId);
    }

    public void checkStock(Product p, int qty) throws OutOfStockException {
        if (!p.hasStock(qty)){
            throw new OutOfStockException(p.getName(), qty, p.getStockQuantity());
        }
    }

    public void printInventory() {
        System.out.println("\n===== INVENTORY =====");
        if (products.isEmpty()) {
            System.out.println("No products.");
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i));
        }
        System.out.println("=====================\n");
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }
}
    
