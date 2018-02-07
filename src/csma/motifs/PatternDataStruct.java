/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csma.motifs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author ASUS
 */
public class PatternDataStruct<T> implements Callable<PatternDataStruct>{

    public List<T> element;
    public double frequency;
    public List<Position> positions;
    //T token;
    boolean nontrivial;

    public PatternDataStruct(List<T> element, int frequcency, List<Position> positions) {
        this.element = element;
        this.frequency = frequcency;
        this.positions = positions;
        nontrivial = true;
    }
    
    public PatternDataStruct(List<T> element, double frequcency, List<Position> positions) {
        this.element = element;
        this.frequency = frequcency;
        this.positions = positions;
        nontrivial = true;
    }

    public PatternDataStruct(T t, int size, List<Position> occurences) {
        this.element = new ArrayList<>();
        element.add(t);
        this.frequency = size;
        this.positions = occurences;
        nontrivial = true;
    }

    public PatternDataStruct join(PatternDataStruct newPattern, int maxGap, int maxLength) {
        //System.out.println("freq1 = "+frequency+" freq2 = "+newPattern.frequency);
        List<Position> newPositions = new ArrayList<>();

        Candidate candidate = new Candidate(this, newPattern);
        
        Position p;
        
        for (int j : candidate.postionsMap.keySet()) {
            p = new Position(positions.get(candidate.postionsMap.get(j)).index, positions.get(candidate.postionsMap.get(j)).length,
                    ((Position) newPattern.positions.get(j)).index, ((Position) newPattern.positions.get(j)).length);
            if (p.gapWidth > maxGap || p.length > maxLength) {
                continue;
            }
            newPositions.add(p);
        }
        
        int freq = newPositions.size();
        List<T> newElemet = new ArrayList<>();
        newElemet.addAll(element);
        //newElemet.add(token);
        newElemet.addAll(element.size(), newPattern.element);
        PatternDataStruct result = new PatternDataStruct(newElemet, freq, newPositions);
        if (freq == frequency || freq == newPattern.frequency) {
            result.nontrivial = false;
        }
        
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
        if(obj.element.size() != element.size() || obj.frequency != frequency) return false;
        int i=0;
//        for(T t: element){
//            if(obj.element.contains(t)) i++;
//        }
        for (i = 0; i < element.size(); i++) {
            if(!element.get(i).equals(obj.element.get(i))) return false;
        }

//if(i == obj.element.size() && i == element.size()) return true;
        //return (obj.element.equals(element));
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

    @Override
    public PatternDataStruct call() throws Exception {
        return join(candidate, maxGap, maxLength);
    }
}
