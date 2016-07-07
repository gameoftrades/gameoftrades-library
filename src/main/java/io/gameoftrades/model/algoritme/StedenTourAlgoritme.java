package io.gameoftrades.model.algoritme;

import java.util.List;

import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;

public interface StedenTourAlgoritme {

    List<Stad> bereken(Kaart kaart, List<Stad> steden);

}
