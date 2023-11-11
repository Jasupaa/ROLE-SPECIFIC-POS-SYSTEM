/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import other.menu1;
import other.menu2;
import other.menu3;

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
    private ImageView foodImg;

    @FXML
    private Label foodLabel;
    private menu2 menuData;

    private boolean askmeRadioSelected = false;

    private static int customerCounter = 0;
    private boolean orderTaken = false;

    public void initialize() {
        // Initialize your combo boxes with data

        initializeSizeComboBox();
        initializeFruitfComboBox();
        initializeSinkersComboBox();

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

    public void setData(menu2 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }

    private void insertOrderToDatabase(int customer_id, String menuName, Integer selectedQuantity, String selectedSize, boolean askmeRadioSelected) {
        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO milk_tea (customer_id, item_name, quantity, size, add_ons, sugar_level, ask_me, size_price, addons_price, final_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setString(4, selectedSize);
                    stmt.setBoolean(7, askmeRadioSelected);

                    // Check if size and add-ons are selected and set the corresponding prices
                    int sizePrice = calculateSizePrice(selectedSize);

                    stmt.setInt(8, sizePrice);

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
        if (menuData != null) {

            // Reset the ComboBoxes to "None"
            sizeComboBox.setValue("None");
            fruitfComboBox.setValue("None");
            sinkersComboBox.setValue("None");

            // Reset the Spinner to the default value (e.g., 0)
            spinnerQuantity.getValueFactory().setValue(0);

            // Reset the radio button
            askmeRadioHead.setSelected(false);
            askmeRadioSelected = false;
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

    private void initializeFruitfComboBox() {
        // Populate the sugarlevelComboBox with items
        ObservableList<String> fruitfList = FXCollections.observableArrayList(
                "None",
                "Lychee",
                "Strawberry",
                "Blueberry",
                "Mango",
                "Lemon"
        );
        fruitfComboBox.setItems(fruitfList);
    }

    private void initializeSinkersComboBox() {
        // Populate the sugarlevelComboBox with items
        ObservableList<String> sinkerList = FXCollections.observableArrayList(
                "None",
                "Nata De Coco",
                "Fruit Jelly",
                "Strawberry Popping Bobba",
                "Mango Popping Bobba",
                "Blueberry Popping Bobba",
                "Lychee Popping Bobba",
                "Yogurt Popping Bobba"
        );
        sinkersComboBox.setItems(sinkerList);
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
