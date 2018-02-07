/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liu.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import liu.join.StringJoin;
import liu.tree.Node;
import liu.tree.Tree;

/**
 *
 * @author ASUS
 */
public class FindRepeatingPatterns {
    
    public static List<Pattern> nonTrivialPatterns = new ArrayList<>();
    
    public static List<Pattern> findRP(List string){
        Map<Integer, List<Pattern>> rp = new HashMap<>();
        List<Pattern> RP1 = findRP1(string);
        rp.put(1, RP1);
//        System.out.println("RP1 = "+rp.get(1));
        int k = 0;
        boolean isEmpty = false;
        while(!isEmpty){
            k = k+1;
            //System.out.println("puissance = "+Math.pow(2, k-1));
            List<Pattern> patterns2K = findRP2K(rp.get((int) Math.pow(2, k-1)));
//            System.out.println("RP2K (k= "+k+" )= "+patterns2K);
            if(patterns2K.isEmpty() || patterns2K.size() == rp.get((int) Math.pow(2, k-1)).size()){
                isEmpty = true;
                k = k-1;
            }else {
                rp.put((int) Math.pow(2, k), patterns2K);
            }
        }
        List<Pattern> result = new ArrayList<>();
        result = new ArrayList<>(find_Longest_RP(rp.get((int) Math.pow(2, k))));
        
//        List<Pattern> copy = new ArrayList<>(nonTrivialPatterns);
//        for(Pattern p1:copy){
//            for(Pattern p2: nonTrivialPatterns){
//                if(p2.value.containsAll(p1.value)){
//                    for(Pattern p3: RP1){
//                        Pattern joinPattern = StringJoin.join(p1, p3, 0);
//                        if(joinPattern.frequency == p1.frequency){
//                            nonTrivialPatterns.add(joinPattern);
//                            nonTrivialPatterns.remove(p1);
//                        }
//                    }
//                    break;
//                }
//            }
//        }
////System.out.println("before tree construction");
        rp = build_RP_Tree(rp);
        //System.out.println("rp tree: "+rp);

        //rp = refine_RP_Tree(rp);
        //System.out.println("rp tree refinement: "+rp);
        nonTrivialPatterns = new ArrayList<>();
        for(Integer i: rp.keySet()){
            System.out.println("k= "+i+" nbr = "+rp.get(i).size());
            nonTrivialPatterns.addAll(rp.get(i));
        }
//
//        generate_Non-trivial_RP(RP);
        
        
        
        
        return nonTrivialPatterns;
    }
    
    public static List<Pattern> findRP1(List<String> string){
        
        Map<String, List<Integer>> patterns = new HashMap<>();
        for (int i = 0; i < string.size(); i++) {
            List<Integer> positions = patterns.get(string.get(i));
            if(positions == null){
                positions = new ArrayList<>();
            }
            positions.add(i+1);
            patterns.put(string.get(i), positions);
        }
        List<Pattern> result = new ArrayList<>();
        for(String key: patterns.keySet()){
            List<String> value = new ArrayList<>();
            value.add(key);
            Pattern pattern = new Pattern(value, patterns.get(key));
            if(pattern.frequency<2) continue;
            result.add(pattern);
        }
        return result;
    }
    
    public static List<Pattern> findRP2K(List<Pattern> patterns){
        List<Pattern> result = new ArrayList<>();
        for (Pattern p1: patterns) {
            for (Pattern p2: patterns) {
                Pattern joinPattern = StringJoin.join(p1, p2, 0);
                if(joinPattern.frequency<2) continue;
                result.add(StringJoin.join(p1, p2, 0));
            }
        }
        List<Pattern> copy = new ArrayList<>(result);
        for(Pattern p: patterns){
           boolean isTrivial = false;
            for(Pattern pattern2K: copy)
                if(pattern2K.frequency==p.frequency && pattern2K.value.containsAll(p.value)){
                    isTrivial = true;
                }
            if(!isTrivial){
                nonTrivialPatterns.add(p);
            }
        }
        return result;
    }
    
    public static List<Pattern> find_Longest_RP(List<Pattern> patterns){
        return patterns;
    }
    
    public static Map<Integer, List<Pattern>> build_RP_Tree(Map<Integer, List<Pattern>> rp){
        int k = 0;
       
       while(rp.containsKey((int) Math.pow(2, k+1))){
            List<Pattern> highLevel = rp.get((int) Math.pow(2, k+1));
            List<Pattern> currentLevel = rp.get((int) Math.pow(2, k));
            for(Pattern p2K: highLevel)
            for(Pattern p: currentLevel){
                if(p2K.value.containsAll(p.value)){
                    p2K.children.add(p);
                    p.parents.add(p2K);
                }
            }
            k++;
       }
        
        Map<Integer, List<Pattern>> result = new HashMap<>(rp);
        
        return result;
    }
    
    public static Map<Integer, List<Pattern>> refine_RP_Tree(Map<Integer, List<Pattern>> rp){
       
       int k = 0;
       List<Pattern> blackList = new ArrayList<>();
       while(rp.containsKey((int) Math.pow(2, k+1))){
            List<Pattern> highLevel = rp.get((int) Math.pow(2, k+1));
            List<Pattern> highLevelCopy = new ArrayList<>(highLevel);
            List<Pattern> level = rp.get((int) Math.pow(2, k));
            //List<Pattern> lowLevel = rp.get((int) Math.pow(2, k-1));
            for(Pattern p: highLevelCopy){
                List<Pattern> children = new ArrayList<>(p.children);
                //System.out.println(p.value+" avant maj = "+children);
                for(Pattern child: children){
                    if(child.frequency==p.frequency){
//                        List<Pattern> parents = new ArrayList<>(child.parents);
                        for(Pattern parent:(List<Pattern>)  child.parents){                            
                            parent.children.remove(child);
                            for(Pattern grandChild:(List<Pattern>) child.children){
                            grandChild.parents.remove(child);
                            grandChild.parents.add(parent);
                            if(!parent.children.contains(grandChild)) parent.children.add(grandChild);
//                            p.children.add(grandChild);
                            if(highLevel.contains(parent)){
                                highLevel.remove(parent);
                                highLevel.add(parent);
                            }
                        }
                        }
                        level.remove(child);
                       
                        //children.remove(child);
                        
                        //children.addAll(child.children);
                    }
                }
                //System.out.println(p.value+" apres maj = "+children);
                //p.children = children;
            }
            rp.put((int) Math.pow(2, k+1), highLevel);
            rp.put((int) Math.pow(2, k), level);
            k++;
       }
       Map<Integer, List<Pattern>> result = new HashMap<>(rp);
       return result;
    }
    
    public static void main(String[] args) {
        List<String> sequence = new ArrayList<>();
        String s =  "ABCDEFGHABCDEFGHIJABCIJA";
        for(char c:s.toCharArray()){
            sequence.add(String.valueOf(c));
        }
        List<Pattern> patterns = findRP(sequence);
        System.out.println("patterns = "+patterns);
    }
}
