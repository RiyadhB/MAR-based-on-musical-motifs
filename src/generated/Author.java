/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Riyadh
 */
public class Author {
    String name;
    Map<String,Double> rules;
    
    public Author(String name){
        this.name = name;
        rules = new HashMap<>();
    }
    
    public void addRule(String rule, double lift){
        rules.put(rule, lift);
    }
    
    
}
