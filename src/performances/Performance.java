/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performances;

import csma.motifs.CSMA;
import csma.motifs.PatternDataStruct;
import generator.Motif;
import java.awt.AWTEventMulticaster;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static liu.pattern.FindRepeatingPatterns.findRP;
import liu.pattern.Pattern;

/**
 *
 * @author ASUS
 */
public class Performance {

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            int cpt = 10;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("datasets.txt")));
            while (bufferedReader.ready()) {
                String file = bufferedReader.readLine();
                br = new BufferedReader(new FileReader(new File("CSMA/" + file)));
                //System.out.println("cpt = " + cpt);
                String line = br.readLine();
                String[] split = line.split(" ");
                List<String> sequence = new ArrayList<>();
                for (String s : split) {
                    sequence.add(s);
                }
//            List<String> sequence = new ArrayList<>();
//            String s = "ABCDEFGHABCDEFGHIJABC";
//            for (char c : s.toCharArray()) {
//                sequence.add(String.valueOf(c));
//            }
                long time = System.currentTimeMillis();
                CSMA csma = new CSMA(sequence, 1, 10, 2, 0, -1);
                time = System.currentTimeMillis() - time;
                System.out.println("time for "+file+" = "+String.valueOf(time/1000.));
                double freqs1 = freqs(csma.patterns);
                //System.out.println("csma patterns = "+csma.patterns);

                //System.out.println("csma generated " + csma.patterns.size() + " patterns in " + String.valueOf(time / 1000.) + " secondes freqs = "+ freqs);
                //perfs(csma.patterns, cpt);
//                time = System.currentTimeMillis();
//                List<Pattern> patterns = findRP(sequence);
//                time = System.currentTimeMillis() - time;
//                double freqs2 = freqsLiu(patterns);
                //System.out.println("Liu patterns "+patterns);
                //System.out.println("Liu generated " + patterns.size() + " patterns in " + String.valueOf(time / 1000.) + " secondes freqs = "+freqs);
                //perfs(patterns, cpt);
//                System.out.println(csma.patterns.size() + "," + freqs1 + "," + patterns.size() + "," + freqs2);
//            List<Motif> spamMotifs = getSPAMMotifs();
//            //System.out.println("CM-SPADE found "+spamMotifs+" motifs ");
//                System.out.println("CM-SPADE");
//            perfs(spamMotifs, cpt);
                //cpt--;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Performance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Performance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Performance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static double freqs(List<PatternDataStruct> patterns) {
        double result = 0.0;
        for (PatternDataStruct p : patterns) {
            result += p.frequency;
        }
        return result;
    }

    public static double freqsLiu(List<Pattern> patterns) {
        double result = 0.0;
        for (Pattern p : patterns) {
            result += p.frequency;
        }
        return result;
    }

    public static void perfs(List patterns, int cpt) {
        List<Motif> motifsRef = null;
        try (
                InputStream file = new FileInputStream("motifs" + cpt + ".ser");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);) {
            //deserialize the List
            motifsRef = (List<Motif>) input.readObject();

        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int tp = 0;

        motifsRef = motifsRef.stream().filter(predicate -> !predicate.value.isEmpty() && predicate.frequency > 1).collect(Collectors.toList());
        //System.out.println(motifsRef);
        int total = motifsRef.size();
        System.out.println("there are " + total + " motifs");
        if (patterns.get(0) instanceof Pattern) {
            List<Pattern> motifs = (List<Pattern>) patterns;
            for (Pattern m : motifs) {
                Motif m1 = new Motif();
                m1.value = m.value;
                m1.frequency = m.frequency;
                if (motifsRef.contains(m1)) {
                    motifsRef.remove(m1);
                    tp++;
                }
            }
        } else if (patterns.get(0) instanceof PatternDataStruct) {
            List<PatternDataStruct> motifs = (List<PatternDataStruct>) patterns;
            for (PatternDataStruct m : motifs) {
                Motif m1 = new Motif();
                m1.value = m.element;
                m1.frequency = (int) m.frequency;
                if (motifsRef.contains(m1)) {
                    motifsRef.remove(m1);
                    tp++;
                }
            }
        }
        System.out.println("remaining motifs:" + motifsRef);
        System.out.println("tp = " + String.valueOf(tp * 1. / total));
    }

    public static List<Motif> getSPAMMotifs() {
        List<Motif> result = new ArrayList<>();
        BufferedReader br;
        double totalFreqs = 0;
        try {
            br = new BufferedReader(new FileReader(new File("output.txt")));
            while (br.ready()) {
                String line = br.readLine();
                line = line.replace(" -1", "");
                line = line.replace("-2", "");
                String[] split = line.split(" ");
                List<String> values = new ArrayList<>();
                boolean isSupp = false;

                int supp = 0;
                for (String s : split) {
                    if (s.startsWith("#")) {
                        isSupp = true;
                        continue;
                    }
                    if (isSupp) {
                        supp = Integer.parseInt(s);
                    } else {
                        values.add(s);
                    }
                }
                Motif motif = new Motif();
                motif.value = values;
                motif.frequency = supp;
                totalFreqs += supp;
                result.add(motif);
            }

            System.out.println(result.size() + " " + totalFreqs);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Performance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Performance.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
