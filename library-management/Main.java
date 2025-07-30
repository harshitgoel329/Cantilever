public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicit load
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        new ui.LoginScreen();
    }
}
