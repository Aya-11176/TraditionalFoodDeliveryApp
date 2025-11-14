package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;

public class ReservationPanel extends JPanel {

    public ReservationPanel(MainWindow parent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Add the white navigation bar on top (like other pages)
        add(new NavBar(parent, true), BorderLayout.NORTH);

        // Center message
        JLabel lbl = new JLabel("Reservation Page — Coming Soon!", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        
        add(lbl, BorderLayout.CENTER);
    }
}
