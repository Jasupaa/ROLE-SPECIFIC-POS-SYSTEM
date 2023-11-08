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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;


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
    private Button getMenu1;
    
    @FXML
    private Button getMenu3;
    
    @FXML
    private Button getMenu5;

    @FXML
    private Stage stage;

    @FXML
    private GridPane menuGrid;
    
    @FXML
    private Label customerID;

    @FXML
    private Button getMenu2;
    
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
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList.add(menu);

        menu = new menu2();
        menu.setName("Quadraple Triple");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList.add(menu);

        menu = new menu2();
        menu.setName("Fruso");
        menu.setImgSrc("/img/ClassicMT.jpg");
        menuList.add(menu);
        
        menu = new menu2();
        menu.setName("Yakult Mix");
        menu.setImgSrc("/img/ClassicMT.jpg");
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
    
        
    public ObservableList<ItemData> menuGetMilktea() {
    ObservableList<ItemData> listData = FXCollections.observableArrayList();

    String sql = "SELECT size, item_name, final_price, quantity FROM milk_tea";

    try (Connection connect = database.getConnection();
         PreparedStatement prepare = connect.prepareStatement(sql);
         ResultSet result = prepare.executeQuery()) {

        while (result.next()) {
            String itemName = result.getString("size") + " " + result.getString("item_name");
            double itemPrice = result.getDouble("final_price");
            int itemQuantity = result.getInt("quantity");

            ItemData item = new ItemData(itemName, itemPrice, itemQuantity);
            listData.add(item);

        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return listData;
    
}
    private void setupMenusAndRefreshMenuGrid() {
    menus = getMenu1();
    refreshMenuGrid();
}
    
    private ObservableList<ItemData> menuMilkteaListData;
    
    private void setupTableView() {
    menuMilkteaListData = menuGetMilktea();
    
    columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    columnItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    columnItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));


    // Bind the TableView to the ObservableList
    receiptTable.setItems(menuMilkteaListData);
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
        menuGetMilktea();
        setupTableView();
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
}

