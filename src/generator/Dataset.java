/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.reverse;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Dataset {

    public static final int ALPHABET_SIZE = 50;
    public static final int DATABASE_SIZE = 500;

    // on genere au plus un motif par sequence
    public static void insertMotifs(int nbrOfMotifs) {
        BufferedReader bufferedReader = null;
        try {
            String file = "D500I1KT10.txt";
            bufferedReader = new BufferedReader(new FileReader(new File("D:\\dataset\\synthetic\\SPMF\\" + file)));
            List<List<String>> data = new ArrayList<>();
            List<List<String>> dataReversed = new ArrayList<>();
            List<List<String>> dataWithGaps = new ArrayList<>();
            List<String> content;
            int max = 0;
            //int compteur = 0;
            while (bufferedReader.ready()) {
                content = new ArrayList<>();

                String[] split = bufferedReader.readLine().split(" ");
                for (String s : split) {
                    content.add(s);
                }
//                if (compteur < 2) {
//                    content.clear();
//                    for (int i = 130; i < 139; i++) {
//                        content.add("" + i);
//                    }
//                }
                if (max < split.length) {
                    max = split.length; // to compte the longuest sequence length (For us it will be the same)
                }
                data.add(content);// each entry will be a sequence of strings
                dataReversed.add(content);
                dataWithGaps.add(content);
            }
            List<List<String>> motifs = new ArrayList<>();
            List<Integer> blackList = new ArrayList<>();
            double gapSize = 2;

            FileWriter fw = new FileWriter("datasets.txt");

            double v = 1.0;

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("add the v value:");
                v = Double.parseDouble(scanner.next());
                if (v == -1) {
                    break;
                }
                int cpt = 3;
                while (cpt > 0) {

                    //Motifs generation
                    for (int i = 0; i < nbrOfMotifs; i++) {
                        List<String> motif = new ArrayList<>();
                        int motifSize = (int) (Math.random() * (max - gapSize)) + 2; // motif size is randomly computed
                        int last = 0;
                        int newVal = 0;
                        for (int j = 0; j < motifSize; j++) {
                            if (last == ALPHABET_SIZE) {
                                break;
                            }
                            //System.out.println("last = "+last);
                            do {
                                newVal = (int) (Math.random() * (ALPHABET_SIZE - last)) + 1; // the new value to be added to the last chosen value should be >= 1
                            } while ((last + newVal) > ALPHABET_SIZE);
                            last += newVal;
//                    if (blackList.contains(last)) { // if this element is already computed we generate an other
//                        j--;
//                        continue;
//                    }
                            blackList.add(last);
                            motif.add(last + "");
                        }
                        //System.out.println("motif "+i+" generated");
                        //add the generated motif to the global motifs set
                        motifs.add(motif);
                    }

                    List<Integer> frequencies = new ArrayList<>();
                    int availablePlaces = DATABASE_SIZE;
                    // Randomly compute fixe the desired frequency for each motif 
                    for (int i = 0; i < nbrOfMotifs && availablePlaces > 0; i++) {
                        int frequency = (int) (Math.random() * 20) + 5;
                        //if(frequency>DATABASE_SIZE/2)
                        frequencies.add(frequency);
                        availablePlaces -= frequency;// to be sure that we have at most one motif per sequence
                    }

                    List<Integer> visited = new ArrayList<>();
                    List<Motif> motives = new ArrayList<>();
//            double mu    = DATABASE_SIZE/2;
//            double[] sigma = {DATABASE_SIZE/4, DATABASE_SIZE/10, DATABASE_SIZE/20, DATABASE_SIZE/50,DATABASE_SIZE/100};

                    int nonFrequentItem = 130;

                    //Motifs insertion into the database
                    for (int i = 0; i < nbrOfMotifs; i++) {
                        //visited = new ArrayList<>();
                        int freq = 0;
                        Motif motive = new Motif();
                        motive.value = motifs.get(i);
                        double min = DATABASE_SIZE / v;
                        //System.out.println("index is greater than "+min);
                        for (int j = 0; i < frequencies.size() && j < frequencies.get(i) && (visited.size() + frequencies.get(i)) < min; j++) {
                            int index = -1;

                            do {
                                index = (int) (Math.random() * min) + 2; // choose the sequence id

                            } while (visited.contains(index) && freq < DATABASE_SIZE / v || index > min);
                            if (index >= DATABASE_SIZE) {
                                continue;
                            }
                            visited.add(index); // to be sure that this sequence id wasn't chosen before
                            freq++; // the frequency is updated
                            List<String> datum = new ArrayList<>(data.get(index));
                            List<String> datumReversed = new ArrayList<>(data.get(index));
                            List<String> datumGapped = new ArrayList<>(data.get(index));
                            List<String> motif = motifs.get(i);
                            List<String> motifReversed = new ArrayList<>(motifs.get(i));
                            boolean invert = Math.random() > 0.5;

                            int gap = 0; //=(int) (Math.random() * gapSize);

                            //if(invert) 
                            reverse(motifReversed);
//                            if (motif.size() < 8 && freq < 10) {
//                                motif.add(String.valueOf(nonFrequentItem++));
//                            }
                            System.out.println("motif: " + motif + " frequency: " + freq + " index: " + index);
                            for (int k = 0; k < motif.size(); k++) {
                                datum.set(k, motif.get(k)); // modify the motif.size() first elements of the sequence
                                //datumReversed.set(k, motifReversed.get(k));
                                boolean isGap = Math.random() > 0.5;
                                if (isGap && gap < gapSize) {
                                    gap++;
                                }
                                //datumGapped.set(k+gap, motif.get(k));
                            }
                            data.set(index, datum); // update the global dataset
                            //dataReversed.set(index, datumReversed);
                            dataWithGaps.set(index, datumGapped);
                        }
                        motive.frequency = freq;
                        motives.add(motive);
                    }
                    OutputStream out = new FileOutputStream("motifsSIGMA1-5" + cpt + ".ser");
                    ObjectOutputStream osw = new ObjectOutputStream(out);
                    osw.writeObject(motives);
//            LocalDateTime timePoint = LocalDateTime.now();     // The current date and time
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
                    String text = "DS" + cpt;
                    // saving of the new dataset
                    String file1 = "DefaultSIGMA1-" + v + text + file;
                    String file2 = "WithReversementSIGMA1-" + v + text + file;
                    String file3 = "WithGapsSIGMA1-" + v + text + file;
                    saving(data, file1);
                    saving(dataReversed, file2);
                    saving(dataWithGaps, file3);

                    fw.append(file1 + "\n");
                    fw.flush();
                    cpt--;

                }
            }
            fw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void saving(List<List<String>> data, String file) throws IOException {

        String dataToText = "";
        for (List<String> datum : data) {
            for (String s : datum) {
                dataToText += s + " ";
            }
            dataToText += "\n";
        }
        //file saving
        //file = "P" + file;
        FileWriter fw = new FileWriter(new File("SPMF\\" + file));
        fw.append(dataToText);
        fw.close();
    }

    public static void main(String[] args) {
        insertMotifs(100);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("datasets.txt")));
            List<String> files = new ArrayList();
            while (bufferedReader.ready()) {
                files.add(bufferedReader.readLine());
            }
            bufferedReader.close();

            int i = 1;
            for (String file : files) {
                System.out.println("percentage: " + i * 100. / files.size());
                i++;
                bufferedReader = new BufferedReader(new FileReader(new File("SPMF\\" + file)));
                String content = new String();
                while (bufferedReader.ready()) {
                    content += bufferedReader.readLine() + "\n";
                }
                content = content.replace("-1 -2 ", "");

                FileWriter fileWriter = new FileWriter(new File("IBM\\" + file));
                fileWriter.append(content);
                fileWriter.close();

                content = content.replace("\n", "");
                FileWriter fileWriter2 = new FileWriter(new File("CSMA\\" + file));
                fileWriter2.append(content);
                fileWriter2.close();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
