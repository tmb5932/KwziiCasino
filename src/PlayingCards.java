import java.util.Objects;

public class PlayingCards {
    public enum Suit {
        HEARTS,
        DIAMONDS,
        CLUBS,
        SPADES,
        BACK
    }
    private final Suit suit;
    private final int value;
    private final String fileName;

    PlayingCards(Suit suit, int value, String fileName) {
        this.suit = suit;
        this.value = value;
        this.fileName = fileName;
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

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
