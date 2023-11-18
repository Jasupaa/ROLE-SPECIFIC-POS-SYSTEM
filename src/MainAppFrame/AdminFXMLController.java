package MainAppFrame;

import Login.ControllerInterface;
import Login.LoginTest;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AdminFXMLController implements Initializable, ControllerInterface  {
    
    double xOffset, yOffset;
    
    @FXML
    private AnchorPane AdminPane;

    @FXML
    private ImageView CloseButton;

    @FXML
    private Label dateLbl;

    @FXML
    private AnchorPane disCoup;

    @FXML
    private Button disCoupBTN;

    @FXML
    private AnchorPane empDetails;

    @FXML
    private Button empDetailsBTN;

    @FXML
    private AnchorPane invManage;

    @FXML
    private Button invManageBTN;

    @FXML
    private AnchorPane salesRep;

    @FXML
    private Button salesRepBTN;

    @FXML
    private Label timeLbl;

    @FXML
    private Stage stage;
     
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    }
    
    private Button lastClickedButton = null;

    @FXML
    public void SwitchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton == lastClickedButton) {
            // Ignore the click if the same button was clicked twice in a row
            return;
        }

        if (clickedButton == salesRepBTN) {
            // ... (rest of the code remains the same)
        }

        // Update the last clicked button
        lastClickedButton = clickedButton;
        if (clickedButton == salesRepBTN) {

            salesRep.setVisible(true);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == invManageBTN) {

            salesRep.setVisible(false);
            invManage.setVisible(true);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == empDetailsBTN) {
           
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(true);
            disCoup.setVisible(false);

        } else if (clickedButton == disCoupBTN) {
           
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(true);

        }
    }
   
}
