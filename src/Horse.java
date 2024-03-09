import java.util.Random;

/**
 * Class for Horse object in horserace game for KwziiCasino
 * @author Travis Brown (Kwzii)
 */
public class Horse {
    private final int number;
    private double winMulti;
    private double speed;
    private String filename;
    private String color;

    /**
     * Constructor for Horse object
     * @param number the horse's number
     * @param color the color of the horse
     */
    Horse(int number, String color) {
        this.number = number;
        this.color = color;
        setVars();
    }

    /**
     * Helper method to randomize the win multiplier, speed, and to also set the proper filename based on the color
     */
    private void setVars() {
        Random rand = new Random();
        switch (color.toLowerCase()) {
            case "blue" -> {
                filename = "blue_horse.png";
                winMulti = rand.nextDouble(2, 5);
                speed = rand.nextDouble(2.75, 5);
            }
            case "red" -> {
                filename = "red_horse.png";
                winMulti = rand.nextDouble(1, 4);
                speed = rand.nextDouble(3.25, 6);
            }
            case "green" -> {
                filename = "green_horse.png";
                winMulti = rand.nextDouble(4, 15);
                speed = rand.nextDouble(1, 4.6);
            }
            case "black" -> {
                filename = "black_horse.png";
                winMulti = rand.nextDouble(1, 9);
                speed = rand.nextDouble(1, 7.75);
            }
            default -> {
                filename = "error_horse.png"; // Gets here if they choose an "illegal" color
                winMulti = 1;
                speed = 1;
            }
        }
    }

    /**
     * Method to set new random speeds and win multipliers for the horse
     */
    public void resetVars() {
        Random rand = new Random();
        switch (this.color.toLowerCase()) {
            case "blue" -> {
                winMulti = rand.nextDouble(2, 5);
                speed = rand.nextDouble(2.75, 5);
            }
            case "red" -> {
                winMulti = rand.nextDouble(1, 4);
                speed = rand.nextDouble(3.25, 6);
            }
            case "green" -> {
                winMulti = rand.nextDouble(4, 15);
                speed = rand.nextDouble(1, 4.6);
            }
            case "black" -> {
                winMulti = rand.nextDouble(1, 9);
                speed = rand.nextDouble(1, 7.75);
            }
            default -> {
                winMulti = 1;
                speed = 1;
            }
        }
    }

    /**
     * Getter for win multiplier
     * @return the win multiplier
     */
    public double getWinMulti() {
        return winMulti;
    }

    /**
     * Getter for the horses number
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Getter for speed
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Getter for filename
     * @return filename
     */
    public String getFilename() {
        return filename;
    }
}
