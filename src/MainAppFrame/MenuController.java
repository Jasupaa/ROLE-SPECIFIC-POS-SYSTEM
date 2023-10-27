/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package MainAppFrame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import other.menu1;

/**
 * FXML Controller class
 *
 * @author John Paul Uy
 */
public class MenuController {
    
    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;
    
    @FXML
    private Button confirmButton1;
    
    private menu1 menuData;
    
    public void setData(menu1 menu){
            menuData = menu;
            Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
            foodImg.setImage(image);
            foodLabel.setText(menu.getName());
    }
    
     
    public void confirmButton1(ActionEvent event) {
        if (menuData != null) {
            String menuName = menuData.getName();
            // You can use menuName in your code as needed
            System.out.println("Selected menu: " + menuName);
        }
    }
}

