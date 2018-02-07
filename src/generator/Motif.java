/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Motif implements Serializable{
    public List<String> value;
    public int frequency;
    
    public static final long serialVersionUID = 1998L;
    
    public String toString(){
        return value.toString()+" freq = "+frequency;
    }
    
    public boolean equals(Object object){
        Motif m = (Motif) object;
        return m.value.containsAll(value);//&& Math.abs(frequency-m.frequency)<10;
    }
}
