package jp.kthrlab.pianoroll.processing_cmx;

import jp.crestmuse.cmx.elements.MutableNote;
import jp.crestmuse.cmx.filewrappers.SCC;
import jp.crestmuse.cmx.filewrappers.SCCDataSet;
import jp.crestmuse.cmx.misc.PianoRoll;
import jp.crestmuse.cmx.processing.CMXController;
import jp.kthrlab.pianoroll.Keyboard;
import jp.kthrlab.pianoroll.cmx.PianoRollDataModelMultiChannel;
import jp.kthrlab.pianoroll.processing.HorizontalPAppletPianoRoll;
import processing.core.PApplet;

import javax.xml.transform.TransformerException;
import java.awt.*;
import java.util.Arrays;

public class HorizontalPAppletCmxPianoRoll extends HorizontalPAppletPianoRoll {

    protected PianoRoll.DataModel dataModel = null;
    private CMXController cmx = CMXController.getInstance();

    @Override
    public void drawNote() {
        if (isNoteVisible && (dataModel != null)) {
            super.drawNote();
            PianoRollDataModelMultiChannel dataModelMultiChannel = (PianoRollDataModelMultiChannel) dataModel;

            long tickPosition = cmx.getTickPosition();
            Object tickLock = tickPosition;
            synchronized (tickLock) {
                dataModelMultiChannel.getChannels().forEach(channel -> {
                    strokeWeight(1.0f);
                    stroke(channel.color.getRed(), channel.color.getGreen(), channel.color.getBlue(), channel.color.getAlpha());
                    SCCDataSet.Part part = dataModelMultiChannel.getPart(channel.channel_number);

                    if (part != null && part.getNoteOnlyList() != null) {
                        for (MutableNote note : part.getNoteOnlyList()) {

                            Long relativeOnset = note.onset() - tickPosition;
                            float h = (float) ((note.offset() - note.onset()) * dataModelMultiChannel.getPixelPerTick());
                            float y = timeline.getSpan() - (float) (relativeOnset * dataModelMultiChannel.getPixelPerTick()) - h;
                            // Break if it is outside the drawing range
                            if (y < 0) {
                                break;
                            }
                            float x = horizontalKeyboard.semitoneXMap.get(note.notenum());
                            float w = timeline.getSemitoneWidth();

//                            println(String.format("tickPosition=%s, note.onset()=%s, relativeOnset=%s, x=%s, y=%s, w=%s, h=%s",tickPosition, note.onset(), relativeOnset, x, y, w, h));

                            int measure = (int) (note.onset() / dataModelMultiChannel.getScc().getDivision() / dataModelMultiChannel.getBeatNum());
                            double beat = note.onset() / (double) dataModelMultiChannel.getScc().getDivision() - (measure * dataModelMultiChannel.getBeatNum());
                            double duration = (note.offset() - note.onset()) / (double) dataModelMultiChannel.getScc().getDivision();

//                            println(String.format("measure=%s", measur));
                            //                            println("measure=$measure, beat=$beat, duration = ${note.offset()}-${note.onset()} / ${scc.division} = $duration")
//                            drawNote(measure - dataModelMultiChannel.getRelativeFirstMeasure(), beat, duration, note.notenum(), false, dataModelMultiChannel);

//                            Long relativeOffset = note.offset()-tickPosition;
                            fill(channel.color.getRGB());
                            stroke(Color.LIGHT_GRAY.getRGB());
                            this.rect(x, y, w, h);
                        }
                    }
                    blendMode(MULTIPLY);
                });

            }


        }
        blendMode(1);
    }

    private void drawMeasureLine(){

    }

    public static void main(String[] args) {
        PApplet.main(new String[] { HorizontalPAppletCmxPianoRoll.class.getName() });
    }

}
