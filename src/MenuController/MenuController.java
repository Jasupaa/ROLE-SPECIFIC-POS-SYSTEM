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
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import ClassFiles.ControllerManager;
import ClassFiles.ItemData;
import ClassFiles.MilkteaItemData;
import com.mysql.cj.jdbc.Blob;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;

public class MenuController {

    @FXML
    private Spinner spinnerQuantity;

    @FXML
    private ComboBox<String> addonsComboBox;

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

    private MilkteaItemData milkteaItemData;

    private boolean askmeRadioSelected = false;

    private CashierFXMLController existingCashierController;

    private ObservableList<ItemData> menuMilkteaListData;

    public void initialize() {

        initializeSizeComboBox();
        initializeSugarlevelComboBox();

        // Set the default value to "None" for all ComboBoxes
        addonsComboBox.setValue("None");
        sizeComboBox.setValue("None");
        sugarlevelComboBox.setValue("None");

        // Set the Spinner to allow only whole number values and set the default value to 0
        SpinnerValueFactory<Integer> valueFactory = new IntegerSpinnerValueFactory(0, 100, 0);
        spinnerQuantity.setValueFactory(valueFactory);

        // Set a StringConverter to display the Spinner values as whole numbers
        StringConverter<Integer> converter = new IntegerStringConverter();
        spinnerQuantity.getValueFactory().setConverter(converter);

    }

    // Somewhere in your code (where you create or have access to the CashierFXMLController instance)
    public void setExistingCashierController(CashierFXMLController cashierController) {
        this.existingCashierController = cashierController;
    }

    @FXML
    public void askmeRadioHeadSelected(ActionEvent event) {
        askmeRadioSelected = askmeRadioHead.isSelected();
    }

    /* ito connected siya sa MilkteaItemData java class file natin sa "ClassFiles", taga-get ng mga items na
    nadadagdag sa CRUD para makapaggenerate ng FXML every time na may pumapasok na new item */
    
    public void setMilkteaItemData(MilkteaItemData milkteaItemData) throws SQLException {
        // Set data to components
        this.milkteaItemData = milkteaItemData;

        // Assuming you have a method in MilkteaItemData to get the image name or title
        String itemName = milkteaItemData.getItemName();

        // Assuming you have a method in MilkteaItemData to get the addons
        String addons = milkteaItemData.getAddons();

        // Set data to corresponding components
        foodLabel.setText(itemName);
        addonsComboBox.getItems().clear();
        addonsComboBox.getItems().addAll(addons.split(", "));

        /* para doon sa image */
        Blob imageBlob = milkteaItemData.getImage();
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis, 129, 173, false, true);
        foodImg.setImage(image);

    }

    /*

    private void insertOrderToDatabase(int customer_id, String menuName, int selectedQuantity, String selectedSize, String selectedAddon, String selectedSugarLevel, boolean askmeRadioSelected) {

        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO milk_tea (customer_id, date_time, item_name, quantity, size, add_ons, sugar_level, ask_me, size_price, addons_price, final_price) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setString(4, selectedSize);
                    stmt.setString(5, selectedAddon);
                    stmt.setString(6, selectedSugarLevel);
                    stmt.setBoolean(7, askmeRadioSelected);

                    // Check if size and add-ons are selected and set the corresponding prices
                    int sizePrice = calculateSizePrice(selectedSize);
                    int addonsPrice = calculateAddonsPrice(selectedAddon);

                    stmt.setInt(8, sizePrice);
                    stmt.setInt(9, addonsPrice);

                    // Calculate the final price based on selected size and add-ons
                    int finalPrice = (sizePrice + addonsPrice) * selectedQuantity;
                    stmt.setInt(10, finalPrice);

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

        if (menuData != null) {
            String menuName = menuData.getName();
            String selectedAddon = addonsComboBox.getValue();
            String selectedSize = sizeComboBox.getValue();
            String selectedSugarLevel = sugarlevelComboBox.getValue();
            Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

            if ("None".equals(selectedAddon) || "None".equals(selectedSize) || "None".equals(selectedSugarLevel) || selectedQuantity == 0) {
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
                insertOrderToDatabase(customer_id, menuName, selectedQuantity, selectedSize, selectedAddon, selectedSugarLevel, askmeRadioSelected);
                System.out.println("Data inserted into the database.");
            }

            // Reset the ComboBoxes to "None"
            addonsComboBox.setValue("None");
            sizeComboBox.setValue("None");
            sugarlevelComboBox.setValue("None");

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

    } */
    
    /* mga hindi na kailangan ng CRUD, hard-coded customazations */
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

    /*
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
    
     */
}
