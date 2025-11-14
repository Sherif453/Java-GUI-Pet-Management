import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button registerButton;
    @FXML private Button loginLink;
    @FXML private Label statusLabel;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        try {
            User user = new User(username, password);
            boolean success = Database.registerUser(user);

            if (success) {
                statusLabel.setText("Registered successfully.");
                // Optionally, go back to login screen automatically
                loadLoginScene();
            } else {
                statusLabel.setText("Registration failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error during registration.");
        }
    }

    @FXML
    private void handleLoginLink() {
        loadLoginScene();
    }

    private void loadLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading login screen.");
        }
    }
}
