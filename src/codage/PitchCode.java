/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codage;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class PitchCode implements Serializable{

    Note note;//coded on 3 bits
    Octave octave;// coded on 3 bits
    Alter alter; // coded on 2bits
    Rhythm duration; // the duration of the note
    //ex: the form of C4# will be 100 100 11

    public static int informationType = 0;// -1: rhythmic, 0: both, 1: melodic

    boolean[] completeValue;

    /**
     * The note char can take values C, D, E, F, G, A, B, R (for rest) The
     * octave values vary from 1 to 7 The alter type can be 0 for flat, 1 for
     * natural or 2 for sharp
     *
     * @param noteP the note char
     * @param octaveP the octave value
     * @param alterP alter type
     */
    public PitchCode(char noteP, int octaveP, int alterP, String duration) {
        this.note = new Note(noteP, 3);
        this.octave = new Octave((new Information(3)).getBinaryValue(octaveP));
        this.alter = new Alter((new Information(2)).getBinaryValue(alterP));
        this.duration = new Rhythm(3);
        this.duration.setValue(duration);
        completeValue = Information.merge(octave.flags, note.flags, alter.flags, this.duration.flags);
    }

    public PitchCode(int value, String duration) {
        this.duration = new Rhythm(3);
        this.duration.setValue(duration);
        completeValue = new Information(11).getBinaryValue(value);
        boolean[] tabOctave = new boolean[3];
        int i;
        for (i = 0; i < tabOctave.length; i++) {
            tabOctave[i] = completeValue[i];
        }

        boolean[] tabNote = new boolean[3];
        for (; i < tabOctave.length + tabNote.length; i++) {
            tabNote[i - tabOctave.length] = completeValue[i];
        }

        boolean[] tabAlter = new boolean[2];
        for (; i < tabOctave.length + tabNote.length + tabAlter.length; i++) {
            tabAlter[i - tabOctave.length - tabNote.length] = completeValue[i];
        }

        boolean[] tabRhytm = new boolean[3];
        for (; i < completeValue.length; i++) {
            tabRhytm[i - tabOctave.length - tabNote.length - tabAlter.length] = completeValue[i];
        }

        octave = new Octave(tabOctave);
        note = new Note(tabNote);
        alter = new Alter(tabAlter);
        this.duration = new Rhythm(tabRhytm);
    }

    public void setInformationType(int informationType) {
        this.informationType = informationType;
    }

    public int getRepresentationPrime() {
        //System.out.println("note value "+note.value);
        return (octave.value + 1) * 12 + getNotePosition(note.value, alter.value);
    }

    public boolean[] getBinaryRepresentation() {
        return completeValue;
    }

    public int getNumericValue() {
        return Information.getDecimalValue(completeValue);
    }

    public String getBooleanRep() {
        String result = "";
        for (int i = 0; i < completeValue.length; i++) {
            if (i == 3 || i == 6) {
                result += " ";
            }
            result += completeValue[i] ? '1' : '0';
        }
        return result;
    }

    @Override
    public String toString() {
        //return ""+getRepresentationPrime();//octave.toString()+note.toString()+alter.toString()+"-"+duration.value;
        switch (informationType) {
            case -1:
                return duration.value;
            case 0:
                return getRepresentationPrime() + ":" + duration.value;
            case 1:
                return "" + getRepresentationPrime();
        }
        return duration.value;
    }

    public Rhythm getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PitchCode) {
            PitchCode pitch = (PitchCode) o;
            return pitch.toString().equals(toString());
            //if(/*pitch.alter.value == alter.value &&  pitch.octave.value == octave.value && pitch.note.value == note.value &&*/ pitch.duration.value.equals(duration.value)) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public int getCommonValuesWith(PitchCode p) {
        int compteur = 0;
        int min = Math.min(completeValue.length, p.completeValue.length);
        for (int i = 0; i < min; i++) {
            if (completeValue[i] == p.completeValue[i]) {
                compteur++;
            }
        }
        return compteur;
    }

    public int minus(PitchCode p) {
        boolean[] tmp = new boolean[completeValue.length];
        for (int i = 0; i < completeValue.length; i++) {
            tmp[i] = completeValue[i] && p.completeValue[i];
        }

        return Information.getDecimalValue(tmp);
    }

    public int compareTo(PitchCode p) {
        if (p == null) {
            return 1;
        }
        return getNumericValue() - p.getNumericValue();
    }

    /*private int getDurationValue(){
        switch (duration){
            case "whole": return 32;
            case "half": return 16;
            case "quarter": return 8;
            case "eighth": return 4;
            case "16th": return 2;
            case "32nd": return 1;
        }
        return 0;
    }*/
    private int getNotePosition(char noteValue, String alt) {
        
        switch (noteValue) {
            case 'C':
                switch (alt) {
                    case "flat":
                        return -1;
                    case "natural":
                        return 0;
                    case "#":
                        return 1;
                }
                break;

            case 'D':
                switch (alt) {
                    case "flat":
                        return 1;
                    case "natural":
                        return 2;
                    case "#":
                        return 3;
                }
                break;
            case 'E':
                switch (alt) {
                    case "flat":
                        return 3;
                    case "natural":
                        return 4;
                    case "#":
                        return 5;
                }
                break;
            case 'F':
                switch (alt) {
                    case "flat":
                        return 4;
                    case "natural":
                        return 5;
                    case "#":
                        return 6;
                }
                break;
            case 'G':
                switch (alt) {
                    case "flat":
                        return 6;
                    case "natural":
                        return 7;
                    case "#":
                        return 8;
                }
                break;
            case 'A':
                switch (alt) {
                    case "flat":
                        return 8;
                    case "natural":
                        return 9;
                    case "#":
                        return 10;
                }
                break;
            case 'B':
                switch (alt) {
                    case "flat":
                        return 10;
                    case "natural":
                        return 11;
                    case "#":
                        return 0;
                }
                break;
        }
        return -99;
    }

    public PitchCode randomItem() {

        int noteVal = (int) (Math.random() * 144);
        PitchCode pitchCode = new PitchCode(noteVal, "");
        //pitchCode.getRepresentationPrime();
        return pitchCode;
    }

}
