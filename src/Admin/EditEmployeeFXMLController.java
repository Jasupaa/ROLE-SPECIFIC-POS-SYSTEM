/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
/**
 *
 * @author Laira
 */
public class EditEmployeeFXMLController implements Initializable{

    @FXML
    private ImageView CloseButton;
    @FXML
    private TextField FirstNameTxtLbl;
    @FXML
    private TextField LastNameTxtLbl;
    @FXML
    private TextField ContactTxtLbl;
    @FXML
    private TextField emailTxtLbl;
    @FXML
    private TextField bioDataTxtLbl;
    @FXML
    private Button EditEmployeeButton;
    @FXML
    private ComboBox<Role> roleComboBox;
    @FXML
    private Button BioDataEnterButton;

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

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            try {
                Stage stage = (Stage) CloseButton.getScene().getWindow();
                stage.close();

                // Open the login window.
                LoginTest loginTest = new LoginTest();
                loginTest.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

    // Populate the ComboBox with some sample roles
    ObservableList<Role> roles = FXCollections.observableArrayList(
        new Role("cashier"),
        new Role("admin"),
        new Role("kitchen")
    );
    roleComboBox.setItems(roles);
}
}
