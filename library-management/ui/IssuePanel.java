package ui;

import dao.BookDAO;
import dao.IssueDAO;
import dao.StudentDAO;
import model.Book;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class IssuePanel extends JPanel {

    private final StudentDAO studentDAO = new StudentDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final IssueDAO issueDAO = new IssueDAO();

    private final JComboBox<Student> studentCombo = new JComboBox<>();
    private final JComboBox<Book> bookCombo = new JComboBox<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Student", "Book", "Issued On"}, 0);
    private final JTable issueTable = new JTable(tableModel);

    public IssuePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("📦 Issue Book"));

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout());

        formPanel.add(new JLabel("Select Student:"));
        formPanel.add(studentCombo);

        formPanel.add(new JLabel("Select Book:"));
        formPanel.add(bookCombo);

        JButton issueBtn = new JButton("Issue Book");
        issueBtn.addActionListener(e -> issueBook());
        formPanel.add(issueBtn);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(issueTable), BorderLayout.CENTER);

        loadStudents();
        loadBooks();
        loadIssues();
    }

    private void loadStudents() {
        studentCombo.removeAllItems();
        for (Student s : studentDAO.getAllStudents()) {
            studentCombo.addItem(s);
        }
    }

    private void loadBooks() {
        bookCombo.removeAllItems();
        for (Book b : bookDAO.getAllBooks()) {
            bookCombo.addItem(b);
        }
    }

    private void issueBook() {
        Student student = (Student) studentCombo.getSelectedItem();
        Book book = (Book) bookCombo.getSelectedItem();

        if (student == null || book == null) {
            JOptionPane.showMessageDialog(this, "Select both student and book.");
            return;
        }

        boolean success = issueDAO.issueBook(student.getId(), book.getId(), LocalDate.now());
        if (success) {
            JOptionPane.showMessageDialog(this, "Book issued successfully!");
            loadIssues();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to issue book.");
        }
    }

    private void loadIssues() {
        tableModel.setRowCount(0);
        issueDAO.getActiveIssues().forEach(issue -> {
            tableModel.addRow(new Object[]{
                issue.getId(),
                issue.getStudentName(),
                issue.getBookTitle(),
                issue.getIssueDate()
            });
        });
    }
}
