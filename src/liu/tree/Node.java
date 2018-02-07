/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liu.tree;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class Node<T> {
        public T data;
        public List<Node<T>> parents;
        public List<Node<T>> children;
        
        
    }
