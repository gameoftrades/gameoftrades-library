package io.gameoftrades.ui.overlay;

import java.awt.Font;
import java.awt.Graphics2D;

import io.gameoftrades.ui.KaartDisplay;

/**
 * Een Overlay bovenop het {@link KaartDisplay} zodat eigen visualisaties mogelijk worden.   
 */
public interface Overlay {

    /**
     * Tekent een laag boven op het {@link KaartDisplay}.
     * 
     * @param g het graphics object om mee te tekenen.
     * @param font het font dat gebruikt wordt.
     * @param tilesize de grootte van de tiles.
     * @param tilehalfwidth de have grootte van de tiles.
     * @param fontSize de grootte van het font.
     */
    void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize);
    
}
