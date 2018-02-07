/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.features;

import java.util.ArrayList;
import java.util.List;
import learning.configuration.Configuration;
import learning.data.Data;
import learning.data.DataBuild;
import mir.IndexGeneration;

/**
 *
 * @author ASUS
 */
public class MakeFeatures {
    
    public static void main(String[] args) {
        
        Data localTrain = DataBuild.load("SerialsData/localTrain.ser");
        Data localTest = DataBuild.load("SerialsData/localTest.ser");
        Data globalTest = DataBuild.load("SerialsData/globalTest.ser");
        
        
        Configuration configuration = new Configuration();
        int[] frequencies = {2};//, 5, 10, 20};
        int[] minLengths = {7};//, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] maxGaps = {0};//, 1, 2, 3};
        FeaturesFactory featuresFactory = null;
        for (int freq: frequencies){
            configuration.setMinFrequency(freq);
            for (int minL: minLengths){
                configuration.setMinLength(minL);
                for (int maxGap: maxGaps){
                    configuration.setMaxGap(maxGap);
                    featuresFactory = new FeaturesFactory(configuration, localTrain);
                    String csvRepresentation = featuresFactory.csvRepresentation;
                    IndexGeneration.saveFile(csvRepresentation, "confs/"+configuration.toString()+".csv");
                }
            }
        }
    }
}
