package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainWindow() {
        setTitle("H&S Restaurant");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the CardLayout container
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 👇 Paste these lines here
        HomePanel homePanel = new HomePanel(this);
        ProductPanel productPanel = new ProductPanel(this);
        CartPanel cartPanel = new CartPanel(this);
        ContactPanel contactPanel = new ContactPanel(this);
        ReservationPanel reservationPanel = new ReservationPanel(this);
        

        // Add all panels to the card layout
        mainPanel.add(homePanel, "home");
        mainPanel.add(productPanel, "menu");
        mainPanel.add(cartPanel, "cart");
        mainPanel.add(contactPanel, "contact");
        mainPanel.add(reservationPanel, "reservation");
        add(mainPanel);
        setVisible(true);
    }

    // Function to switch between pages
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}

