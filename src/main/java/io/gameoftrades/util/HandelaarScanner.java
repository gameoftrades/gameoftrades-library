package io.gameoftrades.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import io.gameoftrades.model.Handelaar;

public final class HandelaarScanner {

    public static NavigableMap<String, Handelaar> vindImplementaties() {
        NavigableMap<String, Handelaar> results = new TreeMap<>();
        String base = "io.gameoftrades.student";
        try {
            for (int t = 0; t < 100; t++) {
                String nr = (t < 10 ? "0" : "") + String.valueOf(t);
                vindEnInstantieer(results, base, nr);
            }
            if (results.isEmpty()) {
                System.err.println("Waarschuwing: Je hebt de 'studentNN' package nog niet aangepast met je groepsnummer!");
                vindEnInstantieer(results, base, "NN");
            }
        } catch (InstantiationException e) {
            throw new RuntimeException("HandelaarImpl kon niet geinstantieerd worden met een no-args constructor.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("de no-args constructor van HandelaarImpl is niet public.", e);
        }
        return results;
    }

    private static void vindEnInstantieer(Map<String, Handelaar> results, String base, String nr) throws InstantiationException, IllegalAccessException {
        Class<?> found;
        try {
            found = Class.forName(base + nr + ".HandelaarImpl");
            results.put(nr, (Handelaar) found.newInstance());
        } catch (ClassNotFoundException ex) {
            // Ignore
        }
    }

}
