package MainAppFrame;

import Login.ControllerInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.sql.SQLException;
import java.sql.ResultSet;


import other.ItemData;
import other.menu1;

public class CashierFXMLController implements Initializable, ControllerInterface {

    double xOffset, yOffset;

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
    private Stage stage;

    @FXML
    private GridPane menuGrid;

    @FXML
    private Button getMenu2;

    private List<menu1> menus;

    @FXML
    private void getMenu1(ActionEvent event) {
        menus = getMenu1();
        refreshMenuGrid();
    }

    @FXML
    private void getMenu2(ActionEvent event) {
        menus = getMenu2();
        refreshMenuGrid();
    }

    private List<menu1> getMenu1() {
        List<menu1> ls = new ArrayList<>();

        menu1 menu = new menu1();
        menu.setName("Classic Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Taro Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Almond Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Okinawa Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        return ls;
    }

    private List<menu1> getMenu2() {
        List<menu1> ls = new ArrayList<>();

        menu1 menu = new menu1();
        menu.setName("Masarap na exit button");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Taro na kanin");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        menu = new menu1();
        menu.setName("Almond kanin");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);

        return ls;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menus = getMenu1();
        refreshMenuGrid();

        CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });
        
        // Set up the columns in the TableView
    columnItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
    columnItemPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getItemPrice()).asObject());
    columnItemQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getItemQuantity()).asObject());
    
    // Create an ObservableList to store your data
    ObservableList<ItemData> data = FXCollections.observableArrayList();
    receiptTable.setItems(data);
    
    // Fetch data from the database and populate the ObservableList
    loadDataFromDatabase(data);
    
    }
    
    private void loadDataFromDatabase(ObservableList<ItemData> data) {
        try (Connection conn = database.getConnection()) {
        if (conn == null) {
            System.err.println("Database connection is null.");
            return;
        }

        String query = "SELECT size, item_name, final_price, quantity FROM milk_tea";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemName = rs.getString("size") + " " + rs.getString("item_name");
                double itemPrice = rs.getDouble("final_price");
                int itemQuantity = rs.getInt("quantity");
                ItemData item = new ItemData(itemName, itemPrice, itemQuantity);
                data.add(item);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void takeOrderButtonClicked(ActionEvent event) {
        incrementCustomerId(); // Increment the customer_id
        clearTableData(); // Clear the data in the table
}

    private void incrementCustomerId() {
        try (Connection conn = database.getConnection()) {
        if (conn == null) {
            System.err.println("Database connection is null.");
            return;
        }

        // Query to find the maximum current customer_id
        String maxIdQuery = "SELECT MAX(customer_id) FROM milk_tea";
        int currentMaxId = 0;

        try (PreparedStatement maxIdStmt = conn.prepareStatement(maxIdQuery);
             ResultSet maxIdResultSet = maxIdStmt.executeQuery()) {
            if (maxIdResultSet.next()) {
                currentMaxId = maxIdResultSet.getInt(1);
            }
        }

        // Increment the customer_id
        int newCustomerId = currentMaxId + 1;

        String insertQuery = "INSERT INTO milk_tea (customer_id) VALUES (?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setInt(1, newCustomerId);
            insertStmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


// Method to clear data in the table
    private void clearTableData() {
        receiptTable.getItems().clear();
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
}

