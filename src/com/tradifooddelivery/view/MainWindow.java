package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;
import com.tradifooddelivery.controller.CartController;

public class MainWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private NavBar navBar;
    private boolean navAdded = false;
    private CartController cartController = new CartController();

    // Keep references to panels for easier access
    private HomePanel homePanel;
    private ProductPanel productPanel;
    private ContactPanel contactPanel;
    private CartPanel cartPanel;
    private ReservationPanel reservationPanel;
    private CheckoutPanel checkoutPanel;

    public MainWindow() {
        setTitle("H&S Restaurant");
        setSize(1200, 760);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // System look & feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        // Global navbar
        navBar = new NavBar(this, false); // default non-transparent

        // Card layout container
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create pages (pass cartController where needed)
        homePanel = new HomePanel(this);
        productPanel = new ProductPanel(this, cartController);
        contactPanel = new ContactPanel(this);
        cartPanel = new CartPanel(this, cartController);
        cartPanel.setName("cart"); // Important for reload logic
        reservationPanel = new ReservationPanel(this);
        checkoutPanel = new CheckoutPanel(this, cartController);

        // Add pages to card panel
        mainPanel.add(homePanel, "home");
        mainPanel.add(productPanel, "menu");
        mainPanel.add(contactPanel, "contact");
        mainPanel.add(cartPanel, "cart");
        mainPanel.add(reservationPanel, "reservation");
        mainPanel.add(checkoutPanel, "checkout");

        add(mainPanel, BorderLayout.CENTER);

        // Show initial page
        showPanel("home");

        setVisible(true);
    }

    /**
     * Show a panel by name. If it's the cart panel, reload it to show current items.
     */
    public void showPanel(String name) {
        if ("home".equals(name)) {
            if (navAdded) remove(navBar);
            navAdded = false;
        } else {
            if (!navAdded) add(navBar, BorderLayout.NORTH);
            navAdded = true;
            navBar.setTransparent(false);
        }

        // Show requested card
        cardLayout.show(mainPanel, name);

        // ✅ Critical fix: reload cart whenever the cart page is shown
        if ("cart".equals(name) && cartPanel != null) {
            cartPanel.reloadCart();
        }

        revalidate();
        repaint();
    }

    public void setNavbarTransparent(boolean transparent) {
        if (navAdded && navBar != null) {
            navBar.setTransparent(transparent);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}


