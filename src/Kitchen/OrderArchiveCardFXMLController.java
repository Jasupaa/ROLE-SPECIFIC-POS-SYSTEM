package Kitchen;

import ClassFiles.ArchiveOrderCardData;
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
import javafx.scene.image.ImageView;

public class OrderArchiveCardFXMLController implements Initializable {

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
    private ImageView checkBoxIV;

    @FXML
    private ComboBox<String> orderStatusCB;

    private OrderCardData orderCardData;

    private ArchiveOrderCardData archiveOrderCardData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setArchiveCardData(ArchiveOrderCardData archiveOrderCardData) throws SQLException {
        this.archiveOrderCardData = archiveOrderCardData;

        String itemName = archiveOrderCardData.getItemName();
        Integer quantity = archiveOrderCardData.getQuantity();
        String size = archiveOrderCardData.getSize();
        String addOns = archiveOrderCardData.getAddOns();
        String fruitFlavor = archiveOrderCardData.getFruitFlavor();
        String sinkers = archiveOrderCardData.getSinkers();
        String sugarLevel = archiveOrderCardData.getSugarLevel();

        boolean askMe = archiveOrderCardData.getAskMe();

        // Set data to corresponding components
        nameLBL.setText(itemName);
        qtyLBL.setText(String.valueOf(quantity));

        // Check for null values and set appropriate text
        sizeLBL.setText(size != null ? size : "");
        addoLBL.setText(addOns != null ? addOns : "");
        fruitfLBL.setText(fruitFlavor != null ? fruitFlavor : "");
        sinkersLBL.setText(sinkers != null ? sinkers : "");
        sugarlvlLBL.setText(sugarLevel != null ? sugarLevel : "");

        checkBoxIV.setVisible(askMe);
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
