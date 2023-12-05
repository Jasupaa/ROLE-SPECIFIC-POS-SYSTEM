/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainAppFrame;

import MainAppFrame.*;
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
/**
 *
 * @author Laira
 */
public class AddEmployeeFXMLController implements Initializable{

       @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private TextField FirstNameTxtLbl;

    @FXML
    private TextField LastNameTxtLbl;

    @FXML
    private Button AddEmployeeButton;

    @FXML
    private TextField ContactTxtLbl;

    @FXML
    private TextField emailTxtLbl;

    @FXML
    private TextField pinCodeTxtLbl;

    @FXML
    private Label pinCode;

    @FXML
    private Button generateRandomPinCode;

    @FXML
    private ImageView CloseButton;

    
   
    
    @FXML
    private void generateRandomPinCodeAction(ActionEvent event) {
    
    int randomPinCode = generateRandomPinCode();

    
    pinCodeTxtLbl.setText(String.valueOf(randomPinCode));
}
    private int generateRandomPinCode() {
    int randomPinCode;


    do {
        randomPinCode = (int) (Math.random() * 900000) + 100000; // Generates a 6-digit pin code
    } while (isPinCodeExists(randomPinCode));

    return randomPinCode;
}

    private boolean isPinCodeExists(int pinCode) {
    try (Connection connection = database.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT pin_code FROM employees WHERE pin_code = " + pinCode)) {

        return resultSet.next(); 

    } catch (SQLException e) {
        e.printStackTrace();
        
        return true; 
    }
}

    private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO `employees` (`emp_id`, `empFirstName`, `empLastName`, `empContact`, `empEmail`, `emp_role`, `pin_code`, `empStatus`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private AdminFXMLController parentController;

    public void setParentController(AdminFXMLController parentController) {
        this.parentController = parentController;
    }

   private void displayErrorAlert(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
}

    private static int nextEmpID = 1;


   @FXML
   private void addEmployeeAction() {
    StringBuilder errorMessage = new StringBuilder();
    
    EmployeeData employee = null; // Initialize employee

    // Check for empty fields
    if (FirstNameTxtLbl.getText().isEmpty() || LastNameTxtLbl.getText().isEmpty() ||
            ContactTxtLbl.getText().isEmpty() || emailTxtLbl.getText().isEmpty() ||
            roleComboBox.getValue() == null)  {
        errorMessage.append("Please fill in all fields.\n");
    } else {
        try {
            // Validate contact number length
            String contactText = ContactTxtLbl.getText();
            long contactNumber = Long.parseLong(contactText);
            if (contactText.length() != 11) {
                errorMessage.append("Please enter exactly 11 characters for Contact.\n");
            }
            String emailPattern = "[a-zA-Z0-9._%+-]+@(gmail|email|yahoo)\\.com";
            if (!emailTxtLbl.getText().matches(emailPattern)) {
                errorMessage.append("Please enter a valid email address (e.g., user@example.com).\n");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorMessage.append("Please enter a valid number for Contact.\n");
            displayErrorAlert(errorMessage.toString());
            return;
        }}

    if (pinCodeTxtLbl.getText().isEmpty()) {
        errorMessage.append("Pin Code is required to be filled.\n");
    } else {
        try {
            int pinCode = Integer.parseInt(pinCodeTxtLbl.getText());

            // Check pin code length
            if (pinCodeTxtLbl.getText().length() != 6) {
                errorMessage.append("Please enter exactly 6 digits for Pin Code.\n");
            }

            // Create EmployeeData only if pin code is provided
            employee = new EmployeeData(
            nextEmpID,
            FirstNameTxtLbl.getText(),
            LastNameTxtLbl.getText(),
            emailTxtLbl.getText(),
            Long.parseLong(ContactTxtLbl.getText()),
            roleComboBox.getValue().getRoleName(),
            pinCode,
            "Active"
            );
            employee.setEmpStatus("Active");
        } catch (NumberFormatException e) {
            errorMessage.append("Please enter a valid pin code (numeric value).\n");
            displayErrorAlert(errorMessage.toString());
            return;
        }
    }

    if (errorMessage.length() > 0) {
        displayErrorAlert(errorMessage.toString());
        return;
    }
    nextEmpID++;

    parentController.addEmployee(employee);

    try (Connection connection = database.getConnection();
         PreparedStatement statement = employee.getInsertStatement(connection)) {
        statement.setInt(5, employee.getPin_code());
        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            displaySuccessAlert("Employee added successfully!");
        } else {
            displayErrorAlert("Failed to add an employee to the database.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        displayErrorAlert("Error inserting an employee into the database.");
    }}
   
    private void displaySuccessAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();

    Stage stage = (Stage) AddEmployeeButton.getScene().getWindow();
    stage.close();
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try (Connection connection = database.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT MAX(emp_id) FROM employees")) {

        if (resultSet.next()) {
            nextEmpID = resultSet.getInt(1) + 1;
        } else {
            nextEmpID = 1; 
        }

    } catch (SQLException e) {
        e.printStackTrace();
        displayErrorAlert("Error retrieving maximum emp_id from the database.");
}
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
}
}
