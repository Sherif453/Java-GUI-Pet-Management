public class User implements Authenticatable {
    private String username;
    private String password; 

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Encapsulation - private fields, public getters/setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Polymorphism
    @Override
    public boolean authenticate(String inputPassword) {
        return password.equals(inputPassword);
    }
}
