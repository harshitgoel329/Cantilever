package dao;

import db.DBConnection;

import java.sql.*;

public class StatisticsDAO {

    public int getTotalBooks() {
        return getCount("SELECT COUNT(*) FROM books");
    }

    public int getTotalStudents() {
        return getCount("SELECT COUNT(*) FROM students");
    }

    public int getIssuedBooks() {
        return getCount("SELECT COUNT(*) FROM issues WHERE return_date IS NULL");
    }

    public int getReturnedBooks() {
        return getCount("SELECT COUNT(*) FROM issues WHERE return_date IS NOT NULL");
    }

    private int getCount(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
