import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/pet_health_db";
    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static boolean registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps.executeUpdate() > 0;
        }
    }

    public static User loginUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String passFromDb = rs.getString("password");
                User user = new User(username, passFromDb);
                if (user.authenticate(password)) {
                    return user;
                }
            }
            return null;
        }
    }
    
    public static boolean validateUser(String username, String password) {
    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // returns true if a match is found
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    // You can add methods to store pets, appointments, medications similarly
}
