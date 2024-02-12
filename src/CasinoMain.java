import java.util.HashMap;

public class CasinoMain {
    static AccountData accounts = new AccountData();
    public static void main(String[] args) {

        Player player1 = new Player("admin", "admin");
        accounts.saveAccount(player1);
        Player player2 = new Player("cat", "dog", 2500);
        accounts.saveAccount(player2);

        HashMap<String, Player> accountMap = accounts.readAccounts();
        System.out.println(accountMap);
    }
}
