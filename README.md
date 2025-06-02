# Simple Banking System

A beginner-friendly Banking Management System built using Java Swing. This project demonstrates basic GUI development with Java, handling user input, and managing a simple in-memory list of bank accounts.

## Features

This system provides the following core functionalities:

* **Create New Account:** Register a new bank account with an account holder's name and an initial deposit.
* **Deposit Funds:** Add money to an existing account.
* **Withdraw Funds:** Remove money from an existing account, with a check for sufficient balance.
* **Check Balance:** View the current balance of a specific account.
* **List All Accounts:** Display a comprehensive list of all accounts currently in the system.

## Technologies Used

* **Java:** The core programming language.
* **Java Swing:** For building the graphical user interface (GUI).

## How to Run

Follow these simple steps to get the banking system up and running on your local machine:

1.  **Save the Code:**
    Save the provided Java code into a single file named `BankApp.java`. Ensure the file name exactly matches the public class name within the code.

2.  **Compile the Code:**
    Open your terminal or command prompt. Navigate to the directory where you saved `BankApp.java`.
    Compile the Java source file using the Java compiler (`javac`):

    ```bash
    javac BankApp.java
    ```

    If the compilation is successful, a `BankApp.class` file will be created in the same directory.

3.  **Run the Application:**
    Execute the compiled Java program using the `java` command:

    ```bash
    java BankApp
    ```

    A graphical window titled "Simple Banking System" will appear, and you can start using the application!

## Project Structure (Simplified)

This project is intentionally designed as a single-file application for ease of understanding and management for beginners.

* `BankApp.java`: Contains all the Java code, including:
    * The `BankApp` class, which extends `JFrame` to create the main application window.
    * An inner `Account` class, defining the structure and behavior of a bank account.
    * Swing components setup (`JTextField`, `JButton`, `JTextArea`, `JPanel`, etc.).
    * Event listeners and methods to handle user interactions and banking logic.
    * An `ArrayList` to store `Account` objects in memory.

## Limitations

* **In-Memory Data Storage:** All account data is stored in memory (`ArrayList`). This means that all data will be **lost** when the application is closed. For a more robust system, data persistence (e.g., using file I/O or a database like SQLite) would be required.
* **No User Authentication:** There is no login system or user authentication. Anyone who runs the application can access all functionalities.
* **Basic Error Handling:** While some input validation is present, it can be further enhanced for a production-ready application.

## Future Enhancements (Ideas for Improvement)

* **Data Persistence:** Implement saving and loading account data to/from a file (e.g., using Java Serialization, CSV, or a simple database like SQLite).
* **Transaction History:** Add the ability to view a history of deposits and withdrawals for each account.
* **Search Functionality:** Allow users to search for accounts by name or account number.
* **Account Deletion/Update:** Add options to close accounts or modify account holder details.
* **Improved User Interface:** Enhance the layout, aesthetics, and user experience using more advanced Swing features or custom rendering.
* **More Account Types:** Introduce different account types (e.g., Savings, Checking) with specific rules.
