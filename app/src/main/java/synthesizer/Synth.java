package synthesizer;

import javax.sound.midi.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;


public class Synth extends JFrame  {

    private static final Logger LOG = LogManager.getLogger(); // LOGGER
    public static Synthesizer synth;
    public static MidiChannel[] channels;
    public static Instrument[] instruments;

    public static final int VELOCITY = 127;

    public static boolean aPressed = false;
    public static boolean sPressed = false;
    public static boolean dPressed = false;

    public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    public Synth() {

        // Error handling
        try {
            // Get the default synthesizer & open it
            synth = MidiSystem.getSynthesizer();
            synth.open();

            // Get the MIDI channels from synth
            channels = synth.getChannels();

            // Load an instrument
            instruments = synth.getDefaultSoundbank().getInstruments();

            // Use the first instrument
            synth.loadInstrument(instruments[0]);

        } catch (MidiUnavailableException e) {
            LOG.error(e);
        }


        // Create panel
        JPanel panel = new JPanel();
        add(panel);

        // A Inputs
        bindActions(panel, "A",
            // Action for pressed key
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (!aPressed) {
                        System.out.println("A");
                        aPressed = true;
                        channels[0].noteOn(60, VELOCITY);
                        try {
                            Thread.sleep(1);

                        } catch (InterruptedException x) {
                            LOG.error(x);
                        }
                    }
                }
            },
            // Action for released key
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("A released");
                    channels[0].noteOff(60);
                    aPressed = false;
                }
            }
        );

        // S Inputs
        bindActions(panel, "S",
                // Action for pressed key
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        if (!sPressed) {
                            sPressed = true;
                            channels[0].noteOn(62, VELOCITY);
                            try {
                                Thread.sleep(1);

                            } catch (InterruptedException x) {
                                LOG.error(x);
                            }
                        }
                    }
                },
                // Action for released key
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        channels[0].noteOff(62);
                        sPressed = false;
                    }
                }
        );

        // A Inputs
        bindActions(panel, "D",
                // Action for pressed key
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        if (!dPressed) {
                            dPressed = true;
                            channels[0].noteOn(64, VELOCITY);
                            try {
                                Thread.sleep(1);

                            } catch (InterruptedException x) {
                                LOG.error(x);
                            }
                        }
                    }
                },
                // Action for released key
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        channels[0].noteOff(64);
                        dPressed = false;
                    }
                }
        );


        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setVisible(true);

        // exit the application when closing from GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void bindActions(JComponent component,
                             String key, Action pressedAction, Action releasedAction) {

        String pressed = key + "PressedAction";
        String released = key + "ReleasedAction";

        // Map inputs for pressed key
        component.getInputMap(IFW).put(KeyStroke.getKeyStroke(key),pressed);
        component.getActionMap().put(pressed,pressedAction);

        // Map inputs for released key
        component.getInputMap(IFW).put(KeyStroke.getKeyStroke("released " + key),released);
        component.getActionMap().put(released,releasedAction);

    }

}
