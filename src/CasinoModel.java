import java.util.List;
import java.util.*;

public class CasinoModel {
    public static Player activePlayerAccount;
    AccountData accounts = new AccountData();
    HashMap<String, Player> accountMap;

    private final List<Observer<CasinoModel, String>> observers = new LinkedList<>();
    public CasinoModel() {
        accountMap = accounts.readAccounts();
        this.alertObservers("");
    }

    public void signUp(String usr, String pass) {
        Player newPlayer = new Player(usr, pass);
        if (accountMap.containsValue(newPlayer)) {
            alertObservers("This account already exists, please login...");
        } else if (accountMap.containsKey(usr)) {
            alertObservers("This username is already taken, please try another...");
        } else {
            alertObservers("Unknown Error has occurred.");
        }
    }

    public void login(String usr, String pass) {
        if (accountMap.containsKey(usr) && accountMap.get(usr).getPassword().equals(pass)) {
            activePlayerAccount = accountMap.get(usr);
            alertObservers("You have now been signed in, " + usr);
        } else {
            alertObservers("The credentials you have inputted are incorrect");
        }
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

