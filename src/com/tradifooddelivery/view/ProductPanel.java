package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JPanel {
    public ProductPanel(MainWindow parent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Add white navigation bar at the top
        add(new NavBar(parent, true), BorderLayout.NORTH);

        // Content in the center
        JLabel lbl = new JLabel("Menu Page — Coming Soon!", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lbl, BorderLayout.CENTER);
    }
}
