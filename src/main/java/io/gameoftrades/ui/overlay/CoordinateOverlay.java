package io.gameoftrades.ui.overlay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Map;

import io.gameoftrades.model.kaart.Coordinaat;

public class CoordinateOverlay implements Overlay {

    private static final Color FOG = new Color(96, 96, 96, 128);

    private Map<Coordinaat, ?> coords;
    private Color c;

    private Coordinaat best;

    private Color b;

    public CoordinateOverlay(Map<Coordinaat, ?> coords, Color c) {
        this.coords = coords;
        this.c = c;

    }

    public CoordinateOverlay(Map<Coordinaat, ?> coords, Color c, Coordinaat best, Color b) {
        this(coords, c);
        this.best = best;
        this.b = b;
    }

    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        g.setFont(font);
        for (Map.Entry<Coordinaat, ?> e : coords.entrySet()) {
            int x = e.getKey().getX() * tilesize;
            int y = e.getKey().getY() * tilesize;
            int tx = x + fontSize / 4;
            int ty = y + fontSize;
            g.setColor(FOG);
            g.fillRect(x, y, tilesize, tilesize);
            if (best != null && e.getKey().equals(best)) {
                g.setColor(b);
            } else {
                g.setColor(c);
            }
            g.drawString(limit(String.valueOf(e.getValue()), 2), tx, ty);
        }
    }

    private String limit(String s, int i) {
        if (s.length() > 2) {
            return s.substring(0, 2);
        }
        return s;
    }

}
