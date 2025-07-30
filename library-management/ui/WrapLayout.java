package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JScrollPane;

public class WrapLayout extends FlowLayout {
    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

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
            int maxWidth = target.getWidth();
            if (maxWidth == 0 && target.getParent() instanceof JScrollPane) {
                maxWidth = ((JScrollPane) target.getParent()).getViewport().getWidth();
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int width = maxWidth > 0 ? maxWidth - insets.left - insets.right - hgap * 2 : Integer.MAX_VALUE;

            int x = 0, y = insets.top + vgap, rowHeight = 0;
            int requiredWidth = 0;

            for (Component c : target.getComponents()) {
                if (!c.isVisible()) continue;
                Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                if (x == 0 || x + d.width <= width) {
                    if (x > 0) x += hgap;
                    x += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                } else {
                    x = d.width;
                    y += vgap + rowHeight;
                    rowHeight = d.height;
                }
                requiredWidth = Math.max(requiredWidth, x);
            }

            y += rowHeight + vgap;
            return new Dimension(requiredWidth + hgap * 2, y + insets.bottom);
        }
    }
}
