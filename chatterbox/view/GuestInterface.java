
package chatterbox.view;

import chatterbox.controller.ChatterBoxController;
import chatterbox.entity.Message;
import java.util.Scanner;

public class GuestInterface {
    private ChatterBoxController controller;
    private Scanner scanner;

    public GuestInterface(ChatterBoxController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- ChatterBox (Guest Menu) ---");
            System.out.println("1. View all messages");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                displayAllMessages();
            } else if (choice.equals("2")) {
                System.out.print("Username: ");
                String user = scanner.nextLine();
                System.out.print("Password: ");
                String pass = scanner.nextLine();
                if (controller.login(user, pass)) {
                    System.out.println("Login successful!");
                    UserInterface ui = new UserInterface(controller, scanner);
                    ui.showUserMenu();
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void displayAllMessages() {
        System.out.println("\n--- All Messages ---");
        java.util.List<Message> msgs = controller.getAllMessages();
        if (msgs.isEmpty()) {
             System.out.println("No messages yet.");
        }
        for (Message m : msgs) {
            System.out.println("[" + m.getId() + "] " + m.getAuthor().getUsername() + " : " + m.getContent());
        }
    }
}
