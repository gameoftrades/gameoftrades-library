package io.gameoftrades.model.lader;

import io.gameoftrades.model.Wereld;

/**
 * Verantwoordelijk voor het laden van de wereld vanuit een bestand. 
 */
public interface WereldLader {

    /**
     * laad de wereld van de gegeven classpath resource.
     * @param resource de resource.
     * @return de wereld.
     */
    Wereld laad(String resource);

}
