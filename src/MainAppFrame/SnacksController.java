package MainAppFrame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import other.menu6;

public class SnacksController {

    @FXML
    private RadioButton askmeRadioHead;

    @FXML
    private Button confirmButton1;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    @FXML
    private Spinner<?> spinnerQuantity;
    
    private menu6 menuData; 
    
    public void setData(menu6 menu) {
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
