/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import javafx.scene.control.TextField;

/**
 *
 * @author Jasper
 */
public class TxtUtils {
     public static void restrictLetter(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }

    public static void limitCharacters(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Limit the length of the text
            if (textField.getText().length() > maxLength) {
                String limitedText = textField.getText().substring(0, maxLength);
                textField.setText(limitedText);
            }
        });
    }
}
