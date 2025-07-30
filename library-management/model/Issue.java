package model;

import java.time.LocalDate;

public class Issue {
    private int id;
    private String studentName;
    private String bookTitle;
    private LocalDate issueDate;

    public Issue(int id, String studentName, String bookTitle, LocalDate issueDate) {
        this.id = id;
        this.studentName = studentName;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
    }

    public int getId() { return id; }
    public String getStudentName() { return studentName; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getIssueDate() { return issueDate; }
}
