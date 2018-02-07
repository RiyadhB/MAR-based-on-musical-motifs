/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mir.score.ScoreMotifs;

/**
 *
 * @author ASUS
 */
public class Dictionnary {

    private static Map<String, Integer> getUnion(Map<String, Integer> map1, Map<String, Integer> map2) {
        Map<String, Integer> result = new HashMap<>();
        for(String k: map1.keySet()) result.put(k, 0);
        for(String k: map2.keySet()) if(!contains(map1.keySet(), k)){
            //System.out.println("key "+k+" added");
            result.put(k, 0);
        }

        return result;
    }
    
    private static boolean contains(Set<String> keys, String key){
        boolean result = false;
        for(String s: keys){
            if(s.equalsIgnoreCase(key)) result = true;
        }
        return result;
    }

    private static Map<String, Integer> project(Map<String, Integer> shortList, Map<String, Integer> longList) {
        for (String s : longList.keySet()) {
            if (!shortList.containsKey(s)) {
                shortList.put(s, 0);
            }
        }
        return shortList;
    }

    public static ScoreMotifs getScoreMotifsUnion(ScoreMotifs... motifs) {
        ScoreMotifs result = new ScoreMotifs("", "", "", "");
        for (ScoreMotifs scoreMotifs : motifs) {
            result.setIdenticalsMelodic(getUnion(result.getIdenticalsMelodic(), scoreMotifs.getIdenticalsMelodic()));
            result.setIdenticalsRhythmic(getUnion(result.getIdenticalsRhythmic(), scoreMotifs.getIdenticalsRhythmic()));
            result.setIdenticalsBoth(getUnion(result.getIdenticalsBoth(), scoreMotifs.getIdenticalsBoth()));
            result.setInverted(getUnion(result.getInverted(), scoreMotifs.getInverted()));
            result.setTransposed(getUnion(result.getTransposed(), scoreMotifs.getTransposed()));
            result.setMirror(getUnion(result.getMirror(), scoreMotifs.getMirror()));
        }

        return result;
    }
    
    public static ScoreMotifs getScoreMotifsUnion(List<ScoreMotifs> motifs) {
        ScoreMotifs result = new ScoreMotifs("", "", "", "");
        for (ScoreMotifs scoreMotifs : motifs) {
            result.setIdenticalsMelodic(getUnion(result.getIdenticalsMelodic(), scoreMotifs.getIdenticalsMelodic()));
            result.setIdenticalsRhythmic(getUnion(result.getIdenticalsRhythmic(), scoreMotifs.getIdenticalsRhythmic()));
            result.setIdenticalsBoth(getUnion(result.getIdenticalsBoth(), scoreMotifs.getIdenticalsBoth()));
            result.setInverted(getUnion(result.getInverted(), scoreMotifs.getInverted()));
            result.setTransposed(getUnion(result.getTransposed(), scoreMotifs.getTransposed()));
            result.setMirror(getUnion(result.getMirror(), scoreMotifs.getMirror()));
        }

        return result;
    }

    public static ScoreMotifs getProjetion(ScoreMotifs motifs, ScoreMotifs globalMotifs) {
        ScoreMotifs result = motifs;
        //System.out.println("before: "+motifs.getIdenticalsMelodic());
        result.setIdenticalsMelodic(project(motifs.getIdenticalsMelodic(), globalMotifs.getIdenticalsMelodic()));
        //System.out.println("after: "+motifs.getIdenticalsMelodic());
        result.setIdenticalsRhythmic(project(motifs.getIdenticalsRhythmic(), globalMotifs.getIdenticalsRhythmic()));
        result.setIdenticalsBoth(project(motifs.getIdenticalsBoth(), globalMotifs.getIdenticalsBoth()));
        result.setInverted(project(motifs.getInverted(), globalMotifs.getInverted()));
        result.setTransposed(project(motifs.getTransposed(), globalMotifs.getTransposed()));
        result.setMirror(project(motifs.getMirror(), globalMotifs.getMirror()));
        return result;
    }

}
