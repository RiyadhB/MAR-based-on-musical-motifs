/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liu.join;

import java.util.ArrayList;
import java.util.List;
import liu.pattern.Pattern;

/**
 *
 * @author ASUS
 */
public class StringJoin {
    
    public static Pattern join(Pattern p1, Pattern p2, int orderK){
        
        List value = new ArrayList<>(p1.value);
        int n = p2.value.size();
        value.addAll(p2.value.subList(0, n-orderK));
        
        List<Integer> pos = new ArrayList<>();
        int m = p1.value.size();
        for(int x: (List<Integer>) p1.pos){           
            for(int y: (List<Integer>) p2.pos){
                if(x==y-m+orderK){
                    pos.add(x);
                }
            }
        }
        Pattern result = new Pattern(value, pos);
        return result;
    }
    
    public static void main(String[] args) {
        List<String> valueP1 = new ArrayList<>();
        valueP1.add("do");
        valueP1.add("re");
        List<Integer> posP1 = new ArrayList<>();
        posP1.add(1);
        posP1.add(5);
        posP1.add(8);
        
        List<Integer> posP2 = new ArrayList<>();
        posP2.add(3);
        //posP2.add(6);
        posP2.add(10);
        
        List<String> valueP2 = new ArrayList<>();
        valueP2.add("mi");
        valueP2.add("fa");
        
        Pattern<String> pattern1 = new Pattern(valueP1, posP1);
        Pattern<String> pattern2 = new Pattern(valueP2, posP2);
        Pattern pattern3 = StringJoin.join(pattern1, pattern2, 0);
        System.out.println(pattern1+" joins "+pattern2+" gives "+pattern3);
    }
}
