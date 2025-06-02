import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List; // Using List interface for better practice

public class BankApp extends JFrame {

    // --- Account Class (Inner Class for simplicity) ---
    // This defines what an 'Account' is.
    private static class Account {
        private String accountNumber;
        private String accountHolderName;
        private double balance;
        private static int nextAccountNumber = 1000; // Used to generate unique account numbers

        public Account(String accountHolderName, double initialDeposit) {
            this.accountNumber = "ACC" + String.format("%04d", nextAccountNumber++);
            this.accountHolderName = accountHolderName;
            this.balance = initialDeposit;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }
            return false; // Insufficient funds or invalid amount
        }

        @Override
        public String toString() {
            return "Acc No: " + accountNumber +
                   ", Name: " + accountHolderName +
                   ", Balance: $" + String.format("%.2f", balance);
        }
    }

    // --- GUI Components and Data Storage ---
    private List<Account> accounts; // Our in-memory database of accounts

    // Input fields for various operations
    private JTextField nameField, initialDepositField;
    private JTextField accNumDepositField, amountDepositField;
    private JTextField accNumWithdrawField, amountWithdrawField;
    private JTextField accNumBalanceField;

    // Area to display messages and account details
    private JTextArea displayArea;

    // --- Constructor: Sets up the main window and all its components ---
    public BankApp() {
        super("Simple Banking System"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when window closes
        setSize(800, 600); // Set window size
        setLocationRelativeTo(null); // Center the window on the screen

        accounts = new ArrayList<>(); // Initialize our list of accounts

        // --- Initialize GUI Components ---
        initComponents();
    }

    private void initComponents() {
        // Use BorderLayout for the main frame to divide sections
        setLayout(new BorderLayout());

        // --- North Panel: Title ---
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("<html><h1><b>Welcome to Simple Bank!</b></h1></html>"));
        add(northPanel, BorderLayout.NORTH);

        // --- Center Panel: Input Forms for Operations ---
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column, with gaps
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // 1. Create Account Section
        JPanel createAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        createAccountPanel.setBorder(BorderFactory.createTitledBorder("Create New Account"));
        createAccountPanel.add(new JLabel("Holder Name:"));
        nameField = new JTextField(15);
        createAccountPanel.add(nameField);
        createAccountPanel.add(new JLabel("Initial Deposit:"));
        initialDepositField = new JTextField(10);
        createAccountPanel.add(initialDepositField);
        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> createAccount()); // Lambda for simpler action listener
        createAccountPanel.add(createButton);
        centerPanel.add(createAccountPanel);

        // 2. Deposit Section
        JPanel depositPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        depositPanel.setBorder(BorderFactory.createTitledBorder("Deposit Funds"));
        depositPanel.add(new JLabel("Account No:"));
        accNumDepositField = new JTextField(10);
        depositPanel.add(accNumDepositField);
        depositPanel.add(new JLabel("Amount:"));
        amountDepositField = new JTextField(10);
        depositPanel.add(amountDepositField);
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> depositFunds());
        depositPanel.add(depositButton);
        centerPanel.add(depositPanel);

        // 3. Withdraw Section
        JPanel withdrawPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel.setBorder(BorderFactory.createTitledBorder("Withdraw Funds"));
        withdrawPanel.add(new JLabel("Account No:"));
        accNumWithdrawField = new JTextField(10);
        withdrawPanel.add(accNumWithdrawField);
        withdrawPanel.add(new JLabel("Amount:"));
        amountWithdrawField = new JTextField(10);
        withdrawPanel.add(amountWithdrawField);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> withdrawFunds());
        withdrawPanel.add(withdrawButton);
        centerPanel.add(withdrawPanel);

        // 4. Check Balance Section
        JPanel checkBalancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBalancePanel.setBorder(BorderFactory.createTitledBorder("Check Balance"));
        checkBalancePanel.add(new JLabel("Account No:"));
        accNumBalanceField = new JTextField(10);
        checkBalancePanel.add(accNumBalanceField);
        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(e -> checkBalance());
        checkBalancePanel.add(checkBalanceButton);
        centerPanel.add(checkBalancePanel);

        add(centerPanel, BorderLayout.CENTER);

        // --- South Panel: Display Area and List Accounts Button ---
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        displayArea = new JTextArea(10, 60);
        displayArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(displayArea); // Add scroll capability
        southPanel.add(scrollPane, BorderLayout.CENTER);

        JButton listAllAccountsButton = new JButton("List All Accounts");
        listAllAccountsButton.addActionListener(e -> listAllAccounts());
        southPanel.add(listAllAccountsButton, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    // --- Helper Method to find an Account by Number ---
    private Account findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return acc;
            }
        }
        return null; // Account not found
    }

    // --- Action Methods for Buttons ---

    private void createAccount() {
        String name = nameField.getText().trim(); // .trim() removes leading/trailing spaces
        String initialDepositStr = initialDepositField.getText().trim();

        if (name.isEmpty() || initialDepositStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double initialDeposit = Double.parseDouble(initialDepositStr);
            if (initialDeposit < 0) {
                JOptionPane.showMessageDialog(this, "Initial deposit cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account newAccount = new Account(name, initialDeposit);
            accounts.add(newAccount); // Add to our list

            displayArea.append("Account Created: " + newAccount.toString() + "\n");
            JOptionPane.showMessageDialog(this, "Account created successfully!\nAccount Number: " + newAccount.getAccountNumber(), "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after successful creation
            nameField.setText("");
            initialDepositField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number for initial deposit.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void depositFunds() {
        String accNum = accNumDepositField.getText().trim();
        String amountStr = amountDepositField.getText().trim();

        if (accNum.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields for deposit.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Deposit amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account account = findAccount(accNum);
            if (account != null) {
                account.deposit(amount);
                displayArea.append("Deposit successful for " + accNum + ". New Balance: $" + String.format("%.2f", account.getBalance()) + "\n");
                JOptionPane.showMessageDialog(this, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                accNumDepositField.setText("");
                amountDepositField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid deposit amount. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdrawFunds() {
        String accNum = accNumWithdrawField.getText().trim();
        String amountStr = amountWithdrawField.getText().trim();

        if (accNum.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields for withdrawal.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Withdrawal amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account account = findAccount(accNum);
            if (account != null) {
                if (account.withdraw(amount)) {
                    displayArea.append("Withdrawal successful for " + accNum + ". New Balance: $" + String.format("%.2f", account.getBalance()) + "\n");
                    JOptionPane.showMessageDialog(this, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds or invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                accNumWithdrawField.setText("");
                amountWithdrawField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid withdrawal amount. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkBalance() {
        String accNum = accNumBalanceField.getText().trim();

        if (accNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an account number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Account account = findAccount(accNum);
        if (account != null) {
            JOptionPane.showMessageDialog(this, "Account Number: " + account.getAccountNumber() +
                                           "\nHolder: " + account.getAccountHolderName() +
                                           "\nBalance: $" + String.format("%.2f", account.getBalance()),
                                           "Account Balance", JOptionPane.INFORMATION_MESSAGE);
            displayArea.append("Checked Balance for " + accNum + ": $" + String.format("%.2f", account.getBalance()) + "\n");
            accNumBalanceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listAllAccounts() {
        if (accounts.isEmpty()) {
            displayArea.append("No accounts to display yet.\n");
            return;
        }
        displayArea.append("\n--- All Accounts ---\n");
        for (Account acc : accounts) {
            displayArea.append(acc.toString() + "\n");
        }
        displayArea.append("--------------------\n");
    }

    // --- Main Method: Entry point of the application ---
    public static void main(String[] args) {
        // This ensures the GUI is created and updated safely on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new BankApp().setVisible(true); // Create and show the main window
        });
    }
}