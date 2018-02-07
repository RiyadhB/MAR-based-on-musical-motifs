/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.configuration;

/**
 *
 * @author ASUS
 */
public class Configuration {
    
    private int minFrequency ;
    private int minLength;
    private int maxLength;
    private int maxGap;

    public Configuration(int minFrequency, int minLength, int maxLength, int maxGap) {
        this.minFrequency = minFrequency;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.maxGap = maxGap;
    }

    public Configuration() {
        this.minFrequency = 2;
        this.minLength = 1;
        this.maxLength = Integer.MAX_VALUE;
        this.maxGap = Integer.MAX_VALUE;
    }

    public int getMinFrequency() {
        return minFrequency;
    }

    public void setMinFrequency(int minFrequency) {
        this.minFrequency = minFrequency;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxGap() {
        return maxGap;
    }

    public void setMaxGap(int maxGap) {
        this.maxGap = maxGap;
    }

    @Override
    public String toString() {
        return "Configuration{" + "minFrequency=" + minFrequency + ", minLength=" + minLength + ", maxLength=" + maxLength + ", maxGap=" + maxGap + '}';
    }
    
    
}
