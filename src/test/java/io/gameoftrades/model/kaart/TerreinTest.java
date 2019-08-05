package io.gameoftrades.model.kaart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TerreinTest {

    @Test
    public void zouTerreinMoetenMaken() {
        Kaart kaart = Kaart.metOmvang(1, 1);
        Terrein t = new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(TerreinType.BERG, t.getTerreinType());
        assertEquals(Coordinaat.op(0, 0), t.getCoordinaat());
        assertEquals(kaart.getTerreinOp(Coordinaat.op(0, 0)), t);
    }

    @Test
    public void zouRichtingenMoetenBepalen() {
        Kaart kaart = Kaart.metOmvang(3, 3);
        Terrein t = new Terrein(kaart, Coordinaat.op(1, 1), TerreinType.BERG);
        for (Richting r:Richting.values()) {
            new Terrein(kaart, Coordinaat.op(1, 1).naar(r), TerreinType.BERG);
        }
        assertEquals(4, t.getMogelijkeRichtingen().length);
    }

    @Test
    public void zouRichtingenMoetenBepalenMetToegankelijkheid() {
        Kaart kaart = Kaart.metOmvang(3, 3);
        Terrein t = new Terrein(kaart, Coordinaat.op(1, 1), TerreinType.BERG);
        for (Richting r : Richting.values()) {
            new Terrein(kaart, Coordinaat.op(1, 1).naar(r), TerreinType.ZEE);
        }
        assertEquals(0, t.getMogelijkeRichtingen().length);
    }

    @Test
    public void zouRichtingenMoetenBepalenBinnenKaart() {
        Kaart kaart = Kaart.metOmvang(1, 1);
        Terrein t = new Terrein(kaart, Coordinaat.op(0, 0), TerreinType.BERG);
        assertEquals(0, t.getMogelijkeRichtingen().length);
    }

}
