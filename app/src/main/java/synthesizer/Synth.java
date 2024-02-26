package synthesizer;

import javax.sound.midi.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Synth {

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
    }

    public static void play(int note, int velocity) {
        channels[0].noteOn(note, velocity);

        try {
            // Hold the note for 2 seconds
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOG.error(e);
        }

        // Turn the note off
        channels[0].noteOff(note);
    }
}
