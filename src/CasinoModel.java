import java.util.List;
import java.util.*;

public class CasinoModel {
    public static Player activePlayerAccount;
    private final List<Observer<CasinoModel, String>> observers = new LinkedList<>();
    public CasinoModel() {
        this.alertObservers("");
    }
    public void addObserver(Observer<CasinoModel, String> observer) {
        this.observers.add(observer);
    }

    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }
}

