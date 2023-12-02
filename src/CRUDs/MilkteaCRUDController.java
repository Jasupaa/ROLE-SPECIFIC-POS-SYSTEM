package CRUDs;

import ClassFiles.MilkteaItemData;
import Databases.CRUDDatabase;
import MainAppFrame.CashierFXMLController;
import MainAppFrame.database;
import com.mysql.cj.jdbc.Blob;
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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class MilkteaCRUDController implements Initializable {

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

    @FXML
    private Button addBTN1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayMilktea();
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
        try (Connection connection = CRUDDatabase.getConnection()) {
            /* gumawa ako bagong database class kasi bagong database gagamitin natin */
            if (connection != null) {
                /* para sa mga CRUD para di nakakalito tignan sa sample_database */
                String itemName = txtItemName.getText();
                String addons = txtAddons.getText();
                String smallPrice = txtSmallPrice.getText();
                String mediumPrice = txtMediumPrice.getText();
                String largePrice = txtLargePrice.getText();

                // Convert Image to InputStream for database storage
                InputStream imageInputStream = convertImageToInputStream(selectedImage);

                // Call the method to insert data into the database
                insertMilkteaItem(connection, itemName, addons, smallPrice, mediumPrice, largePrice, imageInputStream);

                System.out.println("Data inserted.");

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

    /* ito para sa pagdisplay sa tableview */
    private ObservableList<MilkteaItemData> fetchDataFromDatabase() {
        ObservableList<MilkteaItemData> listData = FXCollections.observableArrayList();

        String sql = "SELECT item_name, addons, small_price, medium_price, large_price FROM milktea_items";

        try (Connection connect = CRUDDatabase.getConnection(); PreparedStatement prepare = connect.prepareStatement(sql); ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                String itemName = result.getString("item_name");
                String addons = result.getString("addons");
                Integer smallPrice = result.getInt("small_price");
                Integer mediumPrice = result.getInt("medium_price");
                Integer largePrice = result.getInt("large_price");

                // Create a MilkteaItemData object and add it to the list
                MilkteaItemData milkteaItemData = new MilkteaItemData(itemName, addons, smallPrice, mediumPrice, largePrice);
                listData.add(milkteaItemData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    private void displayMilktea() {
        // Set up the PropertyValueFactory for each column
        itemMT.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        addonsMT.setCellValueFactory(new PropertyValueFactory<>("addons"));
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
        txtSmallPrice.clear();
        txtMediumPrice.clear();
        txtLargePrice.clear();
        itemIV.setImage(null);
        iconIV.setVisible(true);
    }

}