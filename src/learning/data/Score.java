/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.data;

import codage.PitchCode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Score implements Serializable{
    
    private List<Partition> partitions;
    private String scoreID;

    public Score(List<Partition> partitions, String scoreID) {
        this.partitions = partitions;
        this.scoreID = scoreID;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Partition> partitions) {
        this.partitions = partitions;
    }

    public String getScoreID() {
        return scoreID;
    }

    public void setScoreID(String scoreID) {
        this.scoreID = scoreID;
    }

    public List<PitchCode> getSequence() {
        List<PitchCode> result = new ArrayList<>();
        for (Partition partition: partitions){
            result.addAll(partition.getSequence());
        }
        return result;
    }
    
    public boolean equals(Object o){
        if (o instanceof Score){
            Score score = (Score) o;
            if(scoreID == score.getScoreID()) return true;
        }
        return false;
    }
    
}
