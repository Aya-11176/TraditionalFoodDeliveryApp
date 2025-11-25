package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePanel extends JPanel {
    private Image bg;
    private static final Color TITLE_COLOR = new Color(221, 235, 157);
    private static final Color ORANGE = new Color(255, 102, 0);
    private MainWindow parent;

    public HomePanel(MainWindow parent) {
        this.parent = parent;

        // Load background image from classpath
        try {
            bg = new ImageIcon(getClass().getClassLoader().getResource("couscous-bg.jpeg")).getImage();
        } catch (Exception e) {
            bg = null;
        }

        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel navBar = createNavBar();
        add(navBar, BorderLayout.NORTH);

        JPanel hero = createHeroSection();
        add(hero, BorderLayout.CENTER);
    }

    private JPanel createNavBar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setOpaque(false);
        nav.setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));

        // LEFT: logo and name
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        CircleLabel circle = new CircleLabel("H&S");
        circle.setPreferredSize(new Dimension(48, 48));
        JLabel name = new JLabel("H&S Restaurant");
        name.setFont(new Font("Serif", Font.BOLD, 18));
        name.setForeground(Color.WHITE);
        left.add(circle);
        left.add(name);

        // RIGHT: navigation links
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 0));
        right.setOpaque(false);

        String[] items = {"Home", "Menu", "Contact"};
        for (String s : items) {
            JLabel lbl = new JLabel(s);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lbl.setForeground(Color.WHITE);
            lbl.addMouseListener(new SimpleHoverLabel(lbl) {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (s) {
                        case "Home" -> parent.showPanel("home");
                        case "Menu" -> parent.showPanel("menu");
                        case "Contact" -> parent.showPanel("contact");
                    }
                }
            });
            right.add(lbl);
        }

        JLabel cart = new JLabel("\uD83D\uDED2");
        cart.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cart.setForeground(Color.WHITE);
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
                cart.setForeground(Color.WHITE);
                cart.setCursor(Cursor.getDefaultCursor());
            }
        });
        right.add(cart);

        nav.add(left, BorderLayout.WEST);
        nav.add(right, BorderLayout.EAST);

        return nav;
    }

    private JPanel createHeroSection() {
        JPanel hero = new JPanel(null);
        hero.setOpaque(false);

        JLabel title = new JLabel("<html>Authentic Algerian<br/>Cuisine</html>");
        title.setFont(new Font("Serif", Font.BOLD, 72));
        title.setForeground(TITLE_COLOR);
        title.setBounds(80, 150, 900, 220);
        hero.add(title);

        JLabel desc = new JLabel("<html>Experience the rich flavors and traditions of Algeria in every bite.<br>"
                + "Our dishes are crafted with authentic recipes and the freshest ingredients.</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 18));
        desc.setForeground(Color.WHITE);
        desc.setBounds(80, 380, 800, 80);
        hero.add(desc);

        JButton primary = styledPrimaryButton("View Our Menu");
        primary.setBounds(80, 480, 180, 48);
        primary.addActionListener(e -> parent.showPanel("menu"));

        JButton transparent = styledTransparentButton("Reserve a Table");
        transparent.setBounds(280, 480, 180, 48);
        transparent.addActionListener(e -> parent.showPanel("reservation"));


        hero.add(primary);
        hero.add(transparent);

        return hero;
    }

    private JButton styledPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 15));
        b.setBackground(ORANGE);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            Color orig = b.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(Color.WHITE);
                b.setForeground(ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(orig);
                b.setForeground(Color.WHITE);
            }
        });
        return b;
    }

    private JButton styledTransparentButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 15));
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        b.setContentAreaFilled(false);
        b.setForeground(Color.WHITE);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setContentAreaFilled(true);
                b.setBackground(Color.WHITE);
                b.setForeground(ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setContentAreaFilled(false);
                b.setForeground(Color.WHITE);
            }
        });
        return b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(60, 60, 70));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    // Hover helper (now orange + hand cursor)
    private static class SimpleHoverLabel extends MouseAdapter {
        private final JLabel label;

        SimpleHoverLabel(JLabel label) {
            this.label = label;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            label.setForeground(ORANGE);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setForeground(Color.WHITE);
            label.setCursor(Cursor.getDefaultCursor());
        }
    }

    // Inner orange circle label
    private class CircleLabel extends JLabel {
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
            g2.setColor(ORANGE);
            g2.fillOval(x, y, size, size);
            g2.setColor(new Color(255, 255, 255, 30));
            g2.setStroke(new BasicStroke(1f));
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
