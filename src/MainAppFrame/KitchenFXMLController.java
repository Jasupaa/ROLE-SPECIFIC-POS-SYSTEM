package MainAppFrame;

import ClassFiles.ArchiveCardData;
import ClassFiles.KitchenCardData;
import ClassFiles.MilkteaItemData;
import Databases.CRUDDatabase;
import Login.ControllerInterface;
import Login.LoginTest;
import com.mysql.cj.jdbc.Blob;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import ClassFiles.KitchenCardData;
import Kitchen.ArchiveCardFXMLController;
import Kitchen.KitchenCardFXMLController;
import MenuController.MenuController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

public class KitchenFXMLController implements Initializable, ControllerInterface {

    double xOffset, yOffset;

    @FXML
    private ImageView CloseButton;

    @FXML
    private AnchorPane KitchenPane;

    @FXML
    private AnchorPane archive;

    @FXML
    private Button archiveBTN;

    @FXML
    private Label dateLbl;

    @FXML
    private AnchorPane orderTab;

    @FXML
    private Button orderTabBTN;

    @FXML
    private Label timeLbl;

    @FXML
    private GridPane ordersTabGP;

    @FXML
    private GridPane archiveGP;

    private Stage stage;

    ObservableList<KitchenCardData> kitchenCardDataList = kitchenGetData();

    private ObservableList<KitchenCardData> kitchenCardData = FXCollections.observableArrayList();

    ObservableList<ArchiveCardData> archiveCardDataList = kitchenGetArchive();

    private ObservableList<ArchiveCardData> archiveCardData = FXCollections.observableArrayList();

    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private volatile boolean stop = false;

    private void DateLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(DateFormat);
        dateLbl.setText(formattedDate);
    }

    private void Timenow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    timeLbl.setText(timenow); // This is the label
                });
            }
        });

        thread.start();
    }

    @FXML
    public void getOrders() throws SQLException {
        kitchenCardData.clear();
        kitchenCardData.addAll(kitchenGetData());
        orderTabsGrid();
    }

    @FXML
    public void refresh() throws SQLException {
        kitchenCardData.clear();
    }

    public ObservableList<KitchenCardData> kitchenGetData() {
        String sql = "SELECT DISTINCT customer_id FROM invoice";

        ObservableList<KitchenCardData> listData = FXCollections.observableArrayList();
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.getConnection();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                String customerID = result.getString("customer_id");

                // Create a KitchenCardData object and add it to the list
                KitchenCardData kitchenCardData = new KitchenCardData(customerID);
                listData.add(kitchenCardData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (result, prepare, connect) if needed
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listData;
    }

    private boolean isCustomerDataPresent(String customerID) {
        String sql = "SELECT customer_id FROM invoice WHERE customer_id = ?";
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.getConnection();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, customerID);
            result = prepare.executeQuery();

            return result.next(); // If result.next() is true, data is present; otherwise, it's not present
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception or other error
        } finally {
            // Close resources (result, prepare, connect) if needed
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void orderTabsGrid() throws SQLException {
        ordersTabGP.getChildren().clear();
        int column = 0;
        int row = 1;

        for (KitchenCardData kitchenCardData : kitchenCardDataList) {
            try {
                // Check if the data for the customer is present
                if (isCustomerDataPresent(kitchenCardData.getCustomerID())) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Kitchen/KitchenCardFXML.fxml"));
                    AnchorPane pane = loader.load();

                    // Access the controller and set the data
                    KitchenCardFXMLController kitchenCardFXMLController = loader.getController();
                    kitchenCardFXMLController.setKitchenCardData(kitchenCardData);

                    // Set the reference to KitchenFXMLController
                    kitchenCardFXMLController.setKitchenController(this);

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    ordersTabGP.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(10));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            orderTabsGrid();
            DateLabel();
            Timenow();

            CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    try {
                        Stage stage = (Stage) CloseButton.getScene().getWindow();
                        stage.close();

                        // Open the login window.
                        LoginTest loginTest = new LoginTest();
                        loginTest.start(new Stage());
                    } catch (Exception ex) {
                        Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(KitchenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Button lastClickedButton = null;

    @FXML
    public void SwitchForm(ActionEvent event) throws SQLException {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton == lastClickedButton) {
            // Ignore the click if the same button was clicked twice in a row
            return;
        }

        if (clickedButton == orderTabBTN) {
            orderTab.setVisible(true);
            archive.setVisible(false);

        } else if (clickedButton == archiveBTN) {
            orderTab.setVisible(false);
            archive.setVisible(true);
        }

    }

    ///////////////////////////////////////////////////
    public void getArchive() throws SQLException {
        System.out.println("getArchive method called.");

        // Fetch new data from the database
        archiveCardData.clear();
        archiveCardData.addAll(kitchenGetArchive());

        // Manually trigger UI updates on the JavaFX Application Thread
        Platform.runLater(() -> {
            updateArchiveUI();
        });

        System.out.println("getArchive method completed.");
    }

    private void updateArchiveUI() {
        try {
            archiveTabsGrid();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ObservableList<ArchiveCardData> kitchenGetArchive() {
        String sql = "SELECT DISTINCT customer_id FROM invoice_archive";

        ObservableList<ArchiveCardData> listData = FXCollections.observableArrayList();
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.getConnection();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                String customerID = result.getString("customer_id");

                // Create a KitchenCardData object and add it to the list
                ArchiveCardData archiveCardData = new ArchiveCardData(customerID);
                listData.add(archiveCardData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (result, prepare, connect) if needed
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listData;
    }

    private boolean isCustomerDataPresentArchive(String customerID) {
        String sql = "SELECT customer_id FROM invoice_archive WHERE customer_id = ?";
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.getConnection();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, customerID);
            result = prepare.executeQuery();

            return result.next(); // If result.next() is true, data is present; otherwise, it's not present
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception or other error
        } finally {
            // Close resources (result, prepare, connect) if needed
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void archiveTabsGrid() throws SQLException {
        ordersTabGP.getChildren().clear();
        int column = 0;
        int row = 1;

        for (ArchiveCardData archiveCardData : archiveCardDataList) {
            try {
                // Check if the data for the customer is present
                if (isCustomerDataPresentArchive(archiveCardData.getCustomerID())) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Kitchen/ArchiveCardFXML.fxml"));
                    AnchorPane pane = loader.load();

                    // Access the controller and set the data
                    ArchiveCardFXMLController archiveCardFXMLController = loader.getController();
                    archiveCardFXMLController.setArchiveKitchenCardData(archiveCardData);

                    // Set the reference to KitchenFXMLController
                    archiveCardFXMLController.setKitchenController1(this);

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }

                    ordersTabGP.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(10));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
