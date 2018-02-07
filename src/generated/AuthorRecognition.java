/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riyadh
 */
public class AuthorRecognition {
    List<Author> authors;
    
    public AuthorRecognition(){
        try {
            authors = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(""));
            String line = bufferedReader.readLine();
            Author currentAuthor;
            while (line != null){
                String[] elements = line.split(" ");
                if(elements[0].startsWith("#"))
                    currentAuthor = new Author(elements[0].substring(1));
                else {
                    
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AuthorRecognition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuthorRecognition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        
    }
}
