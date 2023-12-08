/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

import ClassFiles.ControllerManager;
import ClassFiles.ItemData;
import ClassFiles.ItemData2;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Gwyneth Uy
 */
public class CashierConfirmationFXMLController implements Initializable {

    @FXML
    private TableColumn<ItemData, Double> receiptPrice2;

    @FXML
    private TableColumn<ItemData, String> receiptProduct;

    @FXML
    private TableColumn<ItemData, Integer> receiptQuantity2;

    @FXML
    private TableView<ItemData> receiptTV2;

    @FXML
    private Label handlerName;
    @FXML
    private Label subTotal2;
    @FXML
    private Label applDsc2;
    @FXML
    private Label customTotal2;
    @FXML
    private Label cashInput2;
    @FXML
    private Label changeLbl2;
    @FXML
    private Label handlerName1;

    /**
     * Initializes the controller class.
     */
    private SettlePaymentFXMLController settlePaymentController;

    public void setSettlePaymentController(SettlePaymentFXMLController controller) {
        this.settlePaymentController = controller;
    }

    public void setOrderDetails(int customerID, String orderType, double subtotal, double discountApplied, double totalAmount, double cashAmount, double changeAmount) {

        // Concatenate peso sign to the values
        String pesoSign = "₱";
        String subtotalText = pesoSign + String.valueOf(subtotal);
        String discountText = (discountApplied != 0) ? "-" + pesoSign + String.valueOf(Math.abs(discountApplied)) : pesoSign + "0";
        String totalText = pesoSign + String.valueOf(totalAmount);
        String cashText = pesoSign + String.valueOf(cashAmount);
        String changeText = pesoSign + String.valueOf(changeAmount);

        subTotal.setText(subtotalText);
        discount.setText(discountText);
        total.setText(totalText);
        cash.setText(cashText);
        change.setText(changeText);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("CashierConfirmationFXMLController initialized");
        try {
            setupTableView();

        } catch (SQLException ex) {
            Logger.getLogger(CashierConfirmationFXMLController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ObservableList<ItemData> fetchOrderDetails() throws SQLException {
        System.out.println("Fetching order details...");

        ObservableList<ItemData> orderDetailsList1 = FXCollections.observableArrayList();

        CashierFXMLController cashierController = ControllerManager.getCashierController();
        int currentCustomerID = cashierController.getCurrentCustomerID();

        if (currentCustomerID != -1) {
            try (Connection conn = database.getConnection()) {
                if (conn != null) {
                    // Fetch orders from each table based on customer_id
                    String[] tables = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

                    for (String table : tables) {
                        System.out.println("Fetching from table: " + table);
                        String orderQuery = "SELECT * FROM " + table + " WHERE customer_id = ?";

                        try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
                            orderStmt.setInt(1, currentCustomerID);

                            try (ResultSet orderRs = orderStmt.executeQuery()) {
                                while (orderRs.next()) {
                                    // Extract relevant order details (adjust as needed)
                                    int orderId = orderRs.getInt("order_id");
                                    String productName = orderRs.getString("item_name");
                                    double finalPrice = orderRs.getDouble("final_price");
                                    int quantity = orderRs.getInt("quantity");

                                    // Create an ItemData instance with the fetched data
                                    ItemData itemData = new ItemData(orderId, productName, finalPrice, quantity);

                                    // Add the ItemData to the list
                                    orderDetailsList1.add(itemData);

                                    // Print the fetched data
                                    System.out.println("Fetched data: " + itemData);
                                }
                            }
                        }
                    }

                    // Update the table view with the fetched data
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database-related exceptions
            }
        }
        return orderDetailsList1;
    }

    public void setupTableView() throws SQLException {
        // Obtain the customer ID
        CashierFXMLController cashierController = ControllerManager.getCashierController();
        int currentCustomerID = cashierController.getCurrentCustomerID();

        ObservableList<ItemData> orderDetailsList = fetchOrderDetails();
        for (ItemData itemData : orderDetailsList) {
            System.out.println("Product Name: " + itemData.getItemName());
            System.out.println("Final Price: " + itemData.getItemPrice());
            System.out.println("Quantity: " + itemData.getItemQuantity());
        }

        try {
            receiptProduct.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getItemName()));
            receiptPrice2.setCellValueFactory(f -> new SimpleDoubleProperty(f.getValue().getItemPrice()).asObject());
            receiptQuantity2.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getItemQuantity()).asObject());

            // Bind the TableView to the combined ObservableList
            receiptTV2.setItems(fetchOrderDetails());

            // Pass the customer ID to fetchChangeAmountFromDatabase method
            double changeAmount = fetchChangeAmountFromDatabase(currentCustomerID);

            // Populate the changeLbl and transfer the same data to changeLbl2
            changeLbl2.setText(String.format("₱%.2f", changeAmount));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }
    }

    private int fetchCustomerID() {
    CashierFXMLController cashierController = ControllerManager.getCashierController();
    int currentCustomerID = cashierController.getCurrentCustomerID();
    System.out.println("Fetched Customer ID: " + currentCustomerID);
    return currentCustomerID;
}

private double fetchChangeAmountFromDatabase() {
    int customerID = fetchCustomerID();
    System.out.println("Fetching change amount for Customer ID: " + customerID);

    try (Connection conn = database.getConnection()) {
        if (conn != null) {
            String sql = "SELECT `change` FROM invoice WHERE customer_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the customer ID parameter
                stmt.setInt(1, customerID);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double changeAmount = rs.getDouble("change");
                        System.out.println("Fetched change amount: " + changeAmount);
                        return changeAmount;
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database-related exceptions
    }

    System.out.println("No change amount found. Returning default value: 0.0");
    return 0.0; // Default value if no data is found
}


    private double fetchChangeAmountFromDatabase(int customerID) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "SELECT `change` FROM invoice WHERE customer_id = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Set the customer ID parameter
                    stmt.setInt(1, customerID);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getDouble("change");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }

        return 0.0; // Default value if no data is found
    }

}
