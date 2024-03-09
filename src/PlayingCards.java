import java.util.Objects;

/**
 * Class for Playing cards
 * @author Travis Brown (Kwzii)
 */
public class PlayingCards {
    public enum Suit {
        HEARTS,
        DIAMONDS,
        CLUBS,
        SPADES,
        BACK
    }

    public enum Face {
        ACE,
        JACK,
        KING,
        QUEEN,
        NONFACE
    }

    private final Suit suit;
    private final Face face;
    private final int value;
    private final String fileName;

    /**
     * Constructor for playing card object
     * @param suit the suit of the card
     * @param face the face value of the card
     * @param value the value of the card
     * @param fileName the filename of the card
     */
    PlayingCards(Suit suit, Face face, int value, String fileName) {
        this.suit = suit;
        this.face = face;
        this.value = value;
        this.fileName = fileName;
    }

    /**
     * Getter for the value
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for the suit
     * @return suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Getter for the face
     * @return face
     */
    public Face getFace() {
        return face;
    }

    /**
     * Getter for the filename
     * @return filename
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayingCards that = (PlayingCards) o;
        return value == that.value && suit == that.suit && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value, fileName);
    }

    @Override
    public String toString() {
        return "PlayingCards{" +
                "suit=" + suit +
                ", value=" + value +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
