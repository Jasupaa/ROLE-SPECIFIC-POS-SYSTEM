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

public class AddEmployeeFXMLController implements Initializable{
    
    @FXML
    private TextField discCodeTxtLbl;

    @FXML
    private TextField discTxtLbl;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField itmTotalTxtLbl;

    @FXML
    private TextField newTotalTxtLbl;

    @FXML
    private TextField cashTxtLbl;

    @FXML
    private Button cashEnter;

    @FXML
    private ImageView CloseButton;

    @FXML
    void cashEnterButton(ActionEvent event) {

    }

    @FXML
    void confirmButton(ActionEvent event) {

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
