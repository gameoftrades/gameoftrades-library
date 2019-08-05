package io.gameoftrades.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.util.Assert;
import io.gameoftrades.util.HandelaarScanner;

/**
 * Startpunt van de Demo/Debug gebruikersinterface.
 */
public class MainGui {

    private WereldLader lader = null;
    private List<SnelstePadAlgoritme> choices = new ArrayList<>();
    private List<StedenTourAlgoritme> tspChoices = new ArrayList<>();
    private List<HandelsplanAlgoritme> hplans = new ArrayList<>();
    
    public static MainGui builder() {
        return new MainGui();
    }
    
    private MainGui() {
    }
    
    public MainGui add(Handelaar h) {
        lader = h.nieuweWereldLader();
        choices.add(h.nieuwSnelstePadAlgoritme());
        tspChoices.add(h.nieuwStedenTourAlgoritme());
        hplans.add(h.nieuwHandelsplanAlgoritme());
        return this;
    }
    
    public MainGui setWereldLader(WereldLader lader) {
        this.lader = lader;
        return this;
    }
    
    public MainGui add(SnelstePadAlgoritme sp) {
        this.choices.add(sp);
        return this;
    }

    public MainGui add(StedenTourAlgoritme tsp) {
        this.tspChoices.add(tsp);
        return this;
    }
    
    public MainGui add(HandelsplanAlgoritme hp) {
        this.hplans.add(hp);
        return this;
    }

    /**
     * Toont de GUI met de gegeven handelaar en kaart.
     * @param handelaar de handelaar.
     * @param kaart de kaart.
     */
    public void toon(TileSet tileSet, String kaart) {
        Assert.notNull(lader);
        KaartDisplay display = new KaartDisplay(tileSet);
        Wereld wereld = lader.laad(kaart);
        display.setKaart(wereld.getKaart());

        SnelstePadDebugPanel spd = new SnelstePadDebugPanel(display, wereld, choices);
        TspDebugPanel tsp = new TspDebugPanel(display, wereld, tspChoices);
        PlanDebugPanel pdb = new PlanDebugPanel(display, wereld, hplans);

        HandelsPositiePanel hpos = new HandelsPositiePanel();
        display.addHandelsPositieListener(hpos);

        JFrame f = new JFrame();
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel rightTop = new JPanel(new BorderLayout(8, 8));
        Box right = new Box(BoxLayout.Y_AXIS);
        rightTop.add(right, BorderLayout.NORTH);
        panel.add(MainGui.wrapInScrolPane(display,tileSet.getTileSize()), BorderLayout.WEST);
        panel.add(rightTop, BorderLayout.CENTER);
        right.add(Box.createVerticalStrut(8));
        right.add(spd);
        right.add(Box.createVerticalStrut(8));
        right.add(tsp);
        right.add(Box.createVerticalStrut(8));
        right.add(pdb);
        right.add(hpos);

        f.setSize(1024, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(panel);
        f.setVisible(true);

    }
    
    /**
     * wraps a given component in a scroll pane.
     * @param display the component.
     * @param stepSize the scroll step size.
     * @return the scrollpane containing the component.
     */
    public static JScrollPane wrapInScrolPane(JComponent display, int stepSize) {
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setOpaque(true);
        scroll.setBackground(new Color(240, 240, 240));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setBlockIncrement(stepSize);
        scroll.getVerticalScrollBar().setUnitIncrement(stepSize);
        return scroll;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage: MainGui [kaart-resource] [groep-nummer]");
            System.out.println("  voorbeeld: MainGui /maps/kaart.txt 02");
            System.exit(-1);
        } else {
            toon(args[1], args[0]);
        }
    }

    /**
     * scant het classpath voor Handelaren en toont de GUI met het gegeven handelaars id en kaart resource.
     * @param kaart de kaart resource.
     * @param id het handelaars (team) id.
     */
    public static void toon(String kaart, String id) {
        NavigableMap<String, Handelaar> found = HandelaarScanner.vindImplementaties();
        Handelaar handelaar = found.get(id);
        if (handelaar != null) {
            builder().add(handelaar).toon(TileSet.T16, kaart);
        } else {
            throw new IllegalArgumentException("Handelaar met ID " + id + " niet gevonden");
        }
    }

}
