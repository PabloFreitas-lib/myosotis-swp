package com.sap.myosotis;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GUI {
    private Stage primaryStage;
    public void shows() {
        StackPane layout = new StackPane();
        Scene scene = new Scene(layout);
        // Setzen Sie die Größe des Fensters auf 800x600
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);

        // Setzen Sie die maximale und minimale Größe des Fensters auf null, um das Fenster skalierbar zu machen
        primaryStage.setMaxHeight(Double.MAX_VALUE);
        primaryStage.setMaxWidth(Double.MAX_VALUE);
        primaryStage.setMinHeight(0);
        primaryStage.setMinWidth(0);

        // Setzen Sie die Scene und machen Sie das Fenster sichtbar
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
