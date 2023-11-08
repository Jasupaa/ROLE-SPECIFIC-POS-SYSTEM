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
import other.menu4;

/**
 *
 * @author John Paul Uy
 */
public class CoffeeController {
    
    @FXML
    private RadioButton askmeRadioHead;

    @FXML
    private Button confirmButton1;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    @FXML
    private ComboBox<?> sizeComboBox;

    @FXML
    private Spinner<?> spinnerQuantity;

    @FXML
    private ComboBox<?> sugarlevelComboBox;
    
    private menu4 menuData; 

    
    public void setData(menu4 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }
    
    
    @FXML
    void askmeRadioHeadSelected(ActionEvent event) {

    }

    @FXML
    void confirmButton1(ActionEvent event) {

    }

    
}
