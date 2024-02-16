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
    private HashSet<PlayingCards> fullCardDeck = new HashSet<>();
    private HashSet<PlayingCards> currentDeck = new HashSet<>();
    private final PlayingCards coveredCard = new PlayingCards(PlayingCards.Suit.BACK, 0, RESOURCES_DIR + "card_back.svg");

    /**
     * Constructor for CasinoModel
     */
    public CasinoModel() {
        accountMap = accounts.readAccounts();
        currentScene = Scenes.STARTUP;
        this.alertObservers(null);
    }

    /**
     * Method to increase players chip amount
     * GUI calls this and this calls player.winChips()
     * @param amount number of chips won
     */
    public void winBet(int amount) {
        activePlayerAccount.winChips(amount);
        saveAccounts();
        this.alertObservers("YOU WON " + amount + " CHIPS!!");
    }

    public void placeBet(int amount) {
        activePlayerAccount.betChips(amount);
        saveAccounts();
        this.alertObservers("You have bet " + amount + " chips.");
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

    public PlayingCards getCoveredCard() {
        return coveredCard;
    }

    public void setFullCardDeck(HashSet<PlayingCards> fullCardDeck) {
        this.fullCardDeck = fullCardDeck;
    }

    public HashSet<PlayingCards> getFullCardDeck() {
        return fullCardDeck;
    }

    public void resetCardDeck() {
        currentDeck = fullCardDeck;
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

