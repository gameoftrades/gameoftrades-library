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
        g.setColor(new Color(0.8f, 1.0f, 0.8f, 0.8f));
        Coordinaat coordinaat = positie.getCoordinaat();
        int x = coordinaat.getX() * tilesize;
        int y = coordinaat.getY() * tilesize;
        g.fillOval(x, y, 16, 16);
    }

    
}
