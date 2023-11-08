/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainAppFrame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import other.menu1;
import other.menu2;

/**
 *
 * @author John Paul Uy
 */
public class FruitDrinkController {

    @FXML
    private Spinner<?> spinnerQuantity;
    
    @FXML
    private Button confirmButton1;
    
    @FXML
    private ComboBox<?> sizeComboBox;
    
    @FXML
    private ComboBox<?> addonsComboBox;
    
    @FXML
    private ComboBox<?> sugarlevelComboBox;
   
    @FXML
    private RadioButton askmeRadioHead;
    
    @FXML
    private ImageView foodImg;
    
    @FXML
    private Label foodLabel;
    
    private menu2 menuData;  
    
    public void setData(menu2 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }
    
    @FXML
    private void confirmButton1(ActionEvent event) {
    }

    @FXML
    private void askmeRadioHeadSelected(ActionEvent event) {
    }
    
}
