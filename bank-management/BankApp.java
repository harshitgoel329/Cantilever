import java.awt.*;
import javax.swing.*;

public class BankApp extends JFrame {
    private BankSystem bank = new BankSystem();
    private User currentUser;

    private JTextField userField = new JTextField(10);
    private JPasswordField passField = new JPasswordField(10);
    private JTextArea outputArea = new JTextArea(12, 30);

    private JComboBox<String> accountBox = new JComboBox<>();
    private JTextField amountField = new JTextField(10);
    private JLabel balanceLabel = new JLabel();


    public BankApp() {
        setTitle("🏦 Advanced Bank Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("PasswordField.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Monospaced", Font.PLAIN, 13));
    
        loginScreen();
        setVisible(true);
    }

    private void loginScreen() {
        getContentPane().removeAll();
    
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(240, 248, 255));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
    
        JLabel title = new JLabel("🏦 Welcome to DS Bank");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(33, 37, 41));
    
        JLabel subTitle = new JLabel("Login or Register to continue");
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitle.setForeground(new Color(100, 100, 100));
    
        userField = new JTextField(15);
        userField.setMaximumSize(new Dimension(200, 30));
        passField = new JPasswordField(15);
        passField.setMaximumSize(new Dimension(200, 30));
    
        JButton loginBtn = new JButton("🔐 Login");
        JButton registerBtn = new JButton("📝 Register");
    
        styleButton(loginBtn, new Color(0, 123, 255));
        styleButton(registerBtn, new Color(40, 167, 69));
    
        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());
    
        loginPanel.add(title);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(subTitle);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(userField);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(loginBtn);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(registerBtn);
    
        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void dashboard() {
        getContentPane().removeAll();
    
        // Top bar with user info
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(new Color(52, 152, 219));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
        JLabel userLabel = new JLabel("👤 Logged in as: " + currentUser.getUsername());
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
    
        userPanel.add(userLabel, BorderLayout.WEST);
    
        // Account selection and add
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        accountPanel.setBackground(new Color(245, 245, 245));
    
        accountBox = new JComboBox<>();
        for (Account acc : currentUser.getAccounts()) {
            accountBox.addItem(acc.getName());
        }
    
        JButton addAccBtn = new JButton("➕ Add Account");
        styleButton(addAccBtn, new Color(255, 193, 7));
        addAccBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("New account name:");
            if (name != null && !name.isEmpty()) {
                currentUser.addAccount(name);
                accountBox.addItem(name);
                bank.updateData();
                updateBalanceLabel(balanceLabel);
            }
        });
        
        accountPanel.add(new JLabel("Select Account:"));
        accountPanel.add(accountBox);
        accountPanel.add(addAccBtn);

        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(33, 37, 41));
        accountPanel.add(balanceLabel); // 👈 Add balance label here

        accountBox.setSelectedIndex(0); // select first by default
        updateBalanceLabel(balanceLabel); // initial value

        accountBox.addActionListener(e -> updateBalanceLabel(balanceLabel));

    
        // Transaction controls
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        amountField = new JTextField(10);
    
        JButton depositBtn = new JButton("💰 Deposit");
        JButton withdrawBtn = new JButton("💸 Withdraw");
        JButton historyBtn = new JButton("📜 History");
        JButton logoutBtn = new JButton("🚪 Logout");
    
        styleButton(depositBtn, new Color(40, 167, 69));
        styleButton(withdrawBtn, new Color(220, 53, 69));
        styleButton(historyBtn, new Color(108, 117, 125));
        styleButton(logoutBtn, new Color(52, 58, 64));
    
        depositBtn.addActionListener(e -> {
            doDeposit();
            updateBalanceLabel(balanceLabel);
        });
        withdrawBtn.addActionListener(e -> {
            doWithdraw();
            updateBalanceLabel(balanceLabel);
        });
        historyBtn.addActionListener(e -> showHistory());
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            loginScreen();
        });
    
        actionPanel.add(new JLabel("Amount:"));
        actionPanel.add(amountField);
        actionPanel.add(depositBtn);
        actionPanel.add(withdrawBtn);
        actionPanel.add(historyBtn);
        actionPanel.add(logoutBtn);
    
        // Output log
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPane = new JScrollPane(outputArea);
    
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("🧾 Transaction Log"));
        logPanel.add(scrollPane, BorderLayout.CENTER);
    
        add(userPanel, BorderLayout.NORTH);
        add(accountPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(actionPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
    
        revalidate();
        repaint();
        pack();
    }

    private void updateBalanceLabel(JLabel label) {
        Account acc = selectedAccount();
        if (acc != null) {
            label.setText("💰 Balance: ₹" + String.format("%.2f", acc.getBalance()));
        } else {
            label.setText("💰 Balance: ₹0.00");
        }
    }    
    
    private void login() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        currentUser = bank.login(user, pass);
        if (currentUser != null) {
            dashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    private void register() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (bank.register(user, pass)) {
            JOptionPane.showMessageDialog(this, "Registration successful.");
        } else {
            JOptionPane.showMessageDialog(this, "User already exists.");
        }
    }

    private Account selectedAccount() {
        String selected = (String) accountBox.getSelectedItem();
        if (selected == null) return null;
        return currentUser.getAccount(selected);
    }
    

    private void doDeposit() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            Account acc = selectedAccount();
            acc.deposit(amt);
            outputArea.append("Deposited ₹" + amt + " to " + acc.getName() + "\n");
            bank.updateData();
        } catch (NumberFormatException e) {
            outputArea.append("Invalid amount\n");
        }
    }

    private void doWithdraw() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            Account acc = selectedAccount();
            if (acc.withdraw(amt)) {
                outputArea.append("Withdrew ₹" + amt + " from " + acc.getName() + "\n");
            } else {
                outputArea.append("Insufficient funds.\n");
            }
            bank.updateData();
        } catch (NumberFormatException e) {
            outputArea.append("Invalid amount\n");
        }
    }

    private void showHistory() {
        Account acc = selectedAccount();
        outputArea.append("--- " + acc.getName() + " History ---\n");
        for (String txn : acc.getTransactions()) {
            outputArea.append(txn + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankApp());
    }
}
