import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * Player saves the data of the current gambler.
 * May at some point be implemented into a login/sign up system where passwords and other things are saved
 * By Travis Brown (Kwzii)
 */

public class Player {
    private final String username;
    private final String password;
    private int chips;
    private ArrayList<PlayingCards> hand = new ArrayList<>();

    /**
     * Constructor for a new player account. Calls other constructor with 150 starting chips.
     * @param username username of the new account
     * @param password password of the new account
     */
    public Player(String username, String password) {
        this(username, password, 150);
    }

    /**
     * Constructor for player account
     * @param username username of the account
     * @param password password of the account
     * @param chips the amount of betting chips they have
     */
    protected Player(String username, String password, int chips) {
        this.username = username;
        this.password = password;
        this.chips = chips;
    }

    /**
     * Getter for the username
     * @return username of the account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the password
     * @return password of the account
     */
    protected String getPassword() {
        return password;
    }

    /**
     * Decreases chip amount by whatever was bet (passed in)
     * @param inc the amount of chips bet
     */
    public void betChips(int inc) {
        chips -= inc;
    }

    /**
     * Increases chip amount by whatever was won (passed in)
     * @param inc the amount of chips won
     */
    public void winChips(int inc) {
        chips += inc;
    }

    /**
     * Getter for chip
     * @return number of chips
     */
    public int getChips() {
        return chips;
    }

    public ArrayList<PlayingCards> getHand() {
        return hand;
    }

    public void addHand(PlayingCards card) {
        this.hand.add(card);
    }

    /**
     * toString for account
     * @return String of information about the account
     */
    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", chips=" + chips +
                '}';
    }

    /**
     * Equals method to check equality
     * @param o the object that is being checked for equality with this account
     * @return boolean true if the accounts are the same, and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(username, player.username) && Objects.equals(password, player.password);
    }

    /**
     * The hashcode of the account
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}