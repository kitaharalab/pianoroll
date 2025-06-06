package jp.kthrlab.pianoroll.processing;

import jp.kthrlab.pianoroll.Keyboard;
import jp.kthrlab.pianoroll.PianoRoll;
import jp.kthrlab.pianoroll.Timeline;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class PAppletPianoRoll extends PApplet implements PianoRoll {
    // Variables
    protected Keyboard keyboard;
    protected Timeline timeline;
    boolean playheadFixed = false; // 再生位置線の固定フラグ

    @Override
    public void settings() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        AffineTransform at = gd.getDefaultConfiguration().getDefaultTransform();
        Rectangle usableBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        Frame frame = new Frame();
        frame.setUndecorated(false);
        frame.setSize(0, 0);
        frame.setVisible(true);

        size((int) usableBounds.width, (int) (usableBounds.height - frame.getInsets().top));

        frame.dispose();
    }

    @Override
    public void setup() {
        background(255);
        windowMove(0, 0);
    }


    // Draw method
    @Override
    public void draw() {
        background(255);
        // Drawing logic here
        drawKeyboard();
        drawTimeline();
        drawNote();
        drawPlayhead();
    }

    @Override
    public void drawTimeline() {
        // Do nothing
    }

    @Override
    public void drawKeyboard() {
    }

    @Override
    public void drawNote() {
    }

    @Override
    public void drawPlayhead() {
        // Playhead drawing logic here
    }


}
