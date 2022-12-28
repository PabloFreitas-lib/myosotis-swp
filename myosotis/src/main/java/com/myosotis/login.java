package com.myosotis;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.Base64; //Für Passwörterspeicherung

public class login extends Application {
  Color light_steel_blue = Color.web("#A8C0F1");
  Color french_sky_blue = Color.web("#95B3E8");
  Color maximum_blue_purple = Color.web("#A7BAF0");
  Color beige = Color.web("#DAE2D2");
  int initial_width = 1000;
  int initial_height = 700;

  public void showUI() {
    // Startet die JavaFX-Anwendung und ruft die start-Methode auf
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    // Erstellt die zweite Szene
    StackPane root2 = new StackPane();
    Scene scene2 = new Scene(root2, initial_width, initial_height);
    root2.setBackground(new Background(new BackgroundFill(beige, CornerRadii.EMPTY, Insets.EMPTY)));

    //Login Scene folgt auf scene2
    primaryStage.setScene(loginScene(primaryStage, scene2));
    primaryStage.show();
  }

  Scene loginScene(Stage primaryStage, Scene scene2) {
    // Setze den Titel des Fensters
    primaryStage.setTitle("Myosotis:Login");
      
    // Setze die Größe des Fensters auf 1000x700
    primaryStage.setWidth(initial_width);
    primaryStage.setHeight(initial_height);

    // Setze die maximale und minimale Größe des Fensters auf null, um das Fenster skalierbar zu machen
    primaryStage.setMaxHeight(Double.MAX_VALUE);
    primaryStage.setMaxWidth(Double.MAX_VALUE);
    primaryStage.setMinHeight(500);
    primaryStage.setMinWidth(600);

    // Erstellt den Wurzelknoten und die Szene
    VBox root1 = new VBox();
    Scene scene = new Scene(root1, initial_width, initial_height);

    //Laden der CSS-Datei
    Path filePath = Paths.get("myosotis/src/resources/style.css");
    scene.getStylesheets().add(filePath.toUri().toString());
    root1.setBackground(new Background(new BackgroundFill(light_steel_blue, CornerRadii.EMPTY, Insets.EMPTY)));


    // Erstellt eine Liste mit allen Elementen der login Szene
    ArrayList<Node> elemente_login = new ArrayList<>();

    // Erstelle einen Titel //TODO: Titel ändern für Logo
    Label title = new Label("Myosotis");
    title.getStyleClass().add("h1");
    scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Playfair+Display&display=swap");
    title.setTextFill(Color.BLACK);
    elemente_login.add(title);

    // Erstellt eine Fehlermeldung, wenn der Benutzername oder das Passwort falsch ist
    Label error_message = new Label("Benutzername oder Password ist falsch");
    error_message.setVisible(false);
    elemente_login.add(error_message);

    // Erstelle zwei Textfelder und Label für den Benutzernamen und das Passwort
    Label label1 = new Label("Benutzername:");
    TextField user_name_input = new TextField();
    user_name_input.setMaxWidth(200);
    elemente_login.add(label1);
    elemente_login.add(user_name_input);

    Label label2 = new Label("Passwort:");
    PasswordField password_input = new PasswordField();
    password_input.setMaxWidth(200);
    elemente_login.add(label2);
    elemente_login.add(password_input);

    // Erstellt einen Login Knopf, um zwischen den Szenen zu wechseln
    Button login_button = new Button();
    login_button.getStyleClass().add("button_blue");
    login_button.setText("Login");
    login_button.setOnAction(event -> {
      String user_Name = user_name_input.getText();
      String password = password_input.getText();
        if(user_Name.equals("admin") && password.equals("admin")) {
          primaryStage.setScene(scene2);
        }
        else {
          error_message.setVisible(true);
        }
    });
    elemente_login.add(login_button);
    
    // Fügt alle Elemente der Szene hinzu
    root1.getChildren().addAll(elemente_login);
    root1.setAlignment(Pos.CENTER);
    root1.setSpacing(10);

    // Setzen Sie die Scene und machen Sie das Fenster sichtbar
    return scene;
  }
  
}