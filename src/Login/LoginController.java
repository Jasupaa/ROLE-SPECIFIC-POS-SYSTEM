package Login;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class LoginController {
    
     private DbLoginAccess dbLoginAccess;
    

    double xOffset, yOffset;
    private volatile boolean stop = false;

    @FXML
    private ImageView closebutton;

    @FXML
    private Pane Pane;

    @FXML
    private Button Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9;

    @FXML
    private Label CodeLabel;

    @FXML
    private Label DateLbl;

    @FXML
    private Label TimeLbl;

    Stage stage;
    
     private void CashierFrame(){
         
        System.out.println("Cashier");
        loadFXML("/MainAppFrame/CashierFXML.fxml");
         
    
    
    }
    
    private void KitchenFrame(){
      
    System.out.println("Kitchen");
    loadFXML("/MainAppFrame/KitchenFXML.fxml");
     
    }
    
    private void AdminFrame(){
           
    System.out.println("Admin");
    loadFXML("/MainAppFrame/AdminFXML.fxml");
    
   
    }
    

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

    @FXML
    public void initialize() {
        
        closebutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
             
                stage.close();
            }
        });

        DateLabel();
        Timenow();
        dbLoginAccess = new DbLoginAccess();
    }

    @FXML
    private void showNumber(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        if (CodeLabel.getText().length() < 6) {
            CodeLabel.setText(CodeLabel.getText() + buttonText);
        }
    }

    @FXML
    private void clearCodeLabel() {
        CodeLabel.setText("");
    }

    private void DateLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(DateFormat);
        DateLbl.setText(formattedDate);
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
                    TimeLbl.setText(timenow); // This is the label
                });
            }
        });

        thread.start();
    }
    
    @FXML
   private void LoginHandler(ActionEvent event) {
    String codeLabelText = CodeLabel.getText();
    String numericPart = codeLabelText.replaceAll("[^0-9]", "");
    int enteredCode = 0; // Initialize the enteredCode variable

    if (!numericPart.isEmpty()) {
        enteredCode = Integer.parseInt(numericPart);

        DbLoginAccess databaseAccess = new DbLoginAccess();
        String role = databaseAccess.checkAccessCode(enteredCode);

        if (role != null) {
            switch (role) {
                case "cashier":
                    CashierFrame();
                    break;
                case "kitchen":
                    KitchenFrame();
                    break;
                case "admin":
                    AdminFrame();
                    
                    break;
                default:
                   handleInvalidCode("Invalid Role", enteredCode);
            }
           
        } 
        
        
        else {
            
            
         handleInvalidCode("Invalid Code", enteredCode);
        }
    }
    
}

    private void handleInvalidCode(String errorMessage, int enteredCode) {
    CodeLabel.setText("Error Code: " + " (Code: " + enteredCode + ")");
   
}
    
    
   
 
    private void loadFXML(String fxmlPath) {
    try {
       
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent pane = loader.load();
        
        
        ControllerInterface controller = loader.getController();
        if (controller != null) {
            controller.setStage(stage);
        } 
  
   
         Scene scene = new Scene(pane);
        
           scene.setFill(Color.TRANSPARENT);
        
       
       
        stage.setScene(scene);
        stage.show();
         
    } catch (IOException e) {
        e.printStackTrace();
        // Handle any exceptions
    }
    
    

}
    
    
    
  
    
    
}


//wala lng