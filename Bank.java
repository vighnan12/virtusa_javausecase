package banking;

import java.util.HashMap;
import java.util.Map;

/**
 * The Bank class acts as the central store for all users and accounts.
 * Think of it as the "database" for this project.
 */
public class Bank {

    private final String bankName;

    // username -> User object
    private final Map<String, User>        users    = new HashMap<>();

    // accountNumber -> BankAccount object
    private final Map<String, BankAccount> accounts = new HashMap<>();

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    // ── User management ────────────────────────────────────────────────

    /** Register a new user. Returns false if username already taken. */
    public boolean registerUser(String username, String password, String fullName) {
        if (users.containsKey(username)) {
            System.out.println("  [!] Username already taken.");
            return false;
        }
        users.put(username, new User(username, password, fullName));
        System.out.println("  [✓] User registered: " + fullName);
        return true;
    }

    /** Login: returns the User if credentials match, null otherwise. */
    public User login(String username, String password) {
        User u = users.get(username);
        if (u == null || !u.checkPassword(password)) {
            return null;
        }
        return u;
    }

    // ── Account management ─────────────────────────────────────────────

    /** Creates a new account linked to the user. */
    public BankAccount createAccount(User user, String type, double openingDeposit) {
        BankAccount acc = new BankAccount(user.getUsername(), type, openingDeposit);
        accounts.put(acc.getAccountNumber(), acc);
        user.linkAccount(acc.getAccountNumber());
        System.out.println("  [✓] Account created: " + acc.getAccountNumber());
        return acc;
    }

    /** Finds an account by number. Returns null if not found. */
    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /** Lists all accounts belonging to this user. */
    public void printUserAccounts(User user) {
        if (user.getAccountNumbers().isEmpty()) {
            System.out.println("  No accounts found.");
            return;
        }
        for (String num : user.getAccountNumbers()) {
            BankAccount acc = accounts.get(num);
            if (acc != null) {
                System.out.println(acc);
                System.out.println();
            }
        }
    }

    public String getBankName() { return bankName; }
}
