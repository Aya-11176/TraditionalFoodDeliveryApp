package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavBar extends JPanel {
    private static final Color ORANGE = new Color(255, 102, 0);
    private final MainWindow parent;

    public NavBar(MainWindow parent, boolean whiteBackground) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));
        setBackground(whiteBackground ? Color.WHITE : new Color(0, 0, 0, 0)); // transparent if false

        // LEFT: logo + name
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        CircleLabel circle = new CircleLabel("H&S");
        circle.setPreferredSize(new Dimension(48, 48));
        JLabel name = new JLabel("H&S Restaurant");
        name.setFont(new Font("Serif", Font.BOLD, 18));
        name.setForeground(whiteBackground ? Color.BLACK : Color.WHITE);
        left.add(circle);
        left.add(name);

        // RIGHT: menu items
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 0));
        right.setOpaque(false);

        String[] items = {"Home", "Menu", "Contact"};
        for (String s : items) {
            JLabel lbl = new JLabel(s);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lbl.setForeground(whiteBackground ? Color.BLACK : Color.WHITE);
            lbl.addMouseListener(new HoverLabel(lbl, s, whiteBackground));
            right.add(lbl);
        }

        JLabel cart = new JLabel("\uD83D\uDED2");
        cart.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cart.setForeground(whiteBackground ? Color.BLACK : Color.WHITE);
        cart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.showPanel("cart");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cart.setForeground(ORANGE);
                cart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cart.setForeground(whiteBackground ? Color.BLACK : Color.WHITE);
                cart.setCursor(Cursor.getDefaultCursor());
            }
        });
        right.add(cart);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
    }

    // inner hover class
    private class HoverLabel extends MouseAdapter {
        private final JLabel label;
        private final String name;
        private final boolean whiteBg;

        HoverLabel(JLabel label, String name, boolean whiteBg) {
            this.label = label;
            this.name = name;
            this.whiteBg = whiteBg;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            label.setForeground(ORANGE);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setForeground(whiteBg ? Color.BLACK : Color.WHITE);
            label.setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (name) {
                case "Home" -> parent.showPanel("home");
                case "Menu" -> parent.showPanel("menu");
                case "Contact" -> parent.showPanel("contact");
            }
        }
    }

    // circle logo
    private static class CircleLabel extends JLabel {
        CircleLabel(String text) {
            super(text, SwingConstants.CENTER);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 16));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            int size = Math.min(w, h);
            int x = (w - size) / 2;
            int y = (h - size) / 2;
            g2.setColor(new Color(255, 102, 0));
            g2.fillOval(x, y, size, size);
            g2.setColor(new Color(255, 255, 255, 40));
            g2.drawOval(x, y, size - 1, size - 1);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            int s = Math.max(d.width, d.height);
            return new Dimension(s + 14, s + 14);
        }
    }
}
