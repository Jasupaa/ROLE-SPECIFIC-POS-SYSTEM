package Login;

import MainAppFrame.CashierFXMLController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import other.ControllerManager;

public class LoginController {

    private database dbLoginAccess;

    double xOffset, yOffset;
    private volatile boolean stop = false;

    @FXML
    private ImageView closebutton;

    @FXML
    private Pane Pane;

    @FXML
    private Button Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9;

    @FXML
    private Label CodeLabel;

    @FXML
    private Label DateLbl;

    @FXML
    private Label TimeLbl;

    Stage stage;

    private void CashierFrame() {
        System.out.println("Cashier");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainAppFrame/CashierFXML.fxml"));
        try {
            Parent cashierRoot = loader.load();

            // Obtain the controller instance
            CashierFXMLController cashierController = loader.getController();

            // Set the reference in the ControllerManager
            ControllerManager.setCashierController(cashierController);

            // Continue with your existing code...
            Scene scene = new Scene(cashierRoot);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions
        }
    }

    private void KitchenFrame() {

        System.out.println("Kitchen");
        loadFXML("/MainAppFrame/KitchenFXML.fxml");

    }

    private void AdminFrame() {

        System.out.println("Admin");
        loadFXML("/MainAppFrame/AdminFXML.fxml");

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

    @FXML
    public void initialize() {

        closebutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

        DateLabel();
        Timenow();
        dbLoginAccess = new database();
    }

    @FXML
    private void showNumber(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        if (CodeLabel.getText().length() < 6) {
            CodeLabel.setText(CodeLabel.getText() + buttonText);
        }
    }

    @FXML
    private void clearCodeLabel() {
        CodeLabel.setText("");
    }

    private void DateLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(DateFormat);
        DateLbl.setText(formattedDate);
    }

    private void Timenow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    TimeLbl.setText(timenow); // This is the label
                });
            }
        });

        thread.start();
    }

    @FXML
    private void LoginHandler(ActionEvent event) {
        String enteredCode = CodeLabel.getText();
        String role = authenticateUser(enteredCode);

        if (role != null) {
            switch (role) {
                case "cashier":
                    CashierFrame();
                    break;
                case "kitchen":
                    KitchenFrame();
                    break;
                case "admin":
                    AdminFrame();
                    break;
                default:
                    // Handle unexpected role
                    break;
            }
        } else {
            handleInvalidCode("Invalid code entered", enteredCode);
        }
    }

    private void handleInvalidCode(String errorMessage, String enteredCode) {
        CodeLabel.setText("Error Code: " + " (Code: " + enteredCode + ")");

    }

    private String authenticateUser(String enteredCode) {
        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT Role FROM employees WHERE Code = ?")) {
            statement.setString(1, enteredCode);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("Role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Authentication failed
    }

    private void loadFXML(String fxmlPath) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent pane = loader.load();

            ControllerInterface controller = loader.getController();
            if (controller != null) {
                controller.setStage(stage);
            }

            Scene scene = new Scene(pane);

            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions
        }

    }

}
