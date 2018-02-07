/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author ASUS
 */
public class DataManger {

    private Data database;
    private Data train;
    private Data test;
    private int percentageSplit;

    /**
     * This class split the data into a train dataset and test dataset following the given percentage. The percentage should be between 0 and 100. 
     * @param percentageSplit the percentage of the train dataset
     * @param database the database on which the subsets will be generated
     */
    public DataManger(int percentageSplit, Data database) {
        this.percentageSplit = percentageSplit;
        this.database = database;
        train = new Data();
        test = new Data();
        
        
        setTrainTest();
    }

    private void setTrainTest() {
        Set<Integer> indices;
        List<Score> trainScores;
        List<Score> testScores;
        for (Author author : database.getAuthors()) {
            indices = new TreeSet<>();
            int authorScoresSize = author.getScores().size();
            int indicesCount =  authorScoresSize * percentageSplit / 100;
            trainScores = new ArrayList<>();
            
            while (indicesCount > 0) {
                int randomScore = (int) (Math.random() * author.getScores().size());
                if (indices.contains(randomScore)) continue;
                trainScores.add(author.getScores().get(randomScore));
                indices.add(randomScore);
                indicesCount--;
            }
            
            testScores = new ArrayList<>();
            for (int i = 0; i < authorScoresSize; i++) {
                if(indices.contains(i)) continue;
                testScores.add(author.getScores().get(i));
            }
            
            Author trainAuthor = new Author(author.getName(), trainScores);
            train.getAuthors().add(trainAuthor);
            
            Author testAuthor = new Author(author.getName(), testScores);
            test.getAuthors().add(testAuthor);
        }

    }

    public Data getTrain() {
        return train;
    }

    public Data getTest() {
        return test;
    }
}
