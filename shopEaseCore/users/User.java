package users;

public abstract class User {
    
    private String userId;
    private String username;
    private String password;
    private String email;
    private String role;
    
    public User(String userId, String username, String password, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }


    public abstract void showDashboard();

    public boolean checkPassword (String input) {
        return this.password.equals(input);
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return "User[" + userId + " | " + username + " | Role: " + role + "]";
    }
}
