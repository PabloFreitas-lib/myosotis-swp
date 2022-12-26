package com.myosotis;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class gui extends Application {
  public void showUI() {
    // Startet die JavaFX-Anwendung und ruft die start-Methode auf
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
      // Setzen Sie die Größe des Fensters auf 1000x700
      int initial_width = 1000;
      int initial_height = 700;
      primaryStage.setWidth(initial_width);
      primaryStage.setHeight(initial_height);

      // Setzen Sie die maximale und minimale Größe des Fensters auf null, um das Fenster skalierbar zu machen
      primaryStage.setMaxHeight(Double.MAX_VALUE);
      primaryStage.setMaxWidth(Double.MAX_VALUE);
      primaryStage.setMinHeight(400);
      primaryStage.setMinWidth(600);

      // Erstellt den Wurzelknoten und die erste Szene
      VBox root1 = new VBox();
      Scene scene1 = new Scene(root1, initial_width, initial_height);
      scene1.getStylesheets().add("file:///C:/Users/kasim/Desktop/myosotis/myosotis/src/resources/style.css");

      // Erstellt den Wurzelknoten und die zweite Szene
      StackPane root2 = new StackPane();
      Scene scene2 = new Scene(root2, initial_width, initial_height);

      // Erstellen Sie zwei Textfelder
      TextField user_name_imput = new TextField();
      TextField password_input = new TextField();
      user_name_imput.setMaxWidth(200);
      password_input.setMaxWidth(200);

      // Erstellt einen Login Knopf, um zwischen den Szenen zu wechseln
      Button login_button = new Button();
      login_button.getStyleClass().add("button_blue");
      login_button.setText("Login");
      login_button.setOnAction(event -> {
        String user_Name = user_name_imput.getText();
        String password = password_input.getText();
          if(user_Name.equals("admin") && password.equals("admin")) {
            primaryStage.setScene(scene2);
          } 
          else if (user_Name.equals("admin") && !password.equals("admin")) {
            System.out.println("Wrong password");
          }
          else {
            System.out.println("Wrong username");
          }
      });

      // Erstellen Sie zwei Labels für die Textfelder
      Label label1 = new Label("Username:");
      Label label2 = new Label("Passwort:");
      
      
      root1.getChildren().addAll(label1, user_name_imput, label2, password_input, login_button);
      root1.setAlignment(Pos.CENTER);
      root1.setSpacing(10);
      // Setzen Sie die Scene und machen Sie das Fenster sichtbar
      primaryStage.setScene(scene1);
      primaryStage.show();

  }
}