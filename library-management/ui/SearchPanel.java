package ui;

import dao.BookDAO;
import dao.StudentDAO;
import model.Book;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchPanel extends JPanel {

    private final JTextField bookSearchField = new JTextField(20);
    private final DefaultTableModel bookModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Quantity"}, 0);
    private final JTable bookTable = new JTable(bookModel);

    private final JTextField studentSearchField = new JTextField(20);
    private final DefaultTableModel studentModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone"}, 0);
    private final JTable studentTable = new JTable(studentModel);

    private final BookDAO bookDAO = new BookDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public SearchPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("🔍 Search"));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("📚 Books", buildBookSearchPanel());
        tabs.addTab("👥 Students", buildStudentSearchPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildBookSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchBar = new JPanel();
        searchBar.add(new JLabel("Search Books:"));
        searchBar.add(bookSearchField);

        bookSearchField.addCaretListener(e -> searchBooks());

        JScrollPane scrollPane = new JScrollPane(bookTable);

        panel.add(searchBar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildStudentSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchBar = new JPanel();
        searchBar.add(new JLabel("Search Students:"));
        searchBar.add(studentSearchField);

        studentSearchField.addCaretListener(e -> searchStudents());

        JScrollPane scrollPane = new JScrollPane(studentTable);

        panel.add(searchBar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void searchBooks() {
        String query = bookSearchField.getText().trim();
        List<Book> books = bookDAO.searchBooks(query);
        bookModel.setRowCount(0);
        for (Book b : books) {
            bookModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getQuantity()});
        }
    }

    private void searchStudents() {
        String query = studentSearchField.getText().trim();
        List<Student> students = studentDAO.searchStudents(query);
        studentModel.setRowCount(0);
        for (Student s : students) {
            studentModel.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getPhone()});
        }
    }
}
