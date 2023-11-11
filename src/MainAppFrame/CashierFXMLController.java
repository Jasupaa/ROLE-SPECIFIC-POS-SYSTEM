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
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

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
    private TableColumn<ItemData, Integer> columnOrderID;

    @FXML
    private TableView<ItemData> receiptTable;

    @FXML
    private ImageView CloseButton;

    @FXML
    private Button takeOrderButton;

    @FXML
    private Button deleteItemButton;

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
    private Label customerID;

    @FXML
    private Button Logout;

    private List<menu1> menus;
    private List<menu2> menuss;
    private List<menu3> menusss;
    private List<menu4> menussss;
    private List<menu5> menusssss;
    private List<menu6> menussssss;
    private List<menu7> menusssssss;

    private volatile boolean stop = false;
    private int currentCustomerID = 0;
    private LocalDate currentDate = LocalDate.now();

    public void setTableViewAndList(TableView<ItemData> tableView, ObservableList<ItemData> dataList) {
        this.receiptTable = tableView;
        this.menuMilkteaAndFrappeListData = dataList;
    }

    private int getCurrentCustomerID() {
        // Check if it's a new day, then reset the customer ID
        if (!LocalDate.now().isEqual(currentDate)) {
            currentDate = LocalDate.now();
            currentCustomerID = 1; // Reset to 1 for a new day
        }
        return currentCustomerID;
    }

    private void updateCustomerLabel() {
        int highestCustomerID = 0;

        // List of databases
        String[] databases = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

        // Iterate through each database
        for (String database1 : databases) {
            String sql = "SELECT MAX(customer_id) AS max_customer_id FROM " + database1;

            try (Connection connect = database.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {

                if (result.next()) {
                    int maxCustomerID = result.getInt("max_customer_id");
                    highestCustomerID = Math.max(highestCustomerID, maxCustomerID);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Update the customerLabel with the highest customer ID
        customerLabel.setText(String.valueOf(highestCustomerID));

        // Set the current customer ID to the highest customer ID
        currentCustomerID = highestCustomerID;
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

        // For milk_tea
        String milkTeaSql = "SELECT order_id, size, item_name, final_price, quantity FROM milk_tea";

        // For frappe
        String frappeSql = "SELECT order_id, size, item_name, final_price, quantity FROM frappe";

        // For Fruit Drink
        String FruitSql = "SELECT order_id, size, item_name, final_price, quantity FROM fruit_drink";
        
        try (Connection connect = database.getConnection(); 
                PreparedStatement milkTeaPrepare = connect.prepareStatement(milkTeaSql); 
                PreparedStatement frappePrepare = connect.prepareStatement(frappeSql);
                PreparedStatement fruitPrepare = connect.prepareStatement(FruitSql)) {

            ResultSet milkTeaResult = milkTeaPrepare.executeQuery();
            while (milkTeaResult.next()) {
                int orderID = milkTeaResult.getInt("order_id");
                String itemName = milkTeaResult.getString("size") + " " + milkTeaResult.getString("item_name");
                double itemPrice = milkTeaResult.getDouble("final_price");
                int itemQuantity = milkTeaResult.getInt("quantity");

                ItemData item = new ItemData(orderID, itemName, itemPrice, itemQuantity);
                listData.add(item);
            }

            ResultSet frappeResult = frappePrepare.executeQuery();
            while (frappeResult.next()) {
                int orderID = frappeResult.getInt("order_id");
                String itemName = frappeResult.getString("size") + " " + frappeResult.getString("item_name");
                double itemPrice = frappeResult.getDouble("final_price");
                int itemQuantity = frappeResult.getInt("quantity");

                ItemData item = new ItemData(orderID, itemName, itemPrice, itemQuantity);
                listData.add(item);
            }
            
            ResultSet fruitResult = fruitPrepare.executeQuery();
            while (frappeResult.next()) {
                int orderID = fruitResult.getInt("order_id");
                String itemName = fruitResult.getString("size") + " " + fruitResult.getString("item_name");
                double itemPrice = fruitResult.getDouble("final_price");
                int itemQuantity = fruitResult.getInt("quantity");

                ItemData item = new ItemData(orderID, itemName, itemPrice, itemQuantity);
                listData.add(item);
            }
            

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
        
        columnOrderID.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getorderID()).asObject());
        columnItemName.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getItemName()));
        columnItemPrice.setCellValueFactory(f -> new SimpleDoubleProperty(f.getValue().getItemPrice()).asObject());
        columnItemQuantity.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getItemQuantity()).asObject());

        // Bind the TableView to the combined ObservableList
        receiptTable.setItems(menuMilkteaAndFrappeListData);
    }

    @FXML
    private void onDeleteItemButtonClicked(ActionEvent event) throws SQLException {
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

        String sql = "DELETE FROM milk_tea WHERE order_id = ? AND CONCAT(size, ' ', item_name) = ? AND final_price = ? AND quantity = ?";
        try (Connection connect = database.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setInt(1, item.getorderID());
            prepare.setString(2, item.getItemName());
            prepare.setDouble(3, item.getItemPrice());
            prepare.setInt(4, item.getItemQuantity());
            int rowsAffected = prepare.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            System.out.println("SQL Parameters: " + item.getItemName() + ", "
                    + item.getItemPrice() + ", " + item.getItemQuantity()); // Add this line
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        try {
            setupMenusAndRefreshMenuGrid();
            DateLabel();
            Timenow();
            setupTableView();

            // Update the customerLabel with the highest customer ID across databases
            updateCustomerLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
