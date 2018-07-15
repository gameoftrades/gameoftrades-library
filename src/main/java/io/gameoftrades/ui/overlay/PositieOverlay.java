package io.gameoftrades.ui.overlay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.markt.actie.HandelsPositie;

public class PositieOverlay implements Overlay {

    private HandelsPositie positie;

    public PositieOverlay(HandelsPositie positie) {
        setPositie(positie);
    }

    public void setPositie(HandelsPositie positie) {
        this.positie = positie;
    }

    @Override
    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        if (positie != null) {
            g.setColor(Color.WHITE);
            Coordinaat coordinaat = positie.getCoordinaat();
            int size = tilehalfwidth / 2;
            int x = coordinaat.getX() * tilesize + tilehalfwidth;
            int y = coordinaat.getY() * tilesize + tilehalfwidth;
            g.fillOval(x - size, y - size, tilehalfwidth, tilehalfwidth);
        }
    }

}
