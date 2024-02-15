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
    private Label startScreenLabel = new Label("Please Sign Up or Login");
    private Label homeLabel = new Label("Choose a game :)");
    private Button[][] homeGameArray;
    private Label loginMessage = new Label("Login");
    private Label signupMessage = new Label("Sign up");
    private final Font basicFont = new Font("Ariel", 19);
    private Stage mainStage;
    private Scene startupScene;
    private Scene loginScene;
    private Scene signupScene;
    private Scene homeScene;


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
        homeGameArray = new Button[2][3];
        TextField loginUsernameField = new TextField();
        PasswordField loginPasswordField = new PasswordField();
        TextField signupUsernameField = new TextField();
        PasswordField signupPasswordField = new PasswordField();

        // LABEL SECTION
        startScreenLabel.setFont(basicFont);
        startScreenLabel.setAlignment(Pos.TOP_CENTER);
        signupMessage.setFont(basicFont);
        signupMessage.setAlignment(Pos.TOP_CENTER);
        loginMessage.setFont(basicFont);
        loginMessage.setAlignment(Pos.TOP_CENTER);
        homeLabel.setFont(basicFont);
        homeLabel.setAlignment(Pos.CENTER);
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
            // Startup screen buttons
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
        logInButton.setOnAction(event -> model.setScene(Scenes.LOGIN));
        logInButton.setFocusTraversable(false);

        // Creating Back buttons
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
        signupScene = new Scene(signupVBox, 500, 250);

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
        loginScene = new Scene(loginVBox, 500, 250);

        // Game Home Buttons
        Button blackjackButton = new Button("Blackjack");
        blackjackButton.setMinSize(150, 100);
        blackjackButton.setFont(basicFont);
        blackjackButton.setAlignment(Pos.CENTER);

        Button rouletteButton = new Button("Roulette");
        rouletteButton.setMinSize(150, 100);
        rouletteButton.setFont(basicFont);
        rouletteButton.setAlignment(Pos.CENTER);

        Button pokerButton = new Button("Poker");
        pokerButton.setMinSize(150, 100);
        pokerButton.setFont(basicFont);
        pokerButton.setAlignment(Pos.CENTER);

        Button slotsButton = new Button("Slots");
        slotsButton.setMinSize(150, 100);
        slotsButton.setFont(basicFont);
        slotsButton.setAlignment(Pos.CENTER);

        Button coinFlipButton = new Button("Coin Flips");
        coinFlipButton.setMinSize(150, 100);
        coinFlipButton.setFont(basicFont);
        coinFlipButton.setAlignment(Pos.CENTER);

        Button horsesButton = new Button("Horse Betting");
        horsesButton.setMinSize(150, 100);
        horsesButton.setFont(basicFont);
        horsesButton.setAlignment(Pos.CENTER);

        // Game Home Scene
        GridPane homeGameGrid = new GridPane();
        homeGameGrid.setPadding(new Insets(10, 10, 10, 10));
        homeGameGrid.setVgap(5);
        homeGameGrid.setHgap(5);
        homeGameGrid.setAlignment(Pos.CENTER);
        GridPane.setConstraints( blackjackButton, 0, 1);
        homeGameGrid.getChildren().add(blackjackButton);
        GridPane.setConstraints(rouletteButton, 1, 1);
        homeGameGrid.getChildren().add(rouletteButton);
        GridPane.setConstraints(pokerButton, 2, 1);
        homeGameGrid.getChildren().add(pokerButton);
        GridPane.setConstraints(slotsButton, 0, 2);
        homeGameGrid.getChildren().add(slotsButton);
        GridPane.setConstraints(coinFlipButton, 1, 2);
        homeGameGrid.getChildren().add(coinFlipButton);
        GridPane.setConstraints(horsesButton, 2, 2);
        homeGameGrid.getChildren().add(horsesButton);

        VBox homeVBox = new VBox(homeLabel, homeGameGrid);
        homeVBox.setPadding(new Insets(20, 0, 20, 0));
        homeVBox.setAlignment(Pos.CENTER);

        homeScene = new Scene(homeVBox, 500, 275);


        HBox startScreenHBox = new HBox(signUpButton, logInButton);
        startScreenHBox.setAlignment(Pos.CENTER);
        startScreenHBox.setSpacing(10);

        VBox startScreenVBox = new VBox(startScreenLabel, startScreenHBox);
        startScreenVBox.setAlignment(Pos.CENTER);

        startScreenVBox.setSpacing(30);
        startScreenVBox.setPadding(new Insets(20, 35, 20, 35));

        startupScene = new Scene(startScreenVBox, 500, 250);
        signupBackButton.setOnAction(event -> model.setScene(Scenes.STARTUP));
        loginBackButton.setOnAction(event -> model.setScene(Scenes.STARTUP));
        mainStage.setTitle("Casino GUI");
        update(model, "Please Sign Up or Login");
        mainStage.setScene(startupScene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    @Override
    public void update(CasinoModel casinoModel, String text) {
        model = casinoModel;
        switch (casinoModel.currentScene) {
            case LOGIN -> mainStage.setScene(loginScene);

            case SIGNUP -> mainStage.setScene(signupScene);

            case STARTUP -> mainStage.setScene(startupScene);

            case HOME -> mainStage.setScene(homeScene);
        }
        if (text != null) {
            switch (casinoModel.currentScene) {
                case LOGIN ->  loginMessage.setText(text);

                case SIGNUP -> signupMessage.setText(text);

                case STARTUP -> startScreenLabel.setText(text);

                case HOME -> homeLabel.setText(text);
            }
        }
    }
}
