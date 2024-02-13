import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Objects;

public class CasinoGUI extends Application implements Observer<CasinoModel, String> {
    private CasinoModel model;
    private final static String RESOURCES_DIR = "resources/";
    private Label topLabel;
    private Button[][] buttonArray;
    private Label message = new Label();
    private final Font basicFont = new Font("Ariel", 19);
    private TextField usernameField = new TextField();
    private TextField passwordField = new TextField();


    /**
     * In the init the GUI creates the model and adds itself as an observer
     * of it.
     */
    @Override
    public void init() {
        this.model = new CasinoModel();
        model.addObserver(this);
        System.out.println("init: Initialize and connect to model!");
    }

    @Override
    public void start(Stage stage) {
        buttonArray = new Button[2][3];
        Scene scene;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // LABEL SECTION
        topLabel = new Label("Please Sign Up or Login");
        topLabel.setFont(basicFont);
        topLabel.setAlignment(Pos.TOP_CENTER);
        usernameField.setPromptText("Enter your Username");
        passwordField.setPromptText("Enter your Password");


        // BUTTON SECTION
        Button signUpButton = new Button("Sign up");
        signUpButton.setMinSize(200, 150);
        signUpButton.setFont(basicFont);
        signUpButton.setAlignment(Pos.CENTER_LEFT);
        signUpButton.setOnAction(event -> model.signUp());

        Button logInButton = new Button("Login");
        logInButton.setMinSize(200, 150);
        logInButton.setFont(basicFont);
        logInButton.setAlignment(Pos.CENTER_RIGHT);
        logInButton.setOnAction(event -> model.setLoginScreen());

        HBox startScreenHBox = new HBox(signUpButton, logInButton);
        startScreenHBox.setAlignment(Pos.CENTER);
        VBox startScreenVBox = new VBox(topLabel, startScreenHBox);
        startScreenVBox.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Submit");
        submitButton.setMinSize(200, 150);
        submitButton.setFont(basicFont);
        submitButton.setAlignment(Pos.CENTER_LEFT);
        submitButton.setOnAction(event -> model.signUp());


        GridPane.setConstraints(usernameField, 0, 0);
        grid.getChildren().add(usernameField);
        GridPane.setConstraints(passwordField, 0, 1);
        grid.getChildren().add(passwordField);
        GridPane.setConstraints(submitButton, 0, 3);
        grid.getChildren().add(submitButton);

        submitButton.setOnAction(e -> {
            if ((usernameField.getText() != null && !passwordField.getText().isEmpty())) {
                topLabel.setText(usernameField.getText() + ", thank you for logging in!");
                model.login(usernameField.getText(), passwordField.getText());
            } else {
                topLabel.setText("You have not entered the required fields.");
            }
        });



        scene = new Scene(startScreenVBox);
        stage.setTitle("Casino GUI");
        update(model, "Please Sign Up or Login");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void update(CasinoModel casinoModel, String text) {
        model = casinoModel;
        topLabel.setText(text);
    }
}
