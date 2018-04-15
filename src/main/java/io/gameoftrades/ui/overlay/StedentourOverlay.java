package io.gameoftrades.ui.overlay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import io.gameoftrades.model.kaart.Stad;

public class StedentourOverlay implements Overlay {

    private static final Color TOUR_KLEUR = new Color(40, 80, 255);

    private List<Stad> steden;

    public StedentourOverlay(List<Stad> steden) {
        this.steden = steden;
    }
    
    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        g.setColor(TOUR_KLEUR);
        int lx = -1;
        int ly = -1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Stad stad : steden) {
            int x = stad.getCoordinaat().getX() * tilesize + tilehalfwidth;
            int y = stad.getCoordinaat().getY() * tilesize + tilehalfwidth;
            if (lx >= 0) {
                g.drawLine(lx, ly, x, y);
            }
            lx = x;
            ly = y;
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

}
