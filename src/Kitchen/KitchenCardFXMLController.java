/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Kitchen;

import ClassFiles.KitchenCardData;
import ClassFiles.MilkteaItemData;
import ClassFiles.OrderCardData;
import Databases.CRUDDatabase;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Gwyneth Uy
 */
// Import statements...
public class KitchenCardFXMLController implements Initializable {

    @FXML
    private Label custNoLBL;

    @FXML
    private GridPane orderCardGP;

    private KitchenCardData kitchenCardData;

    private ObservableList<OrderCardData> orderCardData = FXCollections.observableArrayList();

    public void setKitchenCardData(KitchenCardData kitchenCardData) throws SQLException {
        this.kitchenCardData = kitchenCardData;

        String customerID = kitchenCardData.getCustomerID();

        // Set data to corresponding components
        custNoLBL.setText(customerID);

        // Retrieve orders for the specific customer
        orderCardData = menuGetData(customerID);

        // Set data to the grid
        orderGrid();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No need to retrieve all orders here
    }

    public ObservableList<OrderCardData> menuGetData(String customerID) {
        String sql = "SELECT mt.item_name, mt.quantity, mt.size, mt.add_ons,\n"
                + "       IFNULL(fd.fruit_flavor, 'None') AS fruit_flavor,\n"
                + "       IFNULL(fd.sinkers, 'None') AS sinkers,\n"
                + "       mt.sugar_level\n"
                + "FROM milk_tea mt\n"
                + "LEFT JOIN fruit_drink fd ON mt.customer_id = fd.customer_id\n"
                + "LEFT JOIN frappe f ON mt.customer_id = f.customer_id\n"
                + "WHERE mt.customer_id = ?;";

        ObservableList<OrderCardData> listData = FXCollections.observableArrayList();
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.getConnection();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, customerID);

            result = prepare.executeQuery();

            String currentCustomerID = null;

            while (result.next()) {
                String itemName = result.getString("item_name");
                Integer quantity = result.getInt("quantity");
                String size = result.getString("size");
                String addOns = result.getString("add_ons");
                String fruitFlavor = result.getString("fruit_flavor");
                String sinkers = result.getString("sinkers");
                String sugarlvl = result.getString("sugar_level");

                // Print customer ID if it's a new customer
                if (!customerID.equals(currentCustomerID)) {
                    System.out.println("Customer ID: " + customerID);
                    currentCustomerID = customerID;
                }

                System.out.println("Item Name: " + itemName);
                System.out.println("Quantity: " + quantity);
                System.out.println("Size: " + size);
                System.out.println("Add Ons: " + addOns);
                System.out.println("Fruit Flavor: " + fruitFlavor);
                System.out.println("Sinkers: " + sinkers);
                System.out.println("Sugar Level: " + sugarlvl);

                // Filter orders based on the current custNoLBL
                if (customerID.equals(custNoLBL.getText())) {
                    // Create an OrderCardData object and add it to the list
                    OrderCardData orderCardData = new OrderCardData(itemName, quantity, size, addOns, fruitFlavor, sinkers, sugarlvl);
                    listData.add(orderCardData);
                }
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

    private void orderGrid() throws SQLException {
        orderCardGP.getChildren().clear();
        int column = 0;
        int row = 1;

        for (OrderCardData orderCardData : orderCardData) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Kitchen/OrderCardFXML.fxml"));
                AnchorPane pane = loader.load();

                // Access the controller and set the data
                OrderCardFXMLController orderCardFXMLController = loader.getController();
                orderCardFXMLController.setOrderCardData(orderCardData);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                orderCardGP.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
