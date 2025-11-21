package com.tradifooddelivery.view;

import com.tradifooddelivery.controller.CartController;
import com.tradifooddelivery.model.CartItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartPanel extends JPanel {
    private final MainWindow parent;
    private final CartController cartController;
    private final JPanel itemsContainer;
    private final JLabel totalLabel;

    public CartPanel(MainWindow parent) {
        this.parent = parent;
        this.cartController = parent.getCartController();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new NavBar(parent, true), BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Your Cart");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(title, BorderLayout.NORTH);

        itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(itemsContainer);
        scroll.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        main.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        totalLabel = new JLabel("Total: 0.00 DA");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        bottom.add(totalLabel);

        JButton clearBtn = new JButton("Clear Cart");
        clearBtn.addActionListener(e -> {
            cartController.clearCart();
            refresh();
        });
        bottom.add(clearBtn);

        JButton checkout = new JButton("Checkout");
        checkout.addActionListener(e -> onCheckout());
        bottom.add(checkout);

        main.add(bottom, BorderLayout.SOUTH);
        add(main, BorderLayout.CENTER);

        refresh();
    }

    private void refresh() {
        itemsContainer.removeAll();
        List<CartItem> items = cartController.getCart();

        if (items.isEmpty()) {
            JLabel empty = new JLabel("Your cart is empty.", SwingConstants.CENTER);
            empty.setFont(new Font("SansSerif", Font.PLAIN, 18));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemsContainer.add(Box.createVerticalGlue());
            itemsContainer.add(empty);
            itemsContainer.add(Box.createVerticalGlue());
        } else {
            for (CartItem item : items) {
                itemsContainer.add(createItemRow(item));
                itemsContainer.add(Box.createVerticalStrut(8));
            }
        }

        totalLabel.setText("Total: " + String.format("%.2f", cartController.getTotal()) + " DA");
        revalidate();
        repaint();
    }

    private JPanel createItemRow(CartItem item) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createLineBorder(new Color(220,220,220), 1));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        String name = item.getProduct().getName();
        JLabel left = new JLabel("<html><b>" + name + "</b><br/>Price: " + item.getProduct().getPrice() + " DA</html>");
        left.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        row.add(left, BorderLayout.WEST);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setBackground(Color.WHITE);

        JLabel qtyLabel = new JLabel("Qty:");
        center.add(qtyLabel);

        JTextField qtyField = new JTextField(String.valueOf(item.getQuantity()), 3);
        center.add(qtyField);

        JButton update = new JButton("Update");
        update.addActionListener(e -> {
            try {
                int q = Integer.parseInt(qtyField.getText().trim());

if (q <= 0) return;
                item.setQuantity(q);
                refresh();
            } catch (NumberFormatException ex) {
                // ignore
            }
        });
        center.add(update);

        row.add(center, BorderLayout.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(Color.WHITE);
        JLabel subtotal = new JLabel(String.format("%.2f DA", item.getTotalPrice()));
        subtotal.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        right.add(subtotal);

        JButton remove = new JButton("Remove");
        remove.addActionListener(e -> {
            cartController.removeItem(item.getProduct());
            refresh();
        });
        right.add(remove);

        row.add(right, BorderLayout.EAST);

        return row;
    }

    private void onCheckout() {
        if (cartController.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Your name:");
        if (name == null || name.trim().isEmpty()) return;
        String address = JOptionPane.showInputDialog(this, "Delivery address:");
        if (address == null || address.trim().isEmpty()) return;

        // You can call OrderController here to save or send order. For now show success:
        JOptionPane.showMessageDialog(this, "Order placed. Thank you, " + name + "!");
        cartController.clearCart();
        refresh();
    }
}
