package io.gameoftrades.ui.overlay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class IntegerOverlay implements Overlay {

    private static final Color OVERLAY_KLEUR = new Color(240, 255, 240);

    private Integer[][] overlay;

    public IntegerOverlay(Integer[][] overlay) {
        this.overlay = overlay;
    }
    
    @Override
    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        g.setColor(OVERLAY_KLEUR);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int y = 0; y < overlay.length; y++) {
            for (int x = 0; x < overlay[0].length; x++) {
                Integer value = overlay[y][x];
                if (value != null) {
                    g.drawString(String.valueOf(value), x * tilesize + fontSize / 4, y * tilesize + fontSize);
                }
            }
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

}
