import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class PetManagerController {

    @FXML private TextField nameField;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private ComboBox<String> speciesBox;
    @FXML private TextArea outputArea;

    // Database connection constants
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pet_health_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @FXML
    private void initialize() {
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 1));
        speciesBox.getItems().addAll("Dog", "Cat", "Bird", "Other");
    }

    @FXML
    private void addPet() {
        String name = nameField.getText();
        String species = speciesBox.getValue();
        Integer age = ageSpinner.getValue();

        if (name.isEmpty() || species == null) {
            outputArea.setText("Please fill all fields.");
            return;
        }

        // Create a Pet object using polymorphism
        Pet pet = switch (species) {
            case "Dog" -> new Dog(name, age);
            case "Cat" -> new Cat(name, age);
            default -> new Pet(name, age) {
                @Override public String getPetType() { return species; }
            };
        };

        // Insert into the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO pets (name, species, age) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pet.getName());
            pstmt.setString(2, pet.getPetType());
            pstmt.setInt(3, pet.getAge());
            pstmt.executeUpdate();

            outputArea.appendText("Added to database: " + pet + "\n");

            // Clear inputs
            nameField.clear();
            speciesBox.getSelectionModel().clearSelection();
            ageSpinner.getValueFactory().setValue(1);

        } catch (SQLException e) {
            outputArea.setText("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goBackToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            outputArea.setText("Error loading dashboard: " + e.getMessage());
        }
    }
}

