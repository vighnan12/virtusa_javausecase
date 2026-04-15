# 🏦 JavaBank — Banking System Simulation

A simple console-based banking system built with **Core Java** as a beginner project.
Demonstrates OOP concepts like encapsulation, inheritance, and the Collections framework.

---

## 📁 Project Structure

```
BankingSystem/
├── src/
│   └── banking/
│       ├── Main.java          ← Entry point & console menu
│       ├── Bank.java          ← Central store (users + accounts)
│       ├── BankAccount.java   ← Account logic (deposit/withdraw/transfer)
│       ├── User.java          ← Customer model
│       └── Transaction.java   ← Single transaction record
└── out/                       ← Compiled .class files (auto-generated)
```

---

## ✨ Features

| Feature               | Details                                      |
|-----------------------|----------------------------------------------|
| User Registration     | Sign up with name, username, password        |
| Login / Logout        | Basic username + password authentication     |
| Create Account        | Savings (min Rs.500) or Current (overdraft)  |
| Deposit               | Add money to any of your accounts            |
| Withdraw              | Withdraw with balance/overdraft checks       |
| Transfer Money        | Send money to any account number             |
| Balance Inquiry       | Check your current balance instantly         |
| Transaction History   | Full list of all transactions with timestamp |

---

## ▶️ How to Run

### Prerequisites
- Java 17+ installed → check with `java -version`

### Steps

```bash
# 1. Go to the project folder
cd BankingSystem

# 2. Compile
javac -d out src/banking/*.java

# 3. Run
java -cp out banking.Main
```

---

## 🧪 Demo Credentials

A demo account is pre-loaded so you can try features immediately:

| Field    | Value     |
|----------|-----------|
| Username | `demo`    |
| Password | `1234`    |

---

## 💡 OOP Concepts Used

- **Encapsulation** — private fields with getters in `User`, `BankAccount`, `Transaction`
- **Collections** — `HashMap` for user/account lookup, `ArrayList` for transaction history
- **Separation of Concerns** — each class has one clear responsibility

---

## 🚀 Possible Improvements (future)

- [ ] Save data to a file (file I/O) so it persists after restart
- [ ] Add a simple Swing GUI window
- [ ] Connect to MySQL database using JDBC
- [ ] Add interest calculation for Savings accounts

---

*Built as a fresher Java project to demonstrate core banking operations.*
