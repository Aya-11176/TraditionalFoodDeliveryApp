package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;

public class CheckoutPanel extends JPanel {
    public CheckoutPanel() {
        setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Checkout Page (checkout List Coming Soon)", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(lbl, BorderLayout.CENTER);
    }
}
