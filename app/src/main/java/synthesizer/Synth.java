package synthesizer;

import javax.sound.midi.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Synth extends JFrame implements KeyListener  {

    private static final Logger LOG = LogManager.getLogger(); // LOGGER
    public static Synthesizer synth;
    public static MidiChannel[] channels;
    public static Instrument[] instruments;

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

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setVisible(true);

        // exit the application when closing from GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void play(int note, int velocity) {
        channels[0].noteOn(note, velocity);

        // Turn the note off
        channels[0].noteOff(note);
    }

    public static void stop(int note) {

        // Turn the note off
        channels[0].noteOff(note);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'a':
                System.out.println("A");
                play(60,127);
                break;
            case 's':
                play(62,127);
                break;
            case 'd':
                play(64,127);
                break;


        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'a':
                stop(60);
                break;
            case 's':
                stop(62);
                break;
            case 'd':
                stop(64);
                break;
        }
    }
}
