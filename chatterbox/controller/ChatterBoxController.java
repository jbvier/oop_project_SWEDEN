
package chatterbox.controller;

import chatterbox.entity.Message;
import chatterbox.entity.User;
import chatterbox.view.GuestInterface;
import chatterbox.view.UserInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatterBoxController {
    private List<User> users;
    private List<Message> messages;
    private User currentUser;
    private final String CSV_FILE = "messages.csv";
    private int nextMessageId = 1;

    public ChatterBoxController() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
        // Hardcoded users for simplicity as per requirements
        users.add(new User("Alice", "password123"));
        users.add(new User("Bob", "bobpass"));
        loadFromCSV();
    }

    public void start() {
        GuestInterface guestView = new GuestInterface(this);
        guestView.showMenu();
    }

    public boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password)) {
                this.currentUser = u;
                return true;
            }
        }
        return false;
    }
    
    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Message> getAllMessages() {
        return messages;
    }

    public List<Message> getOwnMessages() {
        List<Message> own = new ArrayList<>();
        if (currentUser == null) return own;
        for (Message m : messages) {
            if (m.getAuthor().getUsername().equals(currentUser.getUsername())) {
                own.add(m);
            }
        }
        return own;
    }

    public void postMessage(String content) {
        if (currentUser != null) {
            Message newMsg = new Message(nextMessageId++, content, currentUser, new Date());
            messages.add(newMsg);
            saveToCSV();
        }
    }

    public boolean deleteMessage(int messageId) {
        if (currentUser == null) return false;
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            if (m.getId() == messageId && m.getAuthor().getUsername().equals(currentUser.getUsername())) {
                messages.remove(i);
                saveToCSV();
                return true;
            }
        }
        return false;
    }

    private void loadFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String authorName = parts[1];
                    long timestamp = Long.parseLong(parts[2]);
                    String content = parts[3].replace("\\n", "\n").replace("\\,", ",");

                    User author = null;
                    for (User u : users) {
                        if (u.getUsername().equals(authorName)) {
                            author = u; break;
                        }
                    }
                    if (author == null) author = new User(authorName, "");

                    messages.add(new Message(id, content, author, new Date(timestamp)));
                    if (id >= nextMessageId) nextMessageId = id + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading messages: " + e.getMessage());
        }
    }

    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Message m : messages) {
                String safeContent = m.getContent().replace(",", "\\,").replace("\n", "\\n");
                pw.println(m.getId() + "," + m.getAuthor().getUsername() + "," + m.getTimestamp().getTime() + "," + safeContent);
            }
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }
}
