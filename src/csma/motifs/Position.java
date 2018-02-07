/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csma.motifs;

/**
 *
 * @author ASUS
 */
public class Position implements Comparable{
    public int index;
    public int length;
    public int gapWidth;
    
    public Position(int index1,int length1, int index2, int length2){
        this.index = index1;
        this.length = index2-index1+length2;
        gapWidth=index2-(index1+length1);
        
    }
    
    public Position(int index, int length, int gap){
        this.index = index;
        this.length = length;
        gapWidth = gap;
    }
    
    public Position(int index){
        this.index = index;
        length = 1;
        gapWidth = 0;
    }
    
    public int getLastIndex(){
        return index+length;
    }
    
    public String toString(){
        return "("+index+", gap = "+gapWidth+", len= "+length+")";
    }
    
    public boolean equals(Object object){        
        return index == ((Position) object).index;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Position){
            Position p = (Position) o;
            return index - p.index;
        }
        return -99;
    }
    
}
