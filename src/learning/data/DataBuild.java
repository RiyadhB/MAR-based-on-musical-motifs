/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.data;

import codage.PitchCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.InformationExtractor;

/**
 *
 * @author ASUS
 */
public class DataBuild {

    public static void main(String[] args) {
        String path = "D:\\dataset\\manual dataset";
        File file = new File(path);
        File[] subFiles = file.listFiles();
        System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
        Data database = new Data();
        //reading all files
        for (File folder : subFiles) {
            if (folder.getName().equalsIgnoreCase("Haydn")) {
                continue;
            }
            //if(folder.getName().equalsIgnoreCase("Monteverdi")) continue;
            //if(folder.getName().equalsIgnoreCase("Palestrina")) continue;
            List<Score> scores = new ArrayList<>();
            File[] scoresFiles = folder.listFiles();
            for (File scoreFile : scoresFiles) {

                // for each file we look for its parts  (instruments)
                System.out.println("score : " + scoreFile.getPath());
                InformationExtractor informationExtractor = new InformationExtractor(null, scoreFile);
                Map<java.lang.String, Map<Integer, List<PitchCode>>> instruments = informationExtractor.sequences;
                int[] sequencesBorder = new int[instruments.size()];

                // convert the set of sequences into one sequence
                int i = 0;
                List<Partition> partitions = new ArrayList<>();
                for (String part : instruments.keySet()) {
                    List<PitchCode> pitchSequence = new ArrayList<>();
                    System.out.println("instrument " + part);
                    Map<Integer, List<PitchCode>> sequence = instruments.get(part);
                    //System.out.println("sequence of part "+part+ " : "+sequence);
                    pitchSequence.addAll(sequence.get(1));
                    partitions.add(new Partition(pitchSequence));
                }
                scores.add(new Score(partitions, scoreFile.getName()));
                
            }
            database.getAuthors().add(new Author(folder.getName(), scores));
        }
        
        System.out.println("sequences computed");

        DataManger globalDataManger = new DataManger(80, database);
        Data globalTrain = globalDataManger.getTrain();
        Data globalTest = globalDataManger.getTest();
        System.out.println("global testSet computed");
        
        DataManger localDataManager = new DataManger(80, globalTrain);
        Data localTrain = localDataManager.getTrain();
        Data localTest = localDataManager.getTest();
        System.out.println("new subsets computed");
        System.out.println("saving ... ");
        save(globalTest, "globalTest.ser");
        save(localTrain, "localTrain.ser");
        save(localTest, "localTest.ser");

    }

    public static void save(Data data, String name) {
        FileOutputStream fout;
        try {
            fout = new FileOutputStream("SerialsData/"+name);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(data);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataBuild.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataBuild.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Data load(String filename){
        FileInputStream fin;
        Data data = null;
        try {
            fin = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            data = (Data) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataBuild.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataBuild.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBuild.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

}
