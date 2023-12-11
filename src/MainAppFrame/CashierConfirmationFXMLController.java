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
    private Label handlerName1;

    @FXML
    private Label subTotal;

    @FXML
    private Label discount;

    @FXML
    private Label total;

    @FXML
    private Label cash;

    @FXML
    private Label change;

    @FXML
    private Label orderType;

    @FXML
    private Label orderID;

    @FXML
    private Label dateTime1;

    /**
     * Initializes the controller class.
     */
    private SettlePaymentFXMLController settlePaymentController;

    public void setSettlePaymentController(SettlePaymentFXMLController controller) {
        this.settlePaymentController = controller;
    }

    public void setOrderDetails(String handlerName, String dateTimeString, int customerID, String orderType, double subtotal, double discountApplied, double totalAmount, double cashAmount, double changeAmount) {

        // Concatenate peso sign to the values
        String pesoSign = "₱";
        String subtotalText = pesoSign + String.valueOf(subtotal);
        String discountText = (discountApplied != 0) ? "-" + pesoSign + String.valueOf(Math.abs(discountApplied)) : pesoSign + "0";
        String totalText = pesoSign + String.valueOf(totalAmount);
        String cashText = pesoSign + String.valueOf(cashAmount);
        String changeText = pesoSign + String.valueOf(changeAmount);

        dateTime1.setText(dateTimeString);
        handlerName1.setText(handlerName);
        orderID.setText(String.valueOf(customerID));
        subTotal.setText(subtotalText);
        discount.setText(discountText);
        total.setText(totalText);
        cash.setText(cashText);
        change.setText(changeText);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receiptTV2.setStyle("-fx-table-cell-border-color: transparent; -fx-table-header-border-color: transparent;");

        System.out.println("CashierConfirmationFXMLController initialized");
        try {
            setupTableView();

        } catch (SQLException ex) {
            Logger.getLogger(CashierConfirmationFXMLController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ObservableList<ItemData> fetchOrderDetails() throws SQLException {
        ObservableList<ItemData> orderDetailsList = FXCollections.observableArrayList();

        CashierFXMLController cashierController = ControllerManager.getCashierController();
        int currentCustomerID = cashierController.getCurrentCustomerID();

        if (currentCustomerID != -1) {
            try (Connection conn = database.getConnection()) {
                if (conn != null) {
                    // Fetch orders from each table based on customer_id
                    String[] tables = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

                    for (String table : tables) {
                        String orderQuery;
                        if ("milk_tea".equals(table)) {
                            orderQuery = "SELECT order_id, item_name, final_price, quantity, size, add_ons FROM " + table + " WHERE customer_id = ?";
                        } else if ("frappe".equals(table) || "fruit_drink".equals(table) || "coffee".equals(table)) {
                            orderQuery = "SELECT order_id, item_name, final_price, quantity, size, '' AS add_ons FROM " + table + " WHERE customer_id = ?";
                        } else {
                            orderQuery = "SELECT order_id, item_name, final_price, quantity, '' AS size, '' AS add_ons FROM " + table + " WHERE customer_id = ?";
                        }

                        try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
                            orderStmt.setInt(1, currentCustomerID);

                            try (ResultSet orderRs = orderStmt.executeQuery()) {
                                while (orderRs.next()) {
                                    // Extract relevant order details (adjust as needed)
                                    int orderId = orderRs.getInt("order_id");
                                    String productName = orderRs.getString("item_name");
                                    double finalPrice = orderRs.getDouble("final_price");
                                    int quantity = orderRs.getInt("quantity");

                                    // Check if size and add_ons columns are available
                                    String size = orderRs.getString("size") != null ? orderRs.getString("size") : "";
                                    String addons = orderRs.getString("add_ons") != null ? orderRs.getString("add_ons") : "";

                                    // Create an ItemData instance with the fetched data
                                    ItemData itemData = new ItemData(orderId, combineWithAddons(size, productName, addons), finalPrice, quantity);

                                    // Add the ItemData to the list
                                    orderDetailsList.add(itemData);
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
        return orderDetailsList;
    }

// Helper method for combining item name with add-ons
    private String combineWithAddons(String size, String itemName, String addons) {
        // Check if addons is not empty or contains only whitespace
        if (!addons.trim().isEmpty()) {
            // Concatenate with add-ons
            return size + " " + itemName + " with " + addons;
        } else {
            // Return without add-ons
            return size + " " + itemName;
        }
    }

    public void setupTableView() throws SQLException {
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
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }
    }

}
