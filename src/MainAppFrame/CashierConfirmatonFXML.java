/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

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
import static javafx.application.Application.launch;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableViewSkinBase;
import java.lang.reflect.Field;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkin;


public class CashierConfirmatonFXML implements Initializable {

    double xOffset, yOffset;
    private volatile boolean stop = false;

        @FXML
    private TableColumn<?, ?> Price;

    @FXML
    private TableColumn<?, ?> Product;

    @FXML
    private TableColumn<?, ?> Quantity;

    @FXML
    private TableColumn<?, ?> Size;

    @FXML
    private Label cash;

    @FXML
    private Label change;

    @FXML
    private Label dateLbl;

    @FXML
    private Label discount;

    @FXML
    private Label handlerName;

    @FXML
    private Label orderID;

    @FXML
    private Label orderNo;

    @FXML
    private Label orderType;

    @FXML
    private Label subTotal;

    @FXML
    private TableView<?> tableView;

    @FXML
    private Label timeLbl;

    @FXML
    private Label total;
    
    private Stage stage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void initialize() {
        // Set transparent background for the TableView
        tableView.setStyle("-fx-background-color: transparent;");

        // Hide column headers
        tableView.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                // Iterate over each column and set the header to null
                for (TableColumn<?, ?> column : tableView.getColumns()) {
                    column.setGraphic(null);
                    column.setText(null);
                }
            }
        });
}

}

    