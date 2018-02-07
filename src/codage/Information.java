/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codage;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Information implements Serializable{
    
    boolean [] flags;
    
    public Information(int nbrOfBits){
        flags = new boolean[nbrOfBits];
    }
    
    public Information(boolean[] flags){
        this.flags = flags;
    }
    
    boolean [] getFlags(){
        return flags;
    }
    
    public static int getDecimalValue(boolean [] bits){
        int result = 0;
        int power = bits.length-1;
        for (boolean bit:bits) {
            result += bit?Math.pow(2, power):0;
            power--;                  
        }
        
        return result;
    }
    
    public static boolean[] merge(boolean[] ... lists){
        int size = 0;
        for (boolean[] list:lists) {
           size += list.length; 
        }
        boolean[] result = new boolean[size];
        size = 0;
        for (boolean[] list:lists) {
            for(boolean b:list)
                result[size++] = b;
        }
        
        return result;
    }
    
    public boolean[] getBinaryValue(int dec){
        String binaryString = "";
        while(dec!=0){
            binaryString=dec%2+binaryString;
            dec = dec/2;
        }
        //System.out.println("dec = "+dec + " binary string= "+binaryString);
        boolean[] result = new boolean[flags.length];
        int j = result.length- binaryString.length();
        for(int i= j ; i<result.length;i++){
            result[i] = binaryString.charAt(i-j)>'0';
        }
        return result;
    }
    
    @Override
    public String toString(){
        String result = "";
        for (boolean b:flags)
            result = b?"1":"0" + result;
        return result;
    }
    
    public static void main(String[] args) {
        boolean[] tab;
        tab = (new Information(4)).getBinaryValue(4);
        for(boolean b:tab){
            System.out.print(b+" ");
        }
        int val = Information.getDecimalValue(tab);
        System.out.println("val = "+val);
    }
    
   
        
}
