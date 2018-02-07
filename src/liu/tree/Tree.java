/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liu.tree;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData, ArrayList children) {
        root = new Node<T>();
        root.data = rootData;
        root.children = children;
    }

    
}