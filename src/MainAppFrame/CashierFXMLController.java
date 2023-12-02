package MainAppFrame;

import ClassFiles.FrappeItemData;
import Login.ControllerInterface;
import Login.LoginTest;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.scene.layout.AnchorPane;
import java.util.Set;
import java.util.HashSet;

import ClassFiles.ItemData;
import ClassFiles.MilkteaItemData;
import Databases.CRUDDatabase;
import MenuController.FrappeController;
import MenuController.MenuController;
import com.mysql.cj.jdbc.Blob;

public class CashierFXMLController implements Initializable, ControllerInterface {

    double xOffset, yOffset;

    @FXML
    private Pane blurPane;

    @FXML
    private Label dateLbl;

    @FXML
    private Label timeLbl;

    @FXML
    private Label customerLabel;

    @FXML
    private TableColumn<ItemData, String> columnItemName;

    @FXML
    private TableColumn<ItemData, Double> columnItemPrice;

    @FXML
    private TableColumn<ItemData, Integer> columnItemQuantity;

    @FXML
    private TableView<ItemData> receiptTable;

    @FXML
    private ImageView CloseButton;

    @FXML
    private Button takeOrderButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private Button deleteAllitemsButton;

    @FXML
    private Button getMenu1;

    @FXML
    private Button getMenu2;

    @FXML
    private Button getMenu3;

    @FXML
    private Button getMenu4;

    @FXML
    private Button getMenu5;

    @FXML
    private Button getMenu6;

    @FXML
    private Button getMenu7;

    @FXML
    private Stage stage;

    @FXML
    private GridPane menuGrid;

    @FXML
    private Button Logout;

    @FXML
    private Button takeOutOrderButton;

    @FXML
    private Label empName;

    private Stage settlePaymentStage;

    private volatile boolean stop = false;
    private LocalDate currentDate = LocalDate.now();
    
   
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        
        empName.setText(employeeName);
    }

    private ObservableList<MilkteaItemData> milkteaListData = FXCollections.observableArrayList();

    private ObservableList<FrappeItemData> frappeListData = FXCollections.observableArrayList();

    public void setTableViewAndList(TableView<ItemData> tableView, ObservableList<ItemData> dataList) {
        this.receiptTable = tableView;
        this.menuMilkteaAndFrappeListData = dataList;
    }

   
    private String employeeName;
    private int employeeId;

    public void setEmployee(String employeeName, int employeeId) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;

        empName.setText(employeeName);
    }

    private int customerID = 0;
    private int currentCustomerID = 0; // Initialize currentCustomerID

    private boolean initialDisplayDone = false;

    public void updateCustomerID() {
        // List of table names
        List<String> tableNames = Arrays.asList("milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras");

        // List to store maximum customer IDs
        List<Integer> maxCustomerIDs = new ArrayList<>();

        // Fetch maximum customer ID from each table
        for (String tableName : tableNames) {
            int maxCustomerID = getMaxCustomerID(tableName);
            maxCustomerIDs.add(maxCustomerID);
        }

        // Find the maximum customer ID across all tables
        int maxOverallCustomerID = maxCustomerIDs.stream().max(Integer::compare).orElse(0);

        // If the maximum customer ID is 0, set customerID to 0 and increment normally
        if (maxOverallCustomerID == 0) {
            customerID = 0;
        } else {
            // Increment the maximum customer ID to get the current customer ID
            customerID = maxOverallCustomerID + 1;
        }

        // Update the customer label
        customerLabel.setText(Integer.toString(customerID));

        // Update the currentCustomerID
        currentCustomerID = customerID;
        initialDisplayDone = true;
    }

    // Method to fetch the maximum customer ID from the UNION of all tables
    private int getMaxCustomerID(String tableName) {
        int maxCustomerID = 0;

        // Use your database connection and SQL query to fetch the maximum customer ID
        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT MAX(customer_id) FROM " + tableName); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                maxCustomerID = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }

        return maxCustomerID;
    }

    public void incrementCurrentCustomerID() {
        // Increment the customer ID
        customerID++;

        // Update the customer label
        customerLabel.setText(Integer.toString(customerID));

        // Update the currentCustomerID
        currentCustomerID = customerID;
        initialDisplayDone = true;
    }

    public int getCurrentCustomerID() {
        return currentCustomerID;
    }

    public Pane getMyPane() {
        return blurPane;
    }

    @FXML
    void takeOutOrder(ActionEvent event) {
        try {
            // Load the SettlePaymentFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SettlePaymentFXML.fxml"));
            Parent root = loader.load();

            // Create a new stage for the SettlePaymentFXML
            settlePaymentStage = new Stage();

            // Set stage properties to make it transparent and non-resizable
            settlePaymentStage.initStyle(StageStyle.TRANSPARENT);
            settlePaymentStage.initStyle(StageStyle.UNDECORATED); // Removes the title bar
            settlePaymentStage.setResizable(false);

            // Set the scene fill to transparent
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Set the scene to the stage
            settlePaymentStage.setScene(scene);

            // Get the controller for SettlePaymentFXML
            SettlePaymentFXMLController settlePaymentController = loader.getController();
            settlePaymentController.setExistingCashierController(this, employeeName, employeeId);

            // Set the order type
            settlePaymentController.setOrderType("Take Out");

            settlePaymentStage.show();
            blurPane.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }
    }

    public Stage getSettlePaymentStage() {
        return settlePaymentStage;
    }

    private void DateLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(DateFormat);
        dateLbl.setText(formattedDate);
    }

    private void Timenow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    timeLbl.setText(timenow); // This is the label
                });
            }
        });

        thread.start();
    }

    @FXML
    private void getMenu1(ActionEvent event) throws SQLException {
        milkteaListData.clear();
        milkteaListData.addAll(menuGetData());

        refreshMenuGrid();
    }

    @FXML
    private void getMenu3(ActionEvent event) throws SQLException {
        frappeListData.clear();
        frappeListData.addAll(menuGetDataForFrappe());

        refreshFrappeGrid();
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

    public ObservableList<MilkteaItemData> menuGetData() {

        String sql = "SELECT * FROM milktea_items";

        ObservableList<MilkteaItemData> listData = FXCollections.observableArrayList();
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = CRUDDatabase.getConnection();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                // Replace these column names with your actual column names from the "milktea_items" table
                String itemName = result.getString("item_name");
                String addons = result.getString("addons");
                Integer smallPrice = result.getInt("small_price");
                Integer mediumPrice = result.getInt("medium_price");
                Integer largePrice = result.getInt("large_price");
                Blob image = (Blob) result.getBlob("image");
                Integer itemID = result.getInt("item_ID");
                // Create a MilkteaItemData object and add it to the list
                MilkteaItemData milkteaItemData = new MilkteaItemData(itemName, addons, smallPrice, mediumPrice, largePrice, image, itemID);
                listData.add(milkteaItemData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (result, prepare, connect) if needed
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listData;
    }

    public ObservableList<FrappeItemData> menuGetDataForFrappe() {

        String sql = "SELECT * FROM frappe_items";

        ObservableList<FrappeItemData> listData = FXCollections.observableArrayList();
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = CRUDDatabase.getConnection();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                // Replace these column names with your actual column names from the "milktea_items" table
                String itemName = result.getString("item_name");
                Integer smallPrice = result.getInt("small_price");
                Integer mediumPrice = result.getInt("medium_price");
                Integer largePrice = result.getInt("large_price");
                Blob image = (Blob) result.getBlob("image");

                // Create a MilkteaItemData object and add it to the list
                FrappeItemData frappeItemData = new FrappeItemData(itemName, smallPrice, mediumPrice, largePrice, image);
                listData.add(frappeItemData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (result, prepare, connect) if needed
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listData;
    }

    public ObservableList<ItemData> menuGetMilkteaAndFrappe() {
        ObservableList<ItemData> listData = FXCollections.observableArrayList();

        String customerIDText = customerLabel.getText();

        if (customerIDText.matches("\\d+")) {
            customerID = Integer.parseInt(customerIDText);
        } else {
            // Handle the case where customerIDText is not a valid number
            // You can display an error message or set a default value
            // Example: customerID = -1; // Default value for an invalid ID
        }

        // For milk_tea
        String combinedSql = "SELECT order_id, size, item_name, final_price, quantity, date_time FROM milk_tea WHERE customer_id = ? "
                + "UNION "
                + "SELECT order_id, size, item_name, final_price, quantity, date_time FROM fruit_drink WHERE customer_id = ? "
                + "UNION "
                + "SELECT order_id, size, item_name, final_price, quantity, date_time FROM frappe WHERE customer_id = ? "
                + "ORDER BY date_time Asc";

        try (Connection connect = database.getConnection(); PreparedStatement combinedPrepare = connect.prepareStatement(combinedSql)) {

            combinedPrepare.setInt(1, customerID);
            combinedPrepare.setInt(2, customerID);
            combinedPrepare.setInt(3, customerID);

            ResultSet combinedResult = combinedPrepare.executeQuery();
            while (combinedResult.next()) {
                int orderID = combinedResult.getInt("order_id");
                String itemName = combinedResult.getString("size") + " " + combinedResult.getString("item_name");
                double itemPrice = combinedResult.getDouble("final_price");
                int itemQuantity = combinedResult.getInt("quantity");

                ItemData item = new ItemData(orderID, itemName, itemPrice, itemQuantity);
                listData.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<ItemData> menuMilkteaAndFrappeListData;

    public void setupTableView() {
        menuMilkteaAndFrappeListData = menuGetMilkteaAndFrappe();

        columnItemName.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getItemName()));
        columnItemPrice.setCellValueFactory(f -> new SimpleDoubleProperty(f.getValue().getItemPrice()).asObject());
        columnItemQuantity.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getItemQuantity()).asObject());

        // Bind the TableView to the combined ObservableList
        receiptTable.setItems(menuMilkteaAndFrappeListData);
    }

    public void onDeleteItemButtonClicked(ActionEvent event) throws SQLException {
        ItemData selectedItem = receiptTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Prompt user for confirmation before deleting the row
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Item");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteItem(selectedItem); // Delete the selected item from the database
                menuMilkteaAndFrappeListData.remove(selectedItem); // Remove the item from the TableView
            }
        }
    }

    private void deleteItem(ItemData item) throws SQLException {
        System.out.println("Deleting item: " + item.getItemName());
        String dltMTsql = "DELETE FROM milk_tea WHERE order_id = ? AND CONCAT(size, ' ', item_name) = ? AND final_price = ? AND quantity = ?";
        String dltfrappesql = "DELETE FROM frappe WHERE order_id = ? AND CONCAT(size, ' ', item_name) = ? AND final_price = ? AND quantity = ?";
        String dltFDsql = "DELETE FROM fruit_drink WHERE order_id = ? AND CONCAT(size, ' ', item_name) = ? AND final_price = ? AND quantity = ?";
        try (Connection connect = database.getConnection(); PreparedStatement prepare = connect.prepareStatement(dltMTsql); PreparedStatement frapprepare = connect.prepareStatement(dltfrappesql); PreparedStatement FDprepare = connect.prepareStatement(dltFDsql)) {

            prepare.setInt(1, item.getorderID());
            prepare.setString(2, item.getItemName());
            prepare.setDouble(3, item.getItemPrice());
            prepare.setInt(4, item.getItemQuantity());

            frapprepare.setInt(1, item.getorderID());
            frapprepare.setString(2, item.getItemName());
            frapprepare.setDouble(3, item.getItemPrice());
            frapprepare.setInt(4, item.getItemQuantity());

            FDprepare.setInt(1, item.getorderID());
            FDprepare.setString(2, item.getItemName());
            FDprepare.setDouble(3, item.getItemPrice());
            FDprepare.setInt(4, item.getItemQuantity());

            int rowsAffected = prepare.executeUpdate();
            int rowsAffectedFrappe = frapprepare.executeUpdate();
            int rowsAffectedFD = FDprepare.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Rows affected (frappe): " + rowsAffectedFrappe);
            System.out.println("Rows affected (fruit_drink): " + rowsAffectedFD);

            System.out.println("SQL Parameters: " + item.getItemName() + ", "
                    + item.getItemPrice() + ", " + item.getItemQuantity()); // Add this line
        }
    }

    public void onDeleteAllitemsButtonClicked(ActionEvent event) throws SQLException {
        // Prompt user for confirmation before deleting all items
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete All Items");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete all items?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Set<Integer> orderIDs = new HashSet<>();

            // Collect unique order IDs from the list
            for (ItemData item : menuMilkteaAndFrappeListData) {
                orderIDs.add(item.getorderID());
            }

            // Iterate through unique order IDs and delete items
            for (int orderID : orderIDs) {
                ItemData dummyItem = new ItemData(orderID, "", 0.0, 0);
                deleteAllitems(dummyItem);
            }

            menuMilkteaAndFrappeListData.clear(); // Clear all items from the TableView
        }
    }

    private void deleteAllitems(ItemData item) throws SQLException {

        String deleteAllMTsql = "DELETE FROM milk_tea WHERE order_id = ?";
        String deleteAllFrappeSql = "DELETE FROM frappe WHERE order_id = ?";
        String deleteAllFDSql = "DELETE FROM fruit_drink WHERE order_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement deleteAllMTPrepare = connect.prepareStatement(deleteAllMTsql); PreparedStatement deleteAllFrappePrepare = connect.prepareStatement(deleteAllFrappeSql); PreparedStatement deleteAllFDPrepare = connect.prepareStatement(deleteAllFDSql)) {

            int orderID = item.getorderID();

            deleteAllMTPrepare.setInt(1, orderID);
            deleteAllFrappePrepare.setInt(1, orderID);
            deleteAllFDPrepare.setInt(1, orderID);

            int rowsAffectedMT = deleteAllMTPrepare.executeUpdate();
            int rowsAffectedFrappe = deleteAllFrappePrepare.executeUpdate();
            int rowsAffectedFD = deleteAllFDPrepare.executeUpdate();

            System.out.println("Rows affected (delete all milk tea): " + rowsAffectedMT);
            System.out.println("Rows affected (delete all frappe): " + rowsAffectedFrappe);
            System.out.println("Rows affected (delete all fruit drink): " + rowsAffectedFD);
        }
    }

    private void refreshMenuGrid() throws SQLException {
        
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (MilkteaItemData milkteaItemData : milkteaListData) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/MenuFXML/Milktea.fxml"));
                AnchorPane pane = loader.load();

                // Access the controller and set the data
                MenuController menuController = loader.getController();
                menuController.setMilkteaItemData(milkteaItemData);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void refreshFrappeGrid() throws SQLException {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (FrappeItemData frappeItemData : frappeListData) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/MenuFXML/Frappe.fxml"));
                AnchorPane pane = loader.load();

                // Access the controller and set the data
                FrappeController frappeController = loader.getController();
                frappeController.setFrappeItemData(frappeItemData);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            refreshMenuGrid();
        } catch (SQLException ex) {
            Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
                    Logger.getLogger(AdminFXMLController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

        columnItemName.setSortable(true);
        columnItemPrice.setSortable(true);
        columnItemQuantity.setSortable(true);

        try {
            updateCustomerID();
            DateLabel();
            Timenow();
            setupTableView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
