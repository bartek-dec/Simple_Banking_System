# Simple_Banking_System
### This is a console program which simulates simple banking system. The main purpose of this project is getting familiarity with SQL language and creating an application which interacts with a database (which is a data source). In order to run this application, user needs to install SQLite database (version 3.33.0 in this case) first. Program uses command line parameter "-fileName" followed by file name (e.g. -fileName cards.db) in order to use appropriate data base file. If provided file does not exist in the "resources" directory than a new file will be created along with the required structure (required table for storing data).
### After running the program, it displays the main menu:
#### 1) Create an account - this option creates a new account which consists of 16-digits credit card number, 4-digits PIN number and default balance equal 0. Program generates unique credit card numbers, and it uses Luhn algorithm to validate those numbers.
#### 2) Log int account - this option allows logging into an existing account. It requires providing correct credit card and PIN numbers. During the logging process, program also uses Luhn algorithm to verify card number. If card number does not pass the verification, than program does not check if it exists in the database.
#### 0) Exit - this option closes the program.

### After successfully login into the account, the program displays the following submenu:
#### 1) Balance - this option displays the current balance of the account
#### 2) add income - this option allows adding income to the account (it accepts only positive integer numbers)
#### 3) Do transfer - this option allows transferring money from this account to other account. This process verifies following cases
#### * If the user tries to transfer more money than he/she has, program outputs: "Not enough money!"
#### * If the user tries to transfer money to the same account, program outputs the following message: “You can't transfer money to the same account!”
#### * If the receiver's card number doesn’t pass the Luhn algorithm, program outputs: “Probably you made mistake in the card number. Please try again!”
#### * If the receiver's card number doesn’t exist, program outputs: “Such a card does not exist.”
#### 4) Close account - this option removes account from the database
#### 5) Log out - this option displays main menu
#### 0) Exit - this option closes the program.