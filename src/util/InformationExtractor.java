/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author ASUS
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import codage.PitchCode;
import codage.Rhythm;
import csma.motifs.PatternDataStruct;
import csma.motifs.Position;
import generated.*;
import generated.Note;
import generated.Pitch;
import generated.ScorePartwise;
import generated.ScorePartwise.Part;
import generated.ScorePartwise.Part.Measure;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import main.Main;
//import math.Function;
//import pattern.NonTrivialPatternDetector;
//import pattern.Sequence;
//import plot.graph.Edge;
//import plot.graph.Node;
//import util.SymbolsToNumeric;

/**
 *
 * @author Riyadh
 */
public class InformationExtractor {

    public static int length = 0;
    java.lang.String[] keys = {"pitchStep", "pitchAlter", "pitchOctave", "type", "voice"};
    public Map<Integer, List<PitchCode>> sequence;
    public Map<Integer, List<List<PitchCode>>> voices;
    public Map<java.lang.String, Map<Integer, List<List<PitchCode>>>> instruments;
    public Map<java.lang.String, Map<Integer, List<PitchCode>>> sequences;
//    List<Sequence> sequences;
//    List<List<Edge>> globalEdgesList;

//    public List<List<Edge>> getGlobalEdgesList() {
//        return globalEdgesList;
//    }
    public InformationExtractor(java.lang.String[] informationToExtract, File musicXMLFile) {
//        globalEdgesList = new ArrayList<>();
        try {

            JAXBContext jc = JAXBContext.newInstance("generated");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            //Marshaller marshaller = jc.createMarshaller();

            //unmarshaller.setValidating(true);
            Map<java.lang.String, java.lang.String> dataArray = new HashMap<>();
            //dataArray["data1"]="p";
            java.lang.String data = ""; // c'est ici qu'on va mettre nos données en fomat CSV 
            ScorePartwise scorePartwise = (ScorePartwise) unmarshaller.unmarshal(musicXMLFile);
            //int partsNumber = scorePartwise.getPart().size();
            if (informationToExtract != null) {
                keys = informationToExtract;
            }

            List<List<PitchCode>> measuresIntValues = new ArrayList<>();
            java.lang.String tmp = "";
//            sequences = new ArrayList<>();
            List<Part> parts = scorePartwise.getPart();
            int i = 0;
            instruments = new HashMap<>();
            for (Part part : parts) {
                voices = new HashMap<>();
                List<Measure> measures = part.getMeasure();
                for (Part.Measure measure : measures) {
                    tmp = "";
                    List<Object> noteOrBackupOrForward = measure.getNoteOrBackupOrForward();
                    getPitchSequence(noteOrBackupOrForward, dataArray, i, data, tmp);
                }

                //System.out.println("id = "+((ScorePart) part.getId()).getId()+" voices "+voices);
                if (part.getId() != null) {
                    instruments.put(((ScorePart) part.getId()).getId(), voices);
                } else {
                    instruments.put("id1", voices);
                }
            }
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
//            marshaller.marshal(scorePartwise, musicXMLFile);
            //System.out.println("voices = " + voices);

            sequences = new HashMap<>();
//            System.out.println("data: \n"+data);
            for (java.lang.String key : instruments.keySet()) {
                voices = instruments.get(key);
                sequence = new HashMap<>();
                for (int voice : voices.keySet()) {
                    List<PitchCode> values = new ArrayList<>();
                    measuresIntValues = voices.get(voice);
                    measuresIntValues.forEach(liste -> {
                        values.addAll(liste);
                    });
                    sequence.put(voice, values);
                }
                sequences.put(key, sequence);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(InformationExtractor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private void getPitchSequence(List<Object> noteOrBackupOrForward, Map<java.lang.String, java.lang.String> dataArray, int i, java.lang.String data, java.lang.String tmp) throws NumberFormatException {
        Note note;
        int voice;
        List<List<PitchCode>> measuresIntValues;
        for (Object object : noteOrBackupOrForward) {
            if (object instanceof Note) {
                java.lang.String type = "";
                int wholeDuration = 1;
                note = (Note) object;

                List<JAXBElement<?>> content = note.getContent();
                for (JAXBElement c : content) {

                    if (c.getDeclaredType().getSimpleName().equalsIgnoreCase("rest")) {

                        dataArray.put("pitchStep", "R");
                    }

                    if (c.getDeclaredType().getSimpleName().equalsIgnoreCase("pitch")) {
                        Pitch pitch = (Pitch) c.getValue();

                        dataArray.put("pitchStep", pitch.getStep().value());

                        if (pitch.getAlter() != null) {
                            dataArray.put("pitchAlter", pitch.getAlter().toString());
                        } else {
                            dataArray.put("pitchAlter", 0 + "");
                        }
                        dataArray.put("pitchOctave", pitch.getOctave() + "");

                        dataArray.put("N", i + "");
                        i++;

                    }
                    if (c.getName().toString().equalsIgnoreCase("duration")) {
                        java.lang.String duration = c.getValue() + "";
                        //data += pitch.getStep() + ";" + pitch.getAlter() + ";" + pitch.getOctave();
                        dataArray.put("type", duration);
                        //System.out.println("voice = "+voice);
                    }

                    if (c.getName().toString().equalsIgnoreCase("type")) {
                        NoteType noteType = (NoteType) c.getValue();
                        //data += pitch.getStep() + ";" + pitch.getAlter() + ";" + pitch.getOctave();
                        if (!noteType.getValue().equalsIgnoreCase("null")) {
                            type = noteType.getValue();
                        }

                    }

                    if (c.getName().toString().equalsIgnoreCase("voice")) {
                        voice = Integer.parseInt((java.lang.String) c.getValue());
                        //data += pitch.getStep() + ";" + pitch.getAlter() + ";" + pitch.getOctave();
                        if (voice != 0) {
                            dataArray.put("voice", voice + "");
                        } else {
                            dataArray.put("voice", "1");
                        }
                    }

                }

                // compute wholeDuration 
                if (!dataArray.get("type").equalsIgnoreCase("null")) {
                    wholeDuration = (int) (Double.parseDouble(dataArray.get("type")) / Rhythm.getValue(type));
                    //System.out.println("duration = "+Double.parseDouble(dataArray.get("type")) +", type "+type+" corresponding to "+Rhythm.getValue(type)+" equals to "+wholeDuration);
                }

                //
                for (java.lang.String key : keys) {
                    //System.out.println("key= " + key + "    value= " + dataArray.get(key));
                    if (key.equals("type") && dataArray.get(key).equalsIgnoreCase("null")) {
                        dataArray.put(key, "" + (int) (wholeDuration * Rhythm.getValue(type)));
                        // continue;
                    }
                    data += dataArray.get(key) + ";";
                    tmp += dataArray.get(key) + ";";

                    dataArray.put(key, "null");
                }
                data += "\n";

                measuresIntValues = voices.get(1); // we consider only scores with one voice per instrument
                if (measuresIntValues == null) {
                    measuresIntValues = new ArrayList<>();
                }
                measuresIntValues.add(fromSymbolicToNumeric(tmp, " ", ";"));
                voices.put(1, measuresIntValues);// voice here!!!!!!!!
                tmp = "";
            }
            //System.out.println("voice = "+voice);
        }
    }

    public static void main(java.lang.String[] args) {
        InformationExtractor informationExtractor = new InformationExtractor(null, new File("data/all motifs.xml"));

        System.out.println("sequence = " + informationExtractor.sequence);
    }

    public java.lang.String randomColor() {
        // create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(256 * 256 * 256);

        // format it as hexadecimal string (with hashtag and leading zeros)
        java.lang.String s = java.lang.String.format("#%06x", nextInt);

        // print it
        //System.out.println(s);
        return s;
    }
    List<java.lang.String> colors = new ArrayList<>();

    public void printMotifs(ScorePartwise scorePartwise, List<PatternDataStruct> motifs, java.lang.String partId, int voiceId, java.lang.String type) {
        motifs.sort((m1, m2) -> (m1.element.size() - m2.element.size()));
        int cpt = 1;
//        ScorePartwise scorePartwise = load(musicXMLFile);
        List<Part> parts = scorePartwise.getPart();
        int pId = 0;
        for (Part part : parts) {
            pId++;
            if (partId.equalsIgnoreCase("P" + pId)) {
                break;
            }
        }
        for (PatternDataStruct motif : motifs) {
            cpt++;
            java.lang.String color = randomColor();
            while (colors.contains(color)) {
                color = randomColor();
            }
            int pos = 1;
            for (Position position : (List<Position>) motif.positions) {
                //System.out.println("motif n°"+cpt+" pos = "+position.index);
                if (type.equals("inverted")) {
                    if (position.index > Main.seqLen) {
                        position.index = position.index - Main.seqLen + 1 ;
                    }
                }
                if (type.equals("mirror")) {
                    if (position.index > Main.seqLen) {
                        position.index = position.index - Main.seqLen + 1 ;
                    }
                }

                pos++;
                colorNotes(parts.get(pId-1), voiceId, position.index, position.length, color, type);
            }
        }

        //saving here
//        Work work = new Work();
//        work.setWorkTitle(type);
//        scorePartwise.setWork(work);
//        saving(musicXMLFile, type, scorePartwise);
    }

    
    public void printMotifs(ScorePartwise scorePartwise, List<PatternDataStruct> motifs, int[] borders,  java.lang.String type) {
        motifs.sort((m1, m2) -> (m1.element.size() - m2.element.size()));
        for (PatternDataStruct motif: motifs){
            Collections.sort(motif.positions);
        }
        int cpt = 1;
//        ScorePartwise scorePartwise = load(musicXMLFile);
        List<Part> parts = scorePartwise.getPart();
        int pId = 0;
        Color[] colorsP = new Color[motifs.size()];
        
        for (int i = 1; i <= colorsP.length; i++) {
            //colorsP[i-1] = new Color(i * (int) (Math.random() * 255) * 255 *255 / (colorsP.length+1));
            colorsP[i-1] = new Color(i%3 != 1? (int) (Math.random() * 196 + 60):0 , i%3 != 2 ? (int) (Math.random() * 196 + 60 ):0 , i%3 != 0 ? (int) (Math.random() * 196 + 60 ): 0 );
            
        }
        
        if(type.equals("transposed")){
            for (int i = 0; i < borders.length; i++) {
                //borders[i]-=1;
            }
        }
        
        int motifID = 0 ;
        for (PatternDataStruct motif : motifs) {
            cpt++;
//            java.lang.String color = randomColor();
//            while (colors.contains(color)) {
//                color = randomColor();
//            }
            int pos = 1;
            for (Position position : (List<Position>) motif.positions) {
                
                if (type.equals("inverted")) {
                    if (position.index > Main.seqLen) {
                        position.index = position.index - Main.seqLen + 1 ;
                    }
                }
                if (type.equals("mirror")) {
                    if (position.index > Main.seqLen) {
                        position.index = position.index - Main.seqLen + 1 ;
                    }
                }

                pos++;
                pId = getPartId(position.index, borders);
                
                if(pId > 0 ){
                colorNotes(parts.get(pId), 1, position.index - borders[pId-1], position.length, java.lang.String.format("#%06x", colorsP[motifID].getRGB()), type);
                System.out.println("motif n°"+cpt+" pos = "+java.lang.String.valueOf(position.index- borders[pId-1])+ " color: "+colorsP[motifID]+ " pId: "+pId+" size: "+motif.element.size());
                }else {
                    System.out.println("motif n°"+cpt+" pos = "+java.lang.String.valueOf(position.index)+ " color: "+colorsP[motifID]+ " pId: "+0+" size: "+motif.element.size());
                    colorNotes(parts.get(pId), 1, position.index, position.length, java.lang.String.format("#%06x", colorsP[motifID].getRGB()), type);
                }
                    
            }
            motifID++;
        }

        //saving here
//        Work work = new Work();
//        work.setWorkTitle(type);
//        scorePartwise.setWork(work);
//        saving(musicXMLFile, type, scorePartwise);
    }

    private int getPartId(int index, int[] indexes){
        //System.out.println("indexes. len " + indexes.length);
        for (int i = 0; i < indexes.length; i++) {
            //System.out.println("index = "+index+" index["+i+"]= "+indexes[i]);
            if(index < indexes[i]) return i;
        }
        return -1;
    }
    
    public ScorePartwise load(java.lang.String musicXMLFile) {

        try {
            JAXBContext jc = JAXBContext.newInstance(ScorePartwise.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            InputStream inputStream = new FileInputStream(musicXMLFile);
            ScorePartwise scorePartwise = (ScorePartwise) unmarshaller.unmarshal(inputStream);
            inputStream.close();
            return scorePartwise;
        } catch (IOException ex) {
            Logger.getLogger(InformationExtractor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(InformationExtractor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return null;
    }

    public void colorNotes(Part part, int voiceId, int motifPosition, int motifLength, java.lang.String color, java.lang.String type) {
        //System.out.println("type:"+type);
        int cpt = 0;
        Note note;
        int noteCount = 0;
        int voice = 1;
        List<Measure> measures = part.getMeasure();
        for (Part.Measure measure : measures) {
            //data += measure.getNumber() + ";";
//                    System.out.println("measure n° " + cpt);
            cpt++;
            List<Object> noteOrBackupOrForward = measure.getNoteOrBackupOrForward();
            for (Object object : noteOrBackupOrForward) {
                boolean isColorable = true;
                if (object instanceof Note) {

                    //System.out.println("note count = "+noteCount);
                    note = (Note) object;
                    List<JAXBElement<?>> content = note.getContent();
                    for (JAXBElement c : content) {

                        if (c.getDeclaredType().getSimpleName().equalsIgnoreCase("rest") && !type.equalsIgnoreCase("RhythmicIdentical")) {
                            
                            noteCount++;
//                            continue;
                            isColorable = false;
                        }//else isColorable =true;
//
                        if (c.getDeclaredType().getSimpleName().equalsIgnoreCase("pitch")) {
                            isColorable = true;

                        }
//                        if (c.getName().toString().equalsIgnoreCase("duration")) {
//                            java.lang.String duration = c.getValue() + "";
//                        }

                        if (c.getName().toString().equalsIgnoreCase("voice")) {
                            voice = Integer.parseInt((java.lang.String) c.getValue());
                        }
                    }
                    //System.out.println("note count "+noteCount+" motifPos"+motifPosition+" mot len = "+motifLength);
                    if(voice != voiceId || !isColorable) continue;
                    switch (type) {
                        case "MelodicIdentical":
                            if (noteCount >= motifPosition && noteCount < motifPosition + motifLength && isColorable) {
                                //if(type.equals("identical") && noteCount == motifPosition+motifLength)
                                ((Note) object).setColor(color.toUpperCase());
                            }
                            break;
                        case "RhythmicIdentical":
                            if (noteCount >= motifPosition && noteCount < motifPosition + motifLength && isColorable) {
                                //if(type.equals("identical") && noteCount == motifPosition+motifLength)
                                ((Note) object).setColor(color.toUpperCase());
                            }
                            break;
                        case "PitchIdentical":
                            if (noteCount >= motifPosition && noteCount < motifPosition + motifLength && isColorable) {
                                //if(type.equals("identical") && noteCount == motifPosition+motifLength)
                                ((Note) object).setColor(color.toUpperCase());
                            }
                            break;
                        case "transposed":                            
                            if (noteCount >= motifPosition && noteCount <= motifPosition + motifLength && isColorable) {
                                //if(type.equals("identical") && noteCount == motifPosition+motifLength)
                                ((Note) object).setColor(color.toUpperCase());
                            }
                            break;
                        case "inverted":
                            if ((noteCount >= motifPosition)
                                    && (noteCount < motifPosition + 1 + motifLength) && isColorable) {
                                //if(type.equals("identical") && noteCount == motifPosition+motifLength)
                                ((Note) object).setColor(color.toUpperCase());
                            }
                            break;
                        case "mirrors":
                            
                            if ((noteCount >= motifPosition)
                                    && (noteCount < motifPosition + 1 + motifLength) && isColorable) {
                                //if(type.equals("identical") && noteCount == motifPosition+motifLength)
                                ((Note) object).setColor(color.toUpperCase());
                            }
                            break;

                    }

                    
                    noteCount++;
                }
                //System.out.println("voice = "+voice);

            }
        }

    }

    public void saving(java.lang.String musicXMLFile, java.lang.String type, ScorePartwise scorePartwise) {

        try {
            java.lang.String filename = musicXMLFile.replace(".xml", "") + "colored" + type + ".xml";
            File f = new File(filename);
            if (!f.exists() && !f.isDirectory()) {
                System.out.println("file created");
                Files.copy(new File(musicXMLFile).toPath(), f.toPath());
            }
            JAXBContext jc = JAXBContext.newInstance(ScorePartwise.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            OutputStream outputStream = new FileOutputStream(filename);
            //marshaller.marshal(scorePartwise, System.out);
            
            marshaller.marshal(scorePartwise, outputStream);
            outputStream.close();
        } catch (JAXBException | IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private List<PitchCode> fromSymbolicToNumeric(java.lang.String data, java.lang.String dataDelimeter, java.lang.String innerDataDelimiter) {
        int i;
        java.lang.String[] completeNotes = data.split(dataDelimeter);
        List<PitchCode> noteValues = new ArrayList<>();
        PitchCode p;
        SymbolsToNumeric symbolsToNumeric = new SymbolsToNumeric(null);
        java.lang.String[] symbols;
        i = 0;
        for (java.lang.String cn : completeNotes) {
            if (cn.startsWith("null") || cn.isEmpty()) {
                continue;
            }
            //System.out.println("cn: "+cn);
            symbols = cn.split(innerDataDelimiter);
            //if(symbols.length<3) continue;
            symbolsToNumeric.setSymbols(symbols);
            p = symbolsToNumeric.getPitchValue();
            //p.setInformationType(infromationType);
            //System.out.println("(violin, " + p.getNumericValue() + ", " + p.getDuration() + ")");
            noteValues.add(p);
        }
        return noteValues;
    }

//    Function function;
    private void saveToFile(java.lang.String result, java.lang.String inputtxt) {
        try {
            FileWriter fw = new FileWriter(inputtxt);
            fw.append(result);
            fw.flush();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(InformationExtractor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

//    private int isMirror(Function values) {
//        int result = -1;
//        int pos = -1;
//
//        int len = 0;
//        int tmp;
//        int compteur = 1;
//        System.out.println("signes : " + values.signes);
//        System.out.println("values : " + values.originalData);
//        while ((pos = values.signes.indexOf(0)) > 0) {
//            System.out.println("pos = " + pos);
//            tmp = len;
//            len = 0;
//            compteur = 1;
//            //System.out.println(values.originalData.get(pos-compteur)+" ; "+values.originalData.get(pos+1+compteur));
//            while (pos - compteur > 0 && pos + 1 + compteur < values.originalData.size() && values.originalData.get(pos - compteur).intValue() == values.originalData.get(pos + 1 + compteur).intValue()) {
//                compteur++;
//                len++;
//            }
//            System.out.println(" len = " + len);
//            if (tmp < len) {
//                tmp = len;
//                result = pos;
//            }
//            values.signes.remove(pos);
//        }
//        if (pos <= 0 && len == 0) {
//            return -1;
//        }
//
//        return result;
//    }

    /*
    private List<Function> getMirrorForms(int minLen, int maxLen){
        List<Function> result = new ArrayList<>();
        
        
        return result;
    }*/
}
