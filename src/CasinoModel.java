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
    private Coin coin = new Coin();
    private HorseRace horseRace = new HorseRace();
    private ArrayList<PlayingCards> fullCardDeck = new ArrayList<>();
    private ArrayList<PlayingCards> currentDeck = new ArrayList<>();
    private ArrayList<PlayingCards> playerHand = new ArrayList<>();
    private ArrayList<PlayingCards> dealerHand = new ArrayList<>();
    private final PlayingCards coveredCard = new PlayingCards(PlayingCards.Suit.BACK, PlayingCards.Face.NONFACE, 0, RESOURCES_DIR + "PNG-cards/card_back.png");

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
        else if (activePlayerAccount.getChips() >= amount) {
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
        alertObservers("Horse #" + winningHorseNum + " won");
        if (horseRace.getBetHorse().getNumber() == winningHorseNum) {
            winBet(currentBet * horseRace.getHorse(winningHorseNum).getWinMulti());
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

