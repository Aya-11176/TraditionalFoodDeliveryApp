package com.tradifooddelivery.view;

import com.tradifooddelivery.controller.CartController;
import com.tradifooddelivery.model.CartItem;
import com.tradifooddelivery.model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CartPanel extends JPanel {

    private MainWindow parent;
    private CartController cartController;

    private JPanel itemsContainer;
    private JLabel totalLabel;
    private static final Color BRAND_BLUE = Color.decode("#143D60");
    private static final Color BRAND_ORANGE = Color.decode("#EB5B00");
    private static final Color ACCENT_GREEN = Color.decode("#A0C878");

    public CartPanel(MainWindow parent, CartController cartController) {
        this.parent = parent;
        this.cartController = cartController;

        setLayout(new BorderLayout());
        setBackground(new Color(247, 248, 250)); // very light background

        // Outer padding panel to center content
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(24, 40, 40, 40));
        add(outer, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel("Your Cart", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(BRAND_BLUE);
        title.setBorder(new EmptyBorder(8, 0, 16, 0));
        outer.add(title, BorderLayout.NORTH);

        // Main card container (white background + rounded border)
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 220), 1),
                new EmptyBorder(0, 0, 0, 0)
        ));
        outer.add(card, BorderLayout.CENTER);

        // Header row (Item / Price / Quantity / )
        JPanel headerRow = new JPanel(new GridBagLayout());
        headerRow.setOpaque(false);
        headerRow.setBorder(new EmptyBorder(12, 16, 12, 16));
        card.add(headerRow, BorderLayout.NORTH);

        GridBagConstraints hc = new GridBagConstraints();
        hc.insets = new Insets(0, 0, 0, 0);
        hc.gridy = 0; hc.fill = GridBagConstraints.HORIZONTAL; hc.weighty = 1;

        hc.gridx = 0; hc.weightx = 0.55;
        JLabel hItem = new JLabel("Item");
        hItem.setFont(new Font("SansSerif", Font.BOLD, 14));
        hItem.setForeground(BRAND_BLUE);
        headerRow.add(hItem, hc);

        hc.gridx = 1; hc.weightx = 0.15;
        JLabel hPrice = new JLabel("Price", SwingConstants.CENTER);
        hPrice.setFont(new Font("SansSerif", Font.BOLD, 14));
        hPrice.setForeground(BRAND_BLUE);
        headerRow.add(hPrice, hc);

        hc.gridx = 2; hc.weightx = 0.2;
        JLabel hQty = new JLabel("Quantity", SwingConstants.CENTER);
        hQty.setFont(new Font("SansSerif", Font.BOLD, 14));
        hQty.setForeground(BRAND_BLUE);
        headerRow.add(hQty, hc);

        hc.gridx = 3; hc.weightx = 0.1;
        headerRow.add(new JLabel(""), hc);

        // Items container with scroll
        itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setOpaque(false);

        JScrollPane scroll = new JScrollPane(itemsContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(255,255,255));
        card.add(scroll, BorderLayout.CENTER);

        // Footer: total + actions
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(true);
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(16, 16, 16, 16));
        card.add(footer, BorderLayout.SOUTH);

        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftFooter.setOpaque(false);
        totalLabel = new JLabel("Total: 0 دج");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        totalLabel.setForeground(BRAND_BLUE);
        leftFooter.add(totalLabel);
        footer.add(leftFooter, BorderLayout.WEST);

        JPanel rightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightFooter.setOpaque(false);

        JButton clearBtn = createOutlineButton("Clear Cart");
        clearBtn.addActionListener(e -> {
            cartController.clearCart();
            reloadCart();
        });

        JButton checkoutBtn = createPrimaryButton("Proceed to Checkout");
        checkoutBtn.setPreferredSize(new Dimension(260, 42));
        checkoutBtn.addActionListener(e -> parent.showPanel("checkout"));
        rightFooter.add(clearBtn);
        rightFooter.add(checkoutBtn);
        footer.add(rightFooter, BorderLayout.EAST);

        // initial load
        reloadCart();
    }

    // Build each item row to match the web layout
    private JPanel buildItemRow(CartItem item) {
        Product product = item.getProduct();

        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(true);
        row.setBackground(new Color(255,255,255));
        row.setBorder(new EmptyBorder(12, 12, 12, 12));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;

        // Item (image + name)
        c.gridx = 0; c.weightx = 0.55;
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        itemPanel.setOpaque(false);

        // image box
        JLabel img = new JLabel();
        img.setPreferredSize(new Dimension(64, 64));
        try {
            ImageIcon icon = new ImageIcon("src/resources/" + product.getImagePath());
            Image scaled = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            img.setText("[img]");
        }
        itemPanel.add(img);

        // name (could act as a link visually)
        JLabel name = new JLabel("<html><span style='font-weight:600;'>" + product.getName() + "</span></html>");
        name.setFont(new Font("SansSerif", Font.PLAIN, 15));
        name.setForeground(BRAND_BLUE);
        itemPanel.add(name);

        row.add(itemPanel, c);

        // Price
        c.gridx = 1; c.weightx = 0.15;
        JLabel price = new JLabel((int)product.getPrice() + " دج", SwingConstants.CENTER);
        price.setForeground(BRAND_ORANGE);
        price.setFont(new Font("SansSerif", Font.BOLD, 14));
        row.add(price, c);

        // Quantity controls
        c.gridx = 2; c.weightx = 0.2;
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        qtyPanel.setOpaque(false);

        JButton minus = smallCircleButton("-");
        minus.addActionListener((ActionEvent e) -> {
            cartController.decreaseQuantity(product);
            reloadCart();
        });
        qtyPanel.add(minus);

        JLabel qtyLabel = new JLabel(String.valueOf(item.getQuantity()));
        qtyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qtyLabel.setPreferredSize(new Dimension(36, 24));
        qtyPanel.add(qtyLabel);

        JButton plus = smallCircleButton("+");
        plus.addActionListener((ActionEvent e) -> {
            cartController.addToCart(product);
            reloadCart();
        });
        qtyPanel.add(plus);

        row.add(qtyPanel, c);

        // Remove button
        c.gridx = 3; c.weightx = 0.1;
        JPanel removeWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        removeWrap.setOpaque(false);
        JButton remove = new JButton("×");
        remove.setForeground(Color.decode("#EB5B00"));
        remove.setFont(new Font("SansSerif", Font.BOLD, 16));
        remove.setBorderPainted(false);
        remove.setContentAreaFilled(false);
        remove.setFocusPainted(false);
        remove.addActionListener(e -> {
            cartController.removeFromCart(product);
            reloadCart();
        });
        removeWrap.add(remove);
        row.add(removeWrap, c);

        // bottom separator (light)
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setOpaque(false);
        container.add(row, BorderLayout.CENTER);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(240, 240, 240));
        container.add(sep, BorderLayout.SOUTH);

        return container;
    }

    // Public so MainWindow can call it
    public void reloadCart() {
        itemsContainer.removeAll();

        List<CartItem> items = cartController.getItems();
        double total = 0;

        if (items.isEmpty()) {
            JPanel empty = new JPanel();
            empty.setOpaque(false);
            empty.setBorder(new EmptyBorder(24, 24, 24, 24));
            empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));

            // small placeholder icon
            JLabel bag = new JLabel("\uD83D\uDED2"); // cart emoji as simple placeholder
            bag.setFont(new Font("SansSerif", Font.PLAIN, 36));
            bag.setForeground(ACCENT_GREEN);
            bag.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel emptyTitle = new JLabel("Your cart is empty");
            emptyTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
            emptyTitle.setForeground(BRAND_BLUE);
            emptyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel hint = new JLabel("Add some delicious Algerian dishes to your cart");
            hint.setFont(new Font("SansSerif", Font.PLAIN, 13));
            hint.setForeground(new Color(80, 95, 110));
            hint.setAlignmentX(Component.CENTER_ALIGNMENT);

            empty.add(bag);
            empty.add(Box.createVerticalStrut(8));
            empty.add(emptyTitle);
            empty.add(Box.createVerticalStrut(6));
            empty.add(hint);

            itemsContainer.add(empty);
        } else {
            for (CartItem it : items) {
                itemsContainer.add(buildItemRow(it));
                itemsContainer.add(Box.createVerticalStrut(8));
                total += it.getTotalPrice();
            }
        }

        totalLabel.setText("Total: " + (int) total + " دج");

        itemsContainer.revalidate();
        itemsContainer.repaint();
    }

    // helper: small circular button for +/- 
    private JButton smallCircleButton(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(30, 30));
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(225,225,225)));
        b.setBackground(new Color(245, 245, 245));
        return b;
    }

    // primary orange button
 // primary orange button (styled like the beautiful card page)
    private JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);

        b.setOpaque(true);
        b.setContentAreaFilled(true);

        b.setBackground(BRAND_ORANGE);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Rounded border + padding
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BRAND_ORANGE, 2, true),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));

        // Prevent truncation ("Procee...")
        b.setMinimumSize(new Dimension(200, 45));
        b.setPreferredSize(new Dimension(200, 45));
        
        // Hover = lighter orange
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(245, 110, 25));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(BRAND_ORANGE);
            }
        });

        return b;
    }




    // outline button (bordered)
    private JButton createOutlineButton(String text) {
        JButton b = new JButton(text);

        // Fix macOS UI overriding colors
        b.setOpaque(true);
        b.setContentAreaFilled(true);

        // Default state = white background + blue border + blue text
        b.setBackground(Color.WHITE);
        b.setForeground(BRAND_BLUE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Border (rounded)
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BRAND_BLUE, 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover state = blue background + white text
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(BRAND_BLUE);
                b.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(Color.WHITE);
                b.setForeground(BRAND_BLUE);
            }
        });

        return b;
    }


}
