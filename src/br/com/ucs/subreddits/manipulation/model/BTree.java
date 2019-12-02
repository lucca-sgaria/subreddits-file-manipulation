package br.com.ucs.subreddits.manipulation.model;

public class BTree {

    private Node root;

    public BTree()
    {
        root = new Node(null);

    }

    public Node search(Node root, int key)
    {
        int i = 0;

        while(i < root.getCount() && key > root.getKey(i))
        {
            i++;
        }

        if(i <= root.getCount() && key == root.getKey(i))
        {


            return root;
        }

        if(root.getLeaf())
        {

            return null ;

        }
        else
        {

            return search(root.getChild(i),key);

        }
    }

    private void split(Node x, int i, Node y)
    {
        Node z = new Node(null);
        z.setLeaf(y.getLeaf());
        z.setCount(1);

        for(int j = 0; j < 1; j++)
        {
            z.setKey(y.getKey(j+1), j);

        }
        if(!y.getLeaf())
        {
            for(int k = 0; k < 2; k++)
            {
                z.setChild(y.getChild(k + 2), k);
            }
        }

        y.setCount(1);

        for(int j = x.getCount() ; j> i ; j--)
        {

            x.setChild(x.getChild(j), j+1);

        }
        x.setChild(z, i+1);

        for(int j = x.getCount(); j> i; j--)
        {
            x.setKey(x.getKey(j), j+1);
        }
        x.setKey(y.getKey(1), i);
        y.setKey(0,1);

        for(int j = 0; j < 1; j++)
        {
            y.setKey(0, j+2);
        }


        x.setCount(x.getCount() + 1);
    }

    private void nonfullInsert(Node x, int key)
    {
        int i = x.getCount();

        if(x.getLeaf())
        {
            while(i >= 1 && key < x.getKey(i-1))
            {
                x.setKey(x.getKey(i-1), i);
                i--;
            }

            x.setKey(key, i);
            x.setCount(x.getCount() + 1);

        } else {
            int j = 0;
            while(j < x.getCount()  && key > x.getKey(j))
            {
                j++;
            }
            if(x.getChild(j).getCount() == 3)
            {
                this.split(x,j,x.getChild(j));
                if(key > x.getKey(j))
                {
                    j++;
                }
            }

            nonfullInsert(x.getChild(j),key);
        }
    }

    public void insert(BTree t, int key)
    {
        Node r = t.root;
        if(r.getCount() == 3)
        {
            Node s = new Node(null);

            t.root = s;
            s.setLeaf(false);
            s.setCount(0);
            s.setChild(r, 0);

            split(s,0,r);

            nonfullInsert(s, key);
        }
        else {
            nonfullInsert(r,key);
        }
    }

    public void print(Node n)
    {
        for(int i = 0; i < n.getCount(); i++)
        {
            System.out.print(n.getValue(i)+" " );
        }

        if(!n.getLeaf())
        {

            for(int j = 0; j <= n.getCount()  ; j++)
            {
                if(n.getChild(j) != null)
                {
                    System.out.println();
                    print(n.getChild(j));
                }
            }
        }
    }

    public void delete(BTree t, int key)
    {

        Node temp = new Node(null);

        temp = search(t.root,key);

        if(temp.getLeaf() && temp.getCount() > 1)
        {
            int i = 0;

            while( key > temp.getValue(i))
            {
                i++;
            }
            for(int j = i; j < 2; j++)
            {
                temp.setKey(temp.getValue(j+1),j);
            }
            temp.setCount(temp.getCount() -1);

        }
        else
        {
            System.out.println("This node is either not a leaf or has less than order - 1 keys.");
        }
    }

    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root = root;
    }

}
