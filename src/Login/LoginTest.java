package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginTest extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file and set the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wla.fxml"));
        LoginController controller = new LoginController();
        loader.setController(controller);
        Parent root = loader.load();
        
        // Set the stage for the controller
        controller.setStage(stage);

        // Create and set the scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        
        // Set the stage style and scene
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

