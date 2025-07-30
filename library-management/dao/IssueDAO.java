package dao;

import db.DBConnection;
import model.Issue;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {

    public boolean issueBook(int studentId, int bookId, LocalDate date) {
        String sql = "INSERT INTO issues (student_id, book_id, issue_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, Date.valueOf(date));
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean returnBook(int issueId, LocalDate returnDate) {
        String updateSql = "UPDATE issues SET return_date = ? WHERE id = ?";
        String updateBookQtySql = """
            UPDATE books SET quantity = quantity + 1
            WHERE id = (SELECT book_id FROM issues WHERE id = ?)
        """;
    
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
    
            try (PreparedStatement stmt1 = conn.prepareStatement(updateSql);
                 PreparedStatement stmt2 = conn.prepareStatement(updateBookQtySql)) {
    
                stmt1.setDate(1, Date.valueOf(returnDate));
                stmt1.setInt(2, issueId);
                stmt1.executeUpdate();
    
                stmt2.setInt(1, issueId);
                stmt2.executeUpdate();
    
                conn.commit();
                return true;
    
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }
    

    public List<Issue> getActiveIssues() {
        List<Issue> list = new ArrayList<>();
        String sql = """
            SELECT i.id, s.name AS student, b.title AS book, i.issue_date
            FROM issues i
            JOIN students s ON i.student_id = s.id
            JOIN books b ON i.book_id = b.id
            WHERE i.return_date IS NULL
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Issue(
                    rs.getInt("id"),
                    rs.getString("student"),
                    rs.getString("book"),
                    rs.getDate("issue_date").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
