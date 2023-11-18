/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import other.ControllerManager;

/**
 * FXML Controller class
 *
 * @author John Paul Uy
 */
public class SettlePaymentFXMLController implements Initializable {

    @FXML
    private ImageView CloseButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button printButton;

    private CashierFXMLController existingCashierController;

    public void setExistingCashierController(CashierFXMLController cashierController) {
        this.existingCashierController = cashierController;
    }

    @FXML
    void confirmButton(ActionEvent event) {

        CashierFXMLController cashierController = ControllerManager.getCashierController();

        if (existingCashierController == null && cashierController != null) {
            existingCashierController = cashierController;
        }
        cashierController.incrementCurrentCustomerID();
        cashierController.menuGetMilkteaAndFrappe();
        cashierController.setupTableView();
    }

    @FXML
    void printButton(ActionEvent event) {
        // Get the existing SettlePayment stage
        Stage settlePaymentStage = existingCashierController.getSettlePaymentStage();

        // Close the SettlePayment stage
        if (settlePaymentStage != null) {
            settlePaymentStage.close();
        }

        try {
            // Load the CashierConfirmationFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CashierConfirmationFXML.fxml"));
            Parent root = loader.load();

            // Create a new stage for the CashierConfirmationFXML
            Stage cashierConfirm = new Stage();

            // Set stage properties to make it transparent and non-resizable
            cashierConfirm.initStyle(StageStyle.TRANSPARENT);
            cashierConfirm.setResizable(false);

            // Set the scene fill to transparent
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Set the scene to the stage
            cashierConfirm.setScene(scene);

            // Set event handler for the hidden event
            cashierConfirm.setOnHidden(e -> {
                // Load the CashierFXML file
                FXMLLoader cashierLoader = new FXMLLoader(getClass().getResource("CashierFXML.fxml"));
                Parent cashierRoot = null;
                try {
                    cashierRoot = cashierLoader.load();
                } catch (IOException ex) {
                    Logger.getLogger(SettlePaymentFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Set the scene for the CashierFXML
                Scene cashierScene = new Scene(cashierRoot);

                // Get the stage of the current scene (assuming your CashierFXML is already loaded)
                Stage currentStage = (Stage) root.getScene().getWindow();

                // Set the scene to the current stage (returning to CashierFXML)
                currentStage.setScene(cashierScene);

                // Set the pane visibility to false
                CashierFXMLController cashierController = ControllerManager.getCashierController();
                if (existingCashierController == null && cashierController != null) {
                    existingCashierController = cashierController;
                }
                existingCashierController.getMyPane().setVisible(false);
            });

            cashierConfirm.show();

            // Create a PauseTransition to wait for 5 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> {
                // Close the CashierConfirmation stage after 5 seconds
                cashierConfirm.close();
            });

            // Start the PauseTransition
            pause.play();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Stage stage = (Stage) CloseButton.getScene().getWindow();
                    stage.close();

                    // Set the pane visibility to false
                    CashierFXMLController cashierController = ControllerManager.getCashierController();
                    if (existingCashierController == null && cashierController != null) {
                        existingCashierController = cashierController;
                    }
                    existingCashierController.getMyPane().setVisible(false);

                } catch (Exception ex) {
                    Logger.getLogger(AdminFXMLController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                // Consume the event to prevent it from propagating
                event.consume();
            }
        });

    }
}
