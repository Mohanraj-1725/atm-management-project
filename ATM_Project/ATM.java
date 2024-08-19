package ATM_Project;

import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userService.authenticateUser(username, password)) {
            System.out.println("Login successful!");

            int userId = userService.getUserIdByUsername(username);

            while (true) {
                System.out.println("Enter 1 to Check Balance");
                System.out.println("Enter 2 to Deposit");
                System.out.println("Enter 3 to Withdraw");
                System.out.println("Enter 4 to Exit");
                System.out.println();
                System.out.println("Enter the number:");

                int choice = scanner.nextInt();
                if (choice == 1) {
                    double balance = transactionService.getBalance(userId);
                    System.out.println("Your balance is: " + balance);
                } else if (choice == 2) {
                    System.out.print("Enter amount to deposit: ");
                    double amount = scanner.nextDouble();
                    transactionService.deposit(userId, amount);
                } else if (choice == 3) {
                    System.out.print("Enter amount to withdraw: ");
                    double amount = scanner.nextDouble();
                    transactionService.withdraw(userId, amount);
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        } else {
            System.out.println("Invalid username or password");
        }

        scanner.close();
    }
	
}




