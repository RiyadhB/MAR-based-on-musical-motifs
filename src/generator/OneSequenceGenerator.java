/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class OneSequenceGenerator {
    
    public static void main(String[] args) {
        try {
            int cpt = 10;
            while(cpt>0){
            BufferedReader br = new BufferedReader(new FileReader(new File("CSMA/WithGaps"+cpt+"D500I1KT10.txt")));
            String line = br.readLine();
            String[] split = line.split(" ");
            //List<String> sequences = new ArrayList<>();
            String s = "";
            for (int i = 0; i < split.length; i++) {
                
                for (int j = i; j < i+10; j++) {
                    if(j< split.length)                      
                    
                        s+= split[j]+" -1 ";
                }
                s+="-2\n";
                //sequences.add(s);
            }
            System.out.println(s);
            // ajout des motifs pour la grande sequence            
            FileWriter fw = new FileWriter("SPMF/OneLongGapSeq"+cpt+".txt");
            fw.append(s);
            fw.flush();
            fw.close();
            cpt --;
            }
            // transformation de la grande sequence en petites sequences de la meme taille avec adaptation pour spmf
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OneSequenceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OneSequenceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
