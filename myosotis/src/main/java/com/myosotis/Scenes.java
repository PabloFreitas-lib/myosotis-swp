package com.myosotis;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Scenes {
    int initial_width = 1000;
    int initial_height = 700;

    Scene basicScene(Stage primaryStage, ArrayList<Node> nodesRight, ArrayList<Node> nodesLeft, ArrayList<Node> nodesTop, ArrayList<Node> nodesCenter, String title) {
        // Erstellt den Wurzelknoten und die Szene
        VBox root = new VBox();
        Scene scene = new Scene(root, initial_width, initial_height);
        return scene;
    }
    
}
