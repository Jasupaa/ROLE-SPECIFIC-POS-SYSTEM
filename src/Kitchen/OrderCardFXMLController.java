package Kitchen;

import ClassFiles.OrderCardData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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

    @FXML
    private ComboBox<String> orderStatusCB;

    private OrderCardData orderCardData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeSizeComboBox();

        // Set the default value to "None" for all ComboBoxes
        orderStatusCB.setValue("Pending");

        // Add a listener to the ComboBox to detect changes in the selected item
        orderStatusCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Set the color based on the selected item
                if ("Completed".equals(newValue)) {
                    orderStatusCB.setStyle("-fx-background-color: #44AF3C;");
                } else {
                    orderStatusCB.setStyle(""); // Reset the style
                }
            }
        });
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

    private void initializeSizeComboBox() {
        // Populate the sizeComboBox with items
        ObservableList<String> sizes = FXCollections.observableArrayList(
                "Pending",
                "In Progress",
                "Completed"
        );
        orderStatusCB.setItems(sizes);

        // Set a custom cell factory for the ComboBox
        orderStatusCB.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle(""); // Reset the style
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });
    }
}