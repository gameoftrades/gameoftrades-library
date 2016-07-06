package io.gameoftrades.model.markt.actie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.gameoftrades.model.markt.actie.BeweegActie;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import io.gameoftrades.model.markt.actie.KoopActie;
import io.gameoftrades.model.markt.actie.VerkoopActie;

public class VerkoopActieTest extends AbstractActieTest {

    @Test
    public void zouMoetenKunnenVerkopen() {
        HandelsPositie positie = new HandelsPositie(wereld, stad2, 10, 10, 8);
        positie = new KoopActie(h2).voerUit(positie);
        positie = new BeweegActie(kaart, stad2, stad1, new MockPad()).voerUit(positie);

        VerkoopActie verkoopActie = new VerkoopActie(h4);
        assertTrue(verkoopActie.isMogelijk(positie));
        
        HandelsPositie result = verkoopActie.voerUit(positie);
        assertEquals(10, result.getRuimte());
        assertEquals(40, result.getKapitaal());
        assertEquals(30, result.getTotaalWinst());
        assertEquals("{}", String.valueOf(result.getVoorraad()));
        assertEquals(4, result.getTotaalActie());
    }

}
