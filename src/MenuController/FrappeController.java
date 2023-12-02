package MenuController;

import MainAppFrame.CashierFXMLController;
import MainAppFrame.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import ClassFiles.ControllerManager;
import ClassFiles.FrappeItemData;
import ClassFiles.MilkteaItemData;
import com.mysql.cj.jdbc.Blob;
import java.io.ByteArrayInputStream;

/**
 *
 * @author John Paul Uy
 */
public class FrappeController {

    @FXML
    private Spinner spinnerQuantity;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private ComboBox<String> sugarlevelComboBox;

    @FXML
    private RadioButton askmeRadioHead;

    @FXML
    private Button confirmButton1;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    private FrappeItemData frappeItemData;

    private boolean askmeRadioSelected = false;

    private CashierFXMLController existingCashierController;

    private String selectedSugarLevel;

    public void initialize() {
        // Initialize your combo boxes with data

        initializeSizeComboBox();
        initializeSugarlevelComboBox();

        // Set the default value to "None" for all ComboBoxes
        sizeComboBox.setValue("None");
        sugarlevelComboBox.setValue("None");

        // Set the Spinner to allow only whole number values and set the default value to 0
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinnerQuantity.setValueFactory(valueFactory);

        // Set a StringConverter to display the Spinner values as whole numbers
        StringConverter<Integer> converter = new IntegerStringConverter();
        spinnerQuantity.getValueFactory().setConverter(converter);

    }

    @FXML
    public void askmeRadioHeadSelected(ActionEvent event) {
        askmeRadioSelected = askmeRadioHead.isSelected();
    }

    public void setExistingCashierController(CashierFXMLController cashierController) {
        this.existingCashierController = cashierController;
    }

    public void setFrappeItemData(FrappeItemData frappeItemData) throws SQLException {
        // Set data to components
        this.frappeItemData = frappeItemData;

        // Assuming you have a method in MilkteaItemData to get the image name or title
        String itemName = frappeItemData.getItemName();

        // Set data to corresponding components
        foodLabel.setText(itemName);

        /* para doon sa image */
        Blob imageBlob = frappeItemData.getImage();
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis, 129, 173, false, true);
        foodImg.setImage(image);

    }

    private void insertOrderToDatabase(int customer_id, String menuName, Integer selectedQuantity, String selectedSize, String selectedSugarLevel, boolean askmeRadioSelected) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO frappe (customer_id, date_time, item_name, quantity, size, sugar_level, ask_me, size_price, final_price) VALUES (?, NOW(),?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setString(4, selectedSize);
                    stmt.setString(5, selectedSugarLevel);
                    stmt.setBoolean(6, askmeRadioSelected);

                    // Check if size and add-ons are selected and set the corresponding prices
                    int sizePrice = calculateSizePrice(selectedSize);

                    stmt.setInt(7, sizePrice);

                    // Calculate the final price based on selected size and add-ons
                    int finalPrice = sizePrice * selectedQuantity;
                    stmt.setInt(8, finalPrice);

                    stmt.executeUpdate();
                }
            } else {
                System.out.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeSizeComboBox() {
        // Populate the sizeComboBox with items
        ObservableList<String> sizes = FXCollections.observableArrayList(
                "None",
                "Small",
                "Medium",
                "Large"
        );
        sizeComboBox.setItems(sizes);
    }

    private void initializeSugarlevelComboBox() {
        // Populate the sugarlevelComboBox with items
        ObservableList<String> sugarLevels = FXCollections.observableArrayList(
                "None",
                "Low",
                "Medium",
                "High"
        );
        sugarlevelComboBox.setItems(sugarLevels);
    }

    private int calculateSizePrice(String selectedSize) {
        switch (selectedSize) {
            case "Small":
                return 39;
            case "Medium":
                return 69;
            case "Large":
                return 79; // Return 0 if "None" is selected
        }
        return 0; // Return 0 if an unknown size is selected
    }

    private int calculateAddonsPrice(String selectedAddon) {
        switch (selectedAddon) {
            case "Cream Cheese":
                return 20;
        }
        return 0; // Return 0 if an unknown addon is selected
    }

}
