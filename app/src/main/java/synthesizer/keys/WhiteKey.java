package synthesizer.keys;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WhiteKey extends JButton implements KeyListener {

    public WhiteKey(String b) {
        super(b);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Pressed button");
        if(e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("A pressed!!");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
