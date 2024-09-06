import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The main GUI program for the Casino Project
 * @author Travis Brown (Kwzii)
 */
public class CasinoGUI extends Application implements Observer<CasinoModel, String> {
    public static final Font basicFont = new Font("Ariel", 19);
    public static final Font largeFont = new Font("Ariel", 24);
    private CasinoModel model;
    private final static String RESOURCES_DIR = "resources/";
    private boolean loggedin;
    private final Label startScreenLabel = new Label("Please Sign Up or Login");
    private final Label homeLabel = new Label("Choose a game :)");
    private final Label loginMessage = new Label("Login");
    private final Label signupMessage = new Label("Sign up");
    private final Label bjCreditLabel = new Label("Credits: " + 0);
    private final Label homeCreditLabel = new Label("Credits: " + 0);
    private final Label blackjackAlertLabel = new Label("");
    private final Label coinCreditLabel = new Label("Credits: " + 0);
    private final Label coinAlertLabel = new Label("");
    private final Label rubixAlertLabel = new Label("");
    private final Label horseAlertLabel = new Label("");
    private final Label horseCreditLabel = new Label("Credits: " + 0);
    private final Label slotsAlertLabel = new Label("");
    private final Label slotsCreditLabel = new Label("Credits: " + 0);
    private GridPane topRubixGrid;
    private GridPane frontRubixGrid;
    private GridPane leftRubixGrid;
    private GridPane rightRubixGrid;
    private GridPane botRubixGrid;
    private GridPane backRubixGrid;
    Rectangle[][] rubixFrontFaceColors;
    Rectangle[][] rubixTopFaceColors;
    Rectangle[][] rubixLeftFaceColors;
    Rectangle[][] rubixRightFaceColors;
    Rectangle[][] rubixBotFaceColors;
    Rectangle[][] rubixBackFaceColors;
    private boolean hRaceFinished;
    private int numPlayerCards = 0;
    private int numDealerCards = 0;
    private final double cardWidth = 120.5;
    private int horsesFinished = 0;
    private Stage mainStage;
    private Scene startupScene;
    private Scene loginScene;
    private Scene signupScene;
    private Scene homeScene;
    private Scene blackjackScene;
    private Scene rouletteScene;
    private Scene rubixScene;
    private Scene slotsScene;
    private Scene coinflipScene;
    private Scene horsebetScene;

    /**
     * In the init the GUI creates the model and adds itself as an observer
     * of it.
     */
    @Override
    public void init() {
        this.model = new CasinoModel();
        model.addObserver(this);
        populateCards();
        System.out.println("init: Initialize and connect to model!");
    }

    /**
     * Calls stages to be created and shows the main stage
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {
        createMainStage(stage);
        blackjackScene = blackjack();
        coinflipScene = createCoinScene();
        horsebetScene = createHorseRace();
        rubixScene = createRubixScene();
        slotsScene = createSlotsScene();
        mainStage.setTitle("Casino GUI");
        update(model, "Please Sign Up or Login");
        mainStage.setScene(startupScene);
        mainStage.sizeToScene();
        mainStage.show();
        centerScreen();
    }

    /**
     * Creates the main Stage that the initial view will show
     * @param stage the primary stage for this application, onto which
     *  the application scene can be set.
     *  Applications may create other stages, if needed, but they will not be
     *  primary stages.
     */
    public void createMainStage(Stage stage) {
        mainStage = stage;
        TextField loginUsrField = new TextField();
        PasswordField loginPassField = new PasswordField();
        TextField signupUsrField = new TextField();
        PasswordField signupPassField = new PasswordField();

        // LABEL SECTION
        homeCreditLabel.setFont(basicFont);
        homeCreditLabel.setAlignment(Pos.CENTER);

        startScreenLabel.setFont(basicFont);
        startScreenLabel.setAlignment(Pos.TOP_CENTER);

        signupMessage.setFont(basicFont);
        signupMessage.setAlignment(Pos.TOP_CENTER);

        loginMessage.setFont(basicFont);
        loginMessage.setAlignment(Pos.TOP_CENTER);

        homeLabel.setFont(basicFont);
        homeLabel.setAlignment(Pos.CENTER);

        loginUsrField.setPromptText("Enter your Username");
        loginUsrField.setAlignment(Pos.CENTER);

        loginPassField.setPromptText("Enter your Password");
        loginPassField.setAlignment(Pos.CENTER);

        signupUsrField.setPromptText("Enter your Username");
        signupUsrField.setAlignment(Pos.CENTER);

        signupPassField.setPromptText("Enter your Password");
        signupPassField.setAlignment(Pos.CENTER);


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

        // Create new account buttons
        Button signupBackButton = new Button("Back");
        signupBackButton.setMinSize(125, 50);
        signupBackButton.setFont(basicFont);
        signupBackButton.setAlignment(Pos.CENTER);
        signupBackButton.setTextAlignment(TextAlignment.CENTER);

        Button signupSubmitButton = new Button("Submit");
        signupSubmitButton.setMinSize(125, 50);
        signupSubmitButton.setFont(basicFont);
        signupSubmitButton.setAlignment(Pos.CENTER);
        VBox signupVBox = new VBox(signupMessage, signupUsrField,
                signupPassField, signupSubmitButton, signupBackButton);
        signupSubmitButton.setOnAction(event -> {
            if ((signupUsrField.getText() != null && !signupPassField.getText().isEmpty())) {
                model.updateModel(signupUsrField.getText() + ", thank you for signing up!");
                model.signUp(signupUsrField.getText(), signupPassField.getText());
            } else {
                model.updateModel("You have not entered the required fields.");
            }
        });
        signupVBox.setSpacing(10);
        signupVBox.setPadding(new Insets(10, 50, 10, 50));
        signupVBox.setAlignment(Pos.CENTER);
        signupScene = new Scene(signupVBox, 500, 250);

        // Log into previously existing account screen
        Button loginBackButton = new Button("Back");
        loginBackButton.setMinSize(125, 50);
        loginBackButton.setFont(basicFont);
        loginBackButton.setAlignment(Pos.CENTER);
        loginBackButton.setTextAlignment(TextAlignment.CENTER);

        Button loginSubmitButton = new Button("Submit");
        loginSubmitButton.setMinSize(125, 50);
        loginSubmitButton.setFont(basicFont);
        loginSubmitButton.setAlignment(Pos.CENTER);
        VBox loginVBox = new VBox(loginMessage, loginUsrField, loginPassField,
                loginSubmitButton, loginBackButton);
        loginSubmitButton.setOnAction(event -> {
            if ((loginUsrField.getText() != null && !loginPassField.getText().isEmpty())) {
                model.updateModel(loginUsrField.getText() + ", thank you for logging in!");
                model.login(loginUsrField.getText(), loginPassField.getText());
            } else {
                model.updateModel("You have not entered the required fields.");
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
        blackjackButton.setOnAction(event -> model.setScene(Scenes.BLACKJACK));

        Button rouletteButton = new Button("Roulette");
        rouletteButton.setMinSize(150, 100);
        rouletteButton.setFont(basicFont);
        rouletteButton.setAlignment(Pos.CENTER);

        Button rubixButton = new Button("Rubix Cube");
        rubixButton.setMinSize(150, 100);
        rubixButton.setFont(basicFont);
        rubixButton.setAlignment(Pos.CENTER);
        rubixButton.setOnAction(event -> model.setScene(Scenes.RUBIX));

        Button slotsButton = new Button("Slots");
        slotsButton.setMinSize(150, 100);
        slotsButton.setFont(basicFont);
        slotsButton.setAlignment(Pos.CENTER);
        slotsButton.setOnAction(event -> model.setScene(Scenes.SLOTS));

        Button coinFlipButton = new Button("Coin Flips");
        coinFlipButton.setMinSize(150, 100);
        coinFlipButton.setFont(basicFont);
        coinFlipButton.setAlignment(Pos.CENTER);
        coinFlipButton.setOnAction(event -> model.setScene(Scenes.COINFLIP));


        Button horsesButton = new Button("Horse Betting");
        horsesButton.setMinSize(150, 100);
        horsesButton.setFont(basicFont);
        horsesButton.setAlignment(Pos.CENTER);
        horsesButton.setOnAction(event -> model.setScene(Scenes.HORSEBETTING));

        // Game Home Scene
        GridPane homeGameGrid = new GridPane();
        homeGameGrid.setPadding(new Insets(10, 10, 10, 10));
        homeGameGrid.setVgap(15);
        homeGameGrid.setHgap(15);
        homeGameGrid.setAlignment(Pos.CENTER);
        GridPane.setConstraints(homeCreditLabel, 4, 0);
        homeGameGrid.getChildren().add(homeCreditLabel);
        GridPane.setConstraints(blackjackButton, 0, 1);
        homeGameGrid.getChildren().add(blackjackButton);
        GridPane.setConstraints(rouletteButton, 1, 1);
        homeGameGrid.getChildren().add(rouletteButton);
        GridPane.setConstraints(rubixButton, 2, 1);
        homeGameGrid.getChildren().add(rubixButton);
        GridPane.setConstraints(slotsButton, 0, 2);
        homeGameGrid.getChildren().add(slotsButton);
        GridPane.setConstraints(coinFlipButton, 1, 2);
        homeGameGrid.getChildren().add(coinFlipButton);
        GridPane.setConstraints(horsesButton, 2, 2);
        homeGameGrid.getChildren().add(horsesButton);

        VBox homeVBox = new VBox(homeLabel, homeGameGrid);
        homeVBox.setPadding(new Insets(20, 0, 20, 0));
        homeVBox.setAlignment(Pos.CENTER);
        homeScene = new Scene(homeVBox, 800, 450);

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
    }

    /**
     * Creates the blackjack scene and all of its actions
     * @return the created blackjack scene
     */
    public Scene blackjack() {                      // todo: maybe add the ability to split if the numbers are the same
        Label dealerLabel = new Label("Dealer");
        dealerLabel.setFont(largeFont);
        Label playerLabel = new Label("Player");
        playerLabel.setFont(largeFont);
        playerLabel.setFont(largeFont);

        blackjackAlertLabel.setFont(basicFont);

        HBox playerHand = new HBox();
        playerHand.setMinHeight(175);

        HBox dealerHand = new HBox();
        dealerHand.setMinHeight(175);

        Button hitButton = new Button("HIT");
        hitButton.setMinSize(100, 75);
        hitButton.setFont(basicFont);
        hitButton.setAlignment(Pos.CENTER);
        hitButton.setTextAlignment(TextAlignment.CENTER);

        Button stayButton = new Button("STAY");
        stayButton.setMinSize(100, 75);
        stayButton.setFont(basicFont);
        stayButton.setAlignment(Pos.CENTER);
        stayButton.setTextAlignment(TextAlignment.CENTER);

        Button submitBjBetButton = new Button("Submit Bet");
        submitBjBetButton.setMinSize(100, 75);
        submitBjBetButton.setFont(basicFont);
        submitBjBetButton.setAlignment(Pos.CENTER);
        submitBjBetButton.setTextAlignment(TextAlignment.CENTER);

        Button playAgainBjButton = new Button("Play Again?");
        playAgainBjButton.setMinSize(100, 75);
        playAgainBjButton.setFont(basicFont);
        playAgainBjButton.setAlignment(Pos.CENTER);
        playAgainBjButton.setTextAlignment(TextAlignment.CENTER);

        TextField enterBetField = new TextField();
        enterBetField.setPromptText("Enter your bet");
        enterBetField.setAlignment(Pos.CENTER);

        ImageView backCard = new ImageView(new Image("file:" + model.getBackCard()));
        backCard.setFitHeight(175);
        backCard.setPreserveRatio(true);

        ImageView backCardStack = new ImageView(new Image("file:" + model.getBackCard()));
        backCardStack.setFitHeight(175);
        backCardStack.setPreserveRatio(true);

        HBox buttonHBox = new HBox(enterBetField, submitBjBetButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(10);

        submitBjBetButton.setOnAction(e -> {
            if (!enterBetField.getText().isEmpty()) {
                try {
                    if (model.placeBet(Integer.parseInt(enterBetField.getText())) == 0) {
                        blackjackAlertLabel.setText("");
                        buttonHBox.getChildren().removeAll(enterBetField, submitBjBetButton);

                        ImageView dealer1 = new ImageView(new Image("file:" + model.dealerHitBlackjack()));
                        dealer1.setFitWidth(cardWidth);
                        dealer1.setPreserveRatio(true);
                        dealerHand.getChildren().add(dealer1);
                        dealer1.setVisible(false);

                        ImageView dealer2 = new ImageView(new Image("file:" + model.getBackCard()));
                        dealer2.setFitHeight(175);
                        dealer2.setPreserveRatio(true);
                        dealerHand.getChildren().add(dealer2);
                        dealer2.setVisible(false);

                        ImageView player1 = new ImageView(new Image("file:" + model.playerHitBlackjack()));
                        player1.setFitWidth(cardWidth);
                        player1.setPreserveRatio(true);
                        playerHand.getChildren().add(player1);
                        player1.setVisible(false);

                        ImageView player2 = new ImageView(new Image("file:" + model.playerHitBlackjack()));
                        player2.setFitWidth(cardWidth);
                        player2.setPreserveRatio(true);
                        playerHand.getChildren().add(player2);
                        player2.setVisible(false);

                        FadeTransition fd = placeCard(dealer1, backCard, 'D');
                        fd.setOnFinished(event -> {
                            FadeTransition fd1 = placeCard(dealer2, backCard, 'D');
                            fd1.setOnFinished(event1 -> {
                                FadeTransition fd2 = placeCard(player1, backCard, 'P');
                                fd2.setOnFinished(event2 -> {
                                    FadeTransition fd3 = placeCard(player2, backCard, 'P');
                                    fd3.setOnFinished(event3 -> {
                                        buttonHBox.getChildren().addAll(hitButton, stayButton);
                                        if (model.initialCheckBjWin() == GameResults.WIN) {
                                        buttonHBox.getChildren().removeAll(hitButton, stayButton);
                                        buttonHBox.getChildren().addAll(playAgainBjButton);
                                        }
                                    });
                                });
                            });
                        });
                    }
                } catch (NumberFormatException nfe) {
                    model.updateModel("Please enter a number");
                }
            } else {
                model.updateModel("You have not entered a bet");
            }
        });

        playAgainBjButton.setOnAction(event -> {
            buttonHBox.getChildren().remove(playAgainBjButton);
            buttonHBox.getChildren().addAll(enterBetField, submitBjBetButton);
            model.resetCardDeck();
            playerHand.getChildren().clear();
            dealerHand.getChildren().clear();
            numPlayerCards = 0;
            numDealerCards = 0;
            enterBetField.setText("");
        });
        hitButton.setOnAction(event -> {
            ImageView temp = new ImageView(new Image("file:" + model.playerHitBlackjack()));
            temp.setFitWidth(cardWidth);
            temp.setPreserveRatio(true);
            playerHand.getChildren().add(temp);
            temp.setVisible(false);
            placeCard(temp, backCard, 'P');
            if (model.midCheckBjWin() != GameResults.NONE) {
                dealerHand.getChildren().remove(1);
                numDealerCards--;
                ImageView dealer1 = new ImageView(new Image("file:" + model.dealerHitBlackjack()));
                dealer1.setFitWidth(cardWidth);
                dealer1.setPreserveRatio(true);
                dealerHand.getChildren().add(dealer1);
                buttonHBox.getChildren().removeAll(hitButton, stayButton);
                buttonHBox.getChildren().addAll(playAgainBjButton);
            }
        });

        stayButton.setOnAction(event -> {
            dealerHand.getChildren().remove(1);
            numDealerCards--;
            ImageView dealer1 = new ImageView(new Image("file:" + model.dealerHitBlackjack()));
            dealer1.setFitWidth(cardWidth);
            dealer1.setPreserveRatio(true);
            dealerHand.getChildren().add(dealer1);
//            dealer1.setVisible(false);
//            placeCard(dealer1, backCard, 'D');
            while (model.bjGetHandTotal('D') < 17) {
                ImageView temp = new ImageView(new Image("file:" + model.dealerHitBlackjack()));
                temp.setFitWidth(cardWidth);
                temp.setPreserveRatio(true);
                dealerHand.getChildren().add(temp);
//                temp.setVisible(false);
//                placeCard(temp, backCard, 'D');
            }
            model.finalCheckBjWin();
            buttonHBox.getChildren().removeAll(hitButton, stayButton);
            buttonHBox.getChildren().addAll(playAgainBjButton);
        });

        Button gameBackButton = new Button("Back");
        gameBackButton.setMinSize(125, 50);
        gameBackButton.setFont(basicFont);
        gameBackButton.setAlignment(Pos.CENTER);
        gameBackButton.setTextAlignment(TextAlignment.CENTER);
        gameBackButton.setOnAction(event ->{
            model.resetCardDeck();
            playerHand.getChildren().clear();
            dealerHand.getChildren().clear();
            numPlayerCards = 0;
            numDealerCards = 0;
            if(buttonHBox.getChildren().contains(hitButton)) {
                buttonHBox.getChildren().removeAll(hitButton, stayButton);
                buttonHBox.getChildren().addAll(enterBetField, submitBjBetButton);
            } else if (buttonHBox.getChildren().contains(playAgainBjButton)) {
                buttonHBox.getChildren().remove(playAgainBjButton);
                buttonHBox.getChildren().addAll(enterBetField, submitBjBetButton);
            }
            model.placeBet(0);
            enterBetField.setText("");
            model.setScene(Scenes.HOME);
        });

        bjCreditLabel.setFont(basicFont);
        bjCreditLabel.setAlignment(Pos.BOTTOM_CENTER);
        HBox topHBox = new HBox(bjCreditLabel, gameBackButton);
        topHBox.setAlignment(Pos.TOP_RIGHT);
        topHBox.setSpacing(45);

        StackPane deckPane = new StackPane(backCardStack, backCard);

        VBox centerVBox = new VBox(dealerLabel, dealerHand, playerLabel, playerHand);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setSpacing(30);
        centerVBox.setPadding(new Insets(20, 35, 20, 35));

        VBox bottomVBox = new VBox(blackjackAlertLabel, buttonHBox);
        bottomVBox.setAlignment(Pos.CENTER);
        bottomVBox.setSpacing(30);
        bottomVBox.setPadding(new Insets(20, 35, 20, 35));

        BorderPane bPane = new BorderPane();
        bPane.setTop(topHBox);
        bPane.setCenter(centerVBox);
        bPane.setRight(deckPane);
        bPane.setBottom(bottomVBox);
        bPane.setPadding(new Insets(20, 35, 20, 35));
        return new Scene(bPane, 1200, 800);
    }

    /**
     * Method to animate cards being placed on the table
     * @param imgV image view that is being replaced by the card
     */
    public FadeTransition placeCard(ImageView imgV, ImageView backCard, char who) {
        Bounds startCoords = backCard.localToScene(backCard.getBoundsInLocal());
        Bounds endCoords = imgV.localToScene(imgV.getBoundsInLocal());
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(backCard);

        if (who == 'D') {
            double xDist;
            if (numDealerCards < 2)
                xDist = (endCoords.getMaxX() + (0)) - startCoords.getMaxX();
            else
                xDist = (endCoords.getMaxX() + (cardWidth * numDealerCards)) - startCoords.getMaxX();
            translate.setDuration(Duration.millis(-xDist * 1.75));
            translate.setByX(xDist);
            numDealerCards++;
        }
        else if (who == 'P'){
            double xDist;
            if (numPlayerCards < 2)
                xDist = ((endCoords.getMaxX() + (0)) - startCoords.getMaxX());
            else
                xDist = ((endCoords.getMaxX() + (cardWidth * numPlayerCards)) - startCoords.getMaxX());
            translate.setDuration(Duration.millis(-xDist*1.75));
            translate.setByX(xDist);
            numPlayerCards++;
        }
        translate.setByY(endCoords.getMinY() - startCoords.getMinY());
        translate.play();

        FadeTransition fade = new FadeTransition();
        fade.setNode(backCard);
        fade.setDuration(Duration.millis(700));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);

        translate.setOnFinished(event -> {
            imgV.setVisible(true);
            fade.play();
        });

        FadeTransition fadeBack = new FadeTransition();
        fadeBack.setNode(backCard);
        fadeBack.setDuration(Duration.millis(1));
        fadeBack.setInterpolator(Interpolator.LINEAR);
        fadeBack.setFromValue(0);
        fadeBack.setToValue(1);

        TranslateTransition transitionBack = new TranslateTransition();
        transitionBack.setNode(backCard);

        transitionBack.setDuration(Duration.millis(10));
        if (who == 'D') {
            if (numDealerCards < 3)
                transitionBack.setByX((startCoords.getMaxX() - cardWidth*(0)) - (endCoords.getMaxX()));
            else
                transitionBack.setByX((startCoords.getMaxX() - cardWidth*(numDealerCards-1)) - (endCoords.getMaxX()));
        }
        else if (who == 'P'){
            if (numPlayerCards < 3)
                transitionBack.setByX((startCoords.getMaxX() - cardWidth*(0)) - (endCoords.getMaxX()));
            else
                transitionBack.setByX((startCoords.getMaxX() - cardWidth*(numPlayerCards-1)) - (endCoords.getMaxX()));
        }
        transitionBack.setByY(startCoords.getMinY() - endCoords.getMinY());

        fade.setOnFinished(event -> transitionBack.play());
        transitionBack.setOnFinished(event -> fadeBack.play());
        return fadeBack;
    }

    /**
     * Method to create all playing cards from text file to use for card games
     */
    public void populateCards() {
        String fileName = "data/playingCardsPNG.txt";
        File file = new File(fileName);
        ArrayList<PlayingCards> pcards = new ArrayList<>();
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            PlayingCards.Face face;
            PlayingCards.Suit suitVal = null;
            while ((line = br.readLine()) != null) {
                int val = 10;
                String[] info = line.strip().split("_");
                if (line.equals("card_back.png")) {
                    continue;
                }
                switch (info[0]) {
                    case "ace" -> {
                        face = PlayingCards.Face.ACE;
                        val = 11;
                    }
                    case "jack" -> face = PlayingCards.Face.JACK;
                    case "king" -> face = PlayingCards.Face.KING;
                    case "queen" -> face = PlayingCards.Face.QUEEN;
                    default -> {
                        face = PlayingCards.Face.NONFACE;
                        val = Integer.parseInt(info[0]);
                    }
                }
                switch (info[2]) {
                    case "hearts.png" -> suitVal = PlayingCards.Suit.HEARTS;
                    case "spades.png" -> suitVal = PlayingCards.Suit.SPADES;
                    case "diamonds.png" -> suitVal = PlayingCards.Suit.DIAMONDS;
                    case "clubs.png" -> suitVal = PlayingCards.Suit.CLUBS;
                }
                PlayingCards card = new PlayingCards(suitVal, face, val, RESOURCES_DIR + "Blackjack/PNG-cards/" + line);
                pcards.add(card);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.setFullCardDeck(pcards);
    }

    /**
     * Method to create coin flip game scene
     */
    public Scene createCoinScene() {
        coinAlertLabel.setFont(basicFont);
        coinAlertLabel.setAlignment(Pos.CENTER);

        TextField betField = new TextField();
        betField.setPromptText("Enter bet");
        betField.setAlignment(Pos.CENTER);
        betField.setFont(new Font(15));

        Button betHeadsButton = new Button("Bet Heads");
        betHeadsButton.setMinSize(200, 125);
        betHeadsButton.setFont(basicFont);
        betHeadsButton.setAlignment(Pos.CENTER);
        betHeadsButton.setTextAlignment(TextAlignment.CENTER);
        betHeadsButton.setFocusTraversable(false);

        Button betTailsButton = new Button("Bet Tails");
        betTailsButton.setMinSize(200, 125);
        betTailsButton.setFont(basicFont);
        betTailsButton.setAlignment(Pos.CENTER);
        betTailsButton.setTextAlignment(TextAlignment.CENTER);
        betTailsButton.setFocusTraversable(false);

        Button coinBackButton = new Button("Back");
        coinBackButton.setMinSize(125, 60);
        coinBackButton.setFont(basicFont);
        coinBackButton.setAlignment(Pos.CENTER);
        coinBackButton.setTextAlignment(TextAlignment.CENTER);

        Button playAgainButton = new Button("Play Again?");
        playAgainButton.setMinSize(125, 60);
        playAgainButton.setFont(basicFont);
        playAgainButton.setAlignment(Pos.CENTER);
        playAgainButton.setTextAlignment(TextAlignment.CENTER);

        coinCreditLabel.setFont(basicFont);
        HBox topHBox = new HBox(coinCreditLabel, coinBackButton);
        topHBox.setAlignment(Pos.TOP_RIGHT);
        topHBox.setSpacing(45);

        ImageView coinHeadImV = new ImageView(new Image("file:" + RESOURCES_DIR + model.getCoin().getCurrentHead()));
        coinHeadImV.setFitHeight(250);
        coinHeadImV.setPreserveRatio(true);

        ImageView coinTailImV = new ImageView(new Image("file:" + RESOURCES_DIR + model.getCoin().getCurrentTail()));
        coinTailImV.setFitHeight(250);
        coinTailImV.setPreserveRatio(true);

        HBox buttonsHBox = new HBox(betHeadsButton, betTailsButton);
        buttonsHBox.setSpacing(25);
        buttonsHBox.setAlignment(Pos.CENTER);

        VBox removableVBox = new VBox(betField, buttonsHBox);
        removableVBox.setAlignment(Pos.CENTER);
        removableVBox.setPadding(new Insets(15));
        removableVBox.setSpacing(10);

        VBox mainVBox = new VBox(topHBox, coinHeadImV, coinAlertLabel, removableVBox);

        betHeadsButton.setOnAction(event -> {
            if (!betField.getText().isEmpty()) {
                try {
                    if (model.placeBet(Integer.parseInt(betField.getText())) != 1) {
                        model.getCoin().setCurrentBet(Coin.CoinSide.HEADS);
                        removableVBox.getChildren().clear();
                        // todo: SHAKE COIN HERE
                        if (model.flipCoin() == Coin.CoinSide.TAILS) {
                            if (mainVBox.getChildren().contains(coinHeadImV)) {
                                mainVBox.getChildren().set(1, coinTailImV);
                            }
                            model.updateModel("You lost :(");
                        } else {
                            if (mainVBox.getChildren().contains(coinTailImV)) {
                                mainVBox.getChildren().set(1, coinHeadImV);
                            }
                            model.winBet(2);
                        }
                    }
                    removableVBox.getChildren().add(playAgainButton);
                } catch (NumberFormatException nfe) {
                    model.updateModel("Please enter a number");
                }
            } else {
                model.updateModel("You have not entered a bet");
            }
        });  // TODO create animation for coin flip (coin just going crazy)

        betTailsButton.setOnAction(event -> {
            if (!betField.getText().isEmpty()) {
                try {
                    if (model.placeBet(Integer.parseInt(betField.getText())) != 1) {
                        model.getCoin().setCurrentBet(Coin.CoinSide.TAILS);
                        removableVBox.getChildren().clear();
                        // todo: SHAKE COIN HERE
                        if (model.flipCoin() == Coin.CoinSide.TAILS) {
                            if (mainVBox.getChildren().contains(coinHeadImV)) {
                                mainVBox.getChildren().set(1, coinTailImV);
                            }
                            model.winBet(2);
                        } else {
                            if (mainVBox.getChildren().contains(coinTailImV)) {
                                mainVBox.getChildren().set(1, coinHeadImV);
                            }
                            model.updateModel("You lost :(");
                        }
                    }
                    removableVBox.getChildren().add(playAgainButton);
                } catch (NumberFormatException nfe) {
                    model.updateModel("Please enter a number");
                }
            } else {
                model.updateModel("You have not entered a bet");
            }
        });

        playAgainButton.setOnAction(event -> {
            removableVBox.getChildren().clear();
            removableVBox.getChildren().addAll(betField, buttonsHBox);
        });

        coinBackButton.setOnAction(event ->{
            removableVBox.getChildren().clear();
            removableVBox.getChildren().addAll(betField, buttonsHBox);
            betField.setText("");
            coinAlertLabel.setText("");
            model.setScene(Scenes.HOME);
        });

        mainVBox.setSpacing(10);
        mainVBox.setPadding(new Insets(15));
        mainVBox.setAlignment(Pos.CENTER);
        return new Scene(mainVBox, 800, 600);
    }

    /**
     * Method to create horse race scene
     * @return horserace scene
     */
    public Scene createHorseRace() {
        ImageView imgVH1 = new ImageView(new Image("file:" + RESOURCES_DIR + "HorseRace/" + model.getHorseRace().getHorse(1).getFilename()));
        imgVH1.setFitHeight(60);
        imgVH1.setPreserveRatio(true);

        ImageView imgVH2 = new ImageView(new Image("file:" + RESOURCES_DIR + "HorseRace/" + model.getHorseRace().getHorse(2).getFilename()));
        imgVH2.setFitHeight(60);
        imgVH2.setPreserveRatio(true);

        ImageView imgVH3 = new ImageView(new Image("file:" + RESOURCES_DIR + "HorseRace/" + model.getHorseRace().getHorse(3).getFilename()));
        imgVH3.setFitHeight(60);
        imgVH3.setPreserveRatio(true);

        ImageView imgVH4 = new ImageView(new Image("file:" + RESOURCES_DIR + "HorseRace/" + model.getHorseRace().getHorse(4).getFilename()));
        imgVH4.setFitHeight(60);
        imgVH4.setPreserveRatio(true);

        ImageView imgVStartLine = new ImageView(new Image("file:" + RESOURCES_DIR + "HorseRace/" + "start_line.png"));
        imgVStartLine.setFitHeight(425);
        imgVStartLine.setPreserveRatio(true);

        ImageView imgVFinishLine = new ImageView(new Image("file:" + RESOURCES_DIR + "HorseRace/" + "finish_line.png"));
        imgVFinishLine.setFitHeight(425);
        imgVFinishLine.setPreserveRatio(true);

        TextField enterBetField = new TextField();
        enterBetField.setPromptText("Enter your bet");
        enterBetField.setAlignment(Pos.CENTER);

        ToggleGroup radioGroup = new ToggleGroup();
        Font radioFont = new Font("Ariel", 14);
        RadioButton radioHorse1 = new RadioButton();
        radioHorse1.setText("Horse 1");
        radioHorse1.setFont(radioFont);
        radioHorse1.setToggleGroup(radioGroup);
        radioHorse1.setStyle("-fx-text-fill: #0000ff;");

        RadioButton radioHorse2 = new RadioButton();
        radioHorse2.setText("Horse 2");
        radioHorse2.setFont(radioFont);
        radioHorse2.setToggleGroup(radioGroup);
        radioHorse2.setStyle("-fx-text-fill: #ff0000;");

        RadioButton radioHorse3 = new RadioButton();
        radioHorse3.setText("Horse 3");
        radioHorse3.setFont(radioFont);
        radioHorse3.setToggleGroup(radioGroup);
        radioHorse3.setStyle("-fx-text-fill: #007e00;");

        RadioButton radioHorse4 = new RadioButton();
        radioHorse4.setText("Horse 4");
        radioHorse4.setFont(radioFont);
        radioHorse4.setToggleGroup(radioGroup);
        radioHorse4.setStyle("-fx-text-fill: #000000;");

        VBox radioButtonHBox = new VBox(radioHorse1, radioHorse2, radioHorse3, radioHorse4);
        radioButtonHBox.setAlignment(Pos.CENTER);
        radioButtonHBox.setSpacing(15);
        radioButtonHBox.setPadding(new Insets(0, 0, 0, 5));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        Label multiLabel = new Label("Odds");
        multiLabel.setFont(basicFont);
        multiLabel.setAlignment(Pos.CENTER_LEFT);

        Label h1MultiLabel = new Label(df.format(model.getHorseRace().getHorse(1).getWinMulti()) + " : 1");
        h1MultiLabel.setFont(radioFont);
        h1MultiLabel.setAlignment(Pos.TOP_RIGHT);
        h1MultiLabel.setStyle("-fx-text-fill: #0000ff;");

        Label h2MultiLabel = new Label(df.format(model.getHorseRace().getHorse(2).getWinMulti()) + " : 1");
        h2MultiLabel.setFont(radioFont);
        h2MultiLabel.setAlignment(Pos.TOP_RIGHT);
        h2MultiLabel.setStyle("-fx-text-fill: #ff0000;");

        Label h3MultiLabel = new Label(df.format(model.getHorseRace().getHorse(3).getWinMulti()) + " : 1");
        h3MultiLabel.setFont(radioFont);
        h3MultiLabel.setAlignment(Pos.TOP_RIGHT);
        h3MultiLabel.setStyle("-fx-text-fill: #007e00;");

        Label h4MultiLabel = new Label(df.format(model.getHorseRace().getHorse(4).getWinMulti()) + " : 1");
        h4MultiLabel.setFont(radioFont);
        h4MultiLabel.setAlignment(Pos.TOP_RIGHT);
        h4MultiLabel.setStyle("-fx-text-fill: #000000;");

        VBox multipliersHBox = new VBox(h1MultiLabel, h2MultiLabel, h3MultiLabel, h4MultiLabel);
        multipliersHBox.setAlignment(Pos.TOP_RIGHT);
        multipliersHBox.setSpacing(16);
        multipliersHBox.setPadding(new Insets(0, 0, 0, 0));

        HBox horsePickerHBox = new HBox(multipliersHBox, radioButtonHBox);

        VBox horsePickVBox = new VBox(multiLabel, horsePickerHBox);
        horsePickVBox.setAlignment(Pos.TOP_CENTER);
        horsePickVBox.setSpacing(15);
        horsePickVBox.setPadding(new Insets(0, 0, 20, 0));

        Button placeBetButton = new Button();
        placeBetButton.setText("Place Bet");
        placeBetButton.setMinSize(100, 75);
        placeBetButton.setFont(basicFont);
        placeBetButton.setAlignment(Pos.CENTER);
        placeBetButton.setTextAlignment(TextAlignment.CENTER);

        Button playAgainButton = new Button();
        playAgainButton.setText("Play Again?");
        playAgainButton.setMinSize(100, 75);
        playAgainButton.setFont(basicFont);
        playAgainButton.setAlignment(Pos.CENTER);
        playAgainButton.setTextAlignment(TextAlignment.CENTER);

        HBox buttonHBox = new HBox(horsePickVBox, enterBetField, placeBetButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(10);

        playAgainButton.setOnAction(event -> {
            buttonHBox.getChildren().remove(playAgainButton);
            horsePickVBox.setVisible(true);
            buttonHBox.getChildren().addAll(enterBetField, placeBetButton);
            model.resetHorseRace();
            horsebetScene = createHorseRace();
            model.setScene(Scenes.HORSEBETTING);
            model.getHorseRace().resetHorses();
            enterBetField.setText("");
            horseAlertLabel.setText("");
            horsesFinished = 0;
            hRaceFinished = false;
        });

        Button gameBackButton = new Button("Back");
        gameBackButton.setMinSize(125, 50);
        gameBackButton.setFont(basicFont);
        gameBackButton.setAlignment(Pos.CENTER);
        gameBackButton.setTextAlignment(TextAlignment.CENTER);
        gameBackButton.setOnAction(event ->{
            model.resetHorseRace();
            horsebetScene = createHorseRace();
            hRaceFinished = false;
            horseAlertLabel.setText("");
            horsesFinished = 0;
            model.setScene(Scenes.HOME);
        });

        placeBetButton.setOnAction(event -> {         // todo Would be funny if when the race starts a horn goes off
            RadioButton selected = (RadioButton) radioGroup.getSelectedToggle();
            if (selected == null) {
                model.updateModel("Please Select a Horse");
            } else if (enterBetField.getText().isEmpty()) {
                model.updateModel("Please Place a Bet");
            } else {
                switch (selected.getText()) {
                    case "Horse 1" -> model.getHorseRace().setBetHorse(1);
                    case "Horse 2" -> model.getHorseRace().setBetHorse(2);
                    case "Horse 3" -> model.getHorseRace().setBetHorse(3);
                    case "Horse 4" -> model.getHorseRace().setBetHorse(4);
                }
                try {
                    horseAlertLabel.setText("Bet Placed");
                    if (model.placeBet(Integer.parseInt(enterBetField.getText())) == 0) {
                        buttonHBox.setVisible(false);
                        gameBackButton.setDisable(true);
                        TranslateTransition t1 = startHorse(1, imgVH1);
                        t1.setOnFinished( e -> {
                            if(!hRaceFinished) {
                                hRaceFinished = true;
                                model.setHorseWin(1);
                            }
                            if(++horsesFinished == 4) {
                                horsePickVBox.setVisible(false);
                                buttonHBox.getChildren().remove(enterBetField);
                                buttonHBox.getChildren().remove(placeBetButton);
                                buttonHBox.getChildren().add(playAgainButton);
                                buttonHBox.setVisible(true);
                                gameBackButton.setDisable(false);
                            }
                        });

                        TranslateTransition t2 = startHorse(2, imgVH2);
                        t2.setOnFinished( e -> {
                            if(!hRaceFinished) {
                                hRaceFinished = true;
                                model.setHorseWin(2);
                            }
                            if(++horsesFinished == 4) {
                                horsePickVBox.setVisible(false);
                                buttonHBox.getChildren().remove(enterBetField);
                                buttonHBox.getChildren().remove(placeBetButton);
                                buttonHBox.getChildren().add(playAgainButton);
                                buttonHBox.setVisible(true);
                                gameBackButton.setDisable(false);
                            }
                        });

                        TranslateTransition t3 = startHorse(3, imgVH3);
                        t3.setOnFinished( e -> {
                            if(!hRaceFinished) {
                                hRaceFinished = true;
                                model.setHorseWin(3);
                            }
                            if(++horsesFinished == 4) {
                                horsePickVBox.setVisible(false);
                                buttonHBox.getChildren().remove(enterBetField);
                                buttonHBox.getChildren().remove(placeBetButton);
                                buttonHBox.getChildren().add(playAgainButton);
                                buttonHBox.setVisible(true);
                                gameBackButton.setDisable(false);
                            }
                        });

                        TranslateTransition t4 = startHorse(4, imgVH4);
                        t4.setOnFinished( e -> {
                            if(!hRaceFinished) {
                                hRaceFinished = true;
                                model.setHorseWin(4);
                            }
                            if(++horsesFinished == 4) {
                                horsePickVBox.setVisible(false);
                                buttonHBox.getChildren().remove(enterBetField);
                                buttonHBox.getChildren().remove(placeBetButton);
                                buttonHBox.getChildren().add(playAgainButton);
                                buttonHBox.setVisible(true);
                                gameBackButton.setDisable(false);
                            }
                        });
                    }
                } catch (NumberFormatException nfe) {
                    model.updateModel("Please Enter a Number");
                }
            }
        });

        VBox leftVBox = new VBox(imgVH1, imgVH2, imgVH3, imgVH4);
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setSpacing(50);

        HBox leftHBox = new HBox(leftVBox, imgVStartLine);
        leftHBox.setAlignment(Pos.CENTER_LEFT);
        leftHBox.setSpacing(-100);
        leftHBox.setPadding(new Insets(20, 35, 20, 35));

        HBox rightHBox = new HBox(imgVFinishLine);
        rightHBox.setAlignment(Pos.CENTER_RIGHT);
        rightHBox.setSpacing(0);
        rightHBox.setPadding(new Insets(20, 35, 20, 35));

        horseAlertLabel.setFont(basicFont);
        horseAlertLabel.setAlignment(Pos.CENTER);

        VBox bottomVBox = new VBox(horseAlertLabel, buttonHBox);
        bottomVBox.setAlignment(Pos.CENTER);
        bottomVBox.setSpacing(30);
        bottomVBox.setPadding(new Insets(20, 35, 20, 35));

        horseCreditLabel.setFont(basicFont);
        horseCreditLabel.setAlignment(Pos.BOTTOM_CENTER);
        HBox topHBox = new HBox(horseCreditLabel, gameBackButton);
        topHBox.setAlignment(Pos.TOP_RIGHT);
        topHBox.setSpacing(45);

        BorderPane bPane = new BorderPane();
        bPane.setTop(topHBox);
        bPane.setLeft(leftHBox);
        bPane.setCenter(rightHBox);
        bPane.setBottom(bottomVBox);
        bPane.setPadding(new Insets(15, 35, 15, 5));
        return new Scene(bPane, 1200, 800);
    }

    /**
     * Method to create transition (animation) effect on the horses in the horse race
     * @param horseNum the number of the horse moving
     * @param horseImg the imageView of the horse moving
     * @return the translateTransition of the horse moving
     */
    public TranslateTransition startHorse(int horseNum, ImageView horseImg) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(horseImg);
        translate.setByX(1088); // Magic number found through trial and error.
                                    // How far horses need to move to cross finish line while staying on screen=
        double speed = model.getHorseRace().getHorse(horseNum).getSpeed();

        translate.setDuration(Duration.millis(6000/speed));
        translate.play();
        return translate;
    }

    /**
     * Method to create Rubix cube game scene
     */
    public Scene createRubixScene() {
        frontRubixGrid = new GridPane();
        frontRubixGrid.setPadding(new Insets(10, 10, 10, 10));
        frontRubixGrid.setVgap(5);
        frontRubixGrid.setHgap(5);
        frontRubixGrid.setAlignment(Pos.CENTER);

        topRubixGrid = new GridPane();
        topRubixGrid.setPadding(new Insets(5, 5, 5, 5));
        topRubixGrid.setVgap(5);
        topRubixGrid.setHgap(5);
        topRubixGrid.setAlignment(Pos.CENTER);

        leftRubixGrid = new GridPane();
        leftRubixGrid.setPadding(new Insets(5, 5, 5, 5));
        leftRubixGrid.setVgap(5);
        leftRubixGrid.setHgap(5);
        leftRubixGrid.setAlignment(Pos.CENTER);

        rightRubixGrid = new GridPane();
        rightRubixGrid.setPadding(new Insets(5, 5, 5, 5));
        rightRubixGrid.setVgap(5);
        rightRubixGrid.setHgap(5);
        rightRubixGrid.setAlignment(Pos.CENTER);

        botRubixGrid = new GridPane();
        botRubixGrid.setPadding(new Insets(5, 5, 5, 5));
        botRubixGrid.setVgap(5);
        botRubixGrid.setHgap(5);
        botRubixGrid.setAlignment(Pos.CENTER);

        backRubixGrid = new GridPane();
        backRubixGrid.setPadding(new Insets(5, 5, 5, 5));
        backRubixGrid.setVgap(5);
        backRubixGrid.setHgap(5);
        backRubixGrid.setAlignment(Pos.CENTER);

        rubixFrontFaceColors = new Rectangle[3][3];
        rubixTopFaceColors = new Rectangle[3][3];
        rubixBotFaceColors = new Rectangle[3][3];
        rubixLeftFaceColors = new Rectangle[3][3];
        rubixRightFaceColors = new Rectangle[3][3];
        rubixBackFaceColors = new Rectangle[3][3];

        setRubixFace();

        Button rubixRandomizeButton = new Button("Randomize");
        rubixRandomizeButton.setMinSize(125, 60);
        rubixRandomizeButton.setFont(basicFont);
        rubixRandomizeButton.setAlignment(Pos.CENTER);
        rubixRandomizeButton.setTextAlignment(TextAlignment.CENTER);

        Button rubixResetButton = new Button("Reset");
        rubixResetButton.setMinSize(125, 60);
        rubixResetButton.setFont(basicFont);
        rubixResetButton.setAlignment(Pos.CENTER);
        rubixResetButton.setTextAlignment(TextAlignment.CENTER);

        Button rubixBackButton = new Button("Back");
        rubixBackButton.setMinSize(125, 60);
        rubixBackButton.setFont(basicFont);
        rubixBackButton.setAlignment(Pos.CENTER);
        rubixBackButton.setTextAlignment(TextAlignment.CENTER);


        HBox horzBox = new HBox(rubixRandomizeButton, rubixResetButton, rubixBackButton);
        horzBox.setAlignment(Pos.CENTER);
        horzBox.setSpacing(30);

        rubixRandomizeButton.setOnAction(event -> {
            model.mixRubixCube();
            updateRubixFace();
        });

        rubixResetButton.setOnAction(event -> {
            model.resetRubixCube();
            updateRubixFace();
        });

        rubixBackButton.setOnAction(event ->{
            rubixAlertLabel.setText("");
            model.setScene(Scenes.HOME);
        });

        HBox viewsHBox = new HBox(leftRubixGrid, frontRubixGrid, rightRubixGrid, backRubixGrid);
        viewsHBox.setAlignment(Pos.CENTER);
        VBox viewsVBox = new VBox(topRubixGrid, viewsHBox, botRubixGrid);
        viewsVBox.setAlignment(Pos.CENTER);

        VBox vertBox = new VBox(viewsVBox, horzBox);
        vertBox.setAlignment(Pos.CENTER);
        vertBox.setSpacing(30);
        vertBox.setPadding(new Insets(15, 35, 15, 35));

        return new Scene(vertBox, 905, 700);
    }

    /**
     * Helper method to set up all the buttons and cubes for the Rubix game
     */
    public void setRubixFace() {
        Button topLeftRotateButton = new Button("^");
        topLeftRotateButton.setMinSize(60, 60);
        topLeftRotateButton.setFont(basicFont);
        topLeftRotateButton.setAlignment(Pos.CENTER);
        topLeftRotateButton.setTextAlignment(TextAlignment.CENTER);
        topLeftRotateButton.setOnAction(event -> {
            model.rotateRubixVert(0, true);
            updateRubixFace();
        });

        Button topMidRotateButton = new Button("^");
        topMidRotateButton.setMinSize(60, 60);
        topMidRotateButton.setFont(basicFont);
        topMidRotateButton.setAlignment(Pos.CENTER);
        topMidRotateButton.setTextAlignment(TextAlignment.CENTER);
        topMidRotateButton.setOnAction(event -> {
            model.rotateRubixVert(1, true);
            updateRubixFace();
        });


        Button topRightRotateButton = new Button("^");
        topRightRotateButton.setMinSize(60, 60);
        topRightRotateButton.setFont(basicFont);
        topRightRotateButton.setAlignment(Pos.CENTER);
        topRightRotateButton.setTextAlignment(TextAlignment.CENTER);
        topRightRotateButton.setOnAction(event -> {
            model.rotateRubixVert(2, true);
            updateRubixFace();
        });

        Button leftTopRotateButton = new Button("<");
        leftTopRotateButton.setMinSize(60, 60);
        leftTopRotateButton.setFont(basicFont);
        leftTopRotateButton.setAlignment(Pos.CENTER);
        leftTopRotateButton.setTextAlignment(TextAlignment.CENTER);
        leftTopRotateButton.setOnAction(event -> {
            model.rotateRubixHorz(0, false);
            updateRubixFace();
        });

        Button leftMidRotateButton = new Button("<");
        leftMidRotateButton.setMinSize(60, 60);
        leftMidRotateButton.setFont(basicFont);
        leftMidRotateButton.setAlignment(Pos.CENTER);
        leftMidRotateButton.setTextAlignment(TextAlignment.CENTER);
        leftMidRotateButton.setOnAction(event -> {
            model.rotateRubixHorz(1, false);
            updateRubixFace();
        });

        Button leftBotRotateButton = new Button("<");
        leftBotRotateButton.setMinSize(60, 60);
        leftBotRotateButton.setFont(basicFont);
        leftBotRotateButton.setAlignment(Pos.CENTER);
        leftBotRotateButton.setTextAlignment(TextAlignment.CENTER);
        leftBotRotateButton.setOnAction(event -> {
            model.rotateRubixHorz(2, false);
            updateRubixFace();
        });

        Button rightTopRotateButton = new Button(">");
        rightTopRotateButton.setMinSize(60, 60);
        rightTopRotateButton.setFont(basicFont);
        rightTopRotateButton.setAlignment(Pos.CENTER);
        rightTopRotateButton.setTextAlignment(TextAlignment.CENTER);
        rightTopRotateButton.setOnAction(event -> {
            model.rotateRubixHorz(0, true);
            updateRubixFace();
        });

        Button rightMidRotateButton = new Button(">");
        rightMidRotateButton.setMinSize(60, 60);
        rightMidRotateButton.setFont(basicFont);
        rightMidRotateButton.setAlignment(Pos.CENTER);
        rightMidRotateButton.setTextAlignment(TextAlignment.CENTER);
        rightMidRotateButton.setOnAction(event -> {
            model.rotateRubixHorz(1, true);
            updateRubixFace();
        });

        Button rightBotRotateButton = new Button(">");
        rightBotRotateButton.setMinSize(60, 60);
        rightBotRotateButton.setFont(basicFont);
        rightBotRotateButton.setAlignment(Pos.CENTER);
        rightBotRotateButton.setTextAlignment(TextAlignment.CENTER);
        rightBotRotateButton.setOnAction(event -> {
            model.rotateRubixHorz(2, true);
            updateRubixFace();
        });

        Button botLeftRotateButton = new Button("V");
        botLeftRotateButton.setMinSize(60, 60);
        botLeftRotateButton.setFont(basicFont);
        botLeftRotateButton.setAlignment(Pos.CENTER);
        botLeftRotateButton.setTextAlignment(TextAlignment.CENTER);
        botLeftRotateButton.setOnAction(event -> {
            model.rotateRubixVert(0, false);
            updateRubixFace();
        });

        Button botMidRotateButton = new Button("V");
        botMidRotateButton.setMinSize(60, 60);
        botMidRotateButton.setFont(basicFont);
        botMidRotateButton.setAlignment(Pos.CENTER);
        botMidRotateButton.setTextAlignment(TextAlignment.CENTER);
        botMidRotateButton.setOnAction(event -> {
            model.rotateRubixVert(1, false);
            updateRubixFace();
        });

        Button botRightRotateButton = new Button("V");
        botRightRotateButton.setMinSize(60, 60);
        botRightRotateButton.setFont(basicFont);
        botRightRotateButton.setAlignment(Pos.CENTER);
        botRightRotateButton.setTextAlignment(TextAlignment.CENTER);
        botRightRotateButton.setOnAction(event -> {
            model.rotateRubixVert(2, false);
            updateRubixFace();
        });

        Button frontClockRotateButton = new Button("↻");
        frontClockRotateButton.setMinSize(60, 60);
        frontClockRotateButton.setFont(basicFont);
        frontClockRotateButton.setAlignment(Pos.CENTER);
        frontClockRotateButton.setTextAlignment(TextAlignment.CENTER);
        frontClockRotateButton.setOnAction(event -> {
            model.rotateFront(true);
            updateRubixFace();
        });

        Button frontCounterRotateButton = new Button("↺");
        frontCounterRotateButton.setMinSize(60, 60);
        frontCounterRotateButton.setFont(basicFont);
        frontCounterRotateButton.setAlignment(Pos.CENTER);
        frontCounterRotateButton.setTextAlignment(TextAlignment.CENTER);
        frontCounterRotateButton.setOnAction(event -> {
            model.rotateFront(false);
            updateRubixFace();
        });

        Button cubeRightRotateButton = new Button("→");
        cubeRightRotateButton.setMinSize(60, 60);
        cubeRightRotateButton.setFont(basicFont);
        cubeRightRotateButton.setAlignment(Pos.CENTER);
        cubeRightRotateButton.setTextAlignment(TextAlignment.CENTER);
        cubeRightRotateButton.setOnAction(event -> {
            model.rotateCube(true);
            updateRubixFace();
        });

        Button cubeLeftRotateButton = new Button("←");
        cubeLeftRotateButton.setMinSize(60, 60);
        cubeLeftRotateButton.setFont(basicFont);
        cubeLeftRotateButton.setAlignment(Pos.CENTER);
        cubeLeftRotateButton.setTextAlignment(TextAlignment.CENTER);
        cubeLeftRotateButton.setOnAction(event -> {
            model.rotateCube(false);
            updateRubixFace();
        });

        frontRubixGrid.add(frontClockRotateButton, 0, 0);
        frontRubixGrid.add(topLeftRotateButton, 1, 0);
        frontRubixGrid.add(topMidRotateButton, 2, 0);
        frontRubixGrid.add(topRightRotateButton, 3, 0);
        frontRubixGrid.add(frontCounterRotateButton, 4, 0);

        frontRubixGrid.add(leftTopRotateButton, 0, 1);
        frontRubixGrid.add(leftMidRotateButton, 0, 2);
        frontRubixGrid.add(leftBotRotateButton, 0, 3);

        frontRubixGrid.add(rightTopRotateButton, 4, 1);
        frontRubixGrid.add(rightMidRotateButton, 4, 2);
        frontRubixGrid.add(rightBotRotateButton, 4, 3);

        frontRubixGrid.add(cubeLeftRotateButton, 0, 4);
        frontRubixGrid.add(botLeftRotateButton, 1, 4);
        frontRubixGrid.add(botMidRotateButton, 2, 4);
        frontRubixGrid.add(botRightRotateButton, 3, 4);
        frontRubixGrid.add(cubeRightRotateButton, 4, 4);

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                rubixFrontFaceColors[i-1][j-1] = new Rectangle(60, 60);
                frontRubixGrid.add(rubixFrontFaceColors[i-1][j-1], j, i);

                rubixLeftFaceColors[i-1][j-1] = new Rectangle(30, 30);
                leftRubixGrid.add(rubixLeftFaceColors[i-1][j-1], j-1, i-1);

                rubixRightFaceColors[i-1][j-1] = new Rectangle(30, 30);
                rightRubixGrid.add(rubixRightFaceColors[i-1][j-1], j-1, i-1);

                rubixTopFaceColors[i-1][j-1] = new Rectangle(30, 30);
                topRubixGrid.add(rubixTopFaceColors[i-1][j-1], j-1, i-1);

                rubixBotFaceColors[i-1][j-1] = new Rectangle(30, 30);
                botRubixGrid.add(rubixBotFaceColors[i-1][j-1], j-1, i-1);

                rubixBackFaceColors[i-1][j-1] = new Rectangle(30, 30);
                backRubixGrid.add(rubixBackFaceColors[i-1][j-1], j-1, i-1);

            }
        }
        updateRubixFace();
    }

    /**
     * Method to update what the faces of the Rubix cube shows when a turn is made
     */
    public void updateRubixFace() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rubixFrontFaceColors[i][j].setFill(model.getRubixFace(RubixCube.RFace.FRONT, i, j));
                rubixFrontFaceColors[i][j].setStroke(Paint.valueOf("black"));

                rubixTopFaceColors[i][j].setFill(model.getRubixFace(RubixCube.RFace.TOP, i, j));
                rubixTopFaceColors[i][j].setStroke(Paint.valueOf("black"));

                rubixLeftFaceColors[i][j].setFill(model.getRubixFace(RubixCube.RFace.LEFT, i, j));
                rubixLeftFaceColors[i][j].setStroke(Paint.valueOf("black"));

                rubixRightFaceColors[i][j].setFill(model.getRubixFace(RubixCube.RFace.RIGHT, i, j));
                rubixRightFaceColors[i][j].setStroke(Paint.valueOf("black"));

                rubixBotFaceColors[i][j].setFill(model.getRubixFace(RubixCube.RFace.BOTTOM, i, j));
                rubixBotFaceColors[i][j].setStroke(Paint.valueOf("black"));

                rubixBackFaceColors[i][j].setFill(model.getRubixFace(RubixCube.RFace.BACK, i, j));
                rubixBackFaceColors[i][j].setStroke(Paint.valueOf("black"));
            }
        }
    }

    public Scene createSlotsScene() {
        Label currentBetLabel = new Label("1000");
        currentBetLabel.setFont(basicFont);

        slotsAlertLabel.setFont(basicFont);
        slotsAlertLabel.setAlignment(Pos.BOTTOM_CENTER);

        slotsCreditLabel.setFont(basicFont);

        Button slotsBackButton = new Button("Back");
        slotsBackButton.setMinSize(125, 60);
        slotsBackButton.setFont(basicFont);
        slotsBackButton.setAlignment(Pos.CENTER);
        slotsBackButton.setTextAlignment(TextAlignment.CENTER);
        slotsBackButton.setOnAction(event -> {
            slotsAlertLabel.setText("");
            model.setScene(Scenes.HOME);
        });

        Button slotsSpinButton = new Button("Spin");
        slotsSpinButton.setMinSize(125, 60);
        slotsSpinButton.setFont(basicFont);
        slotsSpinButton.setAlignment(Pos.CENTER);
        slotsSpinButton.setTextAlignment(TextAlignment.CENTER);
        slotsSpinButton.setOnAction(event -> {
            if (model.placeBet(Integer.parseInt(currentBetLabel.getText())) == 0) {
                //todo do transformations to make the spinning...
                SlotIcons[] results = new SlotIcons[3];
                Random rand = new Random();
                for (int i = 0; i < 3; i++) {
                    results[i] = slotChances(rand.nextInt(0, 1000));
                }
                System.out.println(Arrays.toString(results)); //todo replace this with displaying the winning stuff
                model.slotsEnd(results);
            }
        });

        Button increaseButton = new Button("\uD83E\uDC81");
        increaseButton.setMinSize(85, 30);
        increaseButton.setFont(basicFont);
        increaseButton.setAlignment(Pos.TOP_RIGHT);
        increaseButton.setTextAlignment(TextAlignment.CENTER);

        Button decreaseButton = new Button("\uD83E\uDC83");
        decreaseButton.setMinSize(85, 30);
        decreaseButton.setFont(basicFont);
        decreaseButton.setAlignment(Pos.TOP_RIGHT);
        decreaseButton.setTextAlignment(TextAlignment.CENTER);

        increaseButton.setOnAction(event -> {
            switch(currentBetLabel.getText()) {
                case "10" -> {
                    currentBetLabel.setText("100");
                    decreaseButton.setDisable(false);
                }
                case "100" -> currentBetLabel.setText("500");
                case "500" -> currentBetLabel.setText("1000");
                case "1000" -> currentBetLabel.setText("2000");
                case "2000" -> currentBetLabel.setText("5000");
                case "5000" -> currentBetLabel.setText("10000");
                case "10000" -> {
                    currentBetLabel.setText("100000");
                    increaseButton.setDisable(true);
                }
            }
            model.updateModel("Bet Changed");
        });

        decreaseButton.setOnAction(event -> {
            switch(currentBetLabel.getText()) {
                case "100" -> {
                    currentBetLabel.setText("10");
                    decreaseButton.setDisable(true);
                }
                case "500" -> currentBetLabel.setText("100");
                case "1000" -> currentBetLabel.setText("500");
                case "2000" -> currentBetLabel.setText("1000");
                case "5000" -> currentBetLabel.setText("2000");
                case "10000" -> currentBetLabel.setText("5000");
                case "100000" -> {
                    currentBetLabel.setText("10000");
                    increaseButton.setDisable(false);
                }
            }
            model.updateModel("Bet Changed");
        });

        VBox alterBetVBox = new VBox(increaseButton, decreaseButton);
        HBox slotButtonsHBox = new HBox(currentBetLabel, alterBetVBox, slotsSpinButton);
        slotButtonsHBox.setAlignment(Pos.CENTER);
        slotButtonsHBox.setSpacing(10);

        Rectangle firstSlot = new Rectangle(100, 350);
        firstSlot.setStroke(Paint.valueOf("black"));
        firstSlot.setFill(Paint.valueOf("grey"));

        Rectangle secondSlot = new Rectangle(100, 350);
        secondSlot.setStroke(Paint.valueOf("black"));
        secondSlot.setFill(Paint.valueOf("grey"));

        Rectangle thirdSlot = new Rectangle(100, 350);
        thirdSlot.setStroke(Paint.valueOf("black"));
        thirdSlot.setFill(Paint.valueOf("grey"));

        HBox slotScreen = new HBox(firstSlot, secondSlot, thirdSlot);
        slotScreen.setAlignment(Pos.CENTER);
        slotScreen.setSpacing(10);
        slotScreen.setPadding(new Insets(25));

        HBox topHBox = new HBox(slotsCreditLabel, slotsBackButton);
        topHBox.setAlignment(Pos.TOP_RIGHT);
        topHBox.setSpacing(45);
        topHBox.setPadding(new Insets(25));

        VBox slotsVBox = new VBox(topHBox, slotScreen, slotsAlertLabel, slotButtonsHBox);
        slotsVBox.setAlignment(Pos.CENTER);
        slotsVBox.setSpacing(10);

        return new Scene(slotsVBox, 900,  750);
    }

    public SlotIcons slotChances(int random) {
        if (random < 30)
            return SlotIcons.SEVEN;
        else if (random < 80)
            return SlotIcons.CROWN;
        else if (random < 150)
            return SlotIcons.DIAMOND;
        else if (random < 220)
            return SlotIcons.COIN;
        else if (random < 340)
            return SlotIcons.BAR;
        else if (random < 500)
            return SlotIcons.FOURLEAF;
        else if (random < 660)
            return SlotIcons.HORSESHOE;
        else if (random < 800)
            return SlotIcons.BELL;
        else
            return SlotIcons.CHERRY;
    }

    /**
     * Centers the application on the users screen
     */
    public void centerScreen() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        mainStage.setX((screenBounds.getWidth() - mainStage.getWidth()) / 2);
        mainStage.setY((screenBounds.getHeight() - mainStage.getHeight()) / 2);
    }

    /**
     * Update method to update the application window to the new information
     * @param casinoModel the object that wishes to inform this object
     *                about something that has happened.
     * @param text optional data the server.model can send to the observer
     */
    @Override
    public void update(CasinoModel casinoModel, String text) {
        model = casinoModel;
        switch (casinoModel.currentScene) {
            case LOGIN -> mainStage.setScene(loginScene);

            case SIGNUP -> mainStage.setScene(signupScene);

            case STARTUP -> mainStage.setScene(startupScene);

            case HOME -> {
                mainStage.setScene(homeScene);
                loggedin = true;
            }

            case BLACKJACK -> mainStage.setScene(blackjackScene);

            case ROULETTE -> mainStage.setScene(rouletteScene);

            case RUBIX -> mainStage.setScene(rubixScene);

            case SLOTS -> mainStage.setScene(slotsScene);

            case COINFLIP -> mainStage.setScene(coinflipScene);

            case HORSEBETTING -> mainStage.setScene(horsebetScene);
        }
        centerScreen();

        if (text != null) {
            switch (casinoModel.currentScene) {
                case LOGIN ->  loginMessage.setText(text);

                case SIGNUP -> signupMessage.setText(text);

                case STARTUP -> startScreenLabel.setText(text);

                case HOME -> homeLabel.setText(text);

                case BLACKJACK -> blackjackAlertLabel.setText(text);

                case COINFLIP -> coinAlertLabel.setText(text);

                case HORSEBETTING -> horseAlertLabel.setText(text);

                case RUBIX -> rubixAlertLabel.setText(text);

                case SLOTS -> slotsAlertLabel.setText(text);
            }
        }
        // Set the credits to their proper value
        if(loggedin) {
            bjCreditLabel.setText("Credits: " + model.getActivePlayer().getChips());
            homeCreditLabel.setText("Credits: " + model.getActivePlayer().getChips());
            coinCreditLabel.setText("Credits: " + model.getActivePlayer().getChips());
            horseCreditLabel.setText("Credits: " + model.getActivePlayer().getChips());
            slotsCreditLabel.setText("Credits: " + model.getActivePlayer().getChips());
        }
    }
}
