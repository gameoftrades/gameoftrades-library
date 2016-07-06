package io.gameoftrades.model.kaart;

/**
 * Een stuk van de af te leggen route.
 * 
 * <b>LET OP: Je moet zelf een implementatie maken van deze klasse!</b>
 */
public interface Pad {

    int getTotaleTijd();

    Richting[] getBewegingen();

    Pad omgekeerd();

}
