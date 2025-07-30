import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Account> accounts = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        accounts.add(new Account("Main"));  // Default account
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String name) {
        for (Account acc : accounts) {
            if (acc.getName().equals(name)) return acc;
        }
        return null;
    }

    public void addAccount(String name) {
        accounts.add(new Account(name));
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(",").append(password);
        for (Account acc : accounts) {
            sb.append(";").append(acc.toFileString());
        }
        return sb.toString();
    }

    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        User user = new User(parts[0], parts[1].split(";")[0]);
        String[] accs = line.substring(line.indexOf(';') + 1).split(";");
        user.accounts.clear(); // remove default
        for (String acc : accs) {
            user.accounts.add(Account.fromFileString(acc));
        }
        return user;
    }
}
