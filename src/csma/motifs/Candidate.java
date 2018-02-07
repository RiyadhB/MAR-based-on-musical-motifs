/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csma.motifs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author ASUS
 */
public class Candidate {

    Map<Integer, Integer> postionsMap;

    @SuppressWarnings("empty-statement")
    public Candidate(PatternDataStruct p1, PatternDataStruct p2) {
        postionsMap = new HashMap();
        
        
        
        for (int i = 0; i < p1.positions.size(); i++) {
            int j = 0;
            for (; j < p2.positions.size() && ((Position) p2.positions.get(j)).index < ((Position) p1.positions.get(i)).getLastIndex(); j++);
            
            if (j<p2.positions.size()) {
                postionsMap.put(j, i);
            }
        }
    }

    public Candidate(PatternDataStruct p1, PatternDataStruct p2, int length) {
        postionsMap = new HashMap();
        int j = 0;
        
        //((List<Position>) p1.positions).forEach(positionP1-> {});
        //int tmp = -1;
        int posIndex2 = 0;
        for (int i = 0; i < p1.positions.size(); i++) {
            
            int posIndex = ((Position) p1.positions.get(i)).index/length;
            //if(tmp == posIndex ) continue;
            //tmp = posIndex;
            int lastPosition = ((Position) p1.positions.get(i)).getLastIndex();
            //int lastPosition = ((Position) p1.positions.get(i)).getLastIndex();
            //System.out.println("pos index = "+posIndex);
            //Position pos;
            /*try{
            pos = ((List<Position>) p2.positions).stream().filter(position->position.index>= lastPosition).findFirst().get();
            }catch(Exception e){ continue;}*/
            //j = p2.positions.indexOf(pos);
            for (; j < p2.frequency && ((Position) p2.positions.get(j)).index < lastPosition; j++){
                //System.out.println(p2.element.get(0));
                /*if(((String) p2.element.get(0)).equals("2")){
                    System.out.println("pos index "+posIndex+" pos [2]: "+String.valueOf(((Position) p2.positions.get(j)).index/length));
                }*/
                posIndex2 = ((Position) p2.positions.get(j)).index/length;
                if( posIndex2 > posIndex) {
                    //j=p2.frequency;
                    break;
                } // pour empecher de generer des motifs entre transactions
            }
            if(j<p2.frequency) posIndex2 = ((Position) p2.positions.get(j)).index/length;
            if (j<p2.frequency && posIndex==posIndex2) {
                //System.out.println("p1 pos: "+posIndex+ "\t p2 pos: "+posIndex2);
                postionsMap.put(j, i);
            }
        }
    }
}
