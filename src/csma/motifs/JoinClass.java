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
public class JoinClass<T> implements Callable<PatternDataStruct> {

    PatternDataStruct pattern1;
    PatternDataStruct pattern2;

    int maxGap;
    int maxLength;
    int minFrequency;
    int seqLen;

    public JoinClass(PatternDataStruct pattern1, PatternDataStruct pattern2, int maxGap, int maxLength, int seqLen, int minFrequency) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;

        this.maxGap = maxGap;
        this.maxLength = maxLength;
        this.seqLen = seqLen;
        this.minFrequency = minFrequency;
    }

    public PatternDataStruct join() {
        //System.out.println("freq1 = "+frequency+" freq2 = "+newPattern.frequency);
        List<Position> newPositions = new ArrayList<>();
        Candidate candidate;
        //System.out.println("p1 = "+pattern1+" p2 = "+pattern2);
        if (seqLen != -1) {
            candidate = new Candidate(pattern1, pattern2, seqLen);
        }
        
        else {
            candidate = new Candidate(pattern1, pattern2);
        }
//        if (pattern1.element.toString().contains("62, 69") && pattern2.element.toString().contains("65")) {
//            System.out.println("pattern1 : "+pattern1+ " \n pattern2: "+ pattern2 +  "\ncandidate indixes: \n" + candidate.postionsMap.toString());
//        }
        if (candidate.postionsMap.keySet().size() < minFrequency) {
            return null;
        }
        Position p;

        for (int j : candidate.postionsMap.keySet()) {

            p = new Position(((Position) (pattern1.positions.get(candidate.postionsMap.get(j)))).index, ((Position) (pattern1.positions.get(candidate.postionsMap.get(j)))).length,
                    ((Position) pattern2.positions.get(j)).index, 1);
            //System.out.println("p= "+p);
            //if(positions.get(j).index - p.index)
            if (p.gapWidth > maxGap || p.length > maxLength) {
                continue;
            }
            newPositions.add(p);
        }
        //compute occurences
//        for (int i = 0; i < positions.size(); i++) {
//            if (newPattern.positions.contains(positions.get(i) + size)) {
//                newPositions.add(positions.get(i));
//            }
//        }
        int freq = newPositions.size();
        List<T> newElemet = new ArrayList<>();
        newElemet.addAll(pattern1.element);

        newElemet.addAll(pattern1.element.size(), pattern2.element);
        PatternDataStruct result = new PatternDataStruct(newElemet, freq, newPositions);
        if (freq == pattern1.frequency || freq == pattern2.frequency) {
            result.nontrivial = false;
        }
        //System.out.println("result freq = "+freq);
        return result;
    }

    @Override
    public PatternDataStruct call() throws Exception {
        return join();
    }

}
