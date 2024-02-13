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


    public Player(String username, String password) {
        this(username, password, 150);
    }

    public Player(String username, String password, int chips) {
        this.username = username;
        this.password = password;
        this.chips = chips;
    }

    public String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    public void betChips(int inc) {
        chips -= inc;
    }

    public void winChips(int inc) {
        chips += inc;
    }

    public int getChips() {
        return chips;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", chips=" + chips +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(username, player.username) && Objects.equals(password, player.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}