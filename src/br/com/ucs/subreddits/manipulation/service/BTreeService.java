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

    public BTreeService() {
        this.objBtree = new BTree();
    }

    public void insert(File file)
    {
        try
        {
            RandomAccessFile rn = new RandomAccessFile(file, "r");
            double records = rn.length() / 21;
            System.out.println(records);

            byte[] byteLine = new byte[90];
            for (int i = 0; i < records; i++)
            {
                rn.seek(i * 21);
                rn.read(byteLine, 0, 21);
                String strLine = new String(byteLine, "UTF-8");

                int id = Integer.parseInt(strLine.subSequence(11, 12).toString().trim());

                this.objBtree.insert(this.objBtree, id);

            }

        }
        catch(Exception exc)
        {
//            e.printStackTrace();
        }
        finally
        {
//            rn.close();
        }
    }

    public void print()
    {

        System.out.println("===============================================");
//        this.objBtree.print(this.objTree.root);
    }
}
