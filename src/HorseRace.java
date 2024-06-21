/**
 * Class for HorseRace game
 * @author Travis Brown (Kwzii)
 */
public class HorseRace {
    private final Horse horse1;
    private final Horse horse2;
    private final Horse horse3;
    private final Horse horse4;
    private Horse betHorse = null;

    /**
     * Constructor for horse race object
     */
    public HorseRace() {
        this.horse1 = new Horse(1, "Blue");

        this.horse2 = new Horse(2, "Red");

        this.horse3 = new Horse(3, "Green");

        this.horse4 = new Horse(4, "Black");
    }

    /**
     * Getter method for a horse in the horse race
     * @param horseNum the number of the horse to be returned
     * @return Horse object
     */
    public Horse getHorse(int horseNum) {
        switch (horseNum) {
            case 1 -> { return horse1; }
            case 2 -> { return horse2; }
            case 3 -> { return horse3; }
            case 4 -> { return horse4; }
        }
        return null;    // SHOULD NEVER GET HERE
    }

    /**
     * Sets the current horse being bet on
     * @param horseNum the number of the horse
     */
    public void setBetHorse(int horseNum) {
        betHorse = getHorse(horseNum);
    }

    /**
     * Method to reset the horses, giving them new speeds and multipliers
     */
    public void resetHorses() {
        for (int x = 1; x <= 4; x++) {
            getHorse(x).resetVars();
        }
    }

    /**
     * Gettter method to return the horse that is currently being bet on
     * @return Horse object
     */
    public Horse getBetHorse() {
        return betHorse;    // Returns null if horse bet was never set
    }
}