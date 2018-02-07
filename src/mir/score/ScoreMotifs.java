/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir.score;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ASUS
 */
public class ScoreMotifs implements Serializable{
    private static final long serialVersionUID = 1L;
    
    String authorId;
    String scoreId;
    String partId;
    String voiceId;
    
    Map<String, Integer> identicalsMelodic;
    Map<String, Integer> transposed;
    Map<String, Integer> inverted;
    Map<String, Integer> mirror;
    
    Map<String, Integer> identicalsRhythmic;
    Map<String, Integer> identicalsBoth; // melodic and rhythmic

    public ScoreMotifs(String authorId, String scoreId, String partId, String voiceId) {
        this.authorId = authorId;
        this.scoreId = scoreId;
        this.partId = partId;
        this.voiceId = voiceId;
        
        identicalsMelodic = new HashMap<>();
        identicalsRhythmic = new HashMap<>();
        identicalsBoth = new HashMap<>();
        
        transposed = new HashMap<>();
        inverted = new HashMap<>();
        mirror = new HashMap<>();
    }
    
    public ScoreMotifs(String authorId, String scoreId) {
        this.authorId = authorId;
        this.scoreId = scoreId;
        
        identicalsMelodic = new HashMap<>();
        identicalsRhythmic = new HashMap<>();
        identicalsBoth = new HashMap<>();
        
        transposed = new HashMap<>();
        inverted = new HashMap<>();
        mirror = new HashMap<>();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getScoreId() {
        return scoreId;
    }

    public String getPartId() {
        return partId;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public Map<String, Integer> getIdenticalsMelodic() {
        return identicalsMelodic;
    }

    public Map<String, Integer> getTransposed() {
        return transposed;
    }

    public Map<String, Integer> getInverted() {
        return inverted;
    }

    public Map<String, Integer> getMirror() {
        return mirror;
    }

    public Map<String, Integer> getIdenticalsRhythmic() {
        return identicalsRhythmic;
    }

    public Map<String, Integer> getIdenticalsBoth() {
        return identicalsBoth;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }

    public void setIdenticalsMelodic(Map<String, Integer> identicalsMelodic) {
        this.identicalsMelodic = identicalsMelodic;
    }

    public void setTransposed(Map<String, Integer> transposed) {
        this.transposed = transposed;
    }

    public void setInverted(Map<String, Integer> inverted) {
        this.inverted = inverted;
    }

    public void setMirror(Map<String, Integer> mirror) {
        this.mirror = mirror;
    }

    public void setIdenticalsRhythmic(Map<String, Integer> identicalsRhythmic) {
        this.identicalsRhythmic = identicalsRhythmic;
    }

    public void setIdenticalsBoth(Map<String, Integer> identicalsBoth) {
        this.identicalsBoth = identicalsBoth;
    }

    @Override
    public String toString() {
        return "ScoreMotifs{" + "authorId=" + authorId + ", scoreId=" + scoreId + ", "
                + ", identicalsMelodic=" + identicalsMelodic.size() + ","
                + " transposed=" + transposed.size() + ", inverted=" + inverted.size() + ", "
                + "mirror=" + mirror.size() + ", identicalsRhythmic=" + identicalsRhythmic.size() + ", "
                + "identicalsBoth=" + identicalsBoth.size() + '}';
    }
    
    public String getCSVRepresentation(ScoreMotifs globalScores){
        String result = scoreId+";"+authorId+";";
        result += printMap(identicalsMelodic, globalScores.identicalsMelodic.keySet());
        result += printMap(identicalsRhythmic, globalScores.identicalsRhythmic.keySet());
        result += printMap(identicalsBoth, globalScores.identicalsBoth.keySet());
        result += printMap(inverted, globalScores.inverted.keySet());
        result += printMap(transposed, globalScores.transposed.keySet());
        result += printMap(mirror, globalScores.mirror.keySet());
        result = result.substring(0, result.length()-1); // to remove the last ';'
        return result;
    }
    
    public String getCSVRepresentation(Set globalScores){
        String result = scoreId+";"+authorId+";";
        result += printMap(identicalsMelodic, globalScores);
        result += printMap(identicalsRhythmic, globalScores);
        result += printMap(identicalsBoth, globalScores);
        result += printMap(inverted, globalScores);
        result += printMap(transposed, globalScores);
        result += printMap(mirror, globalScores);
        result = result.substring(0, result.length()-1); // to remove the last ';'
        return result;
    }
    
    private String printMap(Map<String, Integer> motifs, Set<String> printOrder){
        String result = "";
         for(String s: printOrder) result+=motifs.get(s)+";";
        return result;
    }
    
    private String printHeaders(Map<String, Integer> motifs, String prefix){
        String result = "";
         for(String s: motifs.keySet()) result+=prefix+s+";";
        return result;
    }
    
    public String getCSVHeaders(){
        String result = "scoreID; AuthorID;";
        result += printHeaders(identicalsMelodic, "IM");
        result += printHeaders(identicalsRhythmic, "IR");
        result += printHeaders(identicalsBoth, "IB");
        result += printHeaders(inverted, "INV");
        result += printHeaders(transposed, "TRANS");
        result += printHeaders(mirror, "MIRR");
        result = result.substring(0, result.length()-1); // to remove the last ';'
        return result;
    }
    
    
}
