package io.gameoftrades.model.markt.actie;

import java.util.Arrays;

import org.junit.Before;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

public abstract class AbstractActieTest {

    class MockPad implements Pad {
        @Override
        public Richting[] getBewegingen() {
            return new Richting[] { Richting.ZUID, Richting.NOORD };
        }

        @Override
        public int getTotaleTijd() {
            return 2;
        }

        @Override
        public Pad omgekeerd() {
            return new MockPad();
        }

        @Override
        public Coordinaat volg(Coordinaat start) {
            return start;
        }
    }

    Wereld wereld;
    Kaart kaart;
    Stad stad1;
    Stad stad2;
    Handel h1, h2, h3, h4;
    Markt markt;

    @Before
    public void init() {
        kaart = Kaart.metOmvang(2, 2);
        Terrein.op(kaart, Coordinaat.op(0, 0), TerreinType.STAD);
        Terrein.op(kaart, Coordinaat.op(0, 1), TerreinType.BERG);
        Terrein.op(kaart, Coordinaat.op(1, 0), TerreinType.GRASLAND);
        Terrein.op(kaart, Coordinaat.op(1, 1), TerreinType.STAD);
        stad1 = Stad.op(Coordinaat.op(0, 0), "Stad1");
        stad2 = Stad.op(Coordinaat.op(1, 1), "Stad2");
        h1 = new Handel(stad1, HandelType.BIEDT, new Handelswaar("schapen"), 2);
        h2 = new Handel(stad2, HandelType.BIEDT, new Handelswaar("stenen"), 1);
        h3 = new Handel(stad2, HandelType.VRAAGT, new Handelswaar("schapen"), 8);
        h4 = new Handel(stad1, HandelType.VRAAGT, new Handelswaar("stenen"), 4);
        markt = new Markt(Arrays.asList(new Handel[] { h1, h2, h3, h4 }));
        wereld = Wereld.van(kaart, Arrays.asList(new Stad[] { stad1, stad2 }), markt);
    }

}
