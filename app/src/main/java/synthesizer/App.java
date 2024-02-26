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

        // Creating instance of JFrame
        JFrame frame = new JFrame();

        Synth synth = new Synth();
        synth.play(60,127);



        // Creating instance of JButton
        JButton button = new JButton("Test Button");

        // x axis, y axis, width, height
        button.setBounds(150, 200, 220, 50);

        // adding button in JFrame
        frame.add(button);

        // 400 width and 500 height
        frame.setSize(400, 400);

        // using no layout managers
        frame.setLayout(null);

        // making the frame visible
        frame.setVisible(true);

        // exit the application when closing from GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
