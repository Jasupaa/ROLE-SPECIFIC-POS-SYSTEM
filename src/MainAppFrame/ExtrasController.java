package MainAppFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import other.menu7;

public class ExtrasController {

    @FXML
    private Spinner spinnerQuantity;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;
    private menu7 menuData;

    private boolean askmeRadioSelected = false;

    private static int customerCounter = 0;
    private boolean orderTaken = false;

    public void initialize() {

        // Set the Spinner to allow only whole number values and set the default value to 0
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinnerQuantity.setValueFactory(valueFactory);

        // Set a StringConverter to display the Spinner values as whole numbers
        StringConverter<Integer> converter = new IntegerStringConverter();
        spinnerQuantity.getValueFactory().setConverter(converter);

    }

    public void setData(menu7 menu) {
        menuData = menu;
        Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        foodImg.setImage(image);
        foodLabel.setText(menu.getName());
    }

}
