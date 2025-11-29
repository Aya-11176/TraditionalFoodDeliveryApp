package com.tradifooddelivery.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ContactPanel extends JPanel {

    private final MainWindow parent;
    private static final Color ORANGE = new Color(255, 102, 0);
    private static final Color DARK_BLUE = new Color(5, 20, 60);

    public ContactPanel(MainWindow parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ==== TOP DARK BLUE HEADER BAR (RESTORED) ====
        JPanel topBar = new JPanel();
        topBar.setBackground(DARK_BLUE); // FIXED HERE
        topBar.setPreferredSize(new Dimension(0, 120));
        topBar.setLayout(new BorderLayout());

        JLabel title = new JLabel("Contact Us", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(30, 0, 10, 0));
        topBar.add(title, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        // ==== MAIN CONTENT ====
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(20, 60, 20, 60));

        body.add(buildFormPanel(), BorderLayout.WEST);
        body.add(buildInfoPanel(), BorderLayout.EAST);

        add(body, BorderLayout.CENTER);

        // ==== MAP PLACEHOLDER ====
        JLabel map = new JLabel("MAP PLACEHOLDER", SwingConstants.CENTER);
        map.setPreferredSize(new Dimension(200, 250));
        map.setOpaque(true);
        map.setBackground(new Color(230, 230, 230));
        map.setFont(new Font("SansSerif", Font.BOLD, 18));
        map.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(map, BorderLayout.SOUTH);

        // ==== FOOTER ====
        add(buildFooter(), BorderLayout.PAGE_END);
    }

    // ------------------------------
    // LEFT FORM PANEL
    // ------------------------------
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(500, 420));
        panel.setBackground(Color.WHITE);

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);

        JLabel nameL = new JLabel("Name");
        nameL.setFont(labelFont);
        nameL.setBounds(10, 10, 200, 25);

        JTextField nameF = new JTextField();
        nameF.setBounds(10, 40, 420, 35);

        JLabel emailL = new JLabel("Email");
        emailL.setFont(labelFont);
        emailL.setBounds(10, 90, 200, 25);

        JTextField emailF = new JTextField();
        emailF.setBounds(10, 120, 420, 35);

        JLabel subjectL = new JLabel("Subject");
        subjectL.setFont(labelFont);
        subjectL.setBounds(10, 170, 200, 25);

        JTextField subjectF = new JTextField();
        subjectF.setBounds(10, 200, 420, 35);

        JLabel msgL = new JLabel("Message");
        msgL.setFont(labelFont);
        msgL.setBounds(10, 250, 200, 25);

        JTextArea msgA = new JTextArea();
        msgA.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(msgA);
        scroll.setBounds(10, 280, 420, 100);

        // ORANGE BUTTON (solid + readable)
        JButton send = new JButton("Send Message");
        send.setFont(new Font("SansSerif", Font.BOLD, 16));
        send.setBounds(10, 390, 200, 40);
        send.setBackground(ORANGE);
        send.setForeground(Color.WHITE);
        send.setOpaque(true);
        send.setContentAreaFilled(true);
        send.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        send.setFocusPainted(false);
        send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        send.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Your message has been sent!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE));

        panel.add(nameL);
        panel.add(nameF);
        panel.add(emailL);
        panel.add(emailF);
        panel.add(subjectL);
        panel.add(subjectF);
        panel.add(msgL);
        panel.add(scroll);
        panel.add(send);

        return panel;
    }

    // ------------------------------
    // RIGHT CONTACT INFO ORANGE BOX
    // ------------------------------
    private JPanel buildInfoPanel() {
        JPanel box = new JPanel();
        box.setBackground(ORANGE);
        box.setPreferredSize(new Dimension(350, 380));
        box.setBorder(new EmptyBorder(20, 20, 20, 20));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        Font titleFont = new Font("Serif", Font.BOLD, 24);
        Font infoFont = new Font("SansSerif", Font.PLAIN, 15);

        JLabel header = new JLabel("Get in Touch");
        header.setFont(titleFont);
        header.setForeground(Color.BLACK);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel text = new JLabel("<html>Fill out the form or contact us<br>directly through the details below.</html>");
        text.setFont(infoFont);
        text.setForeground(Color.BLACK);
        text.setBorder(new EmptyBorder(10, 0, 20, 0));

        box.add(header);
        box.add(text);

        box.add(makeInfoItem("📍", "Algiers, Algeria", infoFont));
        box.add(makeInfoItem("📞", "+213 123 456 789", infoFont));
        box.add(makeInfoItem("✉", "hsrestaurant@email.com", infoFont));

        return box;
    }

    private JPanel makeInfoItem(String icon, String text, Font f) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        p.setOpaque(false);

        JLabel ic = new JLabel(icon);
        ic.setFont(new Font("SansSerif", Font.PLAIN, 26));
        ic.setForeground(Color.BLACK);

        JLabel t = new JLabel(text);
        t.setFont(f);
        t.setForeground(Color.BLACK);

        p.add(ic);
        p.add(t);

        return p;
    }

    // ------------------------------
    // FOOTER (Quick Links removed)
    // ------------------------------
    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_BLUE);

        JPanel columns = new JPanel(new GridLayout(1, 2));
        columns.setBackground(DARK_BLUE);
        columns.setBorder(new EmptyBorder(20, 50, 20, 50));

        columns.add(buildFooterColumn(
                "H&S Restaurant",
                "<html>Authentic Algerian cuisine<br>crafted with passion.</html>"
        ));

        columns.add(buildFooterColumn(
                "Contact",
                "<html>📍 Algiers, Algeria<br>📞 +213 123 456 789<br>✉ hsrestaurant@email.com</html>"
        ));

        footer.add(columns, BorderLayout.CENTER);

        JLabel copyright =
                new JLabel("© 2025 H&S Restaurant. All rights reserved.", SwingConstants.CENTER);
        copyright.setFont(new Font("SansSerif", Font.PLAIN, 13));
        copyright.setForeground(Color.WHITE);
        copyright.setBorder(new EmptyBorder(10, 0, 10, 0));

        footer.add(copyright, BorderLayout.SOUTH);

        return footer;
    }

    private JPanel buildFooterColumn(String title, String text) {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BLUE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Serif", Font.BOLD, 20));
        t.setForeground(Color.WHITE);

        JLabel body = new JLabel(text);
        body.setFont(new Font("SansSerif", Font.PLAIN, 14));
        body.setForeground(Color.WHITE);
        body.setBorder(new EmptyBorder(10, 0, 0, 0));

        panel.add(t);
        panel.add(body);

        return panel;
    }
}
