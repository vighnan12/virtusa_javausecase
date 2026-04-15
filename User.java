package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * A registered customer of the bank.
 * Keeps a username, password, name, and list of their accounts.
 */
public class User {

    private final String       username;
    private       String       password;    // kept simple for a beginner project
    private final String       fullName;
    private final List<String> accountNumbers;

    public User(String username, String password, String fullName) {
        this.username       = username;
        this.password       = password;
        this.fullName       = fullName;
        this.accountNumbers = new ArrayList<>();
    }

    /** Returns true if the given password matches. */
    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    public void changePassword(String newPass) {
        this.password = newPass;
    }

    public void linkAccount(String accountNumber) {
        accountNumbers.add(accountNumber);
    }

    public String       getUsername()       { return username; }
    public String       getFullName()       { return fullName; }
    public List<String> getAccountNumbers() { return accountNumbers; }

    @Override
    public String toString() {
        return String.format("  Name     : %s\n  Username : %s\n  Accounts : %d",
                fullName, username, accountNumbers.size());
    }
}
