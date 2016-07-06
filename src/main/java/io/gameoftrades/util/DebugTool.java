package io.gameoftrades.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

/**
 * Een verzameling handige visualisaties voor debug doeleinden.
 */
public final class DebugTool {

    /**
     * rendert een kaart met een coordinaat.
     * @param kaart de kaart.
     * @param c het coordinaat (. op de kaart).
     */
    public static void render(Kaart kaart, Coordinaat c) {
        System.out.println(render(kaart, 1, (pos) -> c.equals(pos) ? "." : null));
    }

    /**
     * rendert een kaart met een lijst van coordinaten.
     * @param kaart de kaart.
     * @param c de lijst van coordinaten.
     */
    public static void render(Kaart kaart, List<Coordinaat> c) {
        System.out.println(render(kaart, 1, (pos) -> c.contains(pos) ? "." : null));
    }

    /**
     * rendert een kaart met een pad dat begin op het start coordinaat.
     * @param kaart de kaart.
     * @param start het start coordinaat.
     * @param pad het pad.
     */
    public static void render(Kaart kaart, Coordinaat start, Pad pad) {
        List<Coordinaat> c = new ArrayList<>();
        c.add(start);
        for (Richting r : pad.getBewegingen()) {
            start = start.naar(r);
            c.add(start);
        }
        render(kaart, c);
    }

    /**
     * tekent een nummerieke laag over de kaart heen, daar
     * waar de waarden niet null zijn.
     * @param kaart de kaart.
     * @param overlay, een array[y][x] met dezelfde grootte als de kaart.
     */
    public static void render(Kaart kaart, Integer[][] overlay) {
        System.out.println(render(kaart, 3, (pos) -> {
            Integer value = overlay[pos.getY()][pos.getX()];
            if (value == null) {
                return null;
            }
            return (value < 10 ? "0" : "") + value.toString();
        }));
    }

    /**
     * tekent een nummerieke laag over de kaart heen, daar
     * waar de waarden niet 0 zijn.
     * @param kaart de kaart.
     * @param overlay, een array[y][x] met dezelfde grootte als de kaart.
     */
    public static void render(Kaart kaart, int[][] overlay) {
        System.out.println(render(kaart, 3, (pos) -> {
            int value = overlay[pos.getY()][pos.getX()];
            if (value == 0) {
                return null;
            }
            return (value < 10 ? "0" : "") + String.valueOf(value);
        }));
    }

    /**
     * algemene render functie die een callback doet voor ieder coordinaat. 
     * @param kaart de kaart.
     * @param vakBreedte de breedte van 1 vak op de kaart.
     * @param func de functie.
     * @return de gerenderde kaart as string.
     */
    public static String render(Kaart kaart, int vakBreedte, Function<Coordinaat, String> func) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < kaart.getHoogte(); y++) {
            for (int x = 0; x < kaart.getBreedte(); x++) {
                Coordinaat c = Coordinaat.op(x, y);
                char str = kaart.getTerreinOp(c).getTerreinType().getLetter();
                String alt = func.apply(c);
                if (alt != null) {
                    sb.append(alt);
                    for (int t = 0; t < vakBreedte - alt.length(); t++) {
                        sb.append(" ");
                    }
                } else {
                    sb.append(str);
                    for (int t = 0; t < vakBreedte - 1; t++) {
                        sb.append(" ");
                    }
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

}
