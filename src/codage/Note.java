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
class Note extends Information implements Serializable{
    char value;
    
    public Note(int nbrOfBits) {
        super(nbrOfBits);
    }
    
    public Note(boolean[] flags){
        super(flags);
        value = getNoteValue();
    }
    
    public Note(char c, int nbrOfBits){
        super(nbrOfBits);
        
        value = c;
        flags = getBinaryValue(getFlagValues());
    }

    private char getNoteValue() {
        int val = getDecimalValue(flags);
        switch (val) {
            case 0: return 'R';
            case 1: return 'C';
            case 2: return 'D';
            case 3: return 'E';
            case 4: return 'F';
            case 5: return 'G';
            case 6: return 'A';
            case 7: return 'B';
            default: return ' ';        
        }
    }
    
    private int getFlagValues(){
        switch(value){
            case 'R': return 0;
            case 'C': return 1;
            case 'D': return 2;
            case 'E': return 3;
            case 'F': return 4;
            case 'G': return 5;
            case 'A': return 6;
            case 'B': return 7;
            default: return 10;
        }
    }
    
    public String toString(){
        return getNoteValue()+"";
    }
 
    
}
