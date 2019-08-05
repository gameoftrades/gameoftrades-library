package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StadTest {

    @Test
    public void zouStadMoetenMaken() {
        assertEquals("Stad", Stad.op(Coordinaat.op(1, 1), "Stad").getNaam());
        assertEquals(Coordinaat.op(1, 1), Stad.op(Coordinaat.op(1, 1), "Stad").getCoordinaat());
    }
}
