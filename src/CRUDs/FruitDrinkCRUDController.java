package CRUDs;


import ClassFiles.FruitDrinkItemData;
import Databases.CRUDDatabase;
import java.sql.Blob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class FruitDrinkCRUDController implements Initializable {

    @FXML
    private Button addBTN;

    @FXML
    private Button addBTN1;

  
    @FXML
    private TableColumn<FruitDrinkItemData, String> fruitfFlavorMT;

    @FXML
    private Button attachimageBTN;

    @FXML
    private Button dltBtn;

    @FXML
    private ImageView iconIV;

    @FXML
    private ImageView itemIV;

    @FXML
    private TableColumn<FruitDrinkItemData, String> itemMT;

    @FXML
    private TableColumn<FruitDrinkItemData, Integer> largeMT;

    @FXML
    private TableColumn<FruitDrinkItemData, Integer> medMT;

    @FXML
    private TableColumn<FruitDrinkItemData, String> sinkersMT;

    @FXML
    private TableView<FruitDrinkItemData> fruitDrinkTV;

    @FXML
    private TableColumn<FruitDrinkItemData, Integer> smallMT;

    @FXML
    private TextArea txtFruitFlavor;

    @FXML
    private TextArea txtSinkers;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtLargePrice;

    @FXML
    private TextField txtMediumPrice;

    @FXML
    private TextField txtSmallPrice;
    private Image selectedImage;

    @FXML
    private Button updtBTN;
    
    @FXML
    private ComboBox<String> statusComboBox;
    
     private String getSelectedStatus() {
        
    return statusComboBox.getValue();
}

    private ObservableList<FruitDrinkItemData> fruitDrinkItemData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayFruitDrink();
         restrictLetter(txtLargePrice);
        restrictLetter(txtMediumPrice);
        restrictLetter(txtSmallPrice);
        initializeStatusComboBox();
        statusComboBox.setValue("InStock");

        fruitDrinkTV.setOnMouseClicked(event -> {
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
 FruitDrinkItemData selectedItem = fruitDrinkTV.getSelectionModel().getSelectedItem();
        try (Connection connection = CRUDDatabase.getConnection()) {
            /* gumawa ako bagong database class kasi bagong database gagamitin natin */
            if (connection != null) {
                /* para sa mga CRUD para di nakakalito tignan sa sample_database */
                String itemName = txtItemName.getText();
                String smallPrice = txtSmallPrice.getText();
                String mediumPrice = txtMediumPrice.getText();
                String largePrice = txtLargePrice.getText();
                String fruitFlavor = txtFruitFlavor.getText();
                String sinkers = txtSinkers.getText();
                String status = getSelectedStatus();
                // Convert Image to InputStream for database storage
                InputStream imageInputStream = convertImageToInputStream(selectedImage);
               
                // Call the method to insert data into the database
                Button clickedButton = (Button) event.getSource();
                String buttonId = clickedButton.getId();
                
                switch (buttonId) {
                case "addBTN" -> {
           if (!isProductAlreadyExists(connection, itemName)) {
                  insertFruitDrinkItem(connection, itemName,smallPrice, mediumPrice, largePrice,fruitFlavor, sinkers , imageInputStream, status);
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
                        updateFruitDrinkItem(connection, itemName,smallPrice, mediumPrice, largePrice,fruitFlavor, sinkers , imageInputStream, itemID, status);
                        System.out.println("Data updated.");
                     clearTextFields();
                } else {
                    showAlert("Product Already Exists", "The product '" + itemName + "' already exists.");
                    System.out.println("Product already exists.");
                    return; 
                }
            } else {
                System.out.println("No item selected for update.");
            }
                             }
                
                case "dltBtn" -> {
                
                
               if (selectedItem != null) {
                        // Display confirmation dialog before deletion
                        boolean confirmDelete = showDeleteConfirmation();
                        if (confirmDelete) {
                            int itemID = selectedItem.getItemID();
                            deleteFruitDrinkItem(connection, itemID);
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
                displayFruitDrink();

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
    private void insertFruitDrinkItem(Connection connection, String itemName, String smallPrice, String mediumPrice, String largePrice, String fruitFlavor, String sinkers,InputStream image, String status) {
        String sql = "INSERT INTO fruitdrink_items (item_name, small_price, medium_price, large_price,fruit_flavor, sinkers, image, string) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, smallPrice);
            preparedStatement.setString(3, mediumPrice);
            preparedStatement.setString(4, largePrice);
            preparedStatement.setString(5, fruitFlavor);
            preparedStatement.setString(6, sinkers);
            preparedStatement.setBlob(7, image); // Use setBlob for InputStream
             preparedStatement.setString(8, status);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    private void updateFruitDrinkItem(Connection connection, String itemName, String smallPrice, String mediumPrice, String largePrice,String fruitFlavor,String sinkers, InputStream image, int itemID, String status) {
        String sql;

        if (image != null) {

            sql = "UPDATE fruitdrink_items SET item_name=?, small_price=?, medium_price=?, large_price=?,fruit_drink=?,sinkers=?, image=?, status=? WHERE item_ID=?";
        } else {

            sql = "UPDATE fruitdrink_items SET item_name=?, small_price=?, medium_price=?, large_price=? fruit_drink=?,sinkers=?, status=? WHERE item_ID=?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, smallPrice);
            preparedStatement.setString(3, mediumPrice);
            preparedStatement.setString(4, largePrice);
            preparedStatement.setString(5, fruitFlavor);
            preparedStatement.setString(6, sinkers);
             preparedStatement.setString(7, status);
            

            if (image != null) {
                preparedStatement.setBlob(8, image); // Use setBlob for InputStream
                preparedStatement.setInt(9, itemID);
            } else {
                preparedStatement.setInt(8, itemID);
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void deleteFruitDrinkItem(Connection connection, int itemID) {
        String sql = "DELETE FROM fruitdrink_items WHERE item_ID = ?";

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
    private ObservableList<FruitDrinkItemData> fetchDataFromDatabase() {
        ObservableList<FruitDrinkItemData> listData = FXCollections.observableArrayList();

        String sql = "SELECT item_id, item_name, small_price, medium_price, large_price, fruit_flavor, sinkers, image, status FROM fruitdrink_items";

        try (Connection connect = CRUDDatabase.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                int itemID = result.getInt("item_id");
                String itemName = result.getString("item_name");
                Integer smallPrice = result.getInt("small_price");
                Integer mediumPrice = result.getInt("medium_price");
                Integer largePrice = result.getInt("large_price");
                String fruitFlavor = result.getString("fruit_flavor");
                String sinkers = result.getString("sinkers");
                
                String status = result.getString("status");
                
                Blob imageBlob = result.getBlob("image");

            
                InputStream imageInputStream = (imageBlob != null) ? imageBlob.getBinaryStream() : null;
                
                FruitDrinkItemData fruitDrinkItemData = new FruitDrinkItemData(itemName, smallPrice, mediumPrice, largePrice, fruitFlavor, sinkers);
                fruitDrinkItemData.setItemID(itemID);
                fruitDrinkItemData.setImage(imageBlob);
                fruitDrinkItemData.setImageInputStream(imageInputStream);
                fruitDrinkItemData.setStatus(status);
               
                
                listData.add(fruitDrinkItemData);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    /* ito pangdisplay din */
    private void displayFruitDrink() {
        // Set up the PropertyValueFactory for each column
        itemMT.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        fruitfFlavorMT.setCellValueFactory(new PropertyValueFactory<>("fruitFlavor"));
        sinkersMT.setCellValueFactory(new PropertyValueFactory<>("sinkers"));
        smallMT.setCellValueFactory(new PropertyValueFactory<>("smallPrice"));
        medMT.setCellValueFactory(new PropertyValueFactory<>("mediumPrice"));
        largeMT.setCellValueFactory(new PropertyValueFactory<>("largePrice"));

        // Fetch data from the database and set it in the TableView
        fruitDrinkTV.setItems(fetchDataFromDatabase());
    }

    /* clear button */
    @FXML
    private void clearInput(ActionEvent event) {
        clearTextFields();
    }

    /* clear method na i-cacall sa clear button action event */
    private void clearTextFields() {
        txtItemName.clear();
        txtFruitFlavor.clear();
        txtSinkers.clear();
        txtSmallPrice.clear();
        txtMediumPrice.clear();
        txtLargePrice.clear();
        itemIV.setImage(null);
        iconIV.setVisible(true);
    }

    private void handleTableView() {

        FruitDrinkItemData selectedItem = fruitDrinkTV.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            
            String status = selectedItem.getStatus();
            
            txtItemName.setText(selectedItem.getItemName());

            txtSmallPrice.setText(String.valueOf(selectedItem.getSmallPrice()));
            txtMediumPrice.setText(String.valueOf(selectedItem.getMediumPrice()));
            txtLargePrice.setText(String.valueOf(selectedItem.getLargePrice()));
            txtFruitFlavor.setText(selectedItem.getFruitFlavor()); 
            txtSinkers.setText(selectedItem.getSinkers());  
            statusComboBox.setValue(status);

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
        String sql = "SELECT COUNT(*) FROM fruitdrink_items WHERE item_name = ?";

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
    String sql = "SELECT COUNT(*) FROM fruitdrink_items WHERE item_name = ? AND item_id != ?";

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
    
        private void initializeStatusComboBox() {
        // Populate the sugarlevelComboBox with items
        ObservableList<String> status = FXCollections.observableArrayList(
                "InStock",
                "Out Of Stock"
                
        );
        statusComboBox.setItems(status);
    }

    }

