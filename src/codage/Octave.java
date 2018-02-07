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
class Octave extends Information implements Serializable{
    int value;
    
    public Octave(int nbrOfBits) {
        super(nbrOfBits);
    }
    
    public Octave(boolean [] f){
        super(f);
        value = getDecimalValue(f);
        if(value>7)
            System.out.println("wrong octave value!");
    }
    
    public String toString(){
        return value+"";
    }
}
