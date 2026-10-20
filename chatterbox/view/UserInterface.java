
package chatterbox.view;

import chatterbox.controller.ChatterBoxController;
import chatterbox.entity.Message;
import java.util.Scanner;

public class UserInterface {
    private ChatterBoxController controller;
    private Scanner scanner;

    public UserInterface(ChatterBoxController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void showUserMenu() {
        while (controller.getCurrentUser() != null) {
            System.out.println("\n--- ChatterBox (User: " + controller.getCurrentUser().getUsername() + ") ---");
            System.out.println("1. View all messages");
            System.out.println("2. View own messages");
            System.out.println("3. Post a message");
            System.out.println("4. Delete own message");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayMessages(controller.getAllMessages());
                    break;
                case "2":
                    displayMessages(controller.getOwnMessages());
                    break;
                case "3":
                    System.out.print("Enter your message: ");
                    String content = scanner.nextLine();
                    controller.postMessage(content);
                    System.out.println("Message posted!");
                    break;
                case "4":
                    System.out.print("Enter message ID to delete: ");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        if (controller.deleteMessage(id)) {
                            System.out.println("Message deleted successfully.");
                        } else {
                            System.out.println("Failed to delete. Make sure it's your message and the ID is correct.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format.");
                    }
                    break;
                case "5":
                    controller.logout();
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void displayMessages(java.util.List<Message> messages) {
        System.out.println("\n--- Messages ---");
        if (messages.isEmpty()) {
            System.out.println("No messages to display.");
        }
        for (Message m : messages) {
            System.out.println("[" + m.getId() + "] " + m.getAuthor().getUsername() + " : " + m.getContent());
        }
    }
}
