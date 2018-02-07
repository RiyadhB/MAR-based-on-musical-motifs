/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liu.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Pattern<T> {
    public List<T> value;
    public int frequency;
    public List<Integer> pos;
    public List<Pattern> children;
    public List<Pattern> parents;

    public Pattern(List<T> value, List<Integer> pos) {
        this.value = value;
        this.frequency = pos.size();
        this.pos = pos;
        children = new ArrayList<>();
        parents = new ArrayList<>();
    }
    
    public String toString(){
        return "pattern = ("+value+", freq = "+frequency+" pos = "+pos+" )\n";
    }
    
    public boolean equals(Object o){
        return value.equals((((Pattern) o).value ));
    }
}
