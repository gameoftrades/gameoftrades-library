package io.gameoftrades.ui.overlay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import io.gameoftrades.debug.Debugger.Tak;

public class BoomOverlay implements Overlay {

    public static final Color TREE_COLOR = new Color(96, 64, 96);

    private Tak root;

    public BoomOverlay(Tak root) {
        this.root = root;
    }

    @Override
    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        g.setColor(TREE_COLOR);
        for (Tak child : root.getTakken()) {
            draw(g, tilesize, root, child);
        }
    }

    private void draw(Graphics2D g, int tilesize, Tak parent, Tak child) {
        g.setStroke(new BasicStroke(tilesize / 16));
        int x1 = parent.getCoordinaat().getX();
        int y1 = parent.getCoordinaat().getY();
        int x2 = child.getCoordinaat().getX();
        int y2 = child.getCoordinaat().getY();
        int half = tilesize / 2;
        int x12 = x2 * tilesize + half;
        int y12 = y2 * tilesize + half;
        g.drawLine(x1 * tilesize + half, y1 * tilesize + half, x12, y12);
        if (x1 == x2) {
            if (y1 < y2) {
                // s
                g.drawLine(x12, y12, x12 + tilesize / 6, y12 - tilesize / 6);
                g.drawLine(x12, y12, x12 - tilesize / 6, y12 - tilesize / 6);
            } else {
                // n
                g.drawLine(x12, y12, x12 + tilesize / 6, y12 + tilesize / 6);
                g.drawLine(x12, y12, x12 - tilesize / 6, y12 + tilesize / 6);
            }
        } else {
            if (x1 > x2) {
                // e
                g.drawLine(x12, y12, x12 + tilesize / 6, y12 + tilesize / 6);
                g.drawLine(x12, y12, x12 + tilesize / 6, y12 - tilesize / 6);
            } else {
                // w
                g.drawLine(x12, y12, x12 - tilesize / 6, y12 + tilesize / 6);
                g.drawLine(x12, y12, x12 - tilesize / 6, y12 - tilesize / 6);
            }
        }
        for (Tak sub : child.getTakken()) {
            draw(g, tilesize, child, sub);
        }
    }

}
