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
class Alter extends Information implements Serializable{
    String value;
    
    public Alter(int nbrOfBits) {
        super(nbrOfBits);
    }
    
    public Alter(boolean [] f){
        super(f);
        value = getAlerValue();
    }

    private String getAlerValue() {
        int val = getDecimalValue(flags);
        switch (val){
            case 0: return "flat";
            case 1: return "natural";
            case 2: return "#";
            default: return "";
        }    
    }
    
    public String toString(){
        return "-"+getAlerValue();
    }
}
