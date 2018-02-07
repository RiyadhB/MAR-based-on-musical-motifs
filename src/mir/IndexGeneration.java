/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mir;

import codage.PitchCode;
import csma.motifs.CSMA;
import csma.motifs.PatternDataStruct;
import csma.motifs.Position;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import learning.data.Author;
import learning.data.Score;
import main.Main;
import static main.Main.getInversions;
import static main.Main.informationExtractor;
import static main.Main.removeRedundent;
import static main.Main.seqLen;
import static main.Main.variation;
import mir.score.ScoreMotifs;
import util.Dictionnary;
import util.Function;
import util.InformationExtractor;

/**
 *
 * @author ASUS
 */
public class IndexGeneration {

    private List<ScoreMotifs> motifsDB;

    public IndexGeneration() {
        String path = "D:\\dataset\\manual dataset";
        File file = new File(path);
        File[] subFiles = file.listFiles();
        System.setProperty("http.agent", "Mozilla/5.0 (X11; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
        List<ScoreMotifs> motifsDB = new ArrayList<>();
        //reading all files
        for (File folder : subFiles) {
            List<Double> features = new ArrayList<>();
            if (folder.getName().equalsIgnoreCase("Haydn")) {
                continue;
            }
            //if(folder.getName().equalsIgnoreCase("Monteverdi")) continue;
            //if(folder.getName().equalsIgnoreCase("Palestrina")) continue;
            File[] scoresFiles = folder.listFiles();
            for (File scoreFile : scoresFiles) {
                // for each file we look for its parts  (instruments)
                System.out.println("score : " + scoreFile.getPath());
                InformationExtractor informationExtractor = new InformationExtractor(null, scoreFile);
                Map<Integer, List<PitchCode>> sequence = new HashMap<>(informationExtractor.sequence);
                Map<java.lang.String, Map<Integer, List<PitchCode>>> instruments = informationExtractor.sequences;
                //switch case + list for each kind of motifs

                for (String part : instruments.keySet()) {
                    //for each part we generate a sequence per voice

                    //System.out.println("instrument " + part);
                    sequence = instruments.get(part);
                    for (int voice : sequence.keySet()) {
                        if (voice > 1) {
                            continue;
                        }
                        //System.out.println("sequence = " + sequence);
                        ScoreMotifs scoreMotifs = new ScoreMotifs(folder.getName(), scoreFile.getName(), part, voice + "");
                        for (int informationType = -1; informationType < 2; informationType++) {
                            PitchCode.informationType = informationType;
                            //scoreMotifs = motifsExtraction(informationType, sequence, scoreMotifs);
                        }
                        motifsDB.add(scoreMotifs);
                        Map<String, Integer> identicalsMelodic = scoreMotifs.getIdenticalsMelodic();
                        System.out.println("authorId: " + scoreMotifs.getAuthorId() + " scoreId: " + scoreMotifs.getScoreId() + " partId: " + scoreMotifs.getPartId() + " IDM= " + identicalsMelodic);
//                        System.out.println(scoreMotifs.toString());
//                        System.out.println("csvHeader: "+scoreMotifs.getCSVHeaders());
//                        System.out.println("csvRep: "+scoreMotifs.getCSVRepresentation());
                    }
                }

            }
        }

        ScoreMotifs[] motifsTab = new ScoreMotifs[motifsDB.size()];
        for (int i = 0; i < motifsTab.length; i++) {
            motifsTab[i] = motifsDB.get(i);
        }
        ScoreMotifs globalMotifsDB = Dictionnary.getScoreMotifsUnion(motifsTab);
        //List<ScoreMotifs> updatedMotifsDB = new ArrayList<>();

        String csvRep = globalMotifsDB.getCSVHeaders() + "\n";
        System.out.println("header: " + csvRep);
        for (ScoreMotifs scoreMotifs : motifsDB) {
            ScoreMotifs projection = Dictionnary.getProjetion(scoreMotifs, globalMotifsDB);
            //updatedMotifsDB.add(projection);
            //System.out.println("projection.getCSVRepresentation() = "+projection.getCSVRepresentation());
            csvRep += projection.getCSVRepresentation(globalMotifsDB) + "\n";
        }

        //System.out.println("csv: \n"+csvRep);
        saveFile(csvRep, "motifs.csv");
    }

    public IndexGeneration(String authorID, Score score) {
        motifsDB = new ArrayList<>();
        List<PitchCode> sequence = score.getSequence();
        ScoreMotifs scoreMotifs = new ScoreMotifs(authorID, score.getScoreID());
        // compute all kinds of motifs and store them into scoreMotifs
        for (int informationType = -1; informationType < 2; informationType++) {
            PitchCode.informationType = informationType;
            scoreMotifs = motifsExtraction(informationType, sequence, scoreMotifs);
        }
        motifsDB.add(scoreMotifs);
        
        Map<String, Integer> identicalsMelodic = scoreMotifs.getIdenticalsMelodic();
        System.out.println("authorId: " + scoreMotifs.getAuthorId() + " scoreId: " + scoreMotifs.getScoreId() + " partId: " + scoreMotifs.getPartId() + " IDM= " + identicalsMelodic);

        ScoreMotifs[] motifsTab = new ScoreMotifs[motifsDB.size()];
        for (int i = 0; i < motifsTab.length; i++) {
            motifsTab[i] = motifsDB.get(i);
        }
        ScoreMotifs globalMotifsDB = Dictionnary.getScoreMotifsUnion(motifsTab);
        //List<ScoreMotifs> updatedMotifsDB = new ArrayList<>();

        String csvRep = globalMotifsDB.getCSVHeaders() + "\n";
        System.out.println("header: " + csvRep);
        for (ScoreMotifs sm : motifsDB) {
            ScoreMotifs projection = Dictionnary.getProjetion(sm, globalMotifsDB);
            //updatedMotifsDB.add(projection);
            //System.out.println("projection.getCSVRepresentation() = "+projection.getCSVRepresentation());
            csvRep += projection.getCSVRepresentation(globalMotifsDB) + "\n";
        }

        //System.out.println("csv: \n"+csvRep);
        saveFile(csvRep, "motifs.csv");
    }

    public static void saveFile(String data, String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.append(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(IndexGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ScoreMotifs motifsExtraction(int informationType,List<PitchCode> sequence, ScoreMotifs scoreMotifs) throws NumberFormatException {
        return variation(sequence, informationType, scoreMotifs); // later replace 1 by scoreMotifs.getVoiceId() and make related updates

    }

    public static void main(String[] args) {
        IndexGeneration indexGeneration = new IndexGeneration();
    }

    public Map<String, Integer> getSubPresentation(List<PatternDataStruct> motifs) {
        Map<String, Integer> result = new HashMap();
        for (PatternDataStruct motif : motifs) {
            result.put(motif.element.toString(), (int) motif.frequency);
        }
        return result;

    }

    public ScoreMotifs variation(List sequence, int informationType, ScoreMotifs scoreMotifs) {
        //System.out.println("sequence length: " + sequence.size());

        int minFrequency = 5;
        CSMA csma = new CSMA(sequence, 4, 20, minFrequency, 0, -1);

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

        System.out.println("#motifs identical= " + identicals.size());

        //List result = new ArrayList<>();
        if (informationType == 1) {
            List<Integer> melodies = new ArrayList<>();
            for (PitchCode pitchCode : (List<PitchCode>) sequence) {
                melodies.add(Integer.parseInt(pitchCode.toString()));
            }
            Function function = new Function(melodies);
//            if (informationType == -1) {
//                csma = new CSMA(function.ration(melodies), 3, 20, minFrequency, 0, -1);
//            } else {
            csma = new CSMA(function.derived, 3, 20, minFrequency, 0, -1);
            //}
            List<Position> toBeRemoved = new ArrayList<>();
            for (PatternDataStruct pattern : identicals) {
                toBeRemoved.addAll(pattern.positions.subList(1, pattern.positions.size()));
            }
            List<PatternDataStruct> transposed = new ArrayList<>(csma.patterns);
            for (PatternDataStruct pattern : (List<PatternDataStruct>) transposed) {
                pattern.positions.removeAll(toBeRemoved);
            }
            //System.out.println("derived sequence: " + function.derived);
            transposed = removeRedundent(transposed);
            //System.out.println("# transposed motifs = " + transposed.size());
            //System.out.println("transposed motif: " + transposed);
            scoreMotifs.setTransposed(getSubPresentation(transposed));
//            csma = new CSMA(function.signes, 3, 20, 2, 0, -1);
//            System.out.println("# signes of derived motifs = " + csma.patterns.size());
//            if (informationType == -1) {
//                melodies = function.merge(function.ration(sequence), function.oppositeD(function.ration(sequence)));
//            } else {
            melodies = function.merge(function.derived, function.opposite(function.derived));
//            }
            csma = new CSMA(melodies, 3, 20, minFrequency, 0, -1);
            //System.out.println("sequence: " + melodies);

            //List translations = getTranslations(melodies, csma.patterns, melodies.size());
            List inversions = getInversions(function.originalData, csma.patterns, melodies.size());
            inversions = removeRedundent(inversions);

            scoreMotifs.setInverted(getSubPresentation(inversions));
            //System.out.println("# inversed motifs = " + inversions.size());
            //System.out.println("inversion: " + inversions);
            //melodies = function.merge(function.derived, function.inverse(function.opposite(function.derived)));
            //System.out.println("miroir sequence : " + melodies);
            seqLen = function.originalData.size();

//            if (informationType == -1) {
//                melodies = function.merge(function.ration(sequence), function.inverse(function.oppositeD(function.ration(sequence))));
//            } else {
            melodies = function.merge(function.derived, function.inverse(function.opposite(function.derived)));
            //}
            //System.out.println("melodies : "+melodies);
            csma = new CSMA(melodies, 3, 200, minFrequency, 0, -1);
            //System.out.println("csma patterns "+csma.patterns);
            List mirrors = Main.getMirrors(melodies, csma.patterns, melodies.size());
            mirrors = removeRedundent(mirrors);

            scoreMotifs.setMirror(getSubPresentation(mirrors));
            //informationExtractor.printMotifs(file, mirrors, informationType, part, voice, "mirror");
            //System.out.println("# mirror motifs = " + mirrors);
            //System.out.println("there are " + identicals.size() + " identical " + transposed.size() + " transposed, " + inversions.size() / 2 + " inversion, and " + mirrors.size() + " mirror");
            //System.out.println("identical " + identicals + "\n" + "transposed " + transposed + "\ninversions " + inversions + "\nmirror: " + mirrors);

//            result.add(transposed.size());
//            result.add(inversions.size());
//            result.add(mirrors.size());
        }
        return scoreMotifs;
    }

}
