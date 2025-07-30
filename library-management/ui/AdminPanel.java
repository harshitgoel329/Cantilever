package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {

    private final UserDAO userDAO = new UserDAO();
    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Username", "Role"}, 0
    );
    private final JTable userTable = new JTable(tableModel);

    public AdminPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("🛠 Admin Panel - User Management"));

        JScrollPane scrollPane = new JScrollPane(userTable);

        JButton promoteBtn = new JButton("Promote to Admin");
        JButton deleteBtn = new JButton("Delete User");

        promoteBtn.addActionListener(e -> promoteUser());
        deleteBtn.addActionListener(e -> deleteUser());

        JPanel btnPanel = new JPanel();
        btnPanel.add(promoteBtn);
        btnPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadUsers();
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[]{u.getId(), u.getUsername(), u.getRole()});
        }
    }

    private void promoteUser() {
        int row = userTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }

        int userId = (int) tableModel.getValueAt(row, 0);
        String currentRole = (String) tableModel.getValueAt(row, 2);

        if ("admin".equalsIgnoreCase(currentRole)) {
            JOptionPane.showMessageDialog(this, "User is already an admin.");
            return;
        }

        if (userDAO.updateRole(userId, "admin")) {
            JOptionPane.showMessageDialog(this, "User promoted to admin!");
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to promote user.");
        }
    }

    private void deleteUser() {
        int row = userTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }

        int userId = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "User deleted.");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.");
            }
        }
    }
}
