/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csma.motifs.optimized;

import csma.motifs.*;
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
        return pattern1.join(pattern2, maxGap, maxLength);
    }

    @Override
    public PatternDataStruct call() throws Exception {
        return join();
    }

}
