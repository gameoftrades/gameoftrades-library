package io.gameoftrades.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import io.gameoftrades.debug.Debugger.PlanControl;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.TerreinType;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.HandelsPositie;
import io.gameoftrades.ui.overlay.HandelsActieOverlay;
import io.gameoftrades.ui.overlay.Overlay;
import io.gameoftrades.ui.overlay.PositieOverlay;

/**
 * Tekent de kaart en debug resultaten.  
 */
public class KaartDisplay extends JPanel implements PlanControl {

    private int tilesize;
    private int tilespacing;
    private int tilehalfwidth;
    private int fontSize;
    private Font font;

    private BufferedImage tiles;
    private BufferedImage[][] tileCache;

    private Kaart kaart;
    private int planStep = 0;
    private Handelsplan plan;

    private HandelsPositie positie;

    private List<HandelsPositieListener> listeners = new ArrayList<>();
    
    private List<Overlay> overlays = new ArrayList<>();

    private PositieOverlay positieOverlay = new PositieOverlay(positie);

    public KaartDisplay() {
        this(TileSet.T16);

    }

    public KaartDisplay(TileSet tileSet) {
        super(null);
        try {
            this.tilesize = tileSet.getTileSize();
            this.tilespacing = tileSet.getTileSpacing();
            this.tilehalfwidth = (tileSet.getTileSize() / 2) - 1;
            this.fontSize = tileSet.getFontSize();
            this.font = this.getFont().deriveFont((float) fontSize);
            tiles = ImageIO.read(KaartDisplay.class.getResourceAsStream(tileSet.getResource()));
            tileCache = new BufferedImage[1 + (tiles.getHeight() / tilesize)][1 + (tiles.getWidth() / (tilesize + 1))];
        } catch (IOException ex) {
            throw new RuntimeException("Kon tile resource niet laden", ex);
        }
    }

    public synchronized void setKaart(Kaart kaart) {
        if (this.kaart != kaart) {
            this.kaart = kaart;
            this.setPreferredSize(new Dimension((kaart.getBreedte()) * tilesize, kaart.getHoogte() * tilesize));
            reset();
        }
    }

    public void reset() {
        plan = null;
        planStep = 0;
        positie = null;
        overlays.clear();
        repaint();
    }
    
    public void addOverlay(Overlay ov) {
        overlays.add(ov);
    }

    public synchronized PlanControl setPlan(Handelsplan plan, HandelsPositie positie) {
        reset();
        addOverlay(new HandelsActieOverlay(positie, plan.getActies()));
        addOverlay(positieOverlay);
        this.plan = plan;
        this.positie = positie;
        this.planStep = -1;
        this.repaint();
        return this;
    }

    public synchronized void nextStep() {
        if (hasNextStep()) {
            this.planStep++;
            this.positie = plan.getActies().get(planStep).voerUit(positie);
            for (HandelsPositieListener l : listeners) {
                l.setHandelsPositie(this.positie);
            }
            positieOverlay.setPositie(positie);
            this.repaint();
        }
    }

    public synchronized boolean hasNextStep() {
        return planStep < plan.getActies().size() - 1;
    }

    public void addHandelsPositieListener(HandelsPositieListener l) {
        listeners.add(l);
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        if (kaart != null) {
            tekenKaart(g);
        }
        for (Overlay ov:overlays) {
            ov.draw(g, font, tilesize, tilehalfwidth, fontSize);
        }
    }

    private void tekenKaart(Graphics g) {
        TerreinType[][] tt = new TerreinType[3][3];
        int h = kaart.getHoogte();
        int w = kaart.getBreedte();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                for (int yy = 0; yy < tt.length; yy++) {
                    for (int xx = 0; xx < tt.length; xx++) {
                        int xt = x - 1 + xx;
                        int yt = y - 1 + yy;
                        if ((xt < 0) || (yt < 0) || (xt >= w) || (yt >= h)) {
                            tt[yy][xx] = TerreinType.ZEE;
                        } else {
                            tt[yy][xx] = kaart.getTerreinOp(Coordinaat.op(xt, yt)).getTerreinType();
                        }
                    }
                }
                BufferedImage img = toTile(tt, ((x + y) % 2) == 0);
                g.drawImage(img, x * tilesize, y * tilesize, null);
            }
        }
    }

    private BufferedImage toTile(TerreinType[][] t, boolean odd) {
        switch (t[1][1]) {
        case BERG:
            return getTile(odd ? 0 : 1, 3);
        case BOS:
            return getTile(odd ? 0 : 1, 2);
        case GRASLAND:
            return getTile(odd ? 0 : 1, 1);
        case STAD:
            return getTile(6, odd ? 0 : 1);
        case ZEE:
            int edge = t[1][2] != TerreinType.ZEE ? 1 : 0; // E
            edge |= t[1][0] != TerreinType.ZEE ? 2 : 0; // W
            edge |= t[0][1] != TerreinType.ZEE ? 4 : 0; // N
            edge |= t[2][1] != TerreinType.ZEE ? 8 : 0; // S
            switch (edge) {
            case 1:
                return getTile(2, 0);
            case 2:
                return getTile(3, 0);
            case 3:
                return getTile(3, 3);
            case 4:
                return getTile(4, 0);
            case 5:
                return getTile(2, 1);
            case 6:
                return getTile(4, 1);
            case 7:
                return getTile(2, 2);
            case 8:
                return getTile(5, 0);
            case 9:
                return getTile(5, 1);
            case 10:
                return getTile(3, 1);
            case 11:
                return getTile(3, 2);
            case 12:
                return getTile(2, 3);
            case 13:
                return getTile(5, 2);
            case 14:
                return getTile(4, 2);
            case 15:
                return getTile(4, 3);
            default:
                return getTile(odd ? 0 : 1, 0);
            }
        }
        throw new IllegalArgumentException(String.valueOf(t));
    }

    private BufferedImage getTile(int x, int y) {
        if (tileCache[y][x] == null) {
            BufferedImage tmp = new BufferedImage(tilesize, tilesize, tiles.getType());
            Graphics g = tmp.getGraphics();
            try {
                g.drawImage(tiles, -x * (tilesize + tilespacing), -y * tilesize, null);
            } finally {
                g.dispose();
            }
            tileCache[y][x] = tmp;
        }
        return tileCache[y][x];
    }

}
