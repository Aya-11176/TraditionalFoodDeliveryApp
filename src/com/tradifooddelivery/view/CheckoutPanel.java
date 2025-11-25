package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;
import com.tradifooddelivery.controller.CartController;

public class CheckoutPanel extends JPanel {

    private final MainWindow parent;
    private final CartController cartController;

    public CheckoutPanel(MainWindow parent, CartController cartController) {
        this.parent = parent;
        this.cartController = cartController;

        parent.setNavbarTransparent(false);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ----- Title -----
        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(28, 0, 12, 0));
        add(title, BorderLayout.NORTH);

        // ----- Body -----
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        body.setBackground(Color.WHITE);

        // Total price display
        JLabel totalLabel = new JLabel("Total: " + (int)cartController.getTotal() + " دج");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(totalLabel);

        body.add(Box.createRigidArea(new Dimension(0, 30)));

        // Placeholder checkout content
        JLabel info = new JLabel("Checkout details and payment form.");
        info.setFont(new Font("SansSerif", Font.PLAIN, 16));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(info);

        body.add(Box.createRigidArea(new Dimension(0, 40)));

        // Confirm order button
        JButton confirmBtn = new JButton("Confirm Order");
        confirmBtn.setBackground(new Color(255, 102, 0));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        confirmBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Order confirmed! Thank you.");
            cartController.clearCart();
            parent.showPanel("home");
        });

        body.add(confirmBtn);

        add(body, BorderLayout.CENTER);
    }
}
