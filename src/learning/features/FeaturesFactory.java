/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.features;

import codage.PitchCode;
import csma.motifs.CSMA;
import csma.motifs.PatternDataStruct;
import csma.motifs.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import learning.configuration.Configuration;
import learning.data.Author;
import learning.data.Data;
import learning.data.Partition;
import learning.data.Score;
import main.Main;
import static main.Main.getInversions;
import static main.Main.removeRedundent;
import static main.Main.seqLen;
import mir.score.ScoreMotifs;
import util.Dictionnary;
import util.Function;

/**
 *
 * @author ASUS
 */
public class FeaturesFactory {
    
    Configuration configuration ;
    Data data;
    Set<String> motifs;
    List<ScoreMotifs> scoresMotifs;
    String csvRepresentation = "";

    /**
     * This class compute music motifs on a given database. It generates a CSV representation of the vectorial representation of the music motifs
     * @param configuration
     * @param data 
     */
    public FeaturesFactory(Configuration configuration, Data data) {
        this.configuration = configuration;
        this.data = data;
        motifs = new TreeSet<>();
        scoresMotifs = new ArrayList<>();
        // motifs extraction for each author
        for (Author author: data.getAuthors()){
            for (Score score: author.getScores()){
                System.out.println("authorID: "+author.getName()+ " score: "+score.getScoreID());
                extractMotifs(author.getName(), score);
                //System.out.println("Motifs: "+motifs);
            }            
        }
        // make vectorial representation of motifs
        for (String key: motifs){
            csvRepresentation += key+";";
        }
        csvRepresentation += "\n";

        ScoreMotifs scoreMotifsUnion = Dictionnary.getScoreMotifsUnion(scoresMotifs);
        ScoreMotifs projetion ;
        int i = 0 ;
        for (ScoreMotifs sm : scoresMotifs){
            System.out.println("percentage: "+String.valueOf(i * scoresMotifs.size() / 100));
            projetion = Dictionnary.getProjetion(sm, scoreMotifsUnion);
            csvRepresentation += projetion.getCSVRepresentation(motifs)+" \n ";
            i++;
        }
                
        
        
        
    }
    
    private void extractMotifs(String authorID, Score score){
        List<PitchCode> sequence = score.getSequence();        
        ScoreMotifs scoreMotifs = new ScoreMotifs(authorID, score.getScoreID());
        // compute all kinds of motifs and store them into scoreMotifs
        for (int informationType = -1; informationType < 2; informationType++) {
            PitchCode.informationType = informationType;
            scoreMotifs = motifsExtraction(informationType, sequence, scoreMotifs);
        }
        
        scoresMotifs.add(scoreMotifs);
        
        motifs.addAll(scoreMotifs.getIdenticalsRhythmic().keySet());
        motifs.addAll(scoreMotifs.getIdenticalsMelodic().keySet());
        motifs.addAll(scoreMotifs.getIdenticalsBoth().keySet());
        motifs.addAll(scoreMotifs.getTransposed().keySet());
//        motifs.addAll(scoreMotifs.getInverted().keySet());
//        motifs.addAll(scoreMotifs.getMirror().keySet());
    }

    private ScoreMotifs motifsExtraction(int informationType, List<PitchCode> sequence, ScoreMotifs scoreMotifs) {
        CSMA csma = new CSMA(sequence, configuration.getMinLength(), configuration.getMaxLength(), configuration.getMinFrequency(), 
                configuration.getMaxGap(), -1);

        List<PatternDataStruct> identicals = csma.patterns;
        switch (informationType) {
            case 1:
                scoreMotifs.setIdenticalsMelodic(getSubPresentation(identicals));
                break;
            case -1:
                scoreMotifs.setIdenticalsRhythmic(getSubPresentation(identicals));
                break;
            case 0:
                scoreMotifs.setIdenticalsBoth(getSubPresentation(identicals));
                break;
        }

        if (informationType == 1) {
            List<Integer> melodies = new ArrayList<>();
            for (PitchCode pitchCode : (List<PitchCode>) sequence) {
                melodies.add(Integer.parseInt(pitchCode.toString()));
            }
            Function function = new Function(melodies);
            csma = new CSMA(function.derived, configuration.getMinLength()-1, configuration.getMaxLength()-1, configuration.getMinFrequency(), configuration.getMaxGap(), -1);
          
            List<Position> toBeRemoved = new ArrayList<>();
            for (PatternDataStruct pattern : identicals) {
                toBeRemoved.addAll(pattern.positions.subList(1, pattern.positions.size()));
            }
            List<PatternDataStruct> transposed = new ArrayList<>(csma.patterns);
            for (PatternDataStruct pattern : (List<PatternDataStruct>) transposed) {
                pattern.positions.removeAll(toBeRemoved);
            }
            
            transposed = removeRedundent(transposed);
            scoreMotifs.setTransposed(getSubPresentation(transposed));
        }
        return scoreMotifs;
    }
    
    private Map<String, Integer> getSubPresentation(List<PatternDataStruct> motifs) {
        Map<String, Integer> result = new HashMap();
        for (PatternDataStruct motif : motifs) {
            result.put(motif.element.toString(), (int) motif.frequency);
        }
        return result;

    }
    
}
