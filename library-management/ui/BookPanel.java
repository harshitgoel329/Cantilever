package ui;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {
    private final BookDAO bookDAO = new BookDAO();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Quantity"}, 0);
    private final JTable bookTable = new JTable(tableModel);
    private final JTextField titleField = new JTextField(15);
    private final JTextField authorField = new JTextField(15);
    private final JTextField qtyField = new JTextField(5);

    public BookPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("📚 Add / View Books"));

        // Form
        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("Title:"));
        form.add(titleField);
        form.add(new JLabel("Author:"));
        form.add(authorField);
        form.add(new JLabel("Quantity:"));
        form.add(qtyField);

        JButton addBtn = new JButton("Add Book");
        addBtn.addActionListener(e -> addBook());
        form.add(addBtn);

        // Table
        JScrollPane scrollPane = new JScrollPane(bookTable);
        loadBooks();

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        int qty;

        try {
            qty = Integer.parseInt(qtyField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.");
            return;
        }

        if (bookDAO.addBook(new Book(0, title, author, qty))) {
            JOptionPane.showMessageDialog(this, "Book added!");
            titleField.setText("");
            authorField.setText("");
            qtyField.setText("");
            loadBooks();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add book.");
        }
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getQuantity()});
        }
    }
}
