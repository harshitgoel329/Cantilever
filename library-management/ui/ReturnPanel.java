package ui;

import dao.IssueDAO;
import model.Issue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReturnPanel extends JPanel {

    private final IssueDAO issueDAO = new IssueDAO();
    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Student", "Book", "Issued On"}, 0
    );
    private final JTable issueTable = new JTable(tableModel);

    public ReturnPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("🔁 Return Book"));

        JScrollPane scrollPane = new JScrollPane(issueTable);
        JButton returnBtn = new JButton("Return Selected Book");

        returnBtn.addActionListener(e -> returnSelectedBook());

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(returnBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadIssuedBooks();
    }

    private void loadIssuedBooks() {
        tableModel.setRowCount(0);
        List<Issue> issues = issueDAO.getActiveIssues();
        for (Issue issue : issues) {
            tableModel.addRow(new Object[]{
                issue.getId(),
                issue.getStudentName(),
                issue.getBookTitle(),
                issue.getIssueDate()
            });
        }
    }

    private void returnSelectedBook() {
        int row = issueTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an issued book to return.");
            return;
        }

        int issueId = (int) tableModel.getValueAt(row, 0);

        if (issueDAO.returnBook(issueId, LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "Book returned successfully!");
            loadIssuedBooks();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to return book.");
        }
    }
}
