package csma.motifs.optimized;

import main.*;
import codage.PitchCode;
import csma.motifs.optimized.CSMA;
import csma.motifs.optimized.PatternDataStruct;
import csma.motifs.optimized.Position;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Function;
import util.InformationExtractor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class Main {

    public static InformationExtractor informationExtractor;
    public static void main(String[] args) {
        List<String> files = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("scores.txt")));
            while (br.ready()) {
                files.add(br.readLine());
            }
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 1 melodic
        // 0 melodic ans rhythmic
        // -1 rhythmic
        int informationType = 1;
        for (String file : files) {
            System.out.println("score : " + file);
            informationExtractor = new InformationExtractor(null, new File(file));
            Map<Integer, List<PitchCode>> sequence = new HashMap<>(informationExtractor.sequence);
            List<PitchCode> pitchSequence = new ArrayList<>();
            Map<java.lang.String, Map<Integer, List<PitchCode>>> instruments = informationExtractor.sequences;
            int[] sequencesBorder = new int[instruments.size()];
            
            // convert the set of sequences into one sequence
            
            for (String part : instruments.keySet()) {
                System.out.println("instrument " + part);
                sequence = instruments.get(part);
                int i = 0;
                for (int voice: sequence.keySet()){
                    pitchSequence.addAll(sequence.get(voice));
                    sequencesBorder[i++] = sequence.get(voice).size();
                }
            }

            for (String part : instruments.keySet()) {
                System.out.println("instrument " + part);
                sequence = instruments.get(part);
                for (int voice : sequence.keySet()) {
                    if(voice>1) continue;
                    System.out.println("voice = " + voice);
                    if (informationType != 0) {
                        List<Integer> melodies = new ArrayList<>();
                        for (PitchCode pitchCode : sequence.get(voice)) {
                            int val = Integer.parseInt(pitchCode.toString());
                            if (val < 0) {
                                //sequence.remove(pitchCode);
                                continue;
                            }
                            melodies.add(Integer.parseInt(pitchCode.toString()));
                        }
                        variation(melodies, informationType, file, part, voice);
                    } else {
                        variation(sequence.get(voice), informationType, file, part, voice);
                    }
                }
            }

        }
    }
    
    public static int seqLen = 1;

    public static List variation(List sequence, int informationType, String file, String part, int voice) {
        //System.out.println("sequence length: " + sequence.size());

        int minFrequency = 2;
        CSMA csma = new CSMA(sequence, 4, 20, minFrequency, 0, -1);
        List<Integer> melodies = new ArrayList<>(sequence);
        List<PatternDataStruct> identicals = csma.patterns;
        
        System.out.println("#motifs identical= " + identicals.size());
        List result = new ArrayList<>();
        if (informationType != 0) {

            Function function = new Function(melodies);
            if (informationType == -1) {
                csma = new CSMA(function.ration(melodies), 3, 20, minFrequency, 0, -1);
            } else {
                csma = new CSMA(function.derived, 3, 20, minFrequency, 0, -1);
            }
            
            List<PatternDataStruct> transposed = new ArrayList<>(csma.patterns);
            
            //System.out.println("derived sequence: " + function.derived);
            transposed = removeRedundent(transposed);
            //System.out.println("# transposed motifs = " + transposed.size());
            //System.out.println("transposed motif: " + transposed);
            
//            csma = new CSMA(function.signes, 3, 20, 2, 0, -1);
//            System.out.println("# signes of derived motifs = " + csma.patterns.size());
            if (informationType == -1) {
                melodies = function.merge(function.ration(sequence), function.oppositeD(function.ration(sequence)));
            } else {
                melodies = function.merge(function.derived, function.opposite(function.derived));
            }
            csma = new CSMA(melodies, 3, 20, minFrequency, 0, -1);
            //System.out.println("sequence: " + melodies);

            //List translations = getTranslations(melodies, csma.patterns, melodies.size());
           
            //System.out.println("# inversed motifs = " + inversions.size());
            //System.out.println("inversion: " + inversions);
            //melodies = function.merge(function.derived, function.inverse(function.opposite(function.derived)));
            //System.out.println("miroir sequence : " + melodies);
            seqLen = function.originalData.size();
            
            if (informationType == -1) {
                melodies = function.merge(function.ration(sequence), function.inverse(function.oppositeD(function.ration(sequence))));
            } else {
                melodies = function.merge(function.derived, function.inverse(function.opposite(function.derived)));
            }
            //System.out.println("melodies : "+melodies);
            csma = new CSMA(melodies, 3, 200, minFrequency, 0, -1);
            //System.out.println("csma patterns "+csma.patterns);
            
            //informationExtractor.printMotifs(file, mirrors, informationType, part, voice, "mirror");
            //System.out.println("# mirror motifs = " + mirrors);
            System.out.println("there are " + identicals.size() + " identical " + transposed.size() + " transposed");
            //System.out.println("identical " + identicals + "\n" + "transposed " + transposed + "\ninversions " + inversions + "\nmirror: " + mirrors);
            
            
            result.add(transposed.size());
        }
        result.add(identicals.size());
        return result;
    }

    public static List<Position> getTranslations(List<Integer> sequence, List<PatternDataStruct> patterns, int sequenceLength) {
        List<Position> result = new ArrayList<>();
        
        for (PatternDataStruct pattern : patterns) {
            pattern.positions.removeIf(t -> ((Position) t).index>seqLen/2);           
        }
        return result;
    }

    public static List<PatternDataStruct> removeRedundent(List<PatternDataStruct> patterns) {
        List<PatternDataStruct> result = new ArrayList<>(patterns);
        for (PatternDataStruct pattern : patterns) {
            //int i = 0;
            for (Position p1 : (List<Position>) pattern.positions) {
                for (PatternDataStruct pattern2 : patterns) {
                    int i = 0;
                    for (Position p2 : (List<Position>) pattern2.positions) {
                        if (p2.index >= p1.index && p2.length < p1.length) {
                            i++;
                        }
                    }
                    if (i == pattern2.frequency) {
                        result.remove(pattern2);
                    }
                }
            }
        }
        return result;
    }
}
