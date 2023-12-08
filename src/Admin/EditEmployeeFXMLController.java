package Admin;

import Login.LoginTest;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Login.ControllerInterface;
import java.io.IOException;
import Login.LoginTest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Databases.CRUDDatabase;
import ClassFiles.EmployeeData;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;

import ClassFiles.Role;
import ClassFiles.EmployeeData;
import MainAppFrame.AdminFXMLController;
import MainAppFrame.database;

/**
 *
 * @author Laira
 */
public class EditEmployeeFXMLController implements Initializable {

    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private TextField FirstNameTxtLbl;

    @FXML
    private TextField LastNameTxtLbl;

    @FXML
    private Button UpdateEmployeeButton;

    @FXML
    private TextField ContactTxtLbl;

    @FXML
    private TextField emailTxtLbl;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ImageView CloseButton;

    private AdminFXMLController parentController;
    private EmployeeData employeeData;
    private Connection connection;

    private ObservableList<EmployeeData> employeeDataList;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public EditEmployeeFXMLController() {

    }

    public EditEmployeeFXMLController(Connection connection) {
        this.connection = connection;
    }

    public void setParentController(AdminFXMLController parentController) {
        this.parentController = parentController;
    }

    public void setEmployeeData(EmployeeData employeeData) {
        this.employeeData = employeeData;

        FirstNameTxtLbl.setText(employeeData.getEmpFirstName());
        LastNameTxtLbl.setText(employeeData.getEmpLastName());
        ContactTxtLbl.setText(String.valueOf(employeeData.getEmpContact()));
        emailTxtLbl.setText(employeeData.getEmpEmail());

        Role employeeRole = new Role(employeeData.getEmp_role());
        roleComboBox.setValue(employeeRole);

        statusComboBox.setValue(employeeData.getEmpStatus());
    }

    @FXML
    private void handleUpdateEmployeeButtonClick(ActionEvent event) {
        StringBuilder errorMessage = new StringBuilder();

        // ... (your existing validation logic)
        if (errorMessage.length() > 0) {
            displayErrorAlert(errorMessage.toString());
            return;
        }

        // Update the employee data and UI
        employeeData.setEmpFirstName(FirstNameTxtLbl.getText());
        employeeData.setEmpLastName(LastNameTxtLbl.getText());
        employeeData.setEmpContact(Long.parseLong(ContactTxtLbl.getText()));
        employeeData.setEmpEmail(emailTxtLbl.getText());

        if (roleComboBox.getValue() != null) {
            employeeData.setEmpRole(roleComboBox.getValue().getRoleName());
        }

        // Set employee status from the selected value in statusComboBox
        employeeData.setEmpStatus(statusComboBox.getValue());

        if (statusComboBox.getValue() != null && statusComboBox.getValue().equals("Terminated")) {
            // If the status is "Terminated," disable the cells and move the employee to the bottom
            try {
                parentController.updateEmployee(connection, employeeData);
                parentController.disableEmployeeCell(employeeData); // New method to disable the cells
                displaySuccessAlert("Employee Data updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                displayErrorAlert("Error updating Employee Data");
                return;
            }
        } else {
            try {
                parentController.updateEmployee(connection, employeeData);
                displaySuccessAlert("Employee Data updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                displayErrorAlert("Error updating Employee Data");
                return;
            }
        }

        parentController.updateEmployeeTable();

        Stage stage = (Stage) UpdateEmployeeButton.getScene().getWindow();
        stage.close();
    }

    private boolean isEmployeeDataUnchanged() {
        return employeeData.getEmpFirstName().equals(FirstNameTxtLbl.getText())
                && employeeData.getEmpLastName().equals(LastNameTxtLbl.getText())
                && employeeData.getEmpContact() == Long.parseLong(ContactTxtLbl.getText())
                && employeeData.getEmpEmail().equals(emailTxtLbl.getText())
                && employeeData.getEmp_role().equals(roleComboBox.getValue().getRoleName())
                && employeeData.getEmpStatus().equals(statusComboBox.getValue());
    }

    private void displayErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void displaySuccessAlert(String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EditEmployeeFXMLController editEmployeeController = new EditEmployeeFXMLController(connection);

        CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Stage stage = (Stage) CloseButton.getScene().getWindow();
                    stage.close();

                    LoginTest loginTest = new LoginTest();
                    loginTest.start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        ObservableList<Role> roles = FXCollections.observableArrayList(
                new Role("cashier"),
                new Role("admin"),
                new Role("kitchen")
        );
        roleComboBox.setItems(roles);

        ObservableList<String> statusOptions = FXCollections.observableArrayList("Active", "Inactive", "Terminated");
        statusComboBox.setItems(statusOptions);

    }
}
