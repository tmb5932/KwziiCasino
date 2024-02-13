import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CasinoGUI extends Application implements Observer<CasinoModel, String> {
    private CasinoModel model;
    private final static String RESOURCES_DIR = "resources/";
    private Label topLabel;
    private Button[][] buttonArray;
    private Label message = new Label();
    private final Font basicFont = new Font("Ariel", 19);
    private Stage mainStage;
    private Scene mainScene;
    private Scene loginScene;
    private Scene signupScene;
    private Scene gamesScene;


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
        mainStage = stage;
        buttonArray = new Button[2][3];
        TextField usernameField1 = new TextField();
        TextField passwordField1 = new TextField();
        TextField usernameField2 = new TextField();
        TextField passwordField2 = new TextField();

        GridPane signupGrid = new GridPane();
        signupGrid.setPadding(new Insets(10, 10, 10, 10));
        signupGrid.setVgap(5);
        signupGrid.setHgap(5);

        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10, 10, 10));
        loginGrid.setVgap(5);
        loginGrid.setHgap(5);

        // LABEL SECTION
        topLabel = new Label("Please Sign Up or Login");
        topLabel.setFont(basicFont);
        topLabel.setAlignment(Pos.TOP_CENTER);
        usernameField1.setPromptText("Enter your Username");
        passwordField1.setPromptText("Enter your Password");
        usernameField1.setFocusTraversable(false);
        passwordField1.setFocusTraversable(false);
        usernameField2.setPromptText("Enter your Username");
        passwordField2.setPromptText("Enter your Password");
        usernameField2.setFocusTraversable(false);
        passwordField2.setFocusTraversable(false);

        signupScene = new Scene(signupGrid);

        // BUTTON SECTION
        Button signUpButton = new Button("Sign up");
        signUpButton.setMinSize(200, 150);
        signUpButton.setFont(basicFont);
        signUpButton.setAlignment(Pos.CENTER_LEFT);
        signUpButton.setTextAlignment(TextAlignment.CENTER);
        signUpButton.setOnAction(event -> model.setScene(Scenes.SIGNUP));

        loginScene = new Scene(loginGrid);

        Button logInButton = new Button("Login");
        logInButton.setMinSize(200, 150);
        logInButton.setFont(basicFont);
        logInButton.setAlignment(Pos.CENTER_RIGHT);
        logInButton.setTextAlignment(TextAlignment.CENTER);
        logInButton.setOnAction(event -> model.setScene(Scenes.LOGIN));

        Button signupBackButton = new Button("Back");
        signupBackButton.setMinSize(125, 50);
        signupBackButton.setFont(basicFont);
        signupBackButton.setAlignment(Pos.CENTER);
        signupBackButton.setTextAlignment(TextAlignment.CENTER);

        Button loginBackButton = new Button("Back");
        loginBackButton.setMinSize(125, 50);
        loginBackButton.setFont(basicFont);
        loginBackButton.setAlignment(Pos.CENTER);
        loginBackButton.setTextAlignment(TextAlignment.CENTER);


        HBox startScreenHBox = new HBox(signUpButton, logInButton);
        startScreenHBox.setAlignment(Pos.CENTER);
        VBox startScreenVBox = new VBox(topLabel, startScreenHBox);
        startScreenVBox.setAlignment(Pos.CENTER);

        // Log into a previously existing account screen
        Button submitLoginButton = new Button("Submit");
        submitLoginButton.setMinSize(125, 50);
        submitLoginButton.setFont(basicFont);
        submitLoginButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(usernameField1, 0, 0);
        loginGrid.getChildren().add(usernameField1);
        GridPane.setConstraints(passwordField1, 0, 1);
        loginGrid.getChildren().add(passwordField1);
        GridPane.setConstraints(submitLoginButton, 0, 3);
        loginGrid.getChildren().add(submitLoginButton);
        GridPane.setConstraints(loginBackButton, 0, 4);
        loginGrid.getChildren().add(loginBackButton);
        submitLoginButton.setOnAction(e -> {
            if ((usernameField1.getText() != null && !passwordField1.getText().isEmpty())) {
                topLabel.setText(usernameField1.getText() + ", thank you for logging in!");
                model.login(usernameField1.getText(), passwordField1.getText());
            } else {
                topLabel.setText("You have not entered the required fields.");
            }
        });

        // Create a new account screen
        Button submitSignupButton = new Button("Submit");
        submitSignupButton.setMinSize(125, 50);
        submitSignupButton.setFont(basicFont);
        submitSignupButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(usernameField2, 0, 0);
        signupGrid.getChildren().add(usernameField2);
        GridPane.setConstraints(passwordField2, 0, 1);
        signupGrid.getChildren().add(passwordField2);
        GridPane.setConstraints(submitSignupButton, 0, 3);
        signupGrid.getChildren().add(submitSignupButton);
        GridPane.setConstraints(signupBackButton, 0, 4);
        signupGrid.getChildren().add(signupBackButton);
        submitSignupButton.setOnAction(e -> {
            if ((usernameField2.getText() != null && !passwordField2.getText().isEmpty())) {
                topLabel.setText(usernameField2.getText() + ", thank you for signing up!");
                model.signUp(usernameField2.getText(), passwordField2.getText());
            } else {
                topLabel.setText("You have not entered the required fields.");
            }
        });

        mainScene = new Scene(startScreenVBox);
        signupBackButton.setOnAction(event -> model.setScene(Scenes.HOMEPAGE));
        loginBackButton.setOnAction(event -> model.setScene(Scenes.HOMEPAGE));
        mainStage.setTitle("Casino GUI");
        update(model, "Please Sign Up or Login");
        mainStage.setScene(mainScene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    @Override
    public void update(CasinoModel casinoModel, String text) {
        model = casinoModel;
        switch (casinoModel.currentScene) {
            case LOGIN -> mainStage.setScene(loginScene);
            case SIGNUP -> mainStage.setScene(signupScene);
            case HOMEPAGE -> mainStage.setScene(mainScene);
            case GAMESTAGE -> mainStage.setScene(gamesScene);
        }
        topLabel.setText(text);
        message.setText(text);
    }
}
