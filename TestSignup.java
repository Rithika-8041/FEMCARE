public class TestSignup {
    public static void main(String[] args) {
        String username = "testuser";
        String password = "test123";
        
        System.out.println("Attempting to register: " + username);
        boolean success = UserDAO.registerUser(username, password);
        
        if(success) {
            System.out.println("✓ Signup successful!");
            
            System.out.println("\nAttempting to login...");
            Integer userId = UserDAO.loginUser(username, password);
            if(userId != null) {
                System.out.println("✓ Login successful! User ID: " + userId);
            } else {
                System.out.println("✗ Login failed!");
            }
        } else {
            System.out.println("✗ Signup failed!");
        }
    }
}