import java.util.Objects;
import java.util.Random;

public class Horse {
    private final int number;
    private final double winMulti;
    private final double speed;
    private String filename;

    Horse(int number) {
        Random rand = new Random();
        this.number = number;
        this.speed = rand.nextDouble(1,6);  // gives double [1-5)
        this.winMulti = 15 - (speed * 2); // todo: I dont want this directly linked to speed,
                                                //todo: because lowest win multiplier will always win
        setFilename();
    }

    private void setFilename() {
        switch (number) {
            case 1 -> filename = "blue_horse.png";
            case 2 -> filename = "red_horse.png";
            case 3 -> filename = "green_horse.png";
            case 4 -> filename = "black_horse.png";
            default -> filename = "error_horse.png"; // SHOULD NEVER GET HERE
        }
    }

    public double getWinMulti() {
        return winMulti;
    }

    public int getNumber() {
        return number;
    }

    public double getSpeed() {
        return speed;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horse horse)) return false;
        return number == horse.number && winMulti == horse.winMulti && speed == horse.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, winMulti, speed);
    }

    @Override
    public String toString() {
        return "Horse{" +
                "number=" + number +
                ", winMulti=" + winMulti +
                ", speed=" + speed +
                '}';
    }
}
