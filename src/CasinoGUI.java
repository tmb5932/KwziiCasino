import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private Label topLabel = new Label("Please Sign Up or Login");
    private Button[][] buttonArray;
    private Label loginMessage = new Label("Login");
    private Label signupMessage = new Label("Sign up");
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
        TextField loginUsernameField = new TextField();
        PasswordField loginPasswordField = new PasswordField();
        TextField signupUsernameField = new TextField();
        PasswordField signupPasswordField = new PasswordField();

        // LABEL SECTION
        topLabel.setFont(basicFont);
        topLabel.setAlignment(Pos.TOP_CENTER);
        signupMessage.setFont(basicFont);
        signupMessage.setAlignment(Pos.TOP_CENTER);
        loginMessage.setFont(basicFont);
        loginMessage.setAlignment(Pos.TOP_CENTER);
        loginUsernameField.setPromptText("Enter your Username");
        loginPasswordField.setPromptText("Enter your Password");
        loginUsernameField.setAlignment(Pos.CENTER);
        loginPasswordField.setAlignment(Pos.CENTER);
//        loginUsernameField.setFocusTraversable(false); // TODO: Decide if i want this on or off
//        loginPasswordField.setFocusTraversable(false); // TODO: Decide if i want this on or off
        signupUsernameField.setPromptText("Enter your Username");
        signupPasswordField.setPromptText("Enter your Password");
        signupUsernameField.setAlignment(Pos.CENTER);
        signupPasswordField.setAlignment(Pos.CENTER);
//        signupUsernameField.setFocusTraversable(false); // TODO: Decide if i want this on or off
//        signupPasswordField.setFocusTraversable(false); // TODO: Decide if i want this on or off


        // BUTTON SECTION
        Button signUpButton = new Button("Sign up");
        signUpButton.setMinSize(200, 150);
        signUpButton.setFont(basicFont);
        signUpButton.setAlignment(Pos.CENTER);
        signUpButton.setTextAlignment(TextAlignment.CENTER);
        signUpButton.setOnAction(event -> model.setScene(Scenes.SIGNUP));
        signUpButton.setFocusTraversable(false);

        Button logInButton = new Button("Login");
        logInButton.setMinSize(200, 150);
        logInButton.setFont(basicFont);
        logInButton.setAlignment(Pos.CENTER);
        logInButton.setTextAlignment(TextAlignment.CENTER);
        logInButton.setOnAction(event -> {
            model.setScene(Scenes.LOGIN);
            System.out.println(loginPasswordField.getStyle());
        });
        logInButton.setFocusTraversable(false);

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

        // Create a new account screen
        Button signupSubmitButton = new Button("Submit");
        signupSubmitButton.setMinSize(125, 50);
        signupSubmitButton.setFont(basicFont);
        signupSubmitButton.setAlignment(Pos.CENTER);
        VBox signupVBox = new VBox(signupMessage, signupUsernameField, signupPasswordField, signupSubmitButton, signupBackButton);
        signupSubmitButton.setOnAction(e -> {
            if ((signupUsernameField.getText() != null && !signupPasswordField.getText().isEmpty())) {
                signupMessage.setText(signupUsernameField.getText() + ", thank you for signing up!");
                model.signUp(signupUsernameField.getText(), signupPasswordField.getText());
            } else {
                signupMessage.setText("You have not entered the required fields.");
            }
        });
        signupVBox.setSpacing(10);
        signupVBox.setPadding(new Insets(10, 50, 10, 50));
        signupVBox.setAlignment(Pos.CENTER);
        signupScene = new Scene(signupVBox, 480, 244);

        // Log into a previously existing account screen
        Button loginSubmitButton = new Button("Submit");
        loginSubmitButton.setMinSize(125, 50);
        loginSubmitButton.setFont(basicFont);
        loginSubmitButton.setAlignment(Pos.CENTER);
        VBox loginVBox = new VBox(loginMessage, loginUsernameField, loginPasswordField, loginSubmitButton, loginBackButton);
        loginSubmitButton.setOnAction(e -> {
            if ((loginUsernameField.getText() != null && !loginPasswordField.getText().isEmpty())) {
                loginMessage.setText(loginUsernameField.getText() + ", thank you for logging in!");
                model.login(loginUsernameField.getText(), loginPasswordField.getText());
            } else {
                loginMessage.setText("You have not entered the required fields.");
            }
        });
        loginVBox.setSpacing(10);
        loginVBox.setPadding(new Insets(10, 50, 10, 50));
        loginVBox.setAlignment(Pos.CENTER);
        loginScene = new Scene(loginVBox, 480, 244);

        HBox startScreenHBox = new HBox(signUpButton, logInButton);
        startScreenHBox.setAlignment(Pos.CENTER);
        startScreenHBox.setSpacing(10);

        VBox startScreenVBox = new VBox(topLabel, startScreenHBox);
        startScreenVBox.setAlignment(Pos.CENTER);

        startScreenVBox.setSpacing(30);
        startScreenVBox.setPadding(new Insets(20, 35, 20, 35));

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
        if (text != null) {
            switch (casinoModel.currentScene) {
                case LOGIN ->  loginMessage.setText(text);

                case SIGNUP -> signupMessage.setText(text);

                case HOMEPAGE -> topLabel.setText(text);

                case GAMESTAGE -> topLabel.setText(text);
            }
        }
    }
}
