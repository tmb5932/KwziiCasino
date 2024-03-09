
public class HorseRace {
    private final Horse horse1;
    private final Horse horse2;
    private final Horse horse3;
    private final Horse horse4;
    private Horse betHorse = null;

    public HorseRace() {
        this.horse1 = new Horse(1);
        this.horse2 = new Horse(2);
        this.horse3 = new Horse(3);
        this.horse4 = new Horse(4);
    }

    public Horse getHorse(int horseNum) {
        switch (horseNum) {
            case 1 -> { return horse1; }
            case 2 -> { return horse2; }
            case 3 -> { return horse3; }
            case 4 -> { return horse4; }
        }
        return null;    // SHOULD NEVER GET HERE
    }

    public void setBetHorse(int horseNum) {
        betHorse = getHorse(horseNum);
    }

    public Horse getBetHorse() {
        return betHorse;    // Returns null if horse bet was never set
    }
}