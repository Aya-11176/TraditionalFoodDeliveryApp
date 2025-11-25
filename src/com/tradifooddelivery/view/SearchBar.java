package com.tradifooddelivery.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SearchBar extends JTextField {

    private String placeholder = "Search dishes...";

    public SearchBar(String placeholder) {
        this.placeholder = placeholder;

        setPreferredSize(new Dimension(640, 48));
        setFont(new Font("SansSerif", Font.PLAIN, 15));

        // spacing: left padding for icon, right padding for text
        setBorder(BorderFactory.createEmptyBorder(12, 44, 12, 16));
        setOpaque(false);
        setForeground(new Color(27, 44, 63));
        setCaretColor(new Color(27, 44, 63));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = getHeight(); // full pill shape
        int width = getWidth();
        int height = getHeight();

        // background
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, arc, arc));

        // green border
        g2.setColor(new Color(160, 200, 120)); // #A0C878
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new RoundRectangle2D.Double(1, 1, width - 3, height - 3, arc, arc));

        // draw search icon (vector style)
        drawSearchIcon(g2, 16, height / 2 - 6, new Color(20, 61, 96));

        super.paintComponent(g);

        // draw placeholder
        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setFont(getFont());
            g2.setColor(Color.GRAY);
            Insets in = getInsets();
            g2.drawString(placeholder, in.left, getHeight() / 2 + 5);
        }

        g2.dispose();
    }

    private void drawSearchIcon(Graphics2D g2, int x, int y, Color color) {
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(color);
        int r = 6; // radius of circle
        g2.drawOval(x, y, r * 2, r * 2);
        g2.drawLine(x + r * 2 - 1, y + r * 2 - 1, x + r * 2 + 6, y + r * 2 + 6);
    }

    public void setPlaceholder(String text) {
        this.placeholder = text;
        repaint();
    }

    public void resetPlaceholder() {
        repaint();
    }
}

