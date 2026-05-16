package users;

public class Admin extends User {

    private String adminLevel;
    private String department;
    
    public Admin(String userId, String username, String password,
            String email, String adminLevel, String department) {
        super(userId, username, password, email, "ADMIN");
        this.adminLevel = adminLevel;
        this.department = department;
    }

    public void showDashboard() {
        System.out.println("=== Admin Dashboard ===");
        System.out.println("Usernmae : " + getUsername());
        System.out.println("Level : " + adminLevel);
        System.out.println("Department : " + department);
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public String getDepartment() {
        return department;
    }
    
}
