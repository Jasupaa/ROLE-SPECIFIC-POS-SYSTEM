package MenuController;

import ClassFiles.CoffeeItemData;
import ClassFiles.ControllerManager;
import Databases.CRUDDatabase;
import MainAppFrame.CashierFXMLController;
import MainAppFrame.database;
import com.mysql.cj.jdbc.Blob;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 *
 * @author John Paul Uy
 */
public class CoffeeController {

    @FXML
    private Spinner spinnerQuantity;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private RadioButton askmeRadioHead;

    @FXML
    private Button confirmButton1;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    private boolean askmeRadioSelected = false;
    private CoffeeItemData coffeeItemData;
    private CashierFXMLController existingCashierController;
    private static int customerCounter = 0;
    private boolean orderTaken = false;
    private String selectedSugarLevel;

    public void initialize() {
        // Initialize your combo boxes with data

        initializeSizeComboBox();

        // Set the default value to "None" for all ComboBoxes
        sizeComboBox.setValue("None");
        typeComboBox.setValue("None");

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

    public void setCoffeeItemData(CoffeeItemData coffeeItemData) throws SQLException {
        // Set data to components
        this.coffeeItemData = coffeeItemData;

        // Assuming you have a method in MilkteaItemData to get the image name or title
        String itemName = coffeeItemData.getItemName();

        // Assuming you have a method in MilkteaItemData to get the addons
        String type = coffeeItemData.getType();

        // Set data to corresponding components
        foodLabel.setText(itemName);
        typeComboBox.getItems().clear();
        typeComboBox.getItems().addAll(type.split(", "));


        /* para doon sa image */
        Blob imageBlob = (Blob) coffeeItemData.getImage();
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis, 129, 173, false, true);
        foodImg.setImage(image);

    }

    private void insertOrderToDatabase(int customer_id, String menuName, int selectedQuantity, String selectedSize, String selectedType, boolean askmeRadioSelected) {

        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO coffee (customer_id, date_time, item_name, quantity, size, type, ask_me, size_price, final_price) VALUES (?, NOW(), ?, ?, ?, ?,?,?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setString(4, selectedSize);
                    stmt.setString(5, selectedType);
                    stmt.setBoolean(6, askmeRadioSelected);

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

    @FXML
    public void confirmButton1(ActionEvent event) {

        CashierFXMLController cashierController = ControllerManager.getCashierController();

        if (existingCashierController == null && cashierController != null) {
            existingCashierController = cashierController;
        }

        if (coffeeItemData != null) {
            String menuName = coffeeItemData.getItemName();
            String selectedType = typeComboBox.getValue();
            String selectedSize = sizeComboBox.getValue();

            Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

            if ("None".equals(selectedType) || "None".equals(selectedSize) || selectedQuantity == 0) {
                System.out.println("Please select valid options for all ComboBoxes and ensure quantity is greater than 0.");
            } else {
                int customer_id = 0; // Initialize customer_id

                if (existingCashierController != null) {
                    // Now, you can use the existing instance of CashierFXMLController
                    customer_id = existingCashierController.getCurrentCustomerID();
                } else {
                    System.out.println("Cashier controller not available.");
                }

                // Move insertOrderToDatabase inside the else block to ensure customer_id is properly assigned
                insertOrderToDatabase(customer_id, menuName, selectedQuantity, selectedSize, selectedType, askmeRadioSelected);
                System.out.println("Data inserted into the database.");
            }

            // Reset the ComboBoxes to "None"
            typeComboBox.setValue("None");
            sizeComboBox.setValue("None");

            // Reset the Spinner to the default value (e.g., 0)
            spinnerQuantity.getValueFactory().setValue(0);

            // Reset the radio button
            askmeRadioHead.setSelected(false);
            askmeRadioSelected = false;

            if (cashierController != null) {
                // Call the setupTableView method from CashierFXMLController
                cashierController.setupTableView();
            } else {
                System.out.println("Cashier controller not available.");
            }
        }

    }

    public void takeOrderButtonClicked(ActionEvent event) {
        orderTaken = true;
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

    // Generate a customer_id based on whether the customer is new or existing
    private int generateCustomerId() {
        // If the order has not been taken yet, do not increment the customer ID
        if (orderTaken) {
            customerCounter++;
        }
        return customerCounter;
    }

    private int calculateSizePrice(String selectedSize) {
        try (Connection conn = CRUDDatabase.getConnection()) {
            if (conn != null) {
                String sql = "SELECT " + selectedSize.toLowerCase() + "_price FROM coffee_items WHERE item_name = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, foodLabel.getText());  // Assuming foodLabel is the label displaying the food name
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt(selectedSize.toLowerCase() + "_price");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return 0 if an unknown size is selected, the item_name is not found, or if an error occurred
        return 0;
    }

}
