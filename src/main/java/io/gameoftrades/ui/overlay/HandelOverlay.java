package io.gameoftrades.ui.overlay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;

public class HandelOverlay implements Overlay {

    private Map<Handel, List<Handel>> handel;
    
    public HandelOverlay(List<Handel> handel) {
        this.handel = pair(handel);
    }
    
    @Override
    public void draw(Graphics2D g, Font font, int tilesize, int tilehalfwidth, int fontSize) {
        for (Map.Entry<Handel, List<Handel>> e : handel.entrySet()) {
            Coordinaat start = e.getKey().getStad().getCoordinaat();
            for (Handel dst : e.getValue()) {
                Coordinaat end = dst.getStad().getCoordinaat();
                int x = start.getX() * tilesize + tilehalfwidth;
                int y = start.getY() * tilesize + tilehalfwidth;
                g.setColor(new Color(e.getKey().getStad().hashCode() & 0x00FFFFFF | 0x000000FF));
                g.fillOval(x - 6, y - tilehalfwidth / 2, tilehalfwidth, tilehalfwidth);
                int x2 = end.getX() * tilesize + tilehalfwidth;
                int y2 = end.getY() * tilesize + tilehalfwidth;
                g.setColor(new Color(dst.getHandelswaar().hashCode() & 0x00FFFFFF | 0x00FF0000));
                g.drawLine(x - tilehalfwidth / 2, y, x2 + 9, y2);
                g.setColor(new Color(dst.getStad().hashCode() & 0x00FFFFFF | 0x000000FF));
                g.drawOval(x2 + 6, y2 - tilehalfwidth / 2, tilehalfwidth, tilehalfwidth);
            }
        }
    }
    
    private Map<Handel, List<Handel>> pair(List<Handel> handel) {
        if (handel == null) {
            return null;
        }
        Map<Handel, List<Handel>> pairs = new HashMap<>();
        for (Handel h : handel) {
            if (HandelType.BIEDT.equals(h.getHandelType())) {
                if (!pairs.containsKey(h)) {
                    pairs.put(h, new ArrayList<>());
                }
            }
        }
        for (Handel h : handel) {
            if (HandelType.VRAAGT.equals(h.getHandelType())) {
                for (Handel a : pairs.keySet()) {
                    if (h.getHandelswaar().equals(a.getHandelswaar())) {
                        pairs.get(a).add(h);
                    }
                }
            }
        }
        return pairs;
    }


}
