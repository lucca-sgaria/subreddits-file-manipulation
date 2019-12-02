package br.com.ucs.subreddits.manipulation.model;

public class Node {

    private int count;
    private int key[];
    private Node child[];
    private boolean leaf;
    private Node parent;

    public Node(){}

    public Node(Node parent)
    {

        this.parent = parent;
        this.key = new int[1];
        this.child = new Node[2];
        this.leaf = true;
        this.count = 0;
    }

    public int getValue(int index)
    {
        return key[index];
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getKey(int i) {
        return key[i];
    }

    public void setKey(int key, int i) {
        this.key[i] = key;
    }

    public Node getChild(int i) {
        return child[i];
    }

    public void setChild(Node child, int i) {
        this.child[i] = child;
    }


    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean getLeaf() {
        return this.leaf;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

}
