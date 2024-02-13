import java.util.List;
import java.util.*;

public class CasinoModel {
    public static Player activePlayerAccount;
    AccountData accounts = new AccountData();

    private final List<Observer<CasinoModel, String>> observers = new LinkedList<>();
    public CasinoModel() {
        this.alertObservers("");
    }

    public void signUp() {

    }

    public void setLoginScreen() {

        alertObservers("BLANKS");
    }

    public void login(String usr, String pass) {
        HashMap<String, Player> accountMap = accounts.readAccounts();

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

