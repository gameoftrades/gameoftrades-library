package io.gameoftrades.model.markt.actie;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.util.Assert;

/**
 * De HandelsPositie bevat de de huidige situatie van de Handelaar.
 * <p>
 * Dat wil zeggen, zijn huidige locatie, zijn kapitaal, de beschikbare ruimte en de voorraad. 
 * </p>  
 * <p>
 * Deze klasse biedt een aantal methoden aan die samen met de verschillende Acties er voor
 * zorgen dat de Handelaar zijn positie kan verbeteren door te Bewegen, Kopen en Verkopen.
 * </p>
 */
public final class HandelsPositie {

    private Stad stad;
    private Coordinaat coordinaat;

    private int kapitaal;
    private int ruimte;
    private Map<Handelswaar, Integer> voorraad;

    private Wereld wereld;
    private int maxActie;

    private int totaalActie;
    private int totaalWinst;

    /**
     * maakt een nieuwe handels positie na het bewegen van een stad naar een andere stad.
     * Deze actie slaat voor het gemak de gedetailleerde routering over en gebruikt een
     * van te voren berekend aantal bewegingspunten. 
     * @param org de originele handels positie.
     * @param van de stad waarvan vertrokken wordt.
     * @param naar de stad waar naartoe genavigeerd wordt.
     * @param bw het aantal bewegingspunten.
     * @return de nieuwe handelspositie.
     */
    static HandelsPositie beweeg(HandelsPositie org, Stad van, Stad naar, int bw) {
        HandelsPositie copy = new HandelsPositie(org);
        if (!copy.stad.equals(van)) {
            throw new IllegalArgumentException("De huidige locatie is " + org.stad + " en niet " + van);
        }
        copy.stad = naar;
        copy.totaalActie += bw;
        return copy;
    }

    /**
     * maakt de nieuwe handels positie na een navigatie (het gedetailleerd over de kaart bewegen).
     * @param org de originele positie.
     * @param van het coordinaat waarvan vertrokken wordt.
     * @param richting de richting waarheen bewogen wordt.
     * @return de nieuwe handels positie.
     */
    static HandelsPositie navigeer(HandelsPositie org, Coordinaat van, Richting richting) {
        if (!org.coordinaat.equals(van)) {
            throw new IllegalArgumentException("Niet op positie " + van + " maar op " + org.coordinaat);
        }
        HandelsPositie copy = new HandelsPositie(org);
        Terrein terrein = org.wereld.getKaart().getTerreinOp(org.coordinaat);
        copy.coordinaat = org.wereld.getKaart().kijk(terrein, richting).getCoordinaat();
        copy.stad = null;
        copy.totaalActie += terrein.getTerreinType().getBewegingspunten();
        for (Stad stad : org.wereld.getSteden()) {
            if (stad.getCoordinaat().equals(copy.coordinaat)) {
                copy.stad = stad;
            }
        }
        return copy;
    }

    /**
     * maakt een nieuwe handelspositie na het kopen van handelswaar.
     * @param org de originele handelspositie.
     * @param aantal het aantal stuks Handelswaar dat gekocht wordt.
     * @param hw de handelswaar.
     * @param geld de totale hoeveelheid geld dat voor de handelswaar betaald wordt.
     * @return de nieuwe positie.
     */
    static HandelsPositie koop(HandelsPositie org, int aantal, Handelswaar hw, int geld) {
        HandelsPositie copy = new HandelsPositie(org);
        if (org.ruimte - aantal < 0) {
            throw new IllegalArgumentException("Onvoldoende ruimte om " + aantal + " te kunnen kopen. Er is maar " + org.ruimte + " beschikbaar.");
        }
        if (org.kapitaal - geld < 0) {
            throw new IllegalArgumentException("Onvoldoende kapitaal.");
        }
        copy.ruimte = copy.ruimte - aantal;
        copy.kapitaal = org.kapitaal- geld;
        copy.totaalWinst = org.totaalWinst - geld;
        copy.totaalActie += 1;
        if (copy.voorraad.containsKey(hw)) {
            copy.voorraad.put(hw, copy.voorraad.get(hw) + aantal);
        } else {
            copy.voorraad.put(hw, aantal);
        }
        return copy;
    }

    /**
     * maakt een nieuwe handelspositie na het verkopen van handelswaar.
     * @param org de originele positie.
     * @param aantal het aantal stuks handelswaar dat verkocht gaat worden.
     * @param hw de handelswaar.
     * @param geld de totale hoeveelheid geld die ontvangen wordt voor de handelswaar.
     * @return de nieuwe positie.
     */
    public static HandelsPositie verkoop(HandelsPositie org, int aantal, Handelswaar hw, int geld) {
        HandelsPositie copy = new HandelsPositie(org);
        if (!org.voorraad.containsKey(hw)) {
            throw new IllegalArgumentException("Geen " + hw + " op voorraad.");
        }
        if (aantal > org.voorraad.get(hw)) {
            throw new IllegalArgumentException("Onvoldoende " + hw + " op voorraad.");
        }
        copy.ruimte = copy.ruimte + aantal;
        copy.kapitaal = org.kapitaal + geld;
        copy.totaalWinst = org.totaalWinst + geld;
        copy.totaalActie += 1;
        copy.voorraad.put(hw, copy.voorraad.get(hw) - aantal);
        if (copy.voorraad.get(hw) == 0) {
            copy.voorraad.remove(hw);
        }
        return copy;
    }

    /**
     * maakt een nieuwe initieele handelspositie.
     * @param wereld de wereld waarin gehandeld gaat worden.
     * @param stad de stad waarin gestart wordt.
     * @param kapitaal het begin kapitaal.
     * @param ruimte de hoeveelheid ruimte in de voorraad.
     * @param maxActie het maximum aantal bewegingspunten dat verbruikt mag worden.
     */
    public HandelsPositie(Wereld wereld, Stad stad, int kapitaal, int ruimte, int maxActie) {
        Assert.notNull(wereld);
        Assert.notNull(stad);
        this.wereld = wereld;
        this.stad = stad;
        this.maxActie = maxActie;
        this.coordinaat = stad.getCoordinaat();
        this.kapitaal = kapitaal;
        this.ruimte = ruimte;
        this.voorraad = new TreeMap<>();
    }

    /**
     * maakt een kopie van de gegeven handelspositie.
     * @param pos de positie.
     */
    protected HandelsPositie(HandelsPositie pos) {
        Assert.notNull(pos);
        this.wereld = pos.wereld;
        this.stad = pos.stad;
        this.kapitaal = pos.kapitaal;
        this.ruimte = pos.ruimte;
        this.voorraad = new TreeMap<>(pos.voorraad);
        this.coordinaat = pos.coordinaat;
        this.totaalActie = pos.totaalActie;
        this.totaalWinst = pos.totaalWinst;
        this.maxActie = pos.maxActie;
    }

    public Stad getStad() {
        return stad;
    }

    public int getKapitaal() {
        return kapitaal;
    }

    public int getRuimte() {
        return ruimte;
    }

    public Map<Handelswaar, Integer> getVoorraad() {
        return Collections.unmodifiableMap(voorraad);
    }

    public int getTotaalActie() {
        return totaalActie;
    }

    public int getTotaalWinst() {
        return totaalWinst;
    }

    public Coordinaat getCoordinaat() {
        return coordinaat;
    }

    public int getMaxActie() {
        return maxActie;
    }

}
