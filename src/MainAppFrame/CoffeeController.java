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
import other.ControllerManager;

import other.menu4;

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
    private menu4 menuData;   
    private CashierFXMLController existingCashierController;
    private boolean askmeRadioSelected = false;

    private static int customerCounter = 0;
    private boolean orderTaken = false;
    private String selectedSugarLevel;
   

    public void initialize() {
        // Initialize your combo boxes with data

        initializeSizeComboBox();
        initializeTypeComboBox();

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

    public void setData(menu4 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }

    private void insertOrderToDatabase(int customer_id, String menuName, int selectedQuantity, String selectedSize, String selectedType, boolean askmeRadioSelected) {

        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO coffee(customer_id, date_time, item_name, quantity, size, type, ask_me,size_price, final_price) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setString(4, selectedSize);
                    stmt.setString(5, selectedType);
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
    
    public void setExistingCashierController(CashierFXMLController cashierController) {
        this.existingCashierController = cashierController;
    }


    @FXML
    public void confirmButton1(ActionEvent event) {

        CashierFXMLController cashierController = ControllerManager.getCashierController();

        if (existingCashierController == null && cashierController != null) {
            existingCashierController = cashierController;
        }

        if (menuData != null) {
            String menuName = menuData.getName();
            String selectedSize = sizeComboBox.getValue();
            selectedSugarLevel = typeComboBox.getValue(); // Update the class-level variable
            Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

            // Check if any of the ComboBoxes has "None" selected or if the quantity is 0
            if ("None".equals(selectedSize) || "None".equals(selectedSugarLevel) || selectedQuantity == 0) {
                System.out.println("Please select valid options for all ComboBoxes and ensure quantity is greater than 0.");
            } else {
                int customer_id = 0; // Initialize customer_id

                if (existingCashierController != null) {
                    // Now, you can use the existing instance of CashierFXMLController
                    customer_id = existingCashierController.getCurrentCustomerID();
                } else {
                    System.out.println("Cashier controller not available.");
                }

                insertOrderToDatabase(customer_id, menuName, selectedQuantity, selectedSize, selectedSugarLevel, askmeRadioSelected);
                System.out.println("Data inserted into the database.");
            }

            // Reset the ComboBoxes to "None"
            sizeComboBox.setValue("None");
            typeComboBox.setValue("None");

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
    
    
 


    public void takeOrderButtonClicked(ActionEvent event) {
        orderTaken = true;
    }



   

    private void initializeTypeComboBox() {
        // Populate the sugarlevelComboBox with items
        ObservableList<String> typeList = FXCollections.observableArrayList(
            "None",
            "Cold",
            "Hot"
        );
        typeComboBox.setItems(typeList);
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

   
    
}