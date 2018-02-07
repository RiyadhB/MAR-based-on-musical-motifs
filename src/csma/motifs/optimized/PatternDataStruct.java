/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csma.motifs.optimized;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author ASUS
 */
public class PatternDataStruct<T> {

    private List<T> element;
    public double frequency;
    public TreeSet<Position> positions;
    //T token;
    boolean nontrivial;

    public PatternDataStruct(List<T> element, int frequcency, TreeSet<Position> positions) {
        this.element = element;
        this.frequency = frequcency;
        this.positions = positions;
        nontrivial = true;
    }

    public PatternDataStruct(List<T> element, double frequcency, TreeSet<Position> positions) {
        this.element = element;
        this.frequency = frequcency;
        this.positions = positions;
        nontrivial = true;
    }
    
    public PatternDataStruct(List<T> element, TreeSet<Position> positions) {
        this.element = element;
        this.frequency = positions.size();
        this.positions = positions;
        nontrivial = true;
    }

    public PatternDataStruct(T t, int size, TreeSet<Position> occurences) {
        this.element = new ArrayList<>();
        element.add(t);
        this.frequency = size;
        this.positions = occurences;
        nontrivial = true;
    }

    public PatternDataStruct(T t, TreeSet<Position> occurences) {
        this.element = new ArrayList<>();
        element.add(t);
        this.frequency = occurences.size();
        this.positions = occurences;
        nontrivial = true;
    }

    public PatternDataStruct join(PatternDataStruct newPattern, int maxGap, int maxLength) {
        //System.out.println("freq1 = "+frequency+" freq2 = "+newPattern.frequency);
        TreeSet<Position> newPositions = new TreeSet<>();
        for (Position positions1 : (TreeSet<Position>) positions) {
            Position position2 = (Position) newPattern.positions.higher(positions1);
            if (position2 == null) continue;
            int gap = position2.compareTo(positions1)-1;
            System.out.println("position2: "+position2+ " gap : "+gap);
            if (gap <= maxGap && gap>=0){
                Position position = new Position(positions1.index, position2.getLastIndex()- positions1.index, gap);
                newPositions.add(position);
            }
                
        }
        
        int freq = newPositions.size();
        List<T> newElemet = new ArrayList<>(element);
        //newElemet.add(token);
        newElemet.addAll(newPattern.element);
        //System.out.println("m1 : "+element+" m2: "+newPattern.element+ " new element: "+newElemet);
        PatternDataStruct result = new PatternDataStruct(newElemet, newPositions);
        if (freq == frequency || freq == newPattern.frequency) {
            result.nontrivial = false;
        }
        System.out.println("new element: "+result);
        return result;
    }

    public String toString() {
        String result = "(\"";
        for (T t : element) {
            result += t + " ";
        }
        result += "\", freq = " + frequency + " size= " + element.size() + " positions = " + positions.toString() + " )";

        return result;
    }

    @Override
    public boolean equals(Object object) {
        PatternDataStruct obj = (PatternDataStruct) object;
        if (obj.element.size() != element.size() || obj.frequency != frequency) {
            return false;
        }

        if (!element.containsAll(obj.element)) {
            return false;
        }

        return true;
    }

    PatternDataStruct<T> candidate;
    int maxGap;
    int maxLength;

    public void setCandidate(PatternDataStruct<T> candidate, int maxGap, int maxLength) {
        this.candidate = candidate;
        //this.token = token;
        this.maxGap = maxGap;
        this.maxLength = maxLength;
    }


    public List<T> getElement() {
        return element;
    }

    public void setElement(List<T> element) {
        this.element = element;
    }
}
