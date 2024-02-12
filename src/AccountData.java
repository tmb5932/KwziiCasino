import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 * Class to save account data to text file
 */
public class AccountData {
    public void saveAccount(String username, String password, int chips) {
        try {
            FileWriter writer = new FileWriter("data/savedAccounts.txt");
            writer.write(username + "\t" + password + "\t" + chips + "\n");
            writer.close();
            System.out.println("Successfully saved the account.");
        } catch (IOException e) {
            System.out.println("An error occurred saving the account.");
            e.printStackTrace();
        }
    }
}
