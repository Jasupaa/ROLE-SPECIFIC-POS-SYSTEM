package MainAppFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;

public class KeypadFXMLController {

    @FXML
    private TextField targetTextField; // The text field to which the keypad inputs should be applied

    @FXML
    private void keypadButtonAction(javafx.event.ActionEvent event) {
        // Get the text from the button pressed
        String buttonText = ((Button) event.getSource()).getText();

        // Append the text to the target text field
        targetTextField.appendText(buttonText);
    }

    public void setTargetTextField(TextField textField) {
        this.targetTextField = textField;
    }
}
