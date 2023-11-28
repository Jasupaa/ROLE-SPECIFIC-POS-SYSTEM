/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

import Login.ControllerInterface;
import Login.LoginTest;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Set;
import java.util.HashSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.control.TableView;

import ClassFiles.EmployeeData;
/**
 * FXML Controller class
 *
 * @author Jasper
 */
public class AdminFXMLController implements Initializable, ControllerInterface  {
    
    double xOffset, yOffset;
    
    
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
    private Label dateLbl;

    @FXML
    private Label timeLbl;

    @FXML
    private Button sales;


    @FXML
    private Label timeLbl1;

    @FXML
    private ImageView CloseButton;
     
    private Stage stage;
     
    private Stage salesReportStage;
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
    private Button sales1;
    @FXML
    private TableView<?> employeeTable;
 
    @FXML
    void SalesreportButton(ActionEvent event) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SalesReport.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
    @FXML
    void EmployeeDetailsButton(ActionEvent event) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeData.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    
    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void setStage(Stage stage) {
    this.stage = stage;    
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
    
}
