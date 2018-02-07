/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Data implements Serializable{
    
    private List<Author> authors;
    private static final long serialVersionUID = 42L;

    public Data() {
        authors = new ArrayList<>();
    }
    
    public Data(Data data) {
        authors = new ArrayList<>(data.getAuthors());
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }    
    
}
