/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainAppFrame;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private menu3 menuData;   

    private boolean askmeRadioSelected = false;

    private static int customerCounter = 0;
    private boolean orderTaken = false;
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

    public void setData(menu3 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }


    private void insertOrderToDatabase(int customer_id, String menuName, Integer selectedQuantity, String selectedSize, String selectedAddon, boolean askmeRadioSelected) {
    try (Connection conn = database.getConnection()) {
        if (conn != null) {
            String sql = "INSERT INTO frappe (customer_id, date_time, item_name, quantity, size, sugar_level, ask_me, size_price, final_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, customer_id);

                // Set the date_time column to the current date and time
                LocalDateTime currentDateTime = LocalDateTime.now();
                stmt.setObject(2, currentDateTime);

                stmt.setString(3, menuName);
                stmt.setInt(4, selectedQuantity);
                stmt.setString(5, selectedSize);
                stmt.setString(6, selectedSugarLevel);
                stmt.setBoolean(7, askmeRadioSelected);

                // Check if size and add-ons are selected and set the corresponding prices
                int sizePrice = calculateSizePrice(selectedSize);
                int addonsPrice = calculateAddonsPrice(selectedAddon);

                stmt.setInt(8, sizePrice);

                // Calculate the final price based on selected size and add-ons
                int finalPrice = (sizePrice + addonsPrice) * selectedQuantity;
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
    if (menuData != null) {
        String menuName = menuData.getName();
        String selectedSize = sizeComboBox.getValue();
        selectedSugarLevel = sugarlevelComboBox.getValue(); // Update the class-level variable
        Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

        // Check if any of the ComboBoxes has "None" selected or if the quantity is 0
        if ("None".equals(selectedSize) || "None".equals(selectedSugarLevel) || selectedQuantity == 0) {
            System.out.println("Please select valid options for all ComboBoxes and ensure quantity is greater than 0.");
        } else {
            int customer_id = generateCustomerId(); // Generate customer_id based on new or existing customer
            insertOrderToDatabase(customer_id, menuName, selectedQuantity, selectedSize, selectedSugarLevel, askmeRadioSelected);
            System.out.println("Data inserted into the database.");
        }

        // Reset the ComboBoxes to "None"
        sizeComboBox.setValue("None");
        sugarlevelComboBox.setValue("None");

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
