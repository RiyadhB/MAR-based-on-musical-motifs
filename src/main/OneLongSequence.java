/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import codage.PitchCode;
import csma.motifs.CSMA;
import csma.motifs.MotifVariations;
import csma.motifs.PatternDataStruct;
import csma.motifs.Position;
import generated.PartList;
import generated.ScorePartwise;
import generated.Work;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Main.informationExtractor;
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
public class OneLongSequence {

    public static InformationExtractor informationExtractor;
    public static List<MotifVariations> variations;
    public static MotifVariations gloabalVariations = new MotifVariations("global");

    public static void main(String[] args) {
        List<String> files = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("scores.txt")));
            while (br.ready()) {
                String line = br.readLine();
                if (line.startsWith("#")) {
                    continue;
                }
                files.add(line);
            }
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        variations = new ArrayList<>();

        for (String file : files) {
            MotifVariations motifVariations = new MotifVariations(file);
            System.out.println("score : " + file);
            informationExtractor = new InformationExtractor(null, new File(file));
            Map<Integer, List<PitchCode>> sequence = new HashMap<>();
            Map<java.lang.String, Map<Integer, List<PitchCode>>> instruments = informationExtractor.sequences;
            ScorePartwise scorePartwise;
            ScorePartwise[] scorePartwises = new ScorePartwise[6]; // 6 nbr of types of motifs
            for (int i = 0; i < scorePartwises.length; i++) {
                scorePartwise = informationExtractor.load(file);
                scorePartwises[i] = new ScorePartwise();
                scorePartwises[i].getPart().addAll(new ArrayList<>(scorePartwise.getPart()));
            }

            List<PitchCode> pitchSequence = new ArrayList<>();
            int[] sequencesBorder = new int[instruments.size()];

            // convert the set of sequences into one sequence
            int i = 0;
            for (String part : instruments.keySet()) {
                System.out.println("instrument " + part);
                sequence = instruments.get(part);
                //System.out.println("sequence of part "+part+ " : "+sequence);

                for (int voice : sequence.keySet()) {
                    pitchSequence.addAll(sequence.get(voice));
                    if (i == 0) {
                        sequencesBorder[0] = sequence.get(voice).size();;
                    } else {
                        sequencesBorder[i] = sequencesBorder[i - 1] + sequence.get(voice).size();

                    }
                    System.out.println("size at part " + i + "  : " + sequencesBorder[i]);
                    i++;

                }
            }
            // 1 melodic
            // 0 melodic ans rhythmic
            // -1 rhythmic
            int informationType = -1;
            while (informationType < 2) {

                PitchCode.informationType = informationType;
                if (informationType != 0) {
                    List<Integer> melodies = new ArrayList<>();
                    for (PitchCode pitchCode : pitchSequence) {
                        int val = Integer.parseInt(pitchCode.toString());
//                        if (val < 0) {
//                            //sequence.remove(pitchCode);
//                            continue;
//                        }
                        melodies.add(val);
                    }
                    //System.out.println("sequence: "+melodies);
                    variation(melodies, motifVariations, informationType, sequencesBorder, scorePartwises);
                } else {
                    variation(pitchSequence, motifVariations, informationType, sequencesBorder, scorePartwises);
                }
//                    }
                //}
                informationType++;
            }
            for (int scoreID = 0; scoreID < scorePartwises.length; scoreID++) {
                String type = "";
                switch (scoreID) {
                    case 0:
                        type = "RhythmicIdentical";
                        break;
                    case 1:
                        type = "MelodicIdentical";
                        break;
                    case 2:
                        type = "PitchIdentical";
                        break;
                    case 3:
                        type = "transposed";
                        break;
                    case 4:
                        type = "inverted";
                        break;
                    case 5:
                        type = "mirrors";
                        break;
                }
                Work work = new Work();
                work.setWorkTitle(type + "\n minFreq = " + minFrequency + ", minLen = " + minLength);
                scorePartwises[scoreID].setWork(work);
                informationExtractor.saving(file, type, scorePartwises[scoreID]);
            }
            variations.add(motifVariations);
        }
        System.out.println(variations);
    }

    public static int seqLen = 1;
    static int minFrequency = 2;
    static int minLength = 9;

    public static void variation(List sequence, MotifVariations motifVariations, int informationType, int[] positions, ScorePartwise[] scorePartwises) {
        //System.out.println("sequence : " + sequence);

        CSMA csma = new CSMA(sequence, minLength, 20, minFrequency, 0, -1);
        List<Integer> melodies = new ArrayList<>(sequence);
        List<PatternDataStruct> identicals = new ArrayList<>(csma.patterns);
        identicals = removeRedundent(identicals);
        //System.out.println("#motifs identical= " + identicals);
        if (informationType != 0) {

            Function function = new Function(melodies);
            if (informationType == -1) {
                csma = new CSMA(function.ration(melodies), minLength, 20, minFrequency, 0, -1);
            } else {
                if (function.derived.size() > 600) {
                    System.out.println("melodic sequence: \n" + melodies.subList(0, 307) + "\n" + melodies.subList(307, 620) + "\n" + melodies.subList(620, 890));
                    System.out.println("transposition sequence: \n" + function.derived.subList(0, 307) + "\n" + function.derived.subList(307, 620) + "\n" + function.derived.subList(620, 890) + "\n" + function.derived.subList(890, 1129));
                }
                csma = new CSMA(function.derived, minLength - 1, 20, minFrequency, 0, -1);
            }
            List<Position> toBeRemoved = new ArrayList<>();
            for (PatternDataStruct pattern : identicals) {
                toBeRemoved.addAll(pattern.positions.subList(1, pattern.positions.size()));
            }
            List<PatternDataStruct> transposed = new ArrayList<>(csma.patterns);
//            for (PatternDataStruct pattern : (List<PatternDataStruct>) transposed) {
//                pattern.positions.removeAll(toBeRemoved);
//            }
            //System.out.println("derived sequence: " + function.derived);
            transposed = removeRedundent(transposed);
            //System.out.println("# transposed motifs = " + transposed.size());
//            System.out.println("transposed motifs: " + transposed);

//            csma = new CSMA(function.signes, 3, 20, 2, 0, -1);
//            System.out.println("# signes of derived motifs = " + csma.patterns.size());
            if (informationType == -1) {
                melodies = function.merge(function.ration(sequence), function.oppositeD(function.ration(sequence)));
            } else {
                melodies = function.merge(function.derived, function.opposite(function.derived));
            }
            csma = new CSMA(melodies, minLength, 20, minFrequency, 0, -1);
            //System.out.println("sequence: " + melodies);

            //List translations = getTranslations(melodies, csma.patterns, melodies.size());
            List inversions = getInversions(function.originalData, csma.patterns, melodies.size());
            inversions = removeRedundent(inversions);
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
            csma = new CSMA(melodies, minLength, 200, minFrequency, 0, -1);
            //System.out.println("csma patterns "+csma.patterns);
            List mirrors = getMirrors(melodies, csma.patterns, melodies.size());
            mirrors = removeRedundent(mirrors);
//            informationExtractor.printMotifs(file, mirrors, informationType, positions, "mirror");
            //System.out.println("# mirror motifs = " + mirrors);
            switch (informationType) {
                case -1:
                    motifVariations.identicalRhythmic = identicals;
                    informationExtractor.printMotifs(scorePartwises[0], identicals, positions, "RhythmicIdentical");
                    put(gloabalVariations.identicalRhythmic, identicals);
                    break;
                case 1:
                    put(gloabalVariations.identicalMelodic, identicals);
                    informationExtractor.printMotifs(scorePartwises[1], identicals, positions, "MelodicIdentical");
                    motifVariations.identicalMelodic = identicals;
                    motifVariations.inverted = inversions;
                    put(gloabalVariations.inverted, inversions);
                    motifVariations.transposed = transposed;
                    put(gloabalVariations.transposed, transposed);
                    motifVariations.mirror = mirrors;
                    put(gloabalVariations.mirror, mirrors);
                    //informationExtractor.printMotifs(scorePartwises[5], mirrors, positions, "mirrors");
                    //informationExtractor.printMotifs(scorePartwises[4], inversions, positions, "inverted");
                    System.out.println("transposed motifs: " + transposed);
                    informationExtractor.printMotifs(scorePartwises[3], transposed, positions, "transposed");
                    break;
            }

//            System.out.println("there are " + identicals.size() + " identical " + transposed.size() + " transposed, " + inversions.size() / 2 + " inversion, and " + mirrors.size() + " mirror");
//            System.out.println("identical " + identicals + "\n" + "transposed " + transposed + "\ninversions " + inversions + "\nmirror: " + mirrors);
        } else {
            motifVariations.identicalMelodicRhythmic = identicals;
            informationExtractor.printMotifs(scorePartwises[2], identicals, positions, "PitchIdentical");
            put(gloabalVariations.identicalMelodicRhythmic, identicals);
        }
        System.out.println(motifVariations);
    }

    public static void put(List<PatternDataStruct> global, List<PatternDataStruct> candidates) {
        for (PatternDataStruct pattern : candidates) {
            if (!global.contains(pattern)) {
                global.add(pattern);
            }
        }
    }

    public static List<PatternDataStruct> getMirrors(List<Integer> sequence, List<PatternDataStruct> patterns, int sequenceLength) {
        List<PatternDataStruct> result = new ArrayList<>();
        //System.out.println("seqlen " + sequence);
        for (PatternDataStruct pattern : patterns) {
            List<Position> positions = pattern.positions;
            int l = 0;
            for (int i = 0; i < positions.size() && positions.get(i).index < (sequenceLength / 2); i++) {
                for (int j = l; j < positions.size() && j < patterns.size(); j++) {
                    if (positions.get(j).index < sequenceLength / 2) {
                        continue;
                    }
                    if (l == 0 && positions.get(j).index >= (sequenceLength / 2)) {
                        l = j;
                    }
                    // System.out.println("pattern"+pattern.element+"middle "+sequence.get(positions.get(i).index + pattern.element.size() / 2));
                    if (pattern.element.size() % 2 == 1 && sequence.get(positions.get(i).index + pattern.element.size() / 2) == 0) {
                        boolean isMirror = false;
                        //System.out.println("motif: "+pattern.element);
                        int k = 0;
                        int cpt = pattern.element.size() - 1;
                        for (k = positions.get(i).index; k < positions.get(i).index + pattern.element.size() / 2; k++) {
                            if (sequence.get(k) != -sequence.get(positions.get(i).index + cpt)) {
                                //System.out.println(sequence.get(k) + " is <> "+sequence.get(positions.get(i).index+ cpt));
                                break;
                            }
                            cpt--;
                        }
                        if (k == positions.get(i).index + pattern.element.size() / 2) {
                            isMirror = true;
                        }
                        //System.out.println("motif: "+pattern.element+ "k ="+k+" ismirror "+isMirror);
                        if (isMirror) {
                            result.add(pattern);
                        }
                    }
//                    System.out.println("p= "+pattern + "mirror: "+sequence.get(positions.get(i).index + positions.get(j).length + 1));
//                    if (sequence.get(positions.get(i).index + positions.get(j).length + 1) == 0) {
//                        
//                    }
                }
            }
        }
        return result;
    }

    public static List<PatternDataStruct> getInversions(List<Integer> sequence, List<PatternDataStruct> patterns, int sequenceLength) {
        List<PatternDataStruct> result = new ArrayList<>();

        for (PatternDataStruct pattern : patterns) {
            List<Position> positions = new ArrayList<>(pattern.positions);
            int l = 0;
            for (int i = 0; i < positions.size() && positions.get(i).index < (sequenceLength / 2); i++) {
                for (int j = l; j < positions.size() && j < patterns.size(); j++) {
                    if (positions.get(j).index < sequenceLength / 2) {
                        continue;
                    }
                    if (l == 0 && positions.get(j).index >= (sequenceLength / 2)) {
                        l = j;
                    }
                    //System.out.println("p= " + pattern + " melo value = " + sequence.get(positions.get(i).index) + " ext val = " + sequence.get(positions.get(j).index - sequenceLength / 2));
                    if (sequence.get(positions.get(i).index).equals(sequence.get(positions.get(j).index - sequenceLength / 2)) && !result.contains(pattern)) {
                        result.add(pattern);
                    }
                }
            }
        }
        return result;
    }

    public static List<Position> getTranslations(List<Integer> sequence, List<PatternDataStruct> patterns, int sequenceLength) {
        List<Position> result = new ArrayList<>();
        for (PatternDataStruct pattern : patterns) {
            List<Position> positions = pattern.positions;
            int l = 0;
            for (int i = 0; i < positions.size() && i < positions.size() && positions.get(i).index < (sequenceLength / 2); i++) {
                for (int j = i; j < positions.size() && j < positions.size() && positions.get(j).index < sequenceLength / 2; j++) {
                    if (positions.get(i).length == positions.get(j).length) {
                        result.add(positions.get(i));
                    }
                }
            }
        }
        return result;
    }

    public static List<PatternDataStruct> removeRedundent(List<PatternDataStruct> patterns) {
        List<PatternDataStruct> result = new ArrayList<>(patterns);
        for (PatternDataStruct pattern : patterns) {            
            
            for (PatternDataStruct pattern2 : patterns) {
                if (pattern!=pattern2) {
                    if (Collections.indexOfSubList(pattern.element, pattern2.element) != -1 && pattern.frequency == pattern2.frequency) {
                        //System.out.println(pattern2.element + " removed by " + pattern.element);
                        result.remove(pattern2);
                    }
                }
            }
            //int i = 0;
//            for (Position p1 : (List<Position>) pattern.positions) {
//                for (PatternDataStruct pattern2 : patterns) {
//                    if(pattern.frequency != pattern2.frequency) continue;
//                    
//                    int i = 0;
//                    for (Position p2 : (List<Position>) pattern2.positions) {
//                        if (p2.index >= p1.index && p2.length < p1.length) {
//                            i++;
//                        }
//                    }
//                    if (i == pattern2.frequency) {
//                        //System.out.println(pattern2+ " removed by "+pattern);
//                        result.remove(pattern2);
//                    }
//                }
//            }
        }
        return result;
    }
}
