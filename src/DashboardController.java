import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class DashboardController {

    @FXML private Button petManagerBtn;
    @FXML private Button appointmentBtn;
    @FXML private Button medicationBtn;

    @FXML
    private void openPetManager() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PetManager.fxml"));
        Stage stage = (Stage) petManagerBtn.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    @FXML
    private void openAppointments() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentScreen.fxml"));
        Stage stage = (Stage) appointmentBtn.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    @FXML
    private void openMedications() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MedicationScreen.fxml"));
        Stage stage = (Stage) medicationBtn.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }
}
