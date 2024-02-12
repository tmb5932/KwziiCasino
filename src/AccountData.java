import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
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
            writer.write(playerAccount.getUsername() + "\t" + playerAccount.getPassword() + "\t" + playerAccount.getChips() + "\n");
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
                String[] playerInfo = data.strip().split("\t");
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
