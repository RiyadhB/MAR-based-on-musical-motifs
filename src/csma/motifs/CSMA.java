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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author ASUS
 */
public class CSMA<T> {

    List<T> sequence;
    List<T> visited;

    public List<PatternDataStruct<T>> patterns;
    int minPatternLength = -1;
    int minFrequency = 2;
    int maxGap = 999999; // initialized to infinity

    public CSMA(List<T> sequence, int minPatternLen, int maxPatternLen, int minFrequency, int maxGap, int seqLength) {
        this.sequence = sequence;
        visited = new ArrayList<>();
        patterns = new ArrayList<>();
        this.maxGap = maxGap;
        this.minPatternLength = minPatternLen;
        this.minFrequency = minFrequency;

        Runtime runtime = Runtime.getRuntime();
        int numberOfAvailableProcessors = runtime.availableProcessors();

        //System.out.println("there are: " + numberOfAvailableProcessors + " available processors");
        //compute patterns of size 1
        List<Position> postitionsList;
        PatternDataStruct pattern;
        Position p;
        Map<T, List<Integer>> positions = new HashMap<>();
        for (int i = 0; i < sequence.size(); i++) {
            if (positions.containsKey(sequence.get(i))) {
                positions.get(sequence.get(i)).add(i);
            } else {
                List<Integer> occurences = new ArrayList<>();
                occurences.add(i);
                positions.put(sequence.get(i), occurences);
            }
        }
        //System.out.println("keyset: "+positions.keySet());
        for (T t : positions.keySet()) {
//            System.out.println("t= "+t);
            if (positions.get(t).size() >= minFrequency) {
                postitionsList = new ArrayList<>();
                for (int i : positions.get(t)) {
                    p = new Position(i);
                    postitionsList.add(p);
                }
                pattern = new PatternDataStruct(t, postitionsList.size(), postitionsList);
                patterns.add(pattern);
            }

        }
        //System.out.println("patterns  = "+patterns);
        int i = 0;
        List<PatternDataStruct> freq1 = new ArrayList<>(patterns);

        List<PatternDataStruct> F_K_1 = new ArrayList<>(freq1);
        int l = 1;
        ArrayList<Future<PatternDataStruct>> set;
        ExecutorService pool;
        List<PatternDataStruct> tmp;
        double avoidedComparaisons = 0.0;
        int cpt = 0;
//        for(PatternDataStruct pat: F_K_1)
//        System.out.println("pat= "+pat.toString());
        while (!F_K_1.isEmpty()) {
            List<PatternDataStruct> F_K = new ArrayList<>();
            //System.out.println("for k = " + l + " there are " + F_K_1.size() + " pattern generated");
            //if(l==3) System.out.println("patterns of len 5 = "+F_K_1);
            l++;
            for (i = 0; i < F_K_1.size(); i++) {
                //System.out.println("estimated percentage = "+String.valueOf(i*100.0/patterns.size()));
                set = new ArrayList<>();
                tmp = getSelectablePatterns(((Position) F_K_1.get(i).positions.get(this.minFrequency - 1)).getLastIndex(), freq1);
                //tmp = freq1;
                cpt++;
                avoidedComparaisons += 1 - tmp.size() * 1. / freq1.size();
                //if(l>=4) System.out.println("for "+F_K_1.get(i)+" candiates are : "+tmp);
                //if(tmp.size()<11)System.out.println("tmp.size = "+tmp.size());
                //List<Callable<PatternDataStruct>> callables = new ArrayList<>();
                pool = Executors.newFixedThreadPool(numberOfAvailableProcessors);
                for (int j = 0; j < tmp.size(); j++) {
                    JoinClass joinClass = new JoinClass(F_K_1.get(i), tmp.get(j), maxGap, maxPatternLen, seqLength, minFrequency);
                    Future<PatternDataStruct> future = pool.submit(joinClass);
                    set.add(future);
                }
                while (set.size() < tmp.size());
                //System.out.println("set.size = "+set.size()+" tmp.size = "+tmp.size());
                while (!set.isEmpty()) {
                    for (int k = 0; k < set.size(); k++) {
                        Future<PatternDataStruct> future = set.get(k);
                        try {
                            pattern = future.get();
                            if (pattern != null && pattern.frequency >= minFrequency) {
                                patterns.add(pattern);
                                F_K.add(pattern);
                            }
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(CSMA.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        set.remove(k);
                        k--;

                    }
                }

                pool.shutdown();
                try {
                    pool.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CSMA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println("F_K = "+F_K);
            F_K_1 = F_K;
        }
        //System.out.println("patterns " + patterns);
        if (minPatternLength > 1) {
            patterns.forEach((PatternDataStruct patt) -> {
                Stream filter = patt.positions.stream().filter(pos -> ((Position) pos).length >= minPatternLength);
                // patt.positions.clear();
                patt.positions = (List<Position>) filter.collect(Collectors.toList());

                patt.frequency = patt.positions.size();
            });

            patterns = patterns.stream().filter(patt -> patt.frequency >= minFrequency).collect(Collectors.toList());
        }
    }

    private List<PatternDataStruct> getSelectablePatterns(int candidateLast, List<PatternDataStruct> patterns) {
        List<PatternDataStruct> result = new ArrayList<>();
        for (PatternDataStruct pattern : patterns) {
            Position pos = (Position) pattern.positions.get(pattern.positions.size() - 1);
            if (pos.index >= candidateLast) {
                result.add(pattern);
            }
        }
        return result;
    }

    private List<Integer> positions(T candidate) {
        List<Integer> positions = new ArrayList<>();
        int len = sequence.size();
        for (int i = 0; i < len; i++) {
            if (sequence.get(i).equals(candidate)) {
                positions.add(i);
            }
        }

        return positions;
    }

    private List<PatternDataStruct> removeRepetions(List<PatternDataStruct> data) {
        boolean flag = true;
        List<PatternDataStruct> result = new ArrayList<>();
        for (PatternDataStruct p : data) {
            flag = true;
            for (PatternDataStruct p2 : data) {
                if (p.equals(p2)) {
                    continue;
                }
                if (p2.element.containsAll(p.element) && p2.frequency == p.frequency) {
                    flag = false;
                    //System.out.println(p2+" contains "+p);
                    break;
                }
            }
            if (!result.contains(p) && flag) {
                result.add(p);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (PatternDataStruct p : patterns) {
            result += p.toString() + "\n";
        }
        return result;
    }
}
