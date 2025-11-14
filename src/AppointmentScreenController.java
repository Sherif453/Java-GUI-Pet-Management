import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class AppointmentScreenController {

    @FXML private TextField petNameField;
    @FXML private DatePicker appointmentDate;
    @FXML private TextArea notesField;
    @FXML private TextArea outputArea;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/pet_health_db";
    private static final String DB_USER = "root";           // Default user for XAMPP
    private static final String DB_PASS = "";               // Empty password if unchanged

    @FXML
    private void scheduleAppointment() {
        String name = petNameField.getText();
        String date = (appointmentDate.getValue() != null) ? appointmentDate.getValue().toString() : null;
        String notes = notesField.getText();

        if (name.isEmpty() || date == null) {
            outputArea.setText("Please fill all fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO appointments (pet_name, appointment_date, notes) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setString(3, notes);
            pstmt.executeUpdate();

            outputArea.appendText("Scheduled " + name + " for " + date + "\nNotes: " + notes + "\n");

            petNameField.clear();
            appointmentDate.setValue(null);
            notesField.clear();
        } catch (SQLException e) {
            outputArea.setText("DB Error: " + e.getMessage());
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
            outputArea.setText("Error loading dashboard: " + e.getMessage());
        }
    }
}
