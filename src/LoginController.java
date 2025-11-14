import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;         
    @FXML private Button registerLink;     
    
    /**
     * Handles the hyperlink click event to switch to the Register screen.
     */
    @FXML
    private void handleRegisterLink() {
        try {
            // Load the Register.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            
            // Get the current window (stage) from the hyperlink's scene
            Stage stage = (Stage) registerLink.getScene().getWindow();
            
            // Set the new scene on the stage
            stage.setScene(new Scene(root));
            stage.setTitle("Register"); // Optional: change the window title
        } catch (Exception e) {
            System.err.println("Error loading Register screen: " + e.getMessage());
            e.printStackTrace();
            // Optionally update statusLabel for user feedback
            statusLabel.setText("Failed to open registration screen.");
        }
    }
    
    /**
     * Handles the login button click event.
     * Validates input, disables the button, and runs login check on a background thread.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if any fields are empty and show a message
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        // Disable login button to prevent multiple clicks during processing
        loginButton.setDisable(true);
        statusLabel.setText("Logging in...");

        // Create a background task to validate credentials without freezing UI
        Task<Boolean> loginTask = new Task<>() {     //multithreading
            @Override
            protected Boolean call() {
                // Simulate database user validation (replace with your actual DB call)
                return Database.validateUser(username, password);
            }
        };

        // When the task succeeds, update UI accordingly
        loginTask.setOnSucceeded(event -> {
            boolean success = loginTask.getValue();
            loginButton.setDisable(false);

            if (success) {
                statusLabel.setText("Login successful.");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Dashboard");
                } catch (Exception e) {
                    statusLabel.setText("Failed to load dashboard.");
                    e.printStackTrace();
                }
            } else {
                statusLabel.setText("Invalid credentials.");
            }
        });

        // Handle task failure (e.g., DB error)
        loginTask.setOnFailed(event -> {
            statusLabel.setText("Login error.");
            loginButton.setDisable(false);
            // Optionally log error: loginTask.getException().printStackTrace();
        });

        // Start the background login task
        new Thread(loginTask).start();
    }
}

