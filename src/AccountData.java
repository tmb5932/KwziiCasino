import java.io.*;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files

/**
 * Class to save account data to text file
 */
public class AccountData {
    private final String filename = "data/savedAccounts.txt";

    public void saveAccount(Player playerAccount) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(playerAccount.getUsername() + ", " + playerAccount.getPassword() + ", " + playerAccount.getChips() + "\n");
            writer.close();
            System.out.println("Successfully saved the account.");
        } catch (IOException e) {
            System.out.println("An error occurred saving the account.");
            e.printStackTrace();
        }
    }

    public HashMap<String, Player> readAccounts() {
        HashMap<String, Player> accountMap = new HashMap<>();
        try {
            File file = new File(filename);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] playerInfo = data.split(", ");
                Player tempPlayer = new Player(playerInfo[0], playerInfo[1], Integer.parseInt(playerInfo[2]));
                accountMap.put(playerInfo[0], tempPlayer);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return accountMap;
    }
}
