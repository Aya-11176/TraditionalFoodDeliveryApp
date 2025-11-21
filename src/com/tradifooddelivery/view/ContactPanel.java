package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;

public class ContactPanel extends JPanel {
    public ContactPanel(MainWindow parent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new NavBar(parent, true), BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));
        form.setBackground(Color.WHITE);

        JLabel title = new JLabel("Contact Us", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setAlignmentX(CENTER_ALIGNMENT);
        form.add(title);

        form.add(Box.createVerticalStrut(20));

        JTextField name = new JTextField();
        name.setBorder(BorderFactory.createTitledBorder("Full Name"));
        form.add(name);

        form.add(Box.createVerticalStrut(12));

        JTextField email = new JTextField();
        email.setBorder(BorderFactory.createTitledBorder("Email"));
        form.add(email);

        form.add(Box.createVerticalStrut(12));

        JTextArea msg = new JTextArea(6, 20);
        msg.setBorder(BorderFactory.createTitledBorder("Message"));
        form.add(msg);

        form.add(Box.createVerticalStrut(20));

        JButton submit = new JButton("Send Message");
        submit.setFont(new Font("SansSerif", Font.BOLD, 16));
        submit.setAlignmentX(CENTER_ALIGNMENT);

        submit.addActionListener(e -> {
            if (name.getText().trim().isEmpty() ||
                email.getText().trim().isEmpty() ||
                msg.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            // TODO: call controller to persist/send message if backend added
            JOptionPane.showMessageDialog(this, "Message sent — we'll get back to you!");
            name.setText("");
            email.setText("");
            msg.setText("");
        });

        form.add(submit);
        add(form, BorderLayout.CENTER);
    }
}
