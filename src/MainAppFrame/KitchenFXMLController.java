/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

import Login.ControllerInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jasper
 */
public class KitchenFXMLController implements Initializable, ControllerInterface {
double xOffset, yOffset;
    
     @FXML
     private ImageView CloseButton;
     
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
      
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
             
                stage.close();
            }
        });
  
     if (stage != null) {
            stage.initStyle(StageStyle.TRANSPARENT);
        }
  
    }    
    
}
