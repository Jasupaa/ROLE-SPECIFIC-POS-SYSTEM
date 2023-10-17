/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package rolespecific_pos_system;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos; 
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


/**
 *
 * @author Jasper
 */
public class TESTLANGS extends Application {
    
    /*@Override
    public void start(Stage primaryStage) {
        Button btn2 = new Button();
        
        
        btn2.setText(" ERE! ");
        btn2.setOnAction(new EventHandler<ActionEvent>(){
         
            @Override   
            
            public void handle(ActionEvent event) {
                System.out.println("Tatlong Bilyon Ikaw lang ang aking GUSTOO!");
            }
        });
            
         
            
        
       
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        VBox vbox = new VBox();
        vbox.getChildren().addAll(btn, btn2);
        vbox.setAlignment(Pos.CENTER);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
    
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
        
        StackPane root = new StackPane(); 
        
        BackgroundFill backgroundfill = new BackgroundFill(Color.web("#d9d9d9"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        //pang bago ng kulay                                                             ^
        Background background = new Background(backgroundfill);
        root.setBackground(background);
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Test lang Pwede Gamitin");
        primaryStage.setScene(scene);
        
        
        primaryStage.show();
        
    }
}
    

