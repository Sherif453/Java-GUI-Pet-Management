import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class MedicationScreenController {

    @FXML private TextField petNameField;
    @FXML private TextField medicationNameField;
    @FXML private TextArea medicationList;

    // Database connection constants
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pet_health_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @FXML
    private void addMedication() {
        String pet = petNameField.getText().trim();
        String med = medicationNameField.getText().trim();

        if (pet.isEmpty() || med.isEmpty()) {
            medicationList.setText("Please fill all fields.");
            return;
        }

        // Insert into database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO medications (pet_name, medication_name) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pet);
            pstmt.setString(2, med);
            pstmt.executeUpdate();

            medicationList.appendText(pet + ": " + med + "\n");

            // Clear input fields
            petNameField.clear();
            medicationNameField.clear();

        } catch (SQLException e) {
            medicationList.setText("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goBackToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage stage = (Stage) petNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            medicationList.setText("Error loading dashboard: " + e.getMessage());
        }
    }
}
