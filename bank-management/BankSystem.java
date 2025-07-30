import java.io.*;
import java.util.*;

public class BankSystem {
    private HashMap<String, User> users = new HashMap<>();
    private final String FILE = "users.txt";

    public BankSystem() {
        loadFromFile();
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password));
        saveToFile();
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) return user;
        return null;
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE))) {
            for (User user : users.values()) {
                writer.println(user.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromFileString(line);
                users.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateData() {
        saveToFile();
    }
}
