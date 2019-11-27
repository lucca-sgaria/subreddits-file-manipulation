package br.com.ucs.subreddits.manipulation.service;

import br.com.ucs.subreddits.manipulation.model.BTree;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BTreeService {

    public BTree objBtree;

    public BTreeService() {}

    public BTreeService() {
        this.objBtree = new BTree();
    }

    public void insert(int value) {
        this.objBtree(this.objBtree, value);
    }

    public void print() {
        this.objBtree.print(this.objBtree.getRoot());
    }
}