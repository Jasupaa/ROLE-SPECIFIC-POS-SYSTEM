package Login;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoginTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the splash screen
        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("SplashScreenFXML.fxml"));
        Parent splashRoot = splashLoader.load();
        Scene splashScene = new Scene(splashRoot);
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.setScene(splashScene);
        splashStage.show();

        // Set up a Timeline to wait for 5 seconds
        Duration duration = Duration.seconds(1);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // Close the splash screen
            splashStage.close();

            // Load the login screen
            try {
                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
                LoginController loginController = new LoginController();
                loginLoader.setController(loginController);
                Parent loginRoot = loginLoader.load();
                Scene loginScene = new Scene(loginRoot);
                loginScene.setFill(null);

                // Set the stage for the login controller
                loginController.setStage(primaryStage);

                // Set the stage style and scene for the main stage
                primaryStage.initStyle(StageStyle.TRANSPARENT);
                primaryStage.setScene(loginScene);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
}



