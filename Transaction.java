package banking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores details of a single transaction (deposit / withdrawal / transfer).
 */
public class Transaction {

    private final String type;        // "Deposit", "Withdrawal", "Transfer In", "Transfer Out"
    private final double amount;
    private final double balanceAfter;
    private final String date;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type         = type;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.date         = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public String getType()         { return type; }
    public double getAmount()       { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getDate()         { return date; }

    @Override
    public String toString() {
        return String.format("%-14s | Rs.%-10.2f | Balance: Rs.%-10.2f | %s",
                type, amount, balanceAfter, date);
    }
}
