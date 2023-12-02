/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainAppFrame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Login.LoginTest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ClassFiles.ControllerManager;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import ClassFiles.EmployeeData;

/**
 *
 * @author Laira
 */
public class EmployeeDataFXMLController implements Initializable {

    @FXML
    private AnchorPane AdminPane;
    @FXML
    private Button SalesReport;
    @FXML
    private Button InventoryManagement;
    @FXML
    private Button EmployeeDetails;
    @FXML
    private Button DiscountCoupon;
    @FXML
    private ImageView CloseButton;
    @FXML
    private Label dateLbl;
    @FXML
    private Label timeLbl;
    @FXML
    private Button sales;
    @FXML
    private Label timeLbl1;
    @FXML
    private Label timeLbl2;
    @FXML
    private AnchorPane AdminPane1;
    @FXML
    private Button SalesReport1;
    @FXML
    private Button InventoryManagement1;
    @FXML
    private Button EmployeeDetails1;
    @FXML
    private Button DiscountCoupon1;
    @FXML
    private ImageView CloseButton1;
    @FXML
    private Label dateLbl1;
    @FXML
    private Button addEmployee;
    @FXML
    private TableView<EmployeeData> employeeTable;

    @FXML
    private TableColumn<EmployeeData, Integer> empID;
        
    @FXML
    private TableColumn<EmployeeData, String> empFirstName;

    @FXML
    private TableColumn<EmployeeData, String> empLastName;
    
    @FXML
    private TableColumn<EmployeeData, String> empEmail;

    @FXML
    private TableColumn<EmployeeData, Integer> empContact;

    @FXML
    private TableColumn<EmployeeData, String> empRole;
    @FXML
    private Pane blurPane;
    
    private Stage addEmployeeStage;
    @FXML
    private void addEmployeeButton(ActionEvent event) {
        try {
            // Load the SettlePaymentFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            Parent root = loader.load();

            // Create a new stage for the SettlePaymentFXML
            addEmployeeStage = new Stage();

            // Set stage properties to make it transparent and non-resizable
            addEmployeeStage.initStyle(StageStyle.TRANSPARENT);
            addEmployeeStage.initStyle(StageStyle.UNDECORATED); // Removes the title bar
            addEmployeeStage.setResizable(false);

            // Set the scene fill to transparent
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Set the scene to the stage
            addEmployeeStage.setScene(scene);

            // Get the controller for SettlePaymentFXML
            SettlePaymentFXMLController settlePaymentController = loader.getController();

            // Set the order type
            settlePaymentController.setOrderType("Take Out");

            addEmployeeStage.show();
            blurPane.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }
    }

    public Stage getaddEmployeeStage() {
        return addEmployeeStage;
    }
    
    @FXML
    private void handleMouseDragged(MouseEvent event) {
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
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

    }    

    @FXML
    private void SalesreportButton(ActionEvent event) {
    }
    
}
