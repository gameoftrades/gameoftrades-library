package io.gameoftrades.model.algoritme;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;

public interface SnelstePadAlgoritme {

    Pad bereken(Kaart kaart, Coordinaat start, Coordinaat eind);

}
