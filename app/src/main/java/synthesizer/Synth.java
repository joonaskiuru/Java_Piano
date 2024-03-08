package synthesizer;

import synthesizer.keys.*;
import javax.sound.midi.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.geom.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class Synth extends JFrame  {

    private static final Logger LOG = LogManager.getLogger(); // LOGGER
    public static Synthesizer synth;
    public static MidiChannel[] channels;
    public static Instrument[] instruments;

    public static final int VELOCITY = 127;

    public static boolean aPressed = false;
    public static boolean wPressed = false;
    public static boolean sPressed = false;
    public static boolean dPressed = false;

    private static Map<String, JButton> noteButtons = new HashMap<>();
    private static Map<String, Integer> keyValues = new HashMap<>();
    private static Map<String, Boolean> pressedBooleans = new HashMap<>();

    public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

        // Array of white key labels (notes)
    public static final String[] WHITEKEYS = {"C", "D", "E", "F", "G", "A", "B"};

    // Array of black key labels (notes)
    public static final String[] BLACKKEYS = {"C#", "D#", "", "F#", "G#", "A#", ""};

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

        // Set size of the frame
        // this.setSize(300,300);
        this.setLayout(new BorderLayout());

        // Create panel
        JPanel whiteKeysPanel = new JPanel();
        whiteKeysPanel.setLayout(new GridLayout(1,7));

        // Create panel
        JPanel blackKeysPanel = new JPanel();
        blackKeysPanel.setLayout(null);
        //blackKeysPanel.setPreferredSize(new Dimension(100,200));
        blackKeysPanel.setOpaque(false);



        // Keyboard keys that activate white keys
        String[] pressedKeysWhite = {"A","S","D","F","G","H","J"};

        // Keyboard keys that activate black keys
        String[] pressedKeysBlack = {"W","E","T","Y","U"};

        // Create buttons for each note (white keys)
        for(String note : WHITEKEYS) {
            JButton button = new JButton(note); // Create a button for a key
            button.setPreferredSize(new Dimension(100,350)); // Set size for the key
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.setVerticalAlignment(SwingConstants.BOTTOM);
            button.setMargin(new Insets(10, 10, 20, 10));
            whiteKeysPanel.add(button);

            // Place the button into a HashMap with it's corresponding note serving as key
            noteButtons.put(note, button);
        }

        // Create buttons for each note (black keys)
        int offset = 50;
        for(String note : BLACKKEYS) {
            if(!note.isEmpty()) {
                JButton button = new JButton(note);
                button.setBounds(offset,0,100,175);
                button.setBackground(Color.BLACK);
                button.setMargin(new Insets(10, 10, 10, 10));
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                blackKeysPanel.add(button);
                // Place the button into a HashMap with it's corresponding note serving as key
                noteButtons.put(note, button);
            }
            offset += 100;
        }

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(700, 350));
        whiteKeysPanel.setBounds(0, 0, 700, 350);
        blackKeysPanel.setBounds(0, 0, 700, 350);
        layeredPane.add(whiteKeysPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(blackKeysPanel, JLayeredPane.PALETTE_LAYER);


        this.add(layeredPane,BorderLayout.CENTER);
        this.pack();

        int noteValue = 60;
        int counter = 0;
        for(String key : pressedKeysWhite){

            String pressedValue = key + "pressed";
            pressedBooleans.put(pressedValue,false);
            bindActionsToKeys(whiteKeysPanel,key,noteValue,WHITEKEYS[counter],pressedValue);

            // If note is E, increment midi value with 1 , else with 2
            if(noteValue == 64) {
                noteValue += 1;
            } else {
                noteValue += 2;
            }

            counter++;
        }
        noteValue = 61;
        counter = 0;
        for(String key : pressedKeysBlack){

            String pressedValue = key + "pressed";
            pressedBooleans.put(pressedValue,false);
            bindActionsToKeys(blackKeysPanel,key,noteValue,BLACKKEYS[counter],pressedValue);
            
            // If note is D#, increment midi value with 3 , else with 2
            if(noteValue == 63) {
                noteValue += 3;
            } else {
                noteValue += 2;
            }
            counter++;
        }

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

    private void bindActionsToKeys(JPanel panel, String key, int noteValue, String note, String pressedValue) {

        Color defaultColor;
        if(Arrays.asList(BLACKKEYS).contains(key))
            defaultColor = Color.BLACK;
        else
            defaultColor = Color.WHITE;

        bindActions(panel, key,
            // Action for pressed key
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (pressedBooleans.get(pressedValue) == false) {
                        pressedBooleans.put(pressedValue,true);
                        channels[0].noteOn(noteValue, VELOCITY);
                        JButton key = noteButtons.get(note);
                        key.setBackground(Color.GRAY);
                    }
                }
            },
            // Action for released key
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    channels[0].noteOff(noteValue);
                    pressedBooleans.put(pressedValue,false);
                    JButton key = noteButtons.get(note);
                    key.setBackground(defaultColor);
                }
            }
        );
    }

}
