package io.gameoftrades.ui.overlay;

import java.awt.Font;
import java.awt.Graphics2D;

public interface Overlay {

    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize);
    
}
