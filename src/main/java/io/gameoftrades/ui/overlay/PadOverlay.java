package io.gameoftrades.ui.overlay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

public class PadOverlay implements Overlay {

    private static final Color PAD_KLEUR = new Color(220, 255, 220);

    private Coordinaat start;
    private Pad pad;
    
    public PadOverlay(Coordinaat start, Pad pad) {
        this.start = start;
        this.pad = pad;
    }
    
    @Override
    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(PAD_KLEUR);
        g.setStroke(new BasicStroke(tilesize/16));
        int x = start.getX() * tilesize + tilehalfwidth;
        int y = start.getY() * tilesize + tilehalfwidth;
        g.fillOval(x - tilehalfwidth / 2, y - tilehalfwidth / 2, tilehalfwidth, tilehalfwidth);
        int lx = x;
        int ly = y;
        for (Richting r : pad.getBewegingen()) {
            switch (r) {
            case NOORD:
                y = y - tilesize;
                break;
            case ZUID:
                y = y + tilesize;
                break;
            case WEST:
                x = x - tilesize;
                break;
            case OOST:
                x = x + tilesize;
                break;
            }
            g.drawLine(lx, ly, x, y);
            lx = x;
            ly = y;
        }
        g.fillOval(x - tilehalfwidth / 2, y - tilehalfwidth / 2, tilehalfwidth, tilehalfwidth);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

}
