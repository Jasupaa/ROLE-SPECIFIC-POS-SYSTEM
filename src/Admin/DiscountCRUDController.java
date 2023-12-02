/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Admin;

import ClassFiles.Discount;
import MainAppFrame.AdminFXMLController;
import MainAppFrame.database;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import javafx.event.ActionEvent;
import java.sql.*;
import java.util.Random;
import javafx.scene.control.TableView;


/**
 * FXML Controller class
 *
 * @author Gwyneth Uy
 */
public class DiscountCRUDController implements Initializable {

    private AdminFXMLController adminController;

    // Method to set the reference to AdminFXMLController
    public void setAdminController(AdminFXMLController adminController) {
        this.adminController = adminController;
    }
      @FXML
    private Button AGBtn;
      
    @FXML
    private Button EditBtn;

    @FXML
    private Button CLRBtn;

    @FXML
    private Button CreateBtn;

    @FXML
    private TextField DateCreate;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private TextField DisCode;

    @FXML
    private TextField DisCount;

    @FXML
    private TextField disCript;
    
    
    
    private int id;
    
    @FXML
    private TableView<Discount> discountTableView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         AGBtn.setOnAction(event -> generateAndSetCode());
        
          DateCreate.setText(LocalDate.now().toString());
  
          DatePicker.setValue(LocalDate.now());
          
         DatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
    
      @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        // Get data from the UI controls
        String discCode = DisCode.getText();
        double discValue = Double.parseDouble(DisCount.getText());
        String descCoup = disCript.getText();
        LocalDate dateCreated = LocalDate.now();
        LocalDate dateValid = DatePicker.getValue();

        // Call a method to insert data into the database
        insertDiscountIntoDatabase(discCode, discValue, descCoup, dateCreated, dateValid);
        
          adminController.refreshTableView();
    }

    private void insertDiscountIntoDatabase(String discCode, double discValue, String descCoup, LocalDate dateCreated, LocalDate dateValid) {
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO discount (disc_code, disc_value, Desc_coup, Date_created, Date_valid) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, discCode);
            preparedStatement.setDouble(2, discValue);
            preparedStatement.setString(3, descCoup);
            preparedStatement.setDate(4, Date.valueOf(dateCreated));
            preparedStatement.setDate(5, Date.valueOf(dateValid));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Discount successfully inserted into the database.");
            } else {
                System.out.println("Failed to insert discount into the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      @FXML
    private void updateCreateButtonAction(ActionEvent event) {
        // Get data from the UI controls
        String discCode = DisCode.getText();
        double discValue = Double.parseDouble(DisCount.getText());
        String descCoup = disCript.getText();
        LocalDate dateCreated = LocalDate.now();
        LocalDate dateValid = DatePicker.getValue();
        
        // Call a method to insert data into the database
        updateDiscountIntoDatabase(discCode, discValue, descCoup, dateCreated, dateValid);
        
        adminController.refreshTableView();
    }
    
        private void updateDiscountIntoDatabase(String discCode, double discValue, String descCoup, LocalDate dateCreated, LocalDate dateValid) {
       
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE discount SET disc_Code = ?, disc_value = ?, Desc_coup = ?, Date_created = ?, Date_valid = ? WHERE id = ?")) {

            preparedStatement.setString(1, discCode);
            preparedStatement.setDouble(2, discValue);
            preparedStatement.setString(3, descCoup);
            preparedStatement.setDate(4, Date.valueOf(dateCreated));
            preparedStatement.setDate(5, Date.valueOf(dateValid));
            preparedStatement.setInt(6, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
            System.out.println("Discount successfully updated in the database.");
        } else {
            System.out.println("Failed to update discount in the database.");
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
     @FXML
    private void handleClearButtonAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        DisCode.clear();
        DisCount.clear();
        disCript.clear();
       
    }private void generateAndSetCode() {
        String generatedCode = generateRandomAlphanumericCode(8);
        DisCode.setText(generatedCode);
    }

    private String generateRandomAlphanumericCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }
    
    public void setDiscount(Discount discount) {
          this.id = discount.getId();
        // Populate the fields in your UI with the data from the discount object
        DisCode.setText(discount.getDiscCode());
        DisCount.setText(String.valueOf(discount.getDiscValue()));
        disCript.setText(discount.getDescCoup());

        // Set the DatePicker value
        if (discount.getDateValid() != null) {
            DatePicker.setValue(discount.getDateValid());
        }
        // Set other fields as needed...
    }

}
