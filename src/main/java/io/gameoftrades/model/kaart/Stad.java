package io.gameoftrades.model.kaart;

import io.gameoftrades.util.Assert;

/**
 * Een stad op de kaart. 
 */
public final class Stad {

    /**
     * maakt een nieuwe stad met de gegeven naam op het gegeven coordinaat.
     * @param coordinaat het coordinaat van de stad.
     * @param naam the naam van de stad.
     */
    public static Stad op(Coordinaat coordinaat, String naam) {
        return new Stad(coordinaat,naam);
    }
    
    private Coordinaat coordinaat;
    private String naam;

    private Stad(Coordinaat coordinaat, String naam) {
        Assert.notNull(coordinaat);
        Assert.notEmpty(naam);
        this.coordinaat = coordinaat;
        this.naam = naam;
    }

    public Coordinaat getCoordinaat() {
        return coordinaat;
    }

    public String getNaam() {
        return naam;
    }

    @Override
    public String toString() {
        return "Stad(" + getCoordinaat() + "," + naam + ")";
    }

}
