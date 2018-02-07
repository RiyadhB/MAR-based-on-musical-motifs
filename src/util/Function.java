/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import codage.PitchCode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riyadh
 */
public class Function {
    public List<Integer> originalData;
    public List<Integer> derived;
    public List<Integer> signes;
    
    public Function(List<Integer> values) {
        originalData = new ArrayList<>(values);
        
        derived = originalData;
        signes = new ArrayList<>();
        derived = derive();
        computeSignes();
    }
    
    public List<Integer> derive(){
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < derived.size()-1; i++) {
            result.add(derived.get(i+1)-derived.get(i));
        }
        return result;
    }
    
    public void computeSignes(){
        signes.clear();
        for (double value: derived){
            if(value>0) signes.add(1);
            if(value==0) signes.add(0);
            if(value<0) signes.add(-1);            
        }
        System.out.println("data length = "+derived.size()+" signes=" +signes.size());
    }
    
    public List inverse(List list){
        int size = list.size();
        List result = new ArrayList<>(size);
        for (int i = size-1; i >=0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
    
    public List<Double> ration(List<Integer> list){
        int size = list.size();
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < size-1; i++) {
            result.add(list.get(i+1)*1./list.get(i));
        }
        return result;
    }
    
    public List merge(List list1, List list2){
        
        List result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }
    
    public List<Integer> opposite(List<Integer> list1){
        
        List<Integer> result = new ArrayList<>();
        for(int i: list1) result.add(i*-1);
        return result;
    }
    
    public List<Double> oppositeD(List<Double> list1){
        
        List<Double> result = new ArrayList<>();
        for(double i: list1) result.add(i*-1);
        return result;
    }
    
    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            test.add((int) (Math.random()*100));
        }
        Function f = new Function(test);
        System.out.println("f: "+f.originalData);
        System.out.println("f inversed: "+f.inverse(f.originalData));
        System.out.println("f opposite: "+f.opposite(f.derived));
        System.out.println("f merged: "+f.merge(f.originalData,f.originalData));
        System.out.println("derive: "+f.derived);
        System.out.println("signes: "+f.signes);
        System.out.println("ration: "+f.ration(f.derived));
    }
}
