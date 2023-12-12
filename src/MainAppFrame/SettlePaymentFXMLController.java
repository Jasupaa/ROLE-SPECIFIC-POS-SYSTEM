/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

import java.sql.Date;
import ClassFiles.CoffeeItemData;
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
import ClassFiles.ItemData;
import ClassFiles.OrderCardData;
import MenuController.CoffeeController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import ClassFiles.TxtUtils;

/**
 * FXML Controller class
 *
 * @author John Paul Uy
 */
public class SettlePaymentFXMLController implements Initializable {

    double xOffset, yOffset;
    private volatile boolean stop = false;

    @FXML
    private Label handlerName;

    @FXML
    private Label dateTime;

    @FXML
    private ImageView CloseButton;

    @FXML
    private TextField appldDscTxtLbl;

    @FXML
    private Label cash;

    @FXML
    private Button cashEnter;

    @FXML
    private TextField cashTxtLbl;

    @FXML
    private Label change;

    @FXML
    private TextField changeTxtLbl;

    @FXML
    private Button confirmButton;

    @FXML
    private Label customTotalLabel;

    @FXML
    private TextField discCodeTxtLbl;

    @FXML
    private Button discEnter;

    @FXML
    private TextField discTxtLbl;

    @FXML
    private Label appldDscLbl;

    @FXML
    private Label cashInput;

    @FXML
    private Label changeLbl;

    @FXML
    private TextField itmTotalTxtLbl;

    @FXML
    private TextField newTotalTxtLbl;

    @FXML
    private TextField ordertypeTxtField;

    @FXML
    private Button printButton;

    @FXML
    private Label subTotal;

    @FXML
    private TableColumn<ItemData, Double> receiptPrice;

    @FXML
    private TableColumn<ItemData, String> receiptProduct;

    @FXML
    private TableColumn<ItemData, Integer> receiptQuantity;

    @FXML
    private TableView<ItemData> receiptTV;

    @FXML
    private AnchorPane yourAnchorPane;

    private CashierFXMLController existingCashierController;

    private ObservableList<ItemData> menuMilkteaAndFrappeListData;

    private String empName;

    private int empId;

    private String employeeName;

    private int customerID;

    private int employeeId;

    private String originalTotal;

    private double changeValue = 0.0;

    public void setExistingCashierController(CashierFXMLController cashierController, String employeeName, int employeeId) {
        this.existingCashierController = cashierController;
        this.empName = employeeName;
        this.empId = employeeId;
    }

    public void setExistingCashierController(CashierFXMLController cashierController) {
        this.existingCashierController = cashierController;
    }

    public void setOrderType(String orderType) {
        ordertypeTxtField.setText(orderType);
        ordertypeTxtField.setEditable(false); // Disable the TextFiel
    }

    // Setters for employeeName, employeeId, and customerID
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        // Update the UI with the new employeeName
        handlerName.setText(employeeName);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
        // Perform any other operations related to employeeId if needed
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
        // Perform any other operations related to customerID if needed
    }

    private void updateDateTimeLabel() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the date and time using a DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Set the formatted date and time to the dateTime label
        dateTime.setText(formattedDateTime);
    }

    @FXML
    void confirmButton(ActionEvent event) {

    }

    private void insertInvoiceToDatabase(int customerID, String orderType, double totalAmount, double cashAmount, double changeAmount) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO invoice (customer_id, employee_id, emp_name, date_time, order_type, total, cash, `change`) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Set other values as needed (employee ID, employee name, date-time)
                    stmt.setInt(1, customerID);
                    stmt.setInt(2, empId);
                    stmt.setString(3, empName);
                    stmt.setString(4, orderType);
                    stmt.setDouble(5, totalAmount);
                    stmt.setDouble(6, cashAmount);
                    stmt.setDouble(7, changeAmount);

                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    @FXML
    void printButton(ActionEvent event) throws SQLException {
        // Parse the values from UI elements
        double cashAmount;
        try {
            cashAmount = Double.parseDouble(cashTxtLbl.getText());
        } catch (NumberFormatException e) {
            // Handle the case when the user enters non-numeric or invalid input for cash
            showInvalidCashAlert();
            return;  // Return to exit the method
        }

        double newTotalAmount = parseNewTotalAmount();

        // Check if cashAmount is greater than or equal to newTotalAmount
        if (cashAmount >= newTotalAmount) {
            // Load the CashierConfirmationFXML file invisibly
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CashierConfirmationFXML.fxml"));
                Parent root = loader.load();
                CashierConfirmationFXMLController controller = loader.getController();
                controller.setupTableView(); // Make sure to call the setupTableView method

                // Create a new stage for the CashierConfirmationFXML
                Stage cashierConfirm = new Stage();
                cashierConfirm.initStyle(StageStyle.TRANSPARENT);
                cashierConfirm.setResizable(false);

                // Set the scene fill to transparent
                Scene cashierConfirmationScene = new Scene(root);
                cashierConfirmationScene.setFill(Color.TRANSPARENT);

                // Set the scene to the stage
                cashierConfirm.setScene(cashierConfirmationScene);

                // Hide the stage
                cashierConfirm.hide();

                String handlerName = getEmployeeName();
                String dateTimeString = dateTime.getText().substring(1);
                int customerID = existingCashierController.getCurrentCustomerID();
                String orderType = ordertypeTxtField.getText();

                double subtotal = Double.parseDouble(subTotal.getText().substring(1));
                String discountText = appldDscLbl.getText().substring(1);
                discountText = discountText.replaceAll("[^\\d.]", "");

                double discountApplied = Double.parseDouble(discountText);
                double totalAmount = Double.parseDouble(customTotalLabel.getText().substring(1));
                double changeAmount = Double.parseDouble(changeTxtLbl.getText().substring(1));

                // Use the controller passed as a parameter
                controller.setOrderDetails(handlerName, dateTimeString, customerID, orderType, subtotal, discountApplied, totalAmount, cashAmount, changeAmount);

                // Trigger the print job only if the cash amount is sufficient
                triggerPrintJob(root, controller);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions accordingly
            }
        } else {
            // Display an alert or take other actions if cash amount is insufficient
            showInsufficientCashAlert();
        }
    }

    private void showInvalidCashAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid cash amount.");

        alert.showAndWait();
    }

    private void showInsufficientCashAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Insufficient Cash");
        alert.setHeaderText(null);
        alert.setContentText("Insufficient cash amount!");

        alert.showAndWait();
    }

    private void triggerPrintJob(Parent root, CashierConfirmationFXMLController controller) {
        // Create PrinterJob
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            // Set the content for printing
            boolean success = printerJob.printPage(root);
            if (success) {
                CashierFXMLController cashierController = ControllerManager.getCashierController();

                if (existingCashierController == null && cashierController != null) {
                    existingCashierController = cashierController;
                }

                int customerID = existingCashierController.getCurrentCustomerID();
                String orderType = ordertypeTxtField.getText();
                double totalAmount = Double.parseDouble(itmTotalTxtLbl.getText().substring(1));
                double cashAmount = Double.parseDouble(cashTxtLbl.getText());
                double changeAmount = Double.parseDouble(changeTxtLbl.getText().substring(1));

                // Insert into the "invoice" table
                insertInvoiceToDatabase(customerID, orderType, totalAmount, cashAmount, changeAmount);
                Platform.runLater(() -> {
                    // Update UI or perform other actions as needed
                    cashierController.incrementCurrentCustomerID();
                    cashierController.menuGetMilkteaAndFrappe();
                    cashierController.setupTableView();
                    calculateCustomTotal();

                    Stage settlePaymentStage = (Stage) CloseButton.getScene().getWindow();

                    settlePaymentStage.close();
                    existingCashierController.getMyPane().setVisible(false);

                    printerJob.endJob();
                });
            } else {
                showPrintErrorAlert();
            }
        } else {
            showPrintErrorAlert();
        }
    }
    

    private void showPrintErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Print Error");
        alert.setHeaderText(null);
        alert.setContentText("There was an error while trying to print.");

        alert.showAndWait();
    }

    @FXML
    void discEnterButton(ActionEvent event) {
        String discountCode = discCodeTxtLbl.getText();

        if (discountCode != null && !discountCode.isEmpty()) {
            // Check if the discount is still valid
            if (isDiscountValid(discountCode)) {
                double discountPercent = getDiscountValueFromDatabase(discountCode);

                if (discountPercent != -1) {
                    double totalAmount = Double.parseDouble(itmTotalTxtLbl.getText().substring(1)); // Remove the ₱ sign
                    double discountAmount = (discountPercent / 100) * totalAmount;

                    updateDiscountUsage(discountCode);

                    int remainingUsage = getRemainingUsage(discountCode);

                    if (remainingUsage == 0) {

                        discTxtLbl.setText("Code Invalid");
                    } else {
                        if (discountPercent == 0) {
                            discTxtLbl.setText("No discount applied.");
                            appldDscLbl.setText("-₱0.00");
                            appldDscTxtLbl.setText("-₱0.00"); // Apply the same logic to appldDscTxtLbl
                            newTotalTxtLbl.setText(itmTotalTxtLbl.getText());
                        } else {
                            // Display the discount value
                            discTxtLbl.setText(String.format("%.2f%%", discountPercent));

                            // Display the discounted amount in newTotalTxtLbl with a minus sign
                            double discountedTotal = totalAmount - discountAmount;
                            newTotalTxtLbl.setText(String.format("₱%.2f", discountedTotal));

                            itmTotalTxtLbl.setText(String.format("₱%.2f", totalAmount));
                            appldDscLbl.setText(String.format("-₱%.2f", discountAmount));
                            appldDscTxtLbl.setText(String.format("-₱%.2f", discountAmount)); // Apply the same logic to appldDscTxtLbl
                        }

                        // Update the custom total label
                        updateCustomTotalLabel();
                    }
                } else {
                    if (discountCode.equals("0")) {
                        appldDscLbl.setText("-₱0.00");
                        appldDscTxtLbl.setText("-₱0.00"); // Apply the same logic to appldDscTxtLbl
                        newTotalTxtLbl.setText(itmTotalTxtLbl.getText());
                        // Update the custom total label for zero discount
                        updateCustomTotalLabel();
                    } else {
                        discTxtLbl.setText("Invalid!");
                    }
                }
            } else {
                // Handle the case when the discount is not valid
                discTxtLbl.setText("Discount expired or invalid!");
            }
        } else {
            discTxtLbl.setText("No Discount");
            appldDscLbl.setText(appldDscLbl.getText());
            appldDscTxtLbl.setText("₱0.00");
            newTotalTxtLbl.setText(itmTotalTxtLbl.getText());
            // Update the custom total label
            updateCustomTotalLabel();
        }
    }

    private boolean isDiscountValid(String discountCode) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "SELECT Date_valid FROM discount WHERE disc_code = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, discountCode);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            Date validDate = rs.getDate("Date_valid");

                            // Check if the current date is after the valid date
                            LocalDate currentDate = LocalDate.now();
                            LocalDate discountValidDate = validDate.toLocalDate();

                            return currentDate.isBefore(discountValidDate) || currentDate.isEqual(discountValidDate);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if there is an error or the discount code is not found
    }

    private void updateCustomTotalLabel() {
        double newTotalAmount = parseNewTotalAmount();
        customTotalLabel.setText(String.format("₱%.2f", newTotalAmount));
    }

    private double parseNewTotalAmount() {
        String newTotalText = newTotalTxtLbl.getText();
        return newTotalText.isEmpty() ? 0.0 : Double.parseDouble(newTotalText.substring(1));
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

                // Parse the new total amount only if it's not empty and properly initialized
                double newTotalAmount = parseNewTotalAmount();

                // Calculate the change
                double change = cashAmount - newTotalAmount;

                // Display the change in the changeTxtLbl with a minus sign
                changeTxtLbl.setText(String.format("₱%.2f", change));

                // Display the change in the changeLbl with a minus sign
                changeLbl.setText(String.format("₱%.2f", change));

                // Update the class-level variable with the calculated change
                changeValue = change;

                cashInput.setText(String.format("₱%.2f", cashAmount));

            } catch (NumberFormatException e) {
                // Handle the case when the user enters non-numeric or invalid input for cash
                changeTxtLbl.setText("Invalid Cash Amount");
            }
        } else {
            // Handle the case when the cashTxtLbl is empty
            changeTxtLbl.setText("Enter Cash Amount");
        }
    }


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
     * Initializes the controller class.
     *
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TxtUtils.restrictLetter(cashTxtLbl);
        TxtUtils.limitCharacters(cashTxtLbl, 4);
        TxtUtils.limitCharacters(discCodeTxtLbl, 12);

        try {
            setupTableView();
        } catch (SQLException ex) {
            Logger.getLogger(SettlePaymentFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        changeTxtLbl.setDisable(true);
        calculateCustomTotal();
        calculateTotal();
        appldDscLbl.setDisable(true);
        ordertypeTxtField.setDisable(true);
        itmTotalTxtLbl.setDisable(true);
        newTotalTxtLbl.setDisable(true); // Disable the TextField
        discTxtLbl.setDisable(true); // Disable the TextField
        appldDscTxtLbl.setDisable(true); // Disable the TextField
        calculateTotal();

        updateDateTimeLabel();

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

    private void calculateCustomTotal() {
        CashierFXMLController cashierController = ControllerManager.getCashierController();
        int currentCustomerID = cashierController.getCurrentCustomerID();

        if (currentCustomerID != -1) {
            try (Connection conn = database.getConnection()) {
                if (conn != null) {
                    String[] tables = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

                    double total = 0.0;
                    double discountAmount = 0.0;

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

                    // Retrieve discount amount based on the discount code (replace "YOUR_DISCOUNT_CODE" with the actual discount code)
                    String discountCode = "YOUR_DISCOUNT_CODE";
                    discountAmount = getDiscountAmountFromDatabase(discountCode);

                    // Update the label text with the calculated total and discount amount
                    subTotal.setText(String.format("₱%.2f", total));
                    appldDscLbl.setText(discountAmount >= 0 ? String.format("₱%.2f", discountAmount) : "₱0.00");
                    customTotalLabel.setText(subTotal.getText());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case when there is no customer ID
            subTotal.setText("No Customer ID");
        }
    }

    private double getDiscountAmountFromDatabase(String discountCode) {
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

    private void setupTableView() throws SQLException {
        receiptProduct.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getItemName()));
        receiptPrice.setCellValueFactory(f -> new SimpleDoubleProperty(f.getValue().getItemPrice()).asObject());
        receiptQuantity.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getItemQuantity()).asObject());

        // Bind the TableView to the combined ObservableList
        receiptTV.setItems(fetchOrderDetails());
    }

  
    private void updateDiscountUsage(String discountCode) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String updateSql = "UPDATE discount SET limit_usage = limit_usage - 1 WHERE disc_code = ? AND limit_usage > 0";

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, discountCode);
                    int affectedRows = updateStmt.executeUpdate();
                    System.out.println("Rows updated: " + affectedRows);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
    }

    private int getRemainingUsage(String discountCode) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String selectSql = "SELECT limit_usage FROM discount WHERE disc_code = ?";

                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setString(1, discountCode);

                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            int remainingUsage = rs.getInt("limit_usage");

                            System.out.println("Remaining Usage: " + remainingUsage);

                            if (remainingUsage <= 0) {
                                discTxtLbl.setText("Code Invalid.");
                            }

                            return remainingUsage;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions
        }
        return 0; // Return 0 if there is an error or the discount code is not found
    }

}
