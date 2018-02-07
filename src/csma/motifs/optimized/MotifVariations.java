/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csma.motifs.optimized;

import csma.motifs.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class MotifVariations {
    
    public String file ;
    public String part;
    public String voice;
    public List<PatternDataStruct> identicalMelodic;
    public List<PatternDataStruct> transposed;
    public List<PatternDataStruct> inverted;
    public List<PatternDataStruct> mirror;
    public List<PatternDataStruct> identicalRhythmic;
    public List<PatternDataStruct> identicalMelodicRhythmic;

    public MotifVariations(List<PatternDataStruct> identicalMelodic, List<PatternDataStruct> transposed, List<PatternDataStruct> inverted, List<PatternDataStruct> mirror, List<PatternDataStruct> identicalRhythmic, List<PatternDataStruct> identicalMelodicRhythmic) {
        this.identicalMelodic = identicalMelodic;
        this.transposed = transposed;
        this.inverted = inverted;
        this.mirror = mirror;
        this.identicalRhythmic = identicalRhythmic;
        this.identicalMelodicRhythmic = identicalMelodicRhythmic;
    }

    public MotifVariations(String filename) {
        this.file = filename;
        this.identicalMelodic = new ArrayList<>();
        this.transposed = new ArrayList<>();
        this.inverted = new ArrayList<>();
        this.mirror = new ArrayList<>();
        this.identicalRhythmic = new ArrayList<>();
        this.identicalMelodicRhythmic = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "MotifVariations{file=" + file +", part"+part+", voice"+voice+", identicalMelodic=" + identicalMelodic.size() + ", transposed=" + transposed.size() + ", inverted=" + inverted.size() + ", mirror=" + mirror.size()
                + ", identicalRhythmic=" + identicalRhythmic.size() + ", identicalMelodicRhythmic=" + identicalMelodicRhythmic.size() + '}';
    }
    
    
}
