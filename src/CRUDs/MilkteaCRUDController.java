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
import javafx.scene.control.ComboBox;
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
    private TableColumn<MilkteaItemData, Integer> addonsPriceMT;
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
    private ComboBox<String> statusComboBox;
    
    @FXML
    private ImageView iconIV;

    @FXML
    private ImageView itemIV;
    
     @FXML
    private TextArea txtAddonsPrice;

    private Image selectedImage;
    private String status;
    
    private String getSelectedStatus() {
        
    return statusComboBox.getValue();
}

    private ObservableList<MilkteaItemData> milkteaListData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayMilktea();
        restrictLetter(txtLargePrice);
        restrictLetter(txtMediumPrice);
        restrictLetter(txtSmallPrice);
       
        limitCharacters(txtLargePrice, 4);
        limitCharacters(txtMediumPrice,4);
        limitCharacters(txtSmallPrice, 4);
        limitCharacters(txtItemName,50);
        
        initializeStatusComboBox();
        statusComboBox.setValue("InStock");

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
        try (Connection connection = CRUDDatabase.getConnection()) {
            /* gumawa ako bagong database class kasi bagong database gagamitin natin */
            if (connection != null) {
                /* para sa mga CRUD para di nakakalito tignan sa sample_database */
                String itemName = txtItemName.getText();
                String addons = txtAddons.getText();
                String smallPrice = txtSmallPrice.getText();
                String mediumPrice = txtMediumPrice.getText();
                String largePrice = txtLargePrice.getText();
                String status = getSelectedStatus();
                String addonsPrice = txtAddonsPrice.getText();

                // Convert Image to InputStream for database storage
                InputStream imageInputStream = convertImageToInputStream(selectedImage);

                // Call the method to insert data into the database
                Button clickedButton = (Button) event.getSource();
                String buttonId = clickedButton.getId();

                switch (buttonId) {
                    case "addBTN" -> {
                    if (!isProductAlreadyExists(connection, itemName)) {
                         if (selectedImage == null) {
                imageInputStream = null;
            }
                        
                    insertMilkteaItem(connection, itemName, addons, addonsPrice, smallPrice, mediumPrice, largePrice, imageInputStream, status);
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
                    updateMilkteaItem(connection, itemName, addons, addonsPrice, smallPrice, mediumPrice, largePrice, imageInputStream, itemID, status);
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
                                deleteMilkteaItem(connection, itemID);
                                System.out.println("Data deleted.");
                                 clearTextFields();
                            } else {
                                System.out.println("Deletion canceled.");
                            }
                        } else {
                            System.out.println("No item selected for deletion.");
                        }
                    }

                }

               
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
    private void insertMilkteaItem(Connection connection, String itemName, String addons,String addonsPrice, String smallPrice, String mediumPrice, String largePrice, InputStream image, String status) {
        String sql = "INSERT INTO milktea_items (item_name, addons, addons_price, small_price, medium_price, large_price, image, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, addons);
            preparedStatement.setString(3, addonsPrice);
            preparedStatement.setString(4, smallPrice);
            preparedStatement.setString(5, mediumPrice);
            preparedStatement.setString(6, largePrice);
            preparedStatement.setBlob(7, image); // Use setBlob for InputStream
             preparedStatement.setString(8, status);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    private void updateMilkteaItem(Connection connection, String itemName, String addons, String addonsPrice, String smallPrice, String mediumPrice, String largePrice, InputStream image, int itemID, String status) {
        String sql;

        if (image != null) {

            sql = "UPDATE milktea_items SET item_name=?, addons=?, addons_price=?, small_price=?, medium_price=?, large_price=?, image=?, status=? WHERE item_ID=?";
        } else {

            sql = "UPDATE milktea_items SET item_name=?, addons=?, addons_price=?, small_price=?, medium_price=?, large_price=?, status=? WHERE item_ID=?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, addons);
            preparedStatement.setString(3, addonsPrice);
            preparedStatement.setString(4, smallPrice);
            preparedStatement.setString(5, mediumPrice);
            preparedStatement.setString(6, largePrice);
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

        String sql = "SELECT item_id, item_name, addons, addons_price, small_price, medium_price, large_price, image, status FROM milktea_items";

        try (Connection connect = CRUDDatabase.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                int itemID = result.getInt("item_id");
                String itemName = result.getString("item_name");
                String addons = result.getString("addons");
                String addonsPrice = result.getString("addons_price");
                Integer smallPrice = result.getInt("small_price");
                Integer mediumPrice = result.getInt("medium_price");
                Integer largePrice = result.getInt("large_price");
                
                String status = result.getString("status");
                // Retrieve image as Blob
                Blob imageBlob = result.getBlob("image");

                // Convert Blob to InputStream
                InputStream imageInputStream = (imageBlob != null) ? imageBlob.getBinaryStream() : null;

                // Create MilkteaItemData and set properties
                MilkteaItemData milkteaItemData = new MilkteaItemData(itemName, addons, addonsPrice, smallPrice, mediumPrice, largePrice);
                milkteaItemData.setItemID(itemID);
                milkteaItemData.setImage(imageBlob); // Set Blob if needed
                milkteaItemData.setImageInputStream(imageInputStream); // Set InputStream
                 milkteaItemData.setStatus(status);
                 

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
        // Set up the PropertyValueFactory for each column
        itemMT.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        addonsMT.setCellValueFactory(new PropertyValueFactory<>("addons"));
        addonsPriceMT.setCellValueFactory(new PropertyValueFactory<>("addonsPrice"));
        smallMT.setCellValueFactory(new PropertyValueFactory<>("smallPrice"));
        medMT.setCellValueFactory(new PropertyValueFactory<>("mediumPrice"));
        largeMT.setCellValueFactory(new PropertyValueFactory<>("largePrice"));

        // Fetch data from the database and set it in the TableView
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
        txtAddonsPrice.clear();
        txtSmallPrice.clear();
        txtMediumPrice.clear();
        txtLargePrice.clear();
        itemIV.setImage(null);
        iconIV.setVisible(true);
    }
    public void restrictLetter(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }
     public void limitCharacters(TextField textField, int maxLength) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        // Limit the length of the text
        if (textField.getText().length() > maxLength) {
            String limitedText = textField.getText().substring(0, maxLength);
            textField.setText(limitedText);
        }
    });
}


    private void handleTableView() {

        MilkteaItemData selectedItem = milkteaTV.getSelectionModel().getSelectedItem();

        if (selectedItem != null) { 
            
            String status = selectedItem.getStatus();
            txtItemName.setText(selectedItem.getItemName());

            txtAddons.setText(selectedItem.getAddons());
            txtAddonsPrice.setText(String.valueOf(selectedItem.getAddonsPrice()));
            txtSmallPrice.setText(String.valueOf(selectedItem.getSmallPrice()));
            txtMediumPrice.setText(String.valueOf(selectedItem.getMediumPrice()));
            txtLargePrice.setText(String.valueOf(selectedItem.getLargePrice()));

            Blob imageBlob = selectedItem.getImage();
            statusComboBox.setValue(status);
            try {

                InputStream imageInputStream = (imageBlob != null) ? imageBlob.getBinaryStream() : null;

            if (imageInputStream != null) {
                Image selectedItemImage = new Image(imageInputStream);
                itemIV.setImage(selectedItemImage);
                iconIV.setVisible(false);
            } else {
                // Clear the image if it's null
                itemIV.setImage(null);
                iconIV.setVisible(true);
            }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
      private boolean isProductAlreadyExists(Connection connection, String itemName) {
        String sql = "SELECT COUNT(*) FROM milktea_items WHERE item_name = ?";

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
    String sql = "SELECT COUNT(*) FROM milktea_items WHERE item_name = ? AND item_id != ?";

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