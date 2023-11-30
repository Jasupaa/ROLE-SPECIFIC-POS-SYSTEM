/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Kitchen;

import ClassFiles.KitchenCardData;
import ClassFiles.MilkteaItemData;
import ClassFiles.OrderCardData;
import com.mysql.cj.jdbc.Blob;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Gwyneth Uy
 */
public class OrderCardFXMLController implements Initializable {

    @FXML
    private Label addoLBL;

    @FXML
    private Label fruitfLBL;

    @FXML
    private Label nameLBL;

    @FXML
    private Label qtyLBL;

    @FXML
    private Label sinkersLBL;

    @FXML
    private Label sizeLBL;

    @FXML
    private Label sugarlvlLBL;

    private OrderCardData orderCardData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setOrderCardData(OrderCardData orderCardData) throws SQLException {
        this.orderCardData = orderCardData;

        String itemName = orderCardData.getItemName();
        Integer quantity = orderCardData.getQuantity();
        String size = orderCardData.getSize();
        String addOns = orderCardData.getAddOns();
        String fruitFlavor = orderCardData.getFruitFlavor();
        String sinkers = orderCardData.getSinkers();
        String sugarLevel = orderCardData.getSugarLevel();

        // Set data to corresponding components
        nameLBL.setText(itemName);
        qtyLBL.setText(String.valueOf(quantity));

        // Check for null values and set appropriate text
        sizeLBL.setText(size != null ? size : "");
        addoLBL.setText(addOns != null ? addOns : "");
        fruitfLBL.setText(fruitFlavor != null ? fruitFlavor : "");
        sinkersLBL.setText(sinkers != null ? sinkers : "");
        sugarlvlLBL.setText(sugarLevel != null ? sugarLevel : "");
    }

}
