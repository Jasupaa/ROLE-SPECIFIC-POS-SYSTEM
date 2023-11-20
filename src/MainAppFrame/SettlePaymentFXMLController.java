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
import other.ControllerManager;

/**
 * FXML Controller class
 *
 * @author John Paul Uy
 */
public class SettlePaymentFXMLController implements Initializable {

    double xOffset, yOffset;
    private volatile boolean stop = false;

    @FXML
    private ImageView CloseButton;

    @FXML
    private TextField appldDscTxtLbl;

    @FXML
    private Button cashEnter;

    @FXML
    private TextField cashTxtLbl;

    @FXML
    private TextField changeTxtLbl;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField discCodeTxtLbl;

    @FXML
    private Button discEnter;

    @FXML
    private TextField discTxtLbl;

    @FXML
    private TextField itmTotalTxtLbl;

    @FXML
    private TextField newTotalTxtLbl;

    @FXML
    private TextField ordertypeTxtField;

    @FXML
    private Button printButton;

    private CashierFXMLController existingCashierController;

    public void setExistingCashierController(CashierFXMLController cashierController) {
        this.existingCashierController = cashierController;
    }

    public void setOrderType(String orderType) {
        ordertypeTxtField.setText(orderType);
        ordertypeTxtField.setEditable(false); // Disable the TextField
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

    @FXML
    void discEnterButton(ActionEvent event) {

        String discountCode = discCodeTxtLbl.getText();
        if (discountCode != null && !discountCode.isEmpty()) {
            double discountPercent = getDiscountValueFromDatabase(discountCode);
            if (discountPercent != -1) {
                // Display the corresponding discount value with a percent sign
                if (discountPercent == 0) {
                    discTxtLbl.setText("No discount applied.");
                    // If the discount is 0, set appldDscTxtLbl to "-₱0.00"
                    appldDscTxtLbl.setText("-₱0.00");
                    // Retain the original total in newTotalTxtLbl
                    newTotalTxtLbl.setText(itmTotalTxtLbl.getText());
                } else {
                    discTxtLbl.setText(String.format("%.2f%%", discountPercent));

                    // Calculate discounted amount based on the percent
                    double totalAmount = Double.parseDouble(itmTotalTxtLbl.getText().substring(1)); // Remove the ₱ sign
                    double discountAmount = (discountPercent / 100) * totalAmount;
                    double discountedTotal = totalAmount - discountAmount;

                    // Display the discounted amount in newTotalTxtLbl with a minus sign
                    newTotalTxtLbl.setText(String.format("₱%.2f", discountedTotal));

                    // Display the original total in itmTotalTxtLbl
                    itmTotalTxtLbl.setText(String.format("₱%.2f", totalAmount));

                    // Display the discount amount in appldDscTxtLbl with a minus sign
                    appldDscTxtLbl.setText(String.format("-₱%.2f", discountAmount));
                }
            } else {
                // Handle the case when the discount code is not found
                if (discountCode.equals("0")) {
                    // If the discount code is explicitly set to 0, treat it as a valid input
                    appldDscTxtLbl.setText("-₱0.00");
                    newTotalTxtLbl.setText(itmTotalTxtLbl.getText());
                } else {
                    discTxtLbl.setText("Invalid!");
                }
            }
        } else {
            // Handle the case when the discount code is empty
            discTxtLbl.setText("Enter a Discount Code");
        }
    }

    private double getDiscountValueFromDatabase(String discountCode) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "SELECT disc_value FROM discount WHERE disc_code = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, discountCode);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getDouble("disc_value");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the discount code is not found
    }

    @FXML
    private void calculateTotal() {
        CashierFXMLController cashierController = ControllerManager.getCashierController();
        int currentCustomerID = cashierController.getCurrentCustomerID();

        if (currentCustomerID != -1) {
            try (Connection conn = database.getConnection()) {
                if (conn != null) {
                    String[] tables = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

                    double total = 0.0;

                    for (String table : tables) {
                        String sql = "SELECT final_price FROM " + table + " WHERE customer_id = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setInt(1, currentCustomerID);
                            try (ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()) {
                                    total += rs.getDouble("final_price");
                                }
                            }
                        }
                    }

                    itmTotalTxtLbl.setText(String.format("₱%.2f", total));
                    itmTotalTxtLbl.setDisable(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            itmTotalTxtLbl.setText("No Customer ID");
        }
    }

    @FXML
    void cashEnterButton(ActionEvent event) {
        String cashEntered = cashTxtLbl.getText();

        if (!cashEntered.isEmpty()) {
            try {
                // Parse the cash entered by the user
                double cashAmount = Double.parseDouble(cashEntered);

                // Parse the new total amount
                double newTotalAmount = Double.parseDouble(newTotalTxtLbl.getText().substring(1)); // Remove the ₱ sign

                // Calculate the change
                double change = cashAmount - newTotalAmount;

                // Display the change in the changeTxtLbl with a minus sign
                changeTxtLbl.setText(String.format("₱%.2f", change));
            } catch (NumberFormatException e) {
                // Handle the case when the user enters non-numeric or invalid input for cash
                changeTxtLbl.setText("Invalid Cash Amount");
            }
        } else {
            // Handle the case when the cashTxtLbl is empty
            changeTxtLbl.setText("Enter Cash Amount");
        }
    }

    // ... existing code ...
/*

    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
     */
    @FXML
    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    /**
     * @param url  * Initializes the controller class.
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeTxtLbl.setDisable(true);
        
        ordertypeTxtField.setDisable(true);
        itmTotalTxtLbl.setDisable(true);
        newTotalTxtLbl.setDisable(true); // Disable the TextField
        discTxtLbl.setDisable(true); // Disable the TextField
        appldDscTxtLbl.setDisable(true); // Disable the TextField
        calculateTotal();
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
