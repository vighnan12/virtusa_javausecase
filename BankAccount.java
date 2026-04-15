package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account (Savings or Current).
 * - Savings : minimum balance Rs.500, no overdraft
 * - Current : no minimum balance, overdraft up to Rs.10,000
 */
public class BankAccount {

    private static int  counter = 1000;           // used to generate account numbers

    private final String        accountNumber;
    private final String        ownerUsername;
    private final String        accountType;      // "Savings" or "Current"
    private       double        balance;
    private final List<Transaction> history;

    public BankAccount(String ownerUsername, String accountType, double openingDeposit) {
        this.accountNumber = "ACC" + (++counter);
        this.ownerUsername = ownerUsername;
        this.accountType   = accountType;
        this.balance       = openingDeposit;
        this.history       = new ArrayList<>();

        if (openingDeposit > 0) {
            history.add(new Transaction("Deposit", openingDeposit, balance));
        }
    }

    // ── Deposit ────────────────────────────────────────────────────────
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("  [!] Amount must be greater than zero.");
            return;
        }
        balance += amount;
        history.add(new Transaction("Deposit", amount, balance));
        System.out.printf("  [✓] Deposited Rs.%.2f. New balance: Rs.%.2f%n", amount, balance);
    }

    // ── Withdraw ───────────────────────────────────────────────────────
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("  [!] Amount must be greater than zero.");
            return false;
        }

        double minAllowed = accountType.equals("Savings") ? 500.0 : -10_000.0;

        if (balance - amount < minAllowed) {
            if (accountType.equals("Savings")) {
                System.out.printf("  [!] Cannot withdraw. Minimum balance is Rs.500. Available: Rs.%.2f%n",
                        balance - 500);
            } else {
                System.out.println("  [!] Overdraft limit (Rs.10,000) exceeded.");
            }
            return false;
        }

        balance -= amount;
        history.add(new Transaction("Withdrawal", amount, balance));
        System.out.printf("  [✓] Withdrawn Rs.%.2f. New balance: Rs.%.2f%n", amount, balance);
        return true;
    }

    // ── Transfer ───────────────────────────────────────────────────────
    public boolean transfer(BankAccount target, double amount) {
        System.out.println("  Initiating transfer...");
        double before = this.balance;
        if (!this.withdraw(amount)) return false;

        // Adjust history label of the last entry (overwrite with better label)
        history.remove(history.size() - 1);
        history.add(new Transaction("Transfer Out", amount, this.balance));

        target.balance += amount;
        target.history.add(new Transaction("Transfer In", amount, target.balance));
        System.out.printf("  [✓] Transferred Rs.%.2f to %s%n", amount, target.getAccountNumber());
        return true;
    }

    // ── Print history ──────────────────────────────────────────────────
    public void printHistory() {
        if (history.isEmpty()) {
            System.out.println("  No transactions yet.");
            return;
        }
        System.out.println("  " + "-".repeat(70));
        System.out.printf("  %-14s | %-12s | %-22s | %s%n",
                "Type", "Amount", "Balance After", "Date & Time");
        System.out.println("  " + "-".repeat(70));
        for (Transaction t : history) {
            System.out.println("  " + t);
        }
        System.out.println("  " + "-".repeat(70));
    }

    // ── Getters ────────────────────────────────────────────────────────
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerUsername() { return ownerUsername; }
    public String getAccountType()   { return accountType; }
    public double getBalance()       { return balance; }

    @Override
    public String toString() {
        return String.format("  Account No : %s\n  Type       : %s\n  Owner      : %s\n  Balance    : Rs.%.2f",
                accountNumber, accountType, ownerUsername, balance);
    }
}
