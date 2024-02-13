import java.util.Scanner;
import java.util.HashMap;

public class CasinoMain {
    public static Player activePlayer;
    static AccountData accounts = new AccountData();
    public static void main(String[] args) {
        HashMap<String, Player> accountMap = accounts.readAccounts();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("If signing up, press [S]. To log in press [L]");
            String loginSignup = scanner.next();

            if (loginSignup.equals("s") || loginSignup.equals("S")) {
                System.out.println("Enter Username: ");
                String signupUsr = scanner.next();
                System.out.println("Enter Password: ");
                String signupPass = scanner.next();
                Player newPlayer = new Player(signupUsr, signupPass);
                if (accountMap.containsValue(newPlayer)) {
                    System.out.println("This account already exists, please login...");
                    continue;
                } else if (accountMap.containsKey(signupUsr)) {
                    System.out.println("This username is already taken, please try another...");
                    continue;
                }
                accounts.saveAccount(newPlayer);
                activePlayer = newPlayer;
                break;
            } else if (loginSignup.equals("l") || loginSignup.equals("L")) {
                System.out.print("Enter Username: ");
                String loginUsr = scanner.next();
                System.out.print("Enter Password: ");
                String loginPass = scanner.next();
                if (accountMap.containsKey(loginUsr) && accountMap.get(loginUsr).getPassword().equals(loginPass)) {
                    activePlayer = accountMap.get(loginUsr);
                    break;
                } else {
                    System.out.println("Incorrect Username or Password...");
                }
            } else {
                System.out.println("Invalid key entered...");
            }
        }

        System.out.println(activePlayer);
//        Player player1 = new Player("admin", "admin");
//        accounts.saveAccount(player1);
//        Player player2 = new Player("cat", "dog", 2500);
//        accounts.saveAccount(player2);

    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }
}
