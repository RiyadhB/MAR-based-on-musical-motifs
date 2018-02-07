/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.data;

import codage.PitchCode;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Partition implements Serializable{
    
    private List<PitchCode> sequence;

    public Partition(List<PitchCode> sequence) {
        this.sequence = sequence;
    }

    public List<PitchCode> getSequence() {
        return sequence;
    }

    public void setSequence(List<PitchCode> sequence) {
        this.sequence = sequence;
    }
    
}
