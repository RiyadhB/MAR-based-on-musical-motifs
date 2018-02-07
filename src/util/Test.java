/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.util.Arrays;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import static javax.sound.midi.ShortMessage.SYSTEM_RESET;
import javax.sound.midi.Track;

public class Test {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static void main(String[] args) throws Exception {
        Sequence sequence = MidiSystem.getSequence(new File("test.mid"));
        float ppq = sequence.getResolution();// pulse par quarter note
        System.out.println("ppq = "+ppq);
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            long oldTime = 0;
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                long time = event.getTick();
                System.out.print(" duration = "+String.valueOf(time-oldTime)+ " ");
                oldTime = time;
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print(" Channel: " + sm.getChannel() + " ");
                    switch (sm.getCommand()) {
                        case NOTE_ON:
                            {
                                int key = sm.getData1();
                                int octave = (key / 12)-1;
                                int note = key % 12;
                                String noteName = NOTE_NAMES[note];
                                int velocity = sm.getData2();
                                System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                                break;
                            }
                        case NOTE_OFF:
                            {
                                int key = sm.getData1();
                                int octave = (key / 12)-1;
                                int note = key % 12;
                                String noteName = NOTE_NAMES[note];
                                int velocity = sm.getData2();
                                System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                                break;
                            }
                        case SYSTEM_RESET: 
                            int key = sm.getData1();
                            System.out.println("system reset, key=" + key);
                            break;
                        default:
                            System.out.println("Command:" + sm.getCommand());
                            break;
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }

    }
}