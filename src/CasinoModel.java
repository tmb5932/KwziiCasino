import javafx.scene.paint.Paint;

import java.util.List;
import java.util.*;

/**
 * The main model that the GUI works off of for the Casino.
 * @author Travis Brown (Kwzii)
 */
public class CasinoModel {
    private final static String RESOURCES_DIR = "resources/";
    public static Player activePlayerAccount;
    AccountData accounts = new AccountData();
    Scenes currentScene;
    HashMap<String, Player> accountMap;
    private final List<Observer<CasinoModel, String>> observers = new LinkedList<>();
    private int currentBet = 0;
    private final Coin coin = new Coin();
    private final RubixCube cube = new RubixCube();
    private HorseRace horseRace = new HorseRace();
    private ArrayList<PlayingCards> fullCardDeck = new ArrayList<>();
    private ArrayList<PlayingCards> currentDeck = new ArrayList<>();
    private ArrayList<PlayingCards> playerHand = new ArrayList<>();
    private ArrayList<PlayingCards> dealerHand = new ArrayList<>();
    private final PlayingCards coveredCard = new PlayingCards(PlayingCards.Suit.BACK, PlayingCards.Face.NONFACE, 0, RESOURCES_DIR + "Blackjack/PNG-cards/card_back.png");

    /**
     * Constructor for CasinoModel
     */
    public CasinoModel() {
        accountMap = accounts.readAccounts();
        currentScene = Scenes.STARTUP;
        this.alertObservers(null);
    }

    /**
     * Getter for the active player account
     * @return the active player account
     */
    public Player getActivePlayer() {
        return activePlayerAccount;
    }

    /**
     * Method to increase players chip amount
     * GUI calls this and this calls player.winChips()
     * @param multiplier the amount you win compared to your bet in the current game
     */
    public void winBet(double multiplier) {
        int amount = (int) (currentBet*multiplier);
        activePlayerAccount.winChips(amount);
        saveAccounts();
        this.alertObservers("YOU WON " + amount + " CHIPS!!");
    }

    /**
     * Method to place bets
     * @param amount number of chips bet
     */
    public int placeBet(int amount) {
        if (amount == 0) {
            currentBet = 0;
            return 0;
        }
        else if (activePlayerAccount.getChips() >= amount && amount > 0) {
            currentBet = amount;
            activePlayerAccount.betChips(amount);
            saveAccounts();
            this.alertObservers("You have bet " + amount + " chips.");
            return 0;
        } else {
            alertObservers("You ain't that rich buddy. Nice try though.");
            return 1;
        }
    }

    /**
     * Allows new users to sign up. GUI calls this and this uses the AccountData.java functions
     * @param usr username in a String
     * @param pass password in a String
     */
    public void signUp(String usr, String pass) {
        Player newPlayer = new Player(usr, pass);
        if (accountMap.containsValue(newPlayer)) {
            alertObservers("This account already exists, please login.");
        } else if (accountMap.containsKey(usr)) {
            alertObservers("This username is already taken, please try another.");
        } else {
            accounts.saveAccount(newPlayer, true);
            accountMap = accounts.readAccounts();
            login(usr, pass);
        }
    }

    /**
     * Getter for coin for the coin flip game
     * @return the coin model
     */
    public Coin getCoin() {
        return coin;
    }

    /**
     * Method to flip the coin and find out what side it lands on. Randomly generated 50-50 chance
     * @return the CoinSide enum that the coin "lands" on (Heads or Tails)
     */
    public Coin.CoinSide flipCoin() {
        Random rand = new Random();
        if (rand.nextInt(0, 2) == 0) {
            alertObservers("Heads has landed!");
            return Coin.CoinSide.HEADS;
        } else {
            alertObservers("Tails has landed");
            return Coin.CoinSide.TAILS;
        }
    }

    /**
     * Method to return a random card from the current deck for player
     * @return the file name of the card that was drawn from the deck
     */
    public String playerHitBlackjack() {
        Random rand = new Random();
        int randInt = rand.nextInt(currentDeck.size()-1);
        PlayingCards card = currentDeck.get(randInt);
        currentDeck.remove(randInt);
        playerHand.add(card);
        return card.getFileName();
    }

    /**
     * Method to return a random card from the current deck for dealer
     * @return the file name of the card that was drawn from the deck
     */
    public String dealerHitBlackjack() {
        Random rand = new Random();
        int randInt = rand.nextInt(currentDeck.size()-1);
        PlayingCards card = currentDeck.get(randInt);
        currentDeck.remove(randInt);
        dealerHand.add(card);
        return card.getFileName();
    }

    /**
     * Getter for the total of the hand of whichever person is given in param. Calls helper function
     * @param person Whether the call wants the dealers hand total or the players hand total 'D' or 'P'
     * @return int value of the hand
     */
    public int bjGetHandTotal(char person){
        int result = 0;
        if (person == 'D')
            result = getBjValue(result, dealerHand);
         else if (person == 'P')
             result = getBjValue(result, playerHand);

        return result;
    }

    /**
     * Counts the value of the given hand, and if it contains an ace and is above 21, turns the 11 into a 1
     * @param result the total hand value
     * @param hand the hand that is being added
     * @return the total value of the hand
     */
    private int getBjValue(int result, ArrayList<PlayingCards> hand) {
        for (PlayingCards p : hand) {
            result += p.getValue();
        }
        if (result > 21) {
            for (PlayingCards p : hand) {
                if (p.getFace() == PlayingCards.Face.ACE) {
                    result -= 10;
                    if (result <= 21)
                        break;
                }
            }
        }
        return result;
    }

    /**
     * Getter for covered card
     * @return the filename for the covered card
     */
    public String getBackCard() {
        return coveredCard.getFileName();
    }

    /**
     * Method to see if the blackjack game has been won when initial cards are placed
     * @return a GameResults enum, WIN if player won and NONE if the game is still ongoing
     */
    public GameResults initialCheckBjWin() {
        if (bjGetHandTotal('P') == 21 && playerHand.size() == 2) {
            alertObservers("BLACKJACK!!!");
            winBet(2.5);
            return GameResults.WIN;
        } else
            return GameResults.NONE;
    }

    /**
     * Method to see if the blackjack game has been won or lost after every hit
     * @return a GameResults enum, WIN if player won, LOSE if player lost, and NONE if the game is still ongoing
     */
    public GameResults midCheckBjWin() {
        if (bjGetHandTotal('P') == 21) {
            alertObservers("YOU WON!!!");
            winBet(2);
            return GameResults.WIN;
        } else if (bjGetHandTotal('P') > 21) {
            alertObservers("You lost :(");
            return GameResults.LOSE;
        }
        return GameResults.NONE;
    }

    /**
     * Method to see if the blackjack game has been won or lost after staying
     */
    public void finalCheckBjWin() {
        if (bjGetHandTotal('P') > 21) {
            alertObservers("You lost :(");
        } else if ((bjGetHandTotal('P') == 21) ||
                (bjGetHandTotal('P') > bjGetHandTotal('D')) ||
                (bjGetHandTotal('D') > 21)) {
            winBet(2);
            alertObservers("YOU WON!!!");
        } else if (bjGetHandTotal('P') == bjGetHandTotal('D')) {
            winBet(1);
            alertObservers("Push...");
        } else {
            alertObservers("You lost :(");
        }
    }

    /**
     * Method to reset the horse race game
     */
    public void resetHorseRace() {
        horseRace = new HorseRace();
        currentBet = 0;
    }

    /**
     * Method to announce winner of the horse race
     * @param winningHorseNum the horse that won's number
     */
    public void setHorseWin(int winningHorseNum) {
        if (horseRace.getBetHorse().getNumber() == winningHorseNum) {
            winBet(horseRace.getHorse(winningHorseNum).getWinMulti());
            alertObservers("YOU WON!!!");
        } else
            alertObservers("You lost :(");
    }

    /**
     * Getter method for the HorseRace instance
     * @return the HorseRace instance
     */
    public HorseRace getHorseRace() {
        return horseRace;
    }

    /**
     * Saves accounts to the text file with the updated chip balance.
     */
    public void saveAccounts() {
        int count = 0;
        for (Player acc : accountMap.values()) {
            accounts.saveAccount(acc, count != 0);
            count++;
        }
        alertObservers(null);
    }

    /**
     * Method to update the model and give text through the model
     * @param text the message that is to be displayed
     */
    public void updateModel(String text) {
        alertObservers(text);
    }

    /**
     * Method to create the complete full deck.
     * Used when initializing the card deck.
     * @param fullCardDeck the complete arraylist of playing cards
     */
    public void setFullCardDeck(ArrayList<PlayingCards> fullCardDeck) {
        this.fullCardDeck = fullCardDeck;
    }

    /**
     * Method to add all the lost cards back to the current deck for when resetting game or starting a different game
     * Also resets dealer and player hands for games that have those
     */
    public void resetCardDeck() {
        currentDeck = new ArrayList<>(fullCardDeck);
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
    }

    /**
     * Method to rotate Rubix Cube vertically
     * @param col the column to be rotated
     * @param topTurn true if the column is being rotated up, false if rotated down
     */
    public void rotateRubixVert(int col, boolean topTurn) {
        cube.makeVertRotation(col, topTurn);
        alertObservers(null);
    }

    /**
     * Method to rotate Rubix Cube horizontally
     * @param row the row to be rotated
     * @param rightTurn true if the column is being rotated right, false if rotated left
     */
    public void rotateRubixHorz(int row, boolean rightTurn) {
        cube.makeHorzRotation(row, rightTurn);
        alertObservers(null);
    }

    /**
     * Method to rotate front of Rubix Cube
     * @param clockwise true if rotating clockwise, false if counterclockwise
     */
    public void rotateFront(boolean clockwise) {
        cube.rotateFront(clockwise);
        alertObservers(null);
    }

    /**
     * Method to rotate the whole cube
     * @param right true if rotating cube right, false if rotating left
     */
    public void rotateCube(boolean right) {
        cube.rotateCube(right);
        alertObservers(null);
    }

    /**
     * Method to mix up the Rubix Cube
     */
    public void mixRubixCube() {
        cube.randomizeCube();
        alertObservers(null);
    }

    /**
     * Method to retrieve a squares color for Rubix Cube's face
     * @param row the row of the square
     * @param col the column of the square
     * @return the javafx paint color
     */
    public Paint getRubixFace(RubixCube.RFace face, int row, int col) {
        switch (cube.getColor(face, row, col)) {
            case RED -> { return Paint.valueOf("white"); } // red
            case BLUE -> { return Paint.valueOf("teal"); }
            case GREEN -> { return Paint.valueOf("green"); }
            case ORANGE -> { return Paint.valueOf("yellow"); } // orange
            case PINK -> { return Paint.valueOf("orange"); } // pink
            case PURPLE -> { return Paint.valueOf("red"); } // purple
            default -> {
                System.out.println("ERROR: MODEL.GETRUBIXFACE() FAILED at (" + row + ", " + col + ")");
                return null;
            }
        }
    }

    /**
     * Method to reset the Rubix Cube to starting position
     */
    public void resetRubixCube() {
        cube.generateCube();
        alertObservers(null);
    }

    /**
     * Method to determine chips gained from slots
     * @param results the slot icons that were rolled
     */
    public void slotsEnd(SlotIcons[] results) {
        if (results[0] == results[1] && results[1] == results[2]) {
            switch (results[0]) {
                case CHERRY -> winBet(1);
                case BELL -> winBet(1.2);
                case HORSESHOE -> winBet(1.5);
                case FOURLEAF -> winBet(2);
                case COIN -> winBet(2.25);
                case CROWN -> winBet(3.5);
                case DIAMOND -> winBet(5);
                case BAR -> winBet(10);
                case SEVEN -> winBet(150);
            }
        } else
            alertObservers("Better Luck Next Time...");
        currentBet = 0;
    }

    /**
     * Allows GUI users to log into a previously created account. Checks with accountMap to see if it is a valid login
     * @param usr username in a String
     * @param pass password in a String
     */
    public void login(String usr, String pass) {
        if (accountMap.containsKey(usr) && accountMap.get(usr).getPassword().equals(pass)) {
            activePlayerAccount = accountMap.get(usr);
            currentScene = Scenes.HOME;
            alertObservers("You have now been signed in, " + usr + ".");
        } else {
            alertObservers("The credentials you have inputted are incorrect");
        }
    }

    /**
     * Used to change what scene the view is currently displaying
     * @param newScene the scene that the view is being updated to
     */
    public void setScene(Scenes newScene) {
        currentScene = newScene;
        resetCardDeck();
        alertObservers(null);
    }

    /**
     * The view calls this to add itself as an observer.
     * @param observer the view
     */
    public void addObserver(Observer<CasinoModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * Informs the listed observers that the model has been updated, and gives an updated model to the observers
     * update method.
     * @param data the message the model is giving to the view
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }
}

