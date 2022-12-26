package com.myosotis;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import java.util.Base64; //Für Passwörterspeicherung

public class login extends Application {
  Color light_steel_blue = Color.web("#A8C0F1");
  Color french_sky_blue = Color.web("#95B3E8");
  Color maximum_blue_purple = Color.web("#A7BAF0");
  Color beige = Color.web("#DAE2D2");

  public void showUI() {
    // Startet die JavaFX-Anwendung und ruft die start-Methode auf
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
      // Setzen Sie den Titel des Fensters
      primaryStage.setTitle("Myosotis : Login");
      
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
      root1.setBackground(new Background(new BackgroundFill(light_steel_blue, CornerRadii.EMPTY, Insets.EMPTY)));

      // Erstellt den Wurzelknoten und die zweite Szene
      StackPane root2 = new StackPane();
      Scene scene2 = new Scene(root2, initial_width, initial_height);
      root2.setBackground(new Background(new BackgroundFill(beige, CornerRadii.EMPTY, Insets.EMPTY)));

      // Erstellen Sie einen Titel
      Label title = new Label("Myosotis");
      title.getStyleClass().add("h1");
      scene1.getStylesheets().add("https://fonts.googleapis.com/css2?family=Playfair+Display&display=swap");
      title.setTextFill(Color.BLACK);
      
      // Erstellen Sie zwei Textfelder
      TextField user_name_imput = new TextField();
      user_name_imput.setMaxWidth(200);
      PasswordField password_input = new PasswordField();
      password_input.setMaxWidth(200);

      // Erstellt eine Fehlermeldung, wenn der Benutzername oder das Passwort falsch ist
      Label error_message = new Label("Username or Password is wrong");
      error_message.setVisible(false);

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
          else {
            error_message.setVisible(true);
          }
      });


      // Erstellen Sie zwei Labels für die Textfelder
      Label label1 = new Label("Username:");
      Label label2 = new Label("Passwort:");
      
      
      root1.getChildren().addAll(
        title,
        error_message,
        label1, 
        user_name_imput, 
        label2, 
        password_input, 
        login_button
        );

      root1.setAlignment(Pos.CENTER);
      root1.setSpacing(10);
      // Setzen Sie die Scene und machen Sie das Fenster sichtbar
      primaryStage.setScene(scene1);
      primaryStage.show();

  }

  
}