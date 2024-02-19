import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Random;

public class Coin {

    public enum CoinSide {
        HEADS,
        TAILS
    }
    private CoinSide face = CoinSide.HEADS;
    private String wHead = "misc/washington_head.png";
    private String wTail = "misc/washington_tail.png";
    private String gHead = "misc/golden_head.png";
    private String gTail = "misc/golden_tail.png";
    private String currentHead;
    private String currentTail;
    private CoinSide currentBet;
    public Coin() {
        currentHead = wHead;
        currentTail = wTail;
    }

    public CoinSide getFace() {
        return face;
    }

    public void flipCoin() {
        Random rand = new Random();
        int randInt = rand.nextInt() % 2;
        if (randInt == 0)
            face = CoinSide.HEADS;
        else
            face = CoinSide.TAILS;
    }

    public String getCurrentHead() {
        return currentHead;
    }

    public void setCurrentBet(CoinSide bet) {
        currentBet = bet;
    }

    public CoinSide getCurrentBet() {
        return currentBet;
    }

    public String getCurrentTail() {
        return currentTail;
    }

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

    @Override
    public String toString() {
        return "Coin{" +
                "face=" + face +
                '}';
    }
}
