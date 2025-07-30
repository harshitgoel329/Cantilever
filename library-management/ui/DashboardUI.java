package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DashboardUI extends JFrame {
    private final JPanel contentPanel = new JPanel(new CardLayout());

    public DashboardUI() {
        setTitle("📘 Library Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("📚 Library Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(40, 53, 147));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // Card Panel with WrapLayout
        JPanel cardPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setBackground(new Color(250, 250, 250));

        // Add Cards
        addCard(cardPanel, "📚", "New Book", "bookPanel");
        addCard(cardPanel, "👤", "New Student", "studentPanel");
        addCard(cardPanel, "📝", "New Account", "accountPanel");
        addCard(cardPanel, "📊", "Statistics", "statsPanel");
        addCard(cardPanel, "📦", "Issue Book", "issuePanel");
        addCard(cardPanel, "🔁", "Return Book", "returnPanel");
        addCard(cardPanel, "🔍", "Search", "searchPanel");
        addCard(cardPanel, "🛠", "Admin", "adminPanel");

        // ScrollPane for cards
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Register Panels
        contentPanel.add(new BookPanel(), "bookPanel");
        contentPanel.add(new StudentPanel(), "studentPanel");
        contentPanel.add(new IssuePanel(), "issuePanel");
        contentPanel.add(new ReturnPanel(), "returnPanel");
        contentPanel.add(new StatisticsPanel(), "statsPanel");
        contentPanel.add(new SearchPanel(), "searchPanel");
        contentPanel.add(new AdminPanel(), "adminPanel");

        // Combine Scroll + Content
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.add(scrollPane, BorderLayout.NORTH);
        centerWrapper.add(contentPanel, BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addCard(JPanel container, String icon, String label, String cardName) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200, 120));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        JLabel textLabel = new JLabel(label, JLabel.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        textLabel.setForeground(new Color(33, 33, 33));

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(textLabel, BorderLayout.SOUTH);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(232, 245, 253));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, cardName);
            }
        });

        container.add(card);
    }
}
