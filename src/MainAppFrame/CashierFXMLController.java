/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

import Login.ControllerInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import other.menu1;

/**
 * FXML Controller class
 *
 * @author Jasper
 */
public class CashierFXMLController implements Initializable, ControllerInterface {

     double xOffset, yOffset;
    
     @FXML
     private ImageView CloseButton;
     
     @FXML
     private Stage stage;
     
    @FXML
    private GridPane menuGrid;
      
    private List<menu1> menus;
    
    private List<menu1> getMenu(){
        List<menu1> ls = new ArrayList<>();
        
        menu1 menu = new menu1();
        menu.setName("Classic Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);
        
        menu = new menu1();
        menu.setName("Taro Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);
        
        menu = new menu1();
        menu.setName("Almond Milktea");
        menu.setImgSrc("/img/ClassicMT.jpg");
        ls.add(menu);
        
        return ls;
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
      
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          menus = new ArrayList<>(getMenu());
          int column = 0;
          int row = 1;
          
          for(menu1 menu: menus) {
              try {
                  FXMLLoader fxmlloader = new FXMLLoader();
                  fxmlloader.setLocation(getClass().getResource("Menu.fxml"));
                  
                  Pane pane = fxmlloader.load();
                  MenuController menucontroller = fxmlloader.getController();
                  menucontroller.setData(menu);
                  
                  if(column == 1) {
                      column = 0;
                      ++row;
                  }
                  
                  menuGrid.add(pane, column++, row);
                  GridPane.setMargin(pane, new Insets(20));
                          
                       
                          
              } catch (IOException ex) {
                  Logger.getLogger(CashierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        
          CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
             
                stage.close();
            }
        });
        
        
        
       
    }    
    
}
