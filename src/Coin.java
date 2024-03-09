import java.util.Random;

/**
 * Coin object for the coinflip game
 * @author Travis Brown (Kwzii)
 */
public class Coin {

    /**
     * enum for the sides of the coin
     */
    public enum CoinSide {
        HEADS,
        TAILS
    }
    private CoinSide face = CoinSide.HEADS;
    private final String wHead = "CoinFlip/washington_head.png";
    private final String wTail = "CoinFlip/washington_tail.png";
    private final String gHead = "CoinFlip/golden_head.png";
    private final String gTail = "CoinFlip/golden_tail.png";
    private String currentHead;
    private String currentTail;
    private CoinSide currentBet;

    /**
     * Constructor for coin object
     */
    public Coin() {
        currentHead = wHead;
        currentTail = wTail;
    }

    /**
     * Getter for the current coin side that is facing up
     * @return face
     */
    public CoinSide getFace() {
        return face;
    }

    /**
     * Method to flip the coin
     */
    public void flipCoin() {
        Random rand = new Random();
        int randInt = rand.nextInt() % 2;
        if (randInt == 0)
            face = CoinSide.HEADS;
        else
            face = CoinSide.TAILS;
    }

    /**
     * Getter for currentHead value
     * @return currentHead
     */
    public String getCurrentHead() {
        return currentHead;
    }

    /**
     * Getter for currentTail value
     * @return currentTail
     */
    public String getCurrentTail() {
        return currentTail;
    }

    /**
     * Setter for side of head that is currently being bet on
     * @param bet the side of the coin that is being bet on
     */
    public void setCurrentBet(CoinSide bet) {
        currentBet = bet;
    }

    /**
     * Getter for the side of the coin that is currently being bet on
     * @return currentBet
     */
    public CoinSide getCurrentBet() {
        return currentBet;
    }

    /**
     * Methof to swap what coin image is currently being used
     */
    public void swapCurrentCoin() {
        if (currentHead.equals(wHead)) {
            currentHead = gHead;
            currentTail = gTail;
        }
        else {
            currentHead = wHead;
            currentTail = wTail;
        }
    }
}
