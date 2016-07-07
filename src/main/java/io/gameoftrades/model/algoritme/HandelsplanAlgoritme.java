package io.gameoftrades.model.algoritme;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.HandelsPositie;

public interface HandelsplanAlgoritme {

    Handelsplan bereken(Wereld wereld, HandelsPositie positie);

}
