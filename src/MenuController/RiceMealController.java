/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MenuController;

import ClassFiles.ControllerManager;
import ClassFiles.RiceMealsItemData;
import MainAppFrame.CashierFXMLController;
import MainAppFrame.database;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class RiceMealController {

    @FXML
    private RadioButton askmeRadioHead;

    @FXML
    private Button confirmButton1;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;
     @FXML
    private Label StatusLbl;

    @FXML
    private Spinner spinnerQuantity;
    private RiceMealsItemData riceMealsItemData;
    private CashierFXMLController existingCashierController;

    private boolean askmeRadioSelected = false;

    @FXML
    public void askmeRadioHeadSelected(ActionEvent event) {
        askmeRadioSelected = askmeRadioHead.isSelected();
    }

    public void initialize() {
        // Initialize your combo boxes with data

        // Set the Spinner to allow only whole number values and set the default value to 0
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinnerQuantity.setValueFactory(valueFactory);

        // Set a StringConverter to display the Spinner values as whole numbers
        StringConverter<Integer> converter = new IntegerStringConverter();
        spinnerQuantity.getValueFactory().setConverter(converter);

    }

    public void setRiceMealsItemData(RiceMealsItemData riceMealsItemData) throws SQLException {
        // Set data to components
        this.riceMealsItemData = riceMealsItemData;

        // Assuming you have a method in MilkteaItemData to get the image name or title
        String itemName = riceMealsItemData.getItemName();
        Integer price = riceMealsItemData.getPrice();
         String status = riceMealsItemData.getStatus();

        // Set data to corresponding components
        foodLabel.setText(itemName);

        StatusLbl.setText( status);
        /* para doon sa image */
        Blob imageBlob = riceMealsItemData.getImage();
        byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = new Image(bis, 120, 120, false, true);
        foodImg.setImage(image);

    }

    private void insertOrderToDatabase(int customer_id, String menuName, Integer selectedQuantity, boolean askmeRadioSelected) {

        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO rice_meal (customer_id, date_time, item_name, quantity,ask_me,price,final_price) VALUES (?, NOW(), ?, ?, ?, ?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setBoolean(4, askmeRadioSelected);
                    int price = calculatePrice();
                    stmt.setInt(5, price);

                    // Calculate the final price based on selected size and add-ons
                    int final_price = calculatePrice() ;
                    stmt.setInt(6, final_price);

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

        if (riceMealsItemData != null) {
            String menuName = riceMealsItemData.getItemName();

            Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

            if (selectedQuantity == 0) {
                System.out.println("Please select valid options for all ComboBoxes and ensure quantity is greater than 0.");
            } else {
                int customer_id = 0; // Initialize customer_id

                if (existingCashierController != null) {
                    // Now, you can use the existing instance of CashierFXMLController
                    customer_id = existingCashierController.getCurrentCustomerID();
                } else {
                    System.out.println("Cashier controller not available.");
                }
                
                 String status = StatusLbl.getText();
           
                 if ("Out Of Stock".equals(status)) {
                // Product is out of stock, show alert and return without adding to the table view or database
                Alert outOfStockAlert = new Alert(Alert.AlertType.ERROR);
                outOfStockAlert.setTitle("Out of Stock");
                outOfStockAlert.setHeaderText(null);
                outOfStockAlert.setContentText("Sorry, the selected product is out of stock.");
                outOfStockAlert.showAndWait();
                return;
            }

                // Move insertOrderToDatabase inside the else block to ensure customer_id is properly assigned
                insertOrderToDatabase(customer_id, menuName, selectedQuantity, askmeRadioSelected);
                System.out.println("Data inserted into the database.");
            }

            // Reset the ComboBoxes to "None"
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

    
    
    
    
  private int calculatePrice() {
    if (riceMealsItemData != null) {
        Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

        if (selectedQuantity == 0) {
            System.out.println("Please select a valid quantity.");
            return 0; // or handle the error as needed
        }

        int price = riceMealsItemData.getPrice();
        
        // Calculate the final price based on the selected quantity
        int finalPrice = price * selectedQuantity;

        return finalPrice;
    }
    return 0; // or handle the error as needed
}



}

    
    
    
    

