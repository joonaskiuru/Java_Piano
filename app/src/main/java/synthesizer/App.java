package synthesizer;

import javax.swing.*;
import javax.sound.midi.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App {

    private static final Logger LOG = LogManager.getLogger(); // LOGGER

    private App() {
        // Prevent creating instances of App class
    }

    public static void main(String[] args) {


        Synth synth = new Synth();
    }


}
