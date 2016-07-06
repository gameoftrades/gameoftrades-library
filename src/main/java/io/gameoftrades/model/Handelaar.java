package io.gameoftrades.model;

import java.util.List;

import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handelsplan;

/**
 * De Handelaar probeert zo snel mogelijk zo veel mogelijk geld te verdienen.
 * <p>
 * Om dit te kunnen doen moet hij in staat zijn om:
 * <ul>
 * <li>het snelste pad tussen twee steden te kunnen vinden (opdracht 2)</li>
 * <li>een route te bepalen waarbij hij, zo snel mogelijk, iedere stad slechts 1 keer aandoet (opdracht 3)</li>
 * <li>een optimaal handelsplan samen te stellen (opdracht 4)</li>
 * </p>
 * Echter voordat het zo ver is moet eerst de handelswereld geladen kunnen worden via de Loader (opdracht 1). 
 */
public interface Handelaar {

    /**
     * Maakt een nieuwe instantie van een WereldLader.
     * @return de lader om handels werelden in te laden (opdracht 1).
     */
    WereldLader nieuweWereldLader();

    /**
     * bepaalt het snelste pad tussen twee steden (opdracht 2).
     * @param kaart de kaart met het terrein dat de snelheid van reizen bepaalt.
     * @param van de stad waarvan vertrokken wordt.
     * @param naar de stad waar naar toe gereist wordt. 
     * @return het snelste pad tussen de twee steden (dat is niet altijd het kortste pad!)
     */
    Pad bepaalSnelstePad(Kaart kaart, Stad van, Stad naar);

    /**
     * bepaalt de beste volgorde van reizen tussen de steden zodat dit het minste tijd kost en
     * iedere stad slechts 1 keer wordt aangedaan (opdracht 3).
     * @param kaart de kaart waarop de steden liggen. 
     * @param steden de steden die aangedaan moeten worden.
     * @return een lijst op volgorde waarin de steden aangedaan moeten worden.
     */
    List<Stad> stedenTour(Kaart kaart, List<Stad> steden);

    /**
     * maakt een handelsplan dat zo veel mogelijk winst maakt in zo min mogelijk tijd (opdracht 4).
     * @param wereld de wereld waarvoor het handelsplan gemaakt moet worden. 
     * @param startStad de stad waarin gestart wordt.
     * @param kapitaal het start kapitaal om mee in te kopen.
     * @param capaciteit de maximale opslag capaciteit.
     * @param maxTijd het maximaal aantal te gebruiken bewegingspunten. 
     * @return het plan.
     */
    Handelsplan optimaliseerWinst(Wereld wereld, Stad startStad, int kapitaal, int capaciteit, int maxTijd);
}
