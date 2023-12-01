package CRUDs;

import ClassFiles.MilkteaItemData;
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


public class MilkteaCRUDController implements Initializable {
    
    
    @FXML
    private Button dltBtn;
    @FXML
    private Button updtBTN;
    @FXML
    private Button addBTN;

    @FXML
    private TableView<MilkteaItemData> milkteaTV;

    @FXML
    private TableColumn<MilkteaItemData, String> itemMT;

    @FXML
    private TableColumn<MilkteaItemData, Integer> largeMT;

    @FXML
    private TableColumn<MilkteaItemData, Integer> medMT;

    @FXML
    private TableColumn<MilkteaItemData, Integer> smallMT;

    @FXML
    private TableColumn<MilkteaItemData, String> addonsMT;

    @FXML
    private TextArea txtAddons;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtLargePrice;

    @FXML
    private TextField txtMediumPrice;

    @FXML
    private TextField txtSmallPrice;

    @FXML
    private Button attachimageBTN;

    @FXML
    private ImageView iconIV;

    @FXML
    private ImageView itemIV;

    private Image selectedImage;

    private ObservableList<MilkteaItemData> milkteaListData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayMilktea();
        
          milkteaTV.setOnMouseClicked(event -> {
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
        
         MilkteaItemData selectedItem = milkteaTV.getSelectionModel().getSelectedItem();
        try (Connection connection = CRUDDatabase.getConnection()) { /* gumawa ako bagong database class kasi bagong database gagamitin natin */
            if (connection != null) {                                /* para sa mga CRUD para di nakakalito tignan sa sample_database */
                String itemName = txtItemName.getText();
                String addons = txtAddons.getText();
                String smallPrice = txtSmallPrice.getText();
                String mediumPrice = txtMediumPrice.getText();
                String largePrice = txtLargePrice.getText();

                // Convert Image to InputStream for database storage
                InputStream imageInputStream = convertImageToInputStream(selectedImage);
                
              
      
                // Call the method to insert data into the database
              

             Button clickedButton = (Button) event.getSource();
            String buttonId = clickedButton.getId();

            switch (buttonId) {
                case "addBTN" -> {
           
                    insertMilkteaItem(connection, itemName, addons, smallPrice, mediumPrice, largePrice, imageInputStream);
                    System.out.println("Data inserted.");
                    }
                case "updtBTN" -> {
                    if (selectedItem != null) {
                        int itemID = selectedItem.getItemID();
                          selectedItem.setItemID(itemID);
                        updateMilkteaItem(connection, itemName, addons, smallPrice, mediumPrice, largePrice, imageInputStream, itemID);
                        System.out.println("Data updated.");
                    } else {
                        System.out.println("No item selected for update.");
                    }           }
                case "dltBtn" -> {
                
                
               if (selectedItem != null) {
                        // Display confirmation dialog before deletion
                        boolean confirmDelete = showDeleteConfirmation();
                        if (confirmDelete) {
                            int itemID = selectedItem.getItemID();
                            deleteMilkteaItem(connection, itemID);
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
                displayMilktea();

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
    private void insertMilkteaItem(Connection connection, String itemName, String addons, String smallPrice, String mediumPrice, String largePrice, InputStream image) {
        String sql = "INSERT INTO milktea_items (item_name, addons, small_price, medium_price, large_price, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, addons);
            preparedStatement.setString(3, smallPrice);
            preparedStatement.setString(4, mediumPrice);
            preparedStatement.setString(5, largePrice);
            preparedStatement.setBlob(6, image); // Use setBlob for InputStream

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

     private void updateMilkteaItem(Connection connection, String itemName, String addons, String smallPrice, String mediumPrice, String largePrice, InputStream image, int itemID) {
    String sql;

    if (image != null) {
        
        sql = "UPDATE milktea_items SET item_name=?, addons=?, small_price=?, medium_price=?, large_price=?, image=? WHERE item_ID=?";
    } else {
        
        sql = "UPDATE milktea_items SET item_name=?, addons=?, small_price=?, medium_price=?, large_price=? WHERE item_ID=?";
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, itemName);
        preparedStatement.setString(2, addons);
        preparedStatement.setString(3, smallPrice);
        preparedStatement.setString(4, mediumPrice);
        preparedStatement.setString(5, largePrice);

        if (image != null) {
            preparedStatement.setBlob(6, image); // Use setBlob for InputStream
            preparedStatement.setInt(7, itemID);
        } else {
            preparedStatement.setInt(6, itemID);
        }

        preparedStatement.executeUpdate();
       

    } catch (SQLException e) {
        e.printStackTrace();
      
    }
}
     
      private void deleteMilkteaItem(Connection connection, int itemID) {
       String sql = "DELETE FROM milktea_items WHERE item_ID = ?";

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
    private ObservableList<MilkteaItemData> fetchDataFromDatabase() {
    ObservableList<MilkteaItemData> listData = FXCollections.observableArrayList();

    String sql = "SELECT item_id, item_name, addons, small_price, medium_price, large_price, image FROM milktea_items";

    try (Connection connect = CRUDDatabase.getConnection(); 
         PreparedStatement prepare = connect.prepareStatement(sql); 
         ResultSet result = prepare.executeQuery()) {

        while (result.next()) {
            int itemID = result.getInt("item_id");
            String itemName = result.getString("item_name");
            String addons = result.getString("addons");
            Integer smallPrice = result.getInt("small_price");
            Integer mediumPrice = result.getInt("medium_price");
            Integer largePrice = result.getInt("large_price");

            // Retrieve image as Blob
            Blob imageBlob = result.getBlob("image");

            // Convert Blob to InputStream
            InputStream imageInputStream = (imageBlob != null) ? imageBlob.getBinaryStream() : null;

            // Create MilkteaItemData and set properties
            MilkteaItemData milkteaItemData = new MilkteaItemData(itemName, addons, smallPrice, mediumPrice, largePrice);
            milkteaItemData.setItemID(itemID);
            milkteaItemData.setImage(imageBlob); // Set Blob if needed
            milkteaItemData.setImageInputStream(imageInputStream); // Set InputStream

            // Add to the list
            listData.add(milkteaItemData);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return listData;
}

    /* ito pangdisplay din */
    private void displayMilktea() {
      
        itemMT.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        addonsMT.setCellValueFactory(new PropertyValueFactory<>("addons"));
        smallMT.setCellValueFactory(new PropertyValueFactory<>("smallPrice"));
        medMT.setCellValueFactory(new PropertyValueFactory<>("mediumPrice"));
        largeMT.setCellValueFactory(new PropertyValueFactory<>("largePrice"));

      
        milkteaTV.setItems(fetchDataFromDatabase());
    }

    /* clear button */
    @FXML
    private void clearInput(ActionEvent event) {
        clearTextFields();
    }

    /* clear method na i-cacall sa clear button action event */
    private void clearTextFields() {
        txtItemName.clear();
        txtAddons.clear();
        txtSmallPrice.clear();
        txtMediumPrice.clear();
        txtLargePrice.clear();
        itemIV.setImage(null);
        iconIV.setVisible(true);
    }

    private void handleTableView(){
        
         MilkteaItemData selectedItem = milkteaTV.getSelectionModel().getSelectedItem();
         
           if (selectedItem != null) {
        txtItemName.setText(selectedItem.getItemName());

      
        txtAddons.setText(selectedItem.getAddons());
        txtSmallPrice.setText(String.valueOf(selectedItem.getSmallPrice()));
        txtMediumPrice.setText(String.valueOf(selectedItem.getMediumPrice()));
        txtLargePrice.setText(String.valueOf(selectedItem.getLargePrice()));

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
}

