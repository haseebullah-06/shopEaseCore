package users;

public class Customer extends User {
    
    private String fullName;
    private String address;
    private String phone;
    
    public Customer(String userId, String username, String password,
            String email, String fullName, String address, String phone) {
        super(userId, username, password, email, "CUSTOMER");
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public void showDashboard() {
        System.out.println("=== Customer Dashboard ===");
        System.out.println("Name : " + fullName);
        System.out.println("Email : " + getEmail());
        System.out.println("Phone : " + phone);
        System.out.println("Address : " + address);
    }


    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
} 
    

