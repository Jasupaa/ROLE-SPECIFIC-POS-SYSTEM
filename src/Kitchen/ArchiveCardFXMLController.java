/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Kitchen;

import ClassFiles.ArchiveCardData;
import ClassFiles.ArchiveOrderCardData;
import ClassFiles.CustomerData;
import ClassFiles.KitchenCardData;
import ClassFiles.MilkteaItemData;
import ClassFiles.OrderCardData;
import Databases.CRUDDatabase;
import MainAppFrame.KitchenFXMLController;
import MainAppFrame.database;
import MenuController.MenuController;
import com.mysql.cj.jdbc.Blob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Gwyneth Uy
 */
// Import statements...
public class ArchiveCardFXMLController implements Initializable {

    @FXML
    private AnchorPane kitchenCardAP;

    @FXML
    private Label custNoLBL;

    @FXML
    private GridPane orderCardGP;

    @FXML
    private Label cashierLBL;

    @FXML
    private Label datetimeLBL;

    @FXML
    private Button orderCompletedBTN;

    @FXML
    private ComboBox<String> wholeOrderStatusCB;

    private ObservableList<ArchiveOrderCardData> archiveOrderCardData = FXCollections.observableArrayList();

    private KitchenFXMLController kitchenController;

    private KitchenFXMLController kitchencontroller;

    public void setKitchenController1(KitchenFXMLController kitchencontroller) {
        this.kitchencontroller = kitchencontroller;
    }

    public void setArchiveKitchenCardData(ArchiveCardData archiveCardData) throws SQLException {
        // Fetch customer data from the invoice table
        CustomerData customerData = getCustomerDataFromDatabase(archiveCardData.getCustomerID());

        if (customerData != null) {
            // Set data to corresponding components
            custNoLBL.setText(customerData.getCustomerID());

            // Set the retrieved employee name to the label
            cashierLBL.setText(customerData.getCashierHandler());

            // Retrieve orders for the specific customer
            archiveOrderCardData = menuGetData(archiveCardData.getCustomerID());

            // Set data to the grid
            orderArchiveGrid();

            // Fetch and display date time
            String dateTime = getDateTimeFromDatabase(archiveCardData.getCustomerID());
            datetimeLBL.setText(dateTime);
        }
    }

    private String getDateTimeFromDatabase(String customerID) {
        String dateTime = null;

        try (Connection connection = database.getConnection()) {
            String query = "SELECT date_time FROM invoice_archive WHERE customer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, customerID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve date time from the result set
                        dateTime = resultSet.getString("date_time");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    private CustomerData getCustomerDataFromDatabase(String customerID) {
        CustomerData customerData = null;

        try (Connection connection = database.getConnection()) {
            String query = "SELECT emp_name FROM invoice_archive WHERE customer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, customerID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve employee name from the result set
                        String employeeName = resultSet.getString("emp_name");

                        // Create a CustomerData object
                        customerData = new CustomerData(customerID, employeeName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerData;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ObservableList<ArchiveOrderCardData> menuGetData(String customerID) {
        // Fetch milk tea data
        ObservableList<ArchiveOrderCardData> listData = fetchData("SELECT item_name, quantity, size, add_ons, 'None' AS fruit_flavor, 'None' AS sinkers, sugar_level, 'None' AS type, ask_me FROM milk_tea WHERE customer_id = ?", customerID);

        listData.addAll(fetchData("SELECT item_name, quantity, size, 'None' AS add_ons, fruit_flavor, sinkers, 'None' AS sugar_level, 'None' AS type, ask_me FROM fruit_drink WHERE customer_id = ?", customerID));

        listData.addAll(fetchData("SELECT item_name, quantity, size, 'None' AS add_ons, 'None' AS fruit_flavor, 'None' AS sinkers, sugar_level, 'None' AS type, ask_me FROM frappe WHERE customer_id = ?", customerID));

        listData.addAll(fetchData("SELECT item_name, quantity, size, 'None' AS add_ons, 'None' AS fruit_flavor, 'None' AS sinkers, 'None' AS sugar_level, type, ask_me FROM coffee WHERE customer_id = ?", customerID));

        listData.addAll(fetchData("SELECT item_name, quantity, 'None' AS size, 'None' AS add_ons, 'None' AS fruit_flavor, 'None' AS sinkers, 'None' AS sugar_level, 'None' AS type, ask_me FROM rice_meal WHERE customer_id = ?", customerID));

        listData.addAll(fetchData("SELECT item_name, quantity, 'None' AS size, 'None' AS add_ons, 'None' AS fruit_flavor, 'None' AS sinkers, 'None' AS sugar_level, 'None' AS type, ask_me FROM snacks WHERE customer_id = ?", customerID));

        listData.addAll(fetchData("SELECT item_name, quantity, 'None' AS size, 'None' AS add_ons, 'None' AS fruit_flavor, 'None' AS sinkers, 'None' AS sugar_level, 'None' AS type, ask_me FROM extras WHERE customer_id = ?", customerID));

        return listData;
    }

    private ObservableList<ArchiveOrderCardData> fetchData(String sql, String customerID) {
        ObservableList<ArchiveOrderCardData> listData = FXCollections.observableArrayList();
        try (Connection connect = database.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, customerID);

            try (ResultSet result = prepare.executeQuery()) {
                while (result.next()) {
                    String itemName = result.getString("item_name");
                    Integer quantity = result.getInt("quantity");
                    String size = result.getString("size");
                    String addOns = result.getString("add_ons");
                    String fruitFlavor = result.getString("fruit_flavor");
                    String sinkers = result.getString("sinkers");
                    String sugarlvl = result.getString("sugar_level");

                    boolean askMe = result.getBoolean("ask_me");

                    // Create an OrderCardData object and add it to the list
                    ArchiveOrderCardData archiveOrderCardData = new ArchiveOrderCardData(itemName, quantity, size, addOns, fruitFlavor, sinkers, sugarlvl, askMe);
                    listData.add(archiveOrderCardData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    private void orderArchiveGrid() throws SQLException {
        orderCardGP.getChildren().clear();
        int column = 0;
        int row = 1;

        for (ArchiveOrderCardData archiveOrderCardData : archiveOrderCardData) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Kitchen/OrderArchiveCardFXML.fxml"));
                AnchorPane pane = loader.load();

                // Access the controller and set the data
                OrderArchiveCardFXMLController orderArchiveCardFXMLController = loader.getController();
                orderArchiveCardFXMLController.setArchiveCardData(archiveOrderCardData);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                orderCardGP.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(4));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
