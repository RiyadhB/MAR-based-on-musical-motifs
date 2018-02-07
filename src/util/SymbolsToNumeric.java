/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import codage.PitchCode;

/**
 *
 * @author ng152
 */
public class SymbolsToNumeric {

    String[] symbols;

    public SymbolsToNumeric(String[] symbols) {
        this.symbols = symbols;
    }

    public SymbolsToNumeric(String symbols, String splittingChar) {
        this(symbols.split(splittingChar));
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    public PitchCode getPitchValue() {
        if (symbols == null) {
            return null;
        }
        //int result = 0;
        

        //System.out.println("the code of "+symbols[0].charAt(0)+" "+symbols[1]+" "+symbols[2]+ " and duration:  "+symbols[3]);
        if(symbols[1].equalsIgnoreCase("null") || symbols[2].equalsIgnoreCase("null")) return new PitchCode(symbols[0].charAt(0), 0, 0,symbols[3]);
        return new PitchCode(symbols[0].charAt(0), Integer.parseInt(symbols[2]), getAlternativeValue(symbols[1]),symbols[3]);
        
    }
    //

    private int getNoteValue(String symbol) {
        switch (symbol) {
            case "C":
                return 0;
            case "D":
                return 2;
            case "E":
                return 4;
            case "F":
                return 5;
            case "G":
                return 7;
            case "A":
                return 9;
            case "B":
                return 11;
        }
        return 0;
    }

    private int getBasicNotePositionFromOctave(String symbol) {
        return 12 * (Integer.parseInt(symbol)+1);
    }

    private int getAlternativeValue(String symbol) {
        double alterValue = Double.parseDouble(symbol);
        //the value 0.4 is to avoid to have equal values for a falt and sharp of sucessive notes 
        if (alterValue < 0) {
            return 0;// for a falt
        }
        if (alterValue > 0) {
            return 2;// for a sharp
        }
        return 1;
    }

    public String getSymbolicRepresentation(double value) {
        String result = "";
        int octave = (int) value / 7;
        int noteValue = (int) value % 7;
        double alter = value % 7 - noteValue;
        switch (noteValue) {
            case 1:
                if (alter == 0.6) {
                    result = "DF" + octave;
                } else if (alter == 0.4) {
                    result = "CS" + octave;
                } else {
                    result = "C" + octave;
                }
                break;
                
                case 2:
                if (alter == 0.6) {
                    result = "EF" + octave;
                } else if (alter == 0.4) {
                    result = "DS" + octave;
                } else {
                    result = "D" + octave;
                }
                break;
                case 3:
                if (alter == 0.6) {
                    result = "FF" + octave;
                } else if (alter == 0.4) {
                    result = "ES" + octave;
                } else {
                    result = "E" + octave;
                }
                break;
                case 4:
                if (alter == 0.6) {
                    result = "GF" + octave;
                } else if (alter == 0.4) {
                    result = "FS" + octave;
                } else {
                    result = "F" + octave;
                }
                break;
                case 5:
                if (alter == 0.6) {
                    result = "AF" + octave;
                } else if (alter == 0.4) {
                    result = "GS" + octave;
                } else {
                    result = "G" + octave;
                }
                break;
                case 6:
                if (alter == 0.6) {
                    result = "BF" + octave;
                } else if (alter == 0.4) {
                    result = "AS" + octave;
                } else {
                    result = "A" + octave;
                }
                break;
                case 7:
                if (alter == 0.6) {
                    result = "CF" + octave;
                } else if (alter == 0.4) {
                    result = "BS" + octave;
                } else {
                    result = "B" + octave;
                }
                break;
        }

        return result;
    }
}
