package com.myosotis;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Scenes {
    int initial_width = 1000;
    int initial_height = 700;
    
    Scene basicScene(Stage primaryStage){
        // Erstellt den Wurzelknoten und die Szene
        VBox root1 = new VBox();
        Scene scene = new Scene(root1, initial_width, initial_height);
        return scene;
    }
    
}
