package MainAppFrame;

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
import java.util.Set;
import java.util.HashSet;

import other.ItemData;
import other.menu1;
import other.menu2;
import other.menu3;
import other.menu4;
import other.menu5;
import other.menu6;
import other.menu7;

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

    private List<menu1> menus;
    private List<menu2> menuss;
    private List<menu3> menusss;
    private List<menu4> menussss;
    private List<menu5> menusssss;
    private List<menu6> menussssss;
    private List<menu7> menusssssss;

    private volatile boolean stop = false;
    private LocalDate currentDate = LocalDate.now();

    
    public void setTableViewAndList(TableView<ItemData> tableView, ObservableList<ItemData> dataList) {
        this.receiptTable = tableView;
        this.menuMilkteaAndFrappeListData = dataList;
    }

    
    
     private String employeeName;
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        
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

    /*
    @FXML
    void takeOrder(ActionEvent event) {
        incrementCustomerID();
        menuGetMilkteaAndFrappe();
        setupTableView();

        System.out.println(currentCustomerID);
    } */
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
    private void getMenu1(ActionEvent event) {
        menus = getMenu1();
        refreshMenuGrid();
    }

    @FXML
    private void getMenu2(ActionEvent event) {
        menuss = getMenu2();
        refreshFruitDrinkGrid();
    }

    @FXML
    private void getMenu3(ActionEvent event) {
        menusss = getMenu3();
        refreshFrappeGrid();
    }

    @FXML
    private void getMenu4(ActionEvent event) {
        menussss = getMenu4();
        refreshCoffeeGrid();
    }

    @FXML
    private void getMenu5(ActionEvent event) {
        menusssss = getMenu5();
        refreshRiceMealGrid();
    }

    @FXML
    private void getMenu6(ActionEvent event) {
        menussssss = getMenu6();
        refreshSnacksGrid();
    }

    @FXML
    private void getMenu7(ActionEvent event) {
        menusssssss = getMenu7();
        refreshOthersGrid();
    }

    private List<menu1> getMenu1() {
        List<menu1> ls = new ArrayList<>();

        menu1 menu = new menu1();
        menu.setName("Classic Milktea");
        menu.setImgSrc("/img/ClassicMt.png");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Taro Milktea");
        menu.setImgSrc("/img/TaroMt.png");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Almond Milktea");
        menu.setImgSrc("/img/AlmondMT.png");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Strawberry Milktea");
        menu.setImgSrc("/img/StrawberryMT.png");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Matcha Milktea");
        menu.setImgSrc("/img/MatchaMT.png");
        ls.add(menu);

        return ls;
    }

    private List<menu2> getMenu2() {
        List<menu2> menuList = new ArrayList<>();

        menu2 menu = new menu2();
        menu.setName("Fruit Tea");
        menu.setImgSrc("/img/FruitTea.png");
        menuList.add(menu);

        menu = new menu2();
        menu.setName("Quadraple Triple");
        menu.setImgSrc("/img/QuadrapleTriple.png");
        menuList.add(menu);

        menu = new menu2();
        menu.setName("Fruso");
        menu.setImgSrc("/img/Fruso.png");
        menuList.add(menu);

        menu = new menu2();
        menu.setName("Yakult Mix");
        menu.setImgSrc("/img/Fruso.png");
        menuList.add(menu);

        return menuList;
    }

    private List<menu3> getMenu3() {
        List<menu3> menuList3 = new ArrayList<>();

        menu3 menu = new menu3();
        menu.setName("HazelNut Almonds");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList3.add(menu);

        menu = new menu3();
        menu.setName("Caramel Latte");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList3.add(menu);

        menu = new menu3();
        menu.setName("Coffee Jelly");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList3.add(menu);

        menu = new menu3();
        menu.setName("Oreo");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList3.add(menu);

        menu = new menu3();
        menu.setName("Nutella");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList3.add(menu);

        return menuList3;
    }

    private List<menu4> getMenu4() {
        List<menu4> menuList4 = new ArrayList<>();

        menu4 menu = new menu4();
        menu.setName("The Special Coffee");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList4.add(menu);

        menu = new menu4();
        menu.setName("Matcha Latte");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList4.add(menu);

        menu = new menu4();
        menu.setName("Pour Over Coffee");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList4.add(menu);

        return menuList4;
    }

    private List<menu5> getMenu5() {
        List<menu5> menuList5 = new ArrayList<>();

        menu5 menu = new menu5();
        menu.setName("Hotsilog");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList5.add(menu);

        menu = new menu5();
        menu.setName("Chiksilog");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList5.add(menu);

        menu = new menu5();
        menu.setName("Longsilog");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList5.add(menu);

        menu = new menu5();
        menu.setName("Bacsilog");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList5.add(menu);

        menu = new menu5();
        menu.setName("Siomai Rice");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList5.add(menu);

        return menuList5;
    }

    private List<menu6> getMenu6() {
        List<menu6> menuList6 = new ArrayList<>();

        menu6 menu = new menu6();
        menu.setName("Cheesy Burger");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList6.add(menu);

        menu = new menu6();
        menu.setName("Cheesy Bacon Burger");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList6.add(menu);

        menu = new menu6();
        menu.setName("Burger Overload");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList6.add(menu);

        menu = new menu6();
        menu.setName("Beefy Nachos");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList6.add(menu);

        return menuList6;
    }

    private List<menu7> getMenu7() {
        List<menu7> menuList7 = new ArrayList<>();

        menu7 menu = new menu7();
        menu.setName("White Rice");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList7.add(menu);

        menu = new menu7();
        menu.setName("Garlic Rice");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList7.add(menu);

        menu = new menu7();
        menu.setName("Sprite");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList7.add(menu);

        menu = new menu7();
        menu.setName("Coke");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList7.add(menu);

        menu = new menu7();
        menu.setName("Royal");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList7.add(menu);

        menu = new menu7();
        menu.setName("Mountain Dew");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList7.add(menu);

        return menuList7;
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
        String combinedSql = "SELECT order_id, size, item_name, final_price, quantity, date_time FROM milk_tea WHERE customer_id = ? " +
        "UNION " +
        "SELECT order_id, size, item_name, final_price, quantity, date_time FROM fruit_drink WHERE customer_id = ? " +
        "UNION " +
        "SELECT order_id, size, item_name, final_price, quantity, date_time FROM frappe WHERE customer_id = ? " +
        "ORDER BY date_time Asc";


        try (Connection connect = database.getConnection(); PreparedStatement combinedPrepare = connect.prepareStatement(combinedSql)
             ) {

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
        listData.add(item);}
           

           

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }


    private void setupMenusAndRefreshMenuGrid() {
        menus = getMenu1();
        menuMilkteaAndFrappeListData = menuGetMilkteaAndFrappe();
        refreshMenuGrid();
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
    
// ...

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

    try (Connection connect = database.getConnection();
         PreparedStatement deleteAllMTPrepare = connect.prepareStatement(deleteAllMTsql);
         PreparedStatement deleteAllFrappePrepare = connect.prepareStatement(deleteAllFrappeSql);
         PreparedStatement deleteAllFDPrepare = connect.prepareStatement(deleteAllFDSql)) {

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

    private void refreshMenuGrid() {

        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu1 menu : menus) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("Milktea.fxml"));

                Pane pane = fxmlloader.load();
                MenuController menucontroller = fxmlloader.getController();
                menucontroller.setData(menu);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshFruitDrinkGrid() {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu2 menu2 : menuss) { // Change the variable name to 'menu2' for clarity
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("FruitDrink.fxml"));

                Pane pane = fxmlloader.load();
                FruitDrinkController fruitdrinkcontroller = fxmlloader.getController();
                fruitdrinkcontroller.setData(menu2); // Use 'menu2' as the data

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshFrappeGrid() {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu3 menu3 : menusss) { // Change the variable name to 'menu2' for clarity
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("Frappe.fxml"));

                Pane pane = fxmlloader.load();
                FrappeController frappecontroller = fxmlloader.getController();
                frappecontroller.setData(menu3); // Use 'menu2' as the data

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshCoffeeGrid() {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu4 menu4 : menussss) { // Change the variable name to 'menu2' for clarity
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("Coffee.fxml"));

                Pane pane = fxmlloader.load();
                CoffeeController coffeecontroller = fxmlloader.getController();
                coffeecontroller.setData(menu4); // Use 'menu2' as the data

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshRiceMealGrid() {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu5 menu5 : menusssss) { // Change the variable name to 'menu2' for clarity
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("RiceMeal.fxml"));

                Pane pane = fxmlloader.load();
                RiceMealController ricemealcontroller = fxmlloader.getController();
                ricemealcontroller.setData(menu5); // Use 'menu2' as the data

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshSnacksGrid() {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu6 menu6 : menussssss) { // Change the variable name to 'menu2' for clarity
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("Snacks.fxml"));

                Pane pane = fxmlloader.load();
                SnacksController snackscontroller = fxmlloader.getController();
                snackscontroller.setData(menu6); // Use 'menu2' as the data

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshOthersGrid() {
        menuGrid.getChildren().clear();
        int column = 0;
        int row = 1;

        for (menu7 menu7 : menusssssss) { // Change the variable name to 'menu2' for clarity
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("Extras.fxml"));

                Pane pane = fxmlloader.load();
                ExtrasController extrascontroller = fxmlloader.getController();
                extrascontroller.setData(menu7); // Use 'menu2' as the data

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                menuGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                Logger.getLogger(CashierFXMLController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Button lastClickedButton = null;

    /*
    @FXML
    public void SwitchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton == lastClickedButton) {
            // Ignore the click if the same button was clicked twice in a row
            return;
        }

        if (clickedButton == getMenu1) {
            // ... (rest of the code remains the same)
        }

        // Update the last clicked button
        lastClickedButton = clickedButton;
        if (clickedButton == getMenu1) {
            setButtonColor(getMenu1, true);
            setButtonColor(getMenu2, false);
            setButtonColor(getMenu3, false);
            setButtonColor(getMenu4, false);
            setButtonColor(getMenu5, false);
            setButtonColor(getMenu6, false);
            setButtonColor(getMenu7, false);

        } else if (clickedButton == getMenu2) {
            setButtonColor(getMenu2, true);
            setButtonColor(getMenu1, false);
            setButtonColor(getMenu3, false);
            setButtonColor(getMenu4, false);
            setButtonColor(getMenu5, false);
            setButtonColor(getMenu6, false);
            setButtonColor(getMenu7, false);

        } else if (clickedButton == getMenu3) {
            setButtonColor(getMenu3, true);
            setButtonColor(getMenu2, false);
            setButtonColor(getMenu1, false);
            setButtonColor(getMenu4, false);
            setButtonColor(getMenu5, false);
            setButtonColor(getMenu6, false);
            setButtonColor(getMenu7, false);

        } else if (clickedButton == getMenu4) {
            setButtonColor(getMenu4, true);
            setButtonColor(getMenu2, false);
            setButtonColor(getMenu3, false);
            setButtonColor(getMenu1, false);
            setButtonColor(getMenu5, false);
            setButtonColor(getMenu6, false);
            setButtonColor(getMenu7, false);

        } else if (clickedButton == getMenu5) {
            setButtonColor(getMenu5, true);
            setButtonColor(getMenu2, false);
            setButtonColor(getMenu3, false);
            setButtonColor(getMenu4, false);
            setButtonColor(getMenu1, false);
            setButtonColor(getMenu6, false);
            setButtonColor(getMenu7, false);

        } else if (clickedButton == getMenu6) {
            setButtonColor(getMenu6, true);
            setButtonColor(getMenu2, false);
            setButtonColor(getMenu3, false);
            setButtonColor(getMenu4, false);
            setButtonColor(getMenu5, false);
            setButtonColor(getMenu1, false);
            setButtonColor(getMenu7, false);

        } else if (clickedButton == getMenu7) {
            setButtonColor(getMenu7, true);
            setButtonColor(getMenu2, false);
            setButtonColor(getMenu3, false);
            setButtonColor(getMenu4, false);
            setButtonColor(getMenu5, false);
            setButtonColor(getMenu6, false);
            setButtonColor(getMenu1, false);

        }
    }

    private void setButtonColor(Button button, boolean isSelected) {
        if (isSelected) {
            button.getStyleClass().add("selected-button");
        } else {
            button.getStyleClass().remove("selected-button");
        }
    } */
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
            // Update the customerLabel with the highest customer ID across databases
            updateCustomerID();

            // Other initialization code...
            setupMenusAndRefreshMenuGrid();
            DateLabel();
            Timenow();
            setupTableView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
