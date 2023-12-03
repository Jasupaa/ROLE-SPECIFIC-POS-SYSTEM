package CRUDs;


import ClassFiles.RiceMealsItemData;
import Databases.CRUDDatabase;
import MainAppFrame.CashierFXMLController;
import MainAppFrame.database;
import java.sql.Blob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class RiceMealsCRUDController implements Initializable {

    @FXML
    private Button addBTN;

    @FXML
    private Button addBTN1;

    @FXML
    private Button attachimageBTN;

    @FXML
    private Button dltBtn;

    @FXML
    private ImageView iconIV;

    @FXML
    private ImageView itemIV;

    @FXML
    private TableColumn<RiceMealsItemData, String> itemMT;

    @FXML
    private TableColumn<RiceMealsItemData, Integer> priceMT;

    @FXML
    private TableView<RiceMealsItemData> ricemealsTV;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtPrice;

    @FXML
    private Button updtBTN;

    private Image selectedImage;

    private ObservableList<RiceMealsItemData> ricemealsItemData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayRiceMeals();
         restrictLetter(txtPrice);
        

        ricemealsTV.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableView();
            }
        });
    }

    /* ito yung action event para sa attach image button */
    @FXML
    private void onAttachImageButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image into the itemIV
            selectedImage = new Image(selectedFile.toURI().toString());
            itemIV.setImage(selectedImage);

            iconIV.setVisible(false);
        }
    }

    /* ito yung para sa paginsert sa database, magvavary siya sa kung ano lang yung kinukuha sa CRUD */
    @FXML
    private void handleAddButtonClick(ActionEvent event) throws IOException {

        RiceMealsItemData selectedItem = ricemealsTV.getSelectionModel().getSelectedItem();
        try (Connection connection = CRUDDatabase.getConnection()) {
            /* gumawa ako bagong database class kasi bagong database gagamitin natin */
            if (connection != null) {
                /* para sa mga CRUD para di nakakalito tignan sa sample_database */
                String itemName = txtItemName.getText();
                String price = txtPrice.getText();

                // Convert Image to InputStream for database storage
                InputStream imageInputStream = convertImageToInputStream(selectedImage);

                Button clickedButton = (Button) event.getSource();
                String buttonId = clickedButton.getId();

                switch (buttonId) {
                    case "addBTN" -> {
                    if (!isProductAlreadyExists(connection, itemName)) {
                    insertRiceMealsItem(connection, itemName, price, imageInputStream);
                    System.out.println("Data inserted.");
                     clearTextFields();
                } else {
                    showAlert("Product Already Exists", "The product '" + itemName + "' already exists.");
                    System.out.println("Product already exists.");
                    return; 
                }
                       
                    }
                    case "updtBTN" -> {
                         if (selectedItem != null) {
                int itemID = selectedItem.getItemID();
                if (!isProductAlreadyExistsforUpdt(connection, itemName, itemID)) {
                     selectedItem.setItemID(itemID);
                            updateRiceMealsItem(connection, itemName, price, imageInputStream, itemID);
                            System.out.println("Data updated.");
                     clearTextFields();
                } else {
                    showAlert("Product Already Exists", "The product '" + itemName + "' already exists.");
                    System.out.println("Product already exists.");
                    return; 
                     
                        }
                    }
                    }
                    case "dltBtn" -> {

                        if (selectedItem != null) {
                            // Display confirmation dialog before deletion
                            boolean confirmDelete = showDeleteConfirmation();
                            if (confirmDelete) {
                                int itemID = selectedItem.getItemID();
                                deleteRiceMealsItem(connection, itemID);
                                System.out.println("Data deleted.");
                            } else {
                                System.out.println("Deletion canceled.");
                            }
                        } else {
                            System.out.println("No item selected for deletion.");
                        }
                    }

                }

                clearTextFields();
                displayRiceMeals();

                // Optionally, you can update your TableView or perform other actions after insertion
            } else {
                System.out.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    /* ewan ko ano 'to para ata possible na mastore sa database yung image */
    private InputStream convertImageToInputStream(Image image) throws IOException {
        // Convert Image to InputStream
        if (image == null) {
            return null; // or handle the case accordingly
        }

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /* ito sundin mo lang ano yung nasa CRUD UI ng mga food category, basta kapag combobox (like add ons) ang logic natin is
    gagamit tayo comma para ma-identify na iba't-ibang options siya
     */
    private void insertRiceMealsItem(Connection connection, String itemName, String price, InputStream image) {
        String sql = "INSERT INTO ricemeals_items (item_name,price, image) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, price);

            preparedStatement.setBlob(3, image); // Use setBlob for InputStream

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    private void updateRiceMealsItem(Connection connection, String itemName, String price, InputStream image, int itemID) {
        String sql;

        if (image != null) {

            sql = "UPDATE ricemeals_items SET item_name=?, price=?, image=? WHERE item_ID=?";
        } else {

            sql = "UPDATE ricemeals_items SET item_name=?, price=? WHERE item_ID=?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, price);

            if (image != null) {
                preparedStatement.setBlob(3, image); // Use setBlob for InputStream
                preparedStatement.setInt(4, itemID);
            } else {
                preparedStatement.setInt(6, itemID);
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void deleteRiceMealsItem(Connection connection, int itemID) {
        String sql = "DELETE FROM ricemeals_items WHERE item_ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, itemID);
            preparedStatement.executeUpdate();
            System.out.println("Data deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    private boolean showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Item");
        alert.setContentText("Are you sure you want to delete the selected item?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /* ito para sa pagdisplay sa tableview */
    private ObservableList<RiceMealsItemData> fetchDataFromDatabase() {
        ObservableList<RiceMealsItemData> listData = FXCollections.observableArrayList();

        String sql = "SELECT item_id, item_name, price, image FROM ricemeals_items";

        try (Connection connect = CRUDDatabase.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                int itemID = result.getInt("item_id");
                String itemName = result.getString("item_name");
                Integer price = result.getInt("price");
                
                 Blob imageBlob = result.getBlob("image");

                 InputStream imageInputStream = (imageBlob != null) ? imageBlob.getBinaryStream() : null;

   
   

                RiceMealsItemData riceMealsItemData = new RiceMealsItemData(itemName, price);
                riceMealsItemData.setItemID(itemID);
                riceMealsItemData.setImage(imageBlob); // Set Blob if needed
                riceMealsItemData.setImageInputStream(imageInputStream);

                
                listData.add(riceMealsItemData);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    /* ito pangdisplay din */
    private void displayRiceMeals() {
        // Set up the PropertyValueFactory for each column
        itemMT.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceMT.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Fetch data from the database and set it in the TableView
        ricemealsTV.setItems(fetchDataFromDatabase());
    }

    /* clear button */
    @FXML
    private void clearInput(ActionEvent event) {
        clearTextFields();
    }

    /* clear method na i-cacall sa clear button action event */
    private void clearTextFields() {
        txtItemName.clear();
        txtPrice.clear();
        itemIV.setImage(null);
        iconIV.setVisible(true);
    }

    private void handleTableView() {

        RiceMealsItemData selectedItem = ricemealsTV.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtItemName.setText(selectedItem.getItemName());

            txtPrice.setText(String.valueOf(selectedItem.getPrice()));

        Blob imageBlob = selectedItem.getImage();
             
        try {
  
    InputStream imageInputStream = (imageBlob != null) ? imageBlob.getBinaryStream() : null;

    selectedItem.setImageInputStream(imageInputStream);

  
    if (imageInputStream != null) {
        Image selectedItemImage = new Image(imageInputStream);
        itemIV.setImage(selectedItemImage);
        iconIV.setVisible(false);
    } else {
       
        itemIV.setImage(null);
        iconIV.setVisible(true);
    }
} catch (SQLException e) {
    e.printStackTrace();
   
}
        
}
 }
     public void restrictLetter(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }
     
    private boolean isProductAlreadyExists(Connection connection, String itemName) {
        String sql = "SELECT COUNT(*) FROM ricemeals_items WHERE item_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
 private boolean isProductAlreadyExistsforUpdt(Connection connection, String itemName, int itemID) {
    String sql = "SELECT COUNT(*) FROM ricemeals_items WHERE item_name = ? AND item_id != ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, itemName);
        preparedStatement.setInt(2, itemID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    } 
    
    
    
    
    
    
}
