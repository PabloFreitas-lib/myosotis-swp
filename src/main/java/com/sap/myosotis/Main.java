package com.sap.myosotis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Gui gui = new Gui();
        gui.shows();
    }
}
