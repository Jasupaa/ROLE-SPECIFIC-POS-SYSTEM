/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package rolespecific_pos_system;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

/**
 * FXML Controller class
 *
 * @author Jasper
 */
public class WlaController  {
  double xOffset, yOffset;

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
    private ImageView closebutton;
    @FXML
    private Pane Pane;
    Stage stage;

@FXML   
          public void initialize() {
              
              
        closebutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Define the action when the ImageView is clicked (e.g., close the window)
                stage.close();
            }
        });
         KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> TimeLabel());
         Timeline timeline = new Timeline(keyFrame);
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
        
        DateLabel();
        TimeLabel();
}
          @FXML
          private Button Button0, Button1, Button2,Button3, Button4, Button5, Button6, Button7, Button8 , Button9;
          @FXML
          private Label CodeLabel;
          @FXML
          private void showNumber(ActionEvent event) {
          Button clickedButton = (Button) event.getSource();
          String buttonText = clickedButton.getText();
          if (CodeLabel.getText().length() < 6) {
          CodeLabel.setText(CodeLabel.getText() + buttonText);
    }
          }
          
         @FXML
         private void clearCodeLabel(){
             CodeLabel.setText("");
             
             
         }
         
         @FXML
         private Label DateLbl;
         @FXML
         private Label TimeLbl;
      
         @FXML
         private void DateLabel(){
             LocalDate currentDate = LocalDate.now();
             DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", Locale.ENGLISH);
             String formattedDate = currentDate.format(DateFormat);
             
             DateLbl.setText(formattedDate);
         }
         
         @FXML
         private void TimeLabel(){
             LocalTime currenttime = LocalTime.now();
             DateTimeFormatter TimeFormat = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
             String formattedTime = currenttime.format(TimeFormat);
             TimeLbl.setText(formattedTime);
             
             
             
             
             
         }
          

}





   

 
