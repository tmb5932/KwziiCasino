public class CasinoMain {
    static AccountData accounts = new AccountData();
    public static void main(String[] args) {

        Player player = new Player("admin", "admin");
        System.out.println(player);
        accounts.saveAccount("admin", "admin", 2500);
    }
}
