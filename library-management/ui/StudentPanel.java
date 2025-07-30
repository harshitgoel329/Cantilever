package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private final JTextField nameField = new JTextField(12);
    private final JTextField emailField = new JTextField(12);
    private final JTextField phoneField = new JTextField(10);
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone"}, 0);
    private final JTable table = new JTable(tableModel);
    private final StudentDAO studentDAO = new StudentDAO();

    public StudentPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("👤 Add / View Students"));

        // Form
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        JButton addBtn = new JButton("Add Student");
        addBtn.addActionListener(e -> addStudent());
        formPanel.add(addBtn);

        // Table
        JScrollPane scrollPane = new JScrollPane(table);
        loadStudents();

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }

        Student student = new Student(0, name, email, phone);
        if (studentDAO.addStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student added!");
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add student.");
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getPhone()});
        }
    }
}
