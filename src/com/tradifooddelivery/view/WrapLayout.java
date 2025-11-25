package com.tradifooddelivery.view;

import java.awt.*;

public class WrapLayout extends FlowLayout {
    public WrapLayout() { super(LEFT); }
    public WrapLayout(int align) { super(align); }
    public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int hgap = getHgap();
            int vgap = getVgap();
            int width = target.getWidth();
            if (width == 0) width = Integer.MAX_VALUE;

            int x = 0, y = vgap;
            int rowHeight = 0;

            for (Component c : target.getComponents()) {
                if (!c.isVisible()) continue;
                Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                if (x + d.width > width) {
                    x = 0;
                    y += rowHeight + vgap;
                    rowHeight = 0;
                }
                x += d.width + hgap;
                rowHeight = Math.max(rowHeight, d.height);
            }

            y += rowHeight + vgap;
            Insets insets = target.getInsets();
            y += insets.top + insets.bottom;
            return new Dimension(width, y);
        }
    }
}
