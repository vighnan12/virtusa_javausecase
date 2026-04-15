package banking;

import java.util.Scanner;

/**
 * Main entry point — console-based menu interface.
 *
 * HOW TO RUN:
 *   javac src/banking/*.java -d out
 *   java -cp out banking.Main
 */
public class Main {

    static Bank    bank    = new Bank("JavaBank");
    static Scanner scanner = new Scanner(System.in);
    static User    currentUser = null;  // the logged-in user

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║       Welcome to JavaBank        ║");
        System.out.println("╚══════════════════════════════════╝");

        // Seed a demo account so testers can try quickly
        bank.registerUser("demo", "1234", "Demo User");
        User demo = bank.login("demo", "1234");
        bank.createAccount(demo, "Savings", 5000);

        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showDashboard();
            }
        }
    }

    // ── Main menu (not logged in) ──────────────────────────────────────

    static void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default  -> System.out.println("  [!] Invalid choice.");
        }
    }

    static void login() {
        System.out.print("  Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        User user = bank.login(username, password);
        if (user == null) {
            System.out.println("  [!] Invalid username or password.");
        } else {
            currentUser = user;
            System.out.println("  [✓] Welcome, " + user.getFullName() + "!");
        }
    }

    static void register() {
        System.out.print("  Full Name : ");
        String name = scanner.nextLine().trim();
        System.out.print("  Username  : ");
        String username = scanner.nextLine().trim();
        System.out.print("  Password  : ");
        String password = scanner.nextLine().trim();

        if (password.length() < 4) {
            System.out.println("  [!] Password must be at least 4 characters.");
            return;
        }
        bank.registerUser(username, password, name);
    }

    // ── Dashboard (logged in) ──────────────────────────────────────────

    static void showDashboard() {
        System.out.println("\n--- Dashboard [" + currentUser.getFullName() + "] ---");
        System.out.println("1. Create Account");
        System.out.println("2. View My Accounts");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer Money");
        System.out.println("6. Transaction History");
        System.out.println("7. Balance Inquiry");
        System.out.println("8. Logout");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> createAccount();
            case "2" -> bank.printUserAccounts(currentUser);
            case "3" -> deposit();
            case "4" -> withdraw();
            case "5" -> transfer();
            case "6" -> transactionHistory();
            case "7" -> balanceInquiry();
            case "8" -> { currentUser = null; System.out.println("  Logged out."); }
            default  -> System.out.println("  [!] Invalid choice.");
        }
    }

    static void createAccount() {
        System.out.print("  Account type (S = Savings / C = Current): ");
        String input = scanner.nextLine().trim().toUpperCase();
        String type  = input.equals("C") ? "Current" : "Savings";

        System.out.print("  Opening deposit amount: Rs.");
        double amount = readDouble();
        if (amount < 0) return;

        double minOpen = type.equals("Savings") ? 500 : 0;
        if (amount < minOpen) {
            System.out.printf("  [!] Minimum opening deposit for Savings is Rs.%.0f%n", minOpen);
            return;
        }
        bank.createAccount(currentUser, type, amount);
    }

    static void deposit() {
        BankAccount acc = pickAccount();
        if (acc == null) return;
        System.out.print("  Deposit amount: Rs.");
        double amount = readDouble();
        if (amount < 0) return;
        acc.deposit(amount);
    }

    static void withdraw() {
        BankAccount acc = pickAccount();
        if (acc == null) return;
        System.out.print("  Withdrawal amount: Rs.");
        double amount = readDouble();
        if (amount < 0) return;
        acc.withdraw(amount);
    }

    static void transfer() {
        System.out.println("  -- Transfer FROM (your account) --");
        BankAccount from = pickAccount();
        if (from == null) return;

        System.out.print("  To Account No (recipient's account number): ");
        String toNo = scanner.nextLine().trim();
        BankAccount to = bank.findAccount(toNo);
        if (to == null) {
            System.out.println("  [!] Destination account not found.");
            return;
        }
        if (to.getAccountNumber().equals(from.getAccountNumber())) {
            System.out.println("  [!] Cannot transfer to the same account.");
            return;
        }

        System.out.print("  Transfer amount: Rs.");
        double amount = readDouble();
        if (amount < 0) return;

        from.transfer(to, amount);
    }

    static void transactionHistory() {
        BankAccount acc = pickAccount();
        if (acc == null) return;
        System.out.println("\n  Transaction History — " + acc.getAccountNumber());
        acc.printHistory();
    }

    static void balanceInquiry() {
        BankAccount acc = pickAccount();
        if (acc == null) return;
        System.out.printf("  Account : %s (%s)%n", acc.getAccountNumber(), acc.getAccountType());
        System.out.printf("  Balance : Rs.%.2f%n", acc.getBalance());
    }

    // ── Helpers ────────────────────────────────────────────────────────

    /** Shows a numbered list of the user's accounts; they just type 1, 2, 3 … */
    static BankAccount pickAccount() {
        java.util.List<String> nums = currentUser.getAccountNumbers();
        if (nums.isEmpty()) {
            System.out.println("  [!] You have no accounts. Create one first (option 1).");
            return null;
        }

        // Print numbered list
        System.out.println("  Your accounts:");
        for (int i = 0; i < nums.size(); i++) {
            BankAccount acc = bank.findAccount(nums.get(i));
            if (acc != null) {
                System.out.printf("  %d) [%s] %s  —  Balance: Rs.%.2f%n",
                        i + 1, acc.getAccountType(), acc.getAccountNumber(), acc.getBalance());
            }
        }

        System.out.print("  Select account (enter number): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Please enter a number.");
            return null;
        }

        if (choice < 1 || choice > nums.size()) {
            System.out.println("  [!] Invalid selection.");
            return null;
        }
        return bank.findAccount(nums.get(choice - 1));
    }

    /** Safely reads a double from the console. Returns -1 on bad input. */
    static double readDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Please enter a valid number.");
            return -1;
        }
    }
}
