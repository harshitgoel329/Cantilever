package ui;

import dao.StatisticsDAO;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {

    public StatisticsPanel() {
        setLayout(new GridLayout(2, 2, 20, 20));
        setBorder(BorderFactory.createTitledBorder("📊 Library Statistics"));
        setBackground(new Color(245, 248, 255));
        setPreferredSize(new Dimension(800, 400));

        StatisticsDAO dao = new StatisticsDAO();

        add(createStatCard("📚", "Total Books", dao.getTotalBooks()));
        add(createStatCard("👥", "Total Students", dao.getTotalStudents()));
        add(createStatCard("📦", "Books Issued", dao.getIssuedBooks()));
        add(createStatCard("🔁", "Books Returned", dao.getReturnedBooks()));
    }

    private JPanel createStatCard(String emoji, String label, int count) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel icon = new JLabel(emoji, JLabel.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 42));
        JLabel title = new JLabel(label, JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel value = new JLabel(String.valueOf(count), JLabel.CENTER);
        value.setFont(new Font("SansSerif", Font.BOLD, 28));
        value.setForeground(new Color(52, 152, 219));

        panel.add(icon, BorderLayout.NORTH);
        panel.add(title, BorderLayout.CENTER);
        panel.add(value, BorderLayout.SOUTH);
        return panel;
    }
}
