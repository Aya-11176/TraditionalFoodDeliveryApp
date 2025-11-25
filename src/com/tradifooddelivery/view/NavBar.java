package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavBar extends JPanel {
    private final MainWindow parent;
    private boolean transparentMode;

    private final JLabel titleLabel;
    private final JPanel rightPanel;
    private final JLabel cartIcon;

    private static final Color ORANGE = new Color(235, 91, 0);
    private static final Color DARK_BLUE = new Color(20, 61, 96);

    public NavBar(MainWindow parent, boolean startTransparent) {
        this.parent = parent;
        this.transparentMode = startTransparent;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        setOpaque(true);

        // LEFT: logo + name
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel circle = new JLabel("H&S", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ORANGE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String txt = "H&S";
                int x = (getWidth() - fm.stringWidth(txt)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(txt, x, y);
                g2.dispose();
            }
        };
        circle.setPreferredSize(new Dimension(42, 42));
        left.add(circle);

        titleLabel = new JLabel("H&S Restaurant");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        left.add(titleLabel);

        // RIGHT: menu items
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 26, 0));
        rightPanel.setOpaque(false);

        addNavItem("Home", "home");
        addNavItem("Menu", "menu");
        addNavItem("Contact", "contact");

        cartIcon = new JLabel("\uD83D\uDED2");
        cartIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cartIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cartIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.showPanel("cart");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cartIcon.setForeground(ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cartIcon.setForeground(transparentMode ? Color.WHITE : DARK_BLUE);
            }
        });

        rightPanel.add(cartIcon);

        add(left, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        applyMode();
    }

    private void addNavItem(String text, String panel) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lbl.setForeground(ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lbl.setForeground(transparentMode ? Color.WHITE : DARK_BLUE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                parent.showPanel(panel);
            }
        });

        rightPanel.add(lbl);
    }

    public void setTransparent(boolean transparent) {
        this.transparentMode = transparent;
        applyMode();
    }

    private void applyMode() {
        if (transparentMode) {
            setBackground(new Color(0, 0, 0, 0));
            titleLabel.setForeground(Color.WHITE);
            cartIcon.setForeground(Color.WHITE);
            for (Component c : rightPanel.getComponents()) {
                c.setForeground(Color.WHITE);
            }
            setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        } else {
            setBackground(Color.WHITE);
            titleLabel.setForeground(DARK_BLUE);
            cartIcon.setForeground(DARK_BLUE);
            for (Component c : rightPanel.getComponents()) {
                c.setForeground(DARK_BLUE);
            }
            // subtle bottom border to mimic site separation
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0,0,1,0,new Color(230,230,230)),
                    BorderFactory.createEmptyBorder(10,28,10,28)
            ));
        }
        repaint();
    }
}

