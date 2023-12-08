/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import ClassFiles.FruitDrinkItemData;
import ClassFiles.MilkteaItemData;
import Databases.CRUDDatabase;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.ResultSet;

/**
 *
 * @author John Paul Uy
 */
public class FruitDrinkController {

    @FXML
    private Spinner spinnerQuantity;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private ComboBox<String> fruitfComboBox;

    @FXML
    private ComboBox<String> sinkersComboBox;

    @FXML
    private RadioButton askmeRadioHead;

    @FXML
    private Button confirmButton1;
    @FXML
    private Label StatusLbl;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    private boolean askmeRadioSelected = false;

    private CashierFXMLController existingCashierController;

    private FruitDrinkItemData fruitDrinkItemData;

    public void initialize() {
        // Initialize your combo boxes with data

        initializeSizeComboBox();

        // Set the default value to "None" for all ComboBoxes
        sizeComboBox.setValue("None");
        fruitfComboBox.setValue("None");
        sinkersComboBox.setValue("None");

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

    public void setFruitDrinkItemData(FruitDrinkItemData fruitDrinkItemData) throws SQLException {
        // Set data to components
        this.fruitDrinkItemData = fruitDrinkItemData;

        // Assuming you have a method in MilkteaItemData to get the image name or title
        String itemName = fruitDrinkItemData.getItemName();

        String sinkers = fruitDrinkItemData.getSinkers();
        String fruitf = fruitDrinkItemData.getFruitFlavor();
        String status = fruitDrinkItemData.getStatus();
        // Set data to corresponding components
        foodLabel.setText(itemName);
        sinkersComboBox.getItems().clear();
        sinkersComboBox.getItems().addAll(sinkers.split(", "));
        StatusLbl.setText( status);
        fruitfComboBox.getItems().clear();
        fruitfComboBox.getItems().addAll(fruitf.split(", "));

        /* para doon sa image */
        Blob imageBlob = fruitDrinkItemData.getImage();
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis, 120, 120, false, true);
        foodImg.setImage(image);

    }

    private void insertOrderToDatabase(int customer_id, String menuName, Integer selectedQuantity, String selectedSize, String selectedfruit, String selectedsinker, boolean askmeRadioSelected) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO fruit_drink (customer_id, date_time, item_name, quantity, size, fruit_flavor, sinkers, ask_me, size_price, final_price) VALUES (?, NOW(),?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setString(4, selectedSize);
                    stmt.setString(5, selectedfruit);
                    stmt.setString(6, selectedsinker);
                    stmt.setBoolean(7, askmeRadioSelected);

                    // Check if size and add-ons are selected and set the corresponding prices
                    int sizePrice = calculateSizePrice(selectedSize);

                    stmt.setInt(8, sizePrice);
                    int finalPrice = sizePrice * selectedQuantity;
                    stmt.setInt(9, finalPrice);

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

        if (fruitDrinkItemData != null) {
            String menuName = fruitDrinkItemData.getItemName();
            String selectedfruit = fruitfComboBox.getValue();
            String selectedSize = sizeComboBox.getValue();
            String selectedsinker = sinkersComboBox.getValue();
            Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

            // Check if any of the ComboBoxes has "None" selected or if the quantity is 0
            if ("None".equals(selectedfruit) || "None".equals(selectedSize) || "None".equals(selectedsinker) || selectedQuantity == 0) {
                System.out.println("Please select valid options for all ComboBoxes and ensure quantity is greater than 0.");
            } else {
                int customer_id = 0; // Initialize customer_id

                if (existingCashierController != null) {
                    // Now, you can use the existing instance of CashierFXMLController
                    customer_id = existingCashierController.getCurrentCustomerID();
                } else {
                    System.out.println("Cashier controller not available.");
                }

                insertOrderToDatabase(customer_id, menuName, selectedQuantity, selectedSize, selectedfruit, selectedsinker, askmeRadioSelected);
                System.out.println("Data inserted into the database.");
            }

            // Reset the ComboBoxes to "None"
            sizeComboBox.setValue("None");
            fruitfComboBox.setValue("None");
            sinkersComboBox.setValue("None");

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

    private int calculateSizePrice(String selectedSize) {
        try (Connection conn = CRUDDatabase.getConnection()) {
            if (conn != null) {
                String sql = "SELECT " + selectedSize.toLowerCase() + "_price FROM fruitdrink_items WHERE item_name = ?";
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
        return 0;
    }

}
