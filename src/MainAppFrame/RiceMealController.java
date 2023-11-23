/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainAppFrame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import other.menu5;

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
    private Spinner spinnerQuantity;

    private CashierFXMLController existingCashierController;

    private boolean askmeRadioSelected = false;

    private menu5 menuData;

    public void setData(menu5 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }

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

    private void insertOrderToDatabase(int customer_id, String menuName, int selectedQuantity, boolean askmeRadioSelected) {

        try (Connection conn = database.getConnection()) {
            if (conn != null) {
                // Assuming you have a method to calculate the price based on the menu item
                int price = calculatePrice(menuName);
                int finalPrice = price * selectedQuantity;

                String sql = "INSERT INTO rice_meal (customer_id, date_time, item_name, quantity, price, ask_me, final_price) VALUES (?, NOW(), ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    stmt.setString(2, menuName);
                    stmt.setInt(3, selectedQuantity);
                    stmt.setInt(4, price);
                    stmt.setBoolean(5, askmeRadioSelected);
                    stmt.setInt(6, finalPrice);

                    stmt.executeUpdate();
                }
            } else {
                System.out.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculatePrice(String menuName) {
        // Implement your logic to calculate the price based on the menu item
        // For example, you can use a switch statement or a lookup in a database
        // This is just a placeholder, replace it with your actual logic

        // Fetch the menu name from the label instead of using the parameter
        String labelMenuName = foodLabel.getText();

        // Check if the label menu name matches any items
        switch (labelMenuName) {
            case "Hotsilog":
                return 89;
            case "Chiksilog":
                return 89;
            case "Longsilog":
                return 89;
            case "Bacsilog":
                return 89;
            case "Siomai Rice":
                return 69;
            // Add more cases as needed
            default:
                return 0;
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
            Integer selectedQuantity = (Integer) spinnerQuantity.getValue();

            // Check if the quantity is greater than 0
            if (selectedQuantity == 0) {
                System.out.println("Please ensure the quantity is greater than 0.");
            } else {
                int customer_id = 0; // Initialize customer_id

                if (existingCashierController != null) {
                    // Now, you can use the existing instance of CashierFXMLController
                    customer_id = existingCashierController.getCurrentCustomerID();
                } else {
                    System.out.println("Cashier controller not available.");
                }

                // Call the insertOrderToDatabase method
                insertOrderToDatabase(customer_id, menuName, selectedQuantity, askmeRadioSelected);
                System.out.println("Data inserted into the database.");
            }

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
    // Generate a customer_id based on whether the customer is new or existing

}
