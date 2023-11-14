package MainAppFrame;

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
import javafx.scene.image.Image;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import other.ControllerManager;
import other.ItemData;
import other.menu1;
import other.menu2;

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

    private menu1 menuData;

    private boolean askmeRadioSelected = false;

    private boolean orderTaken = false;

    private CashierFXMLController existingCashierController;

    private ObservableList<ItemData> menuMilkteaListData;

    public void initialize() {
        // Initialize your combo boxes with data
        initializeAddonsComboBox();
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

    public void setData(menu1 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }

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

    }

    public void takeOrderButtonClicked(ActionEvent event) {
        orderTaken = true;
    }

    private void initializeAddonsComboBox() {
        // Populate the addonsComboBox with items
        ObservableList<String> addons = FXCollections.observableArrayList(
                "None",
                "Cream Cheese",
                "Pearl",
                "Oreo"
        );
        addonsComboBox.setItems(addons);
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
