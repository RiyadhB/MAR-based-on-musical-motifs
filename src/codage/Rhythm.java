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
public class Rhythm extends Information implements Serializable{
    
    String value;
    public Rhythm(int nbrOfBits) {
        super(nbrOfBits);
    }
    
    public Rhythm(boolean[] values){
        super(values);
        value = getNoteValue();
    }
    
    public void setValue(String value){
        this.value = value;
        //System.out.println("duration = "+value);
        flags = getBinaryValue(getFlagValues());
    }
    
    
    private String getNoteValue() {
        int val = getDecimalValue(flags);
        switch (val) {
            case 0: return "whole";
            case 1: return "half";
            case 2: return "quarter";
            case 3: return "eighth";
            case 4: return "16th";
            case 5: return "32nd";
            case 6: return "64th";
            default: return "";        
        }
    }
       
    private int getFlagValues(){
        switch(value){
            case "whole": return 0;
            case "half": return 1;
            case "quarter": return 2;
            case "eighth": return 3;
            case "16th": return 4;
            case "32nd": return 5;
            case "64th": return 6;
            default: return 7;
        }
    }
    
    public static double getValue(String value){
        switch(value){
            case "whole": return 1;
            case "half": return 1./2;
            case "quarter": return 1./4;
            case "eighth": return 1./8;
            case "16th": return 1./16;
            case "32nd": return 1./32;
            case "64th": return 1./64;
            default: return 1;
        }
    }
    
    public String toString(){
        return getNoteValue()+"";
    }
 
    
}
