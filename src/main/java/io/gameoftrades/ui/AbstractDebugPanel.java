package io.gameoftrades.ui;

import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.HandelsPositie;

public abstract class AbstractDebugPanel extends JPanel {

    public class GuiDebugger implements io.gameoftrades.debug.Debugger {

        @Override
        public void debugCoordinaten(Kaart kaart, List<Coordinaat> c) {
            // TODO Auto-generated method stub
        }
        @Override
        public void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> open, boolean highlight) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setOpenClosed(open, open);
            waitForStep();
        }

        @Override
        public void debugRaster(Kaart kaart, Integer[][] raster) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setOverlay(raster);
            waitForStep();
        }

        @Override
        public void debugPad(Kaart kaart, Coordinaat start, Pad pad) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setPad(start, pad);
            waitForStep();
        }

        @Override
        public void debugSteden(Kaart kaart, List<Stad> gedaan) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setSteden(gedaan);
            waitForStep();
        }

        @Override
        public PlanControl speelPlanAf(Handelsplan plan, HandelsPositie initieel) {
            return kaartDisplay.setPlan(plan, initieel);
        }
    }

    private JButton startButton = new JButton(">> Start");
    private JButton stepButton = new JButton("> Step");
    private JButton stopButton = new JButton("[ ] Stop");

    private KaartDisplay kaartDisplay;
    private Object stepLock = new Object();
    private Thread runner;
    private GuiDebugger debugger;

    public AbstractDebugPanel(KaartDisplay kaartDisplay) {
        this.kaartDisplay = kaartDisplay;
        this.debugger = new GuiDebugger();

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        stepButton.setEnabled(false);

        startButton.addActionListener((e) -> doStart());
        stepButton.addActionListener((e) -> doStep());
        stopButton.addActionListener((e) -> doStop());
    }

    protected Box getButtonPanel() {
        Box btns = new Box(BoxLayout.X_AXIS);
        btns.add(startButton);
        btns.add(Box.createHorizontalStrut(8));
        btns.add(stepButton);
        btns.add(Box.createHorizontalStrut(8));
        btns.add(stopButton);
        return btns;
    }

    protected void doStep() {
        synchronized (stepLock) {
            stepLock.notifyAll();
        }
    }

    protected void linkDebugger(Object target) {
        if (target instanceof Debuggable) {
            ((Debuggable) target).setDebugger(getDebugger());
        }
    }

    protected Debugger getDebugger() {
        return debugger;
    }

    private void doStart() {
        Runnable r = createRunnable();
        if (r != null) {
            kaartDisplay.reset();
            runner = new Thread(r);
            startButton.setEnabled(false);
            runner.start();
            stopButton.setEnabled(true);
        }
    }

    protected abstract Runnable createRunnable();

    private void doStop() {
        stopButton.setEnabled(false);
        if (runner != null) {
            if (runner.isAlive()) {
                runner.stop();
            }
            runner = null;
        }
        startButton.setEnabled(true);
    }

    protected void waitForStep() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                stepButton.setEnabled(true);
            });
            try {
                synchronized (stepLock) {
                    stepLock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                SwingUtilities.invokeAndWait(() -> {
                    stepButton.setEnabled(false);
                });
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
