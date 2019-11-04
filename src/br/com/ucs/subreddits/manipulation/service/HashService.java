package br.com.ucs.subreddits.manipulation.service;

import br.com.ucs.subreddits.manipulation.model.DateLoc;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HashService {

    private HashMap<Integer, List<DateLoc>> map = new HashMap<>();

    public int hash(int ano,int mes) {
        int soma = ano+ mes;
        return soma % ano;
    }

    public void createMap(File file) {
        try {
            RandomAccessFile rn = new RandomAccessFile(file, "r");
            double records = rn.length() / 21;
            System.out.println(records);

            byte[] byteLine = new byte[90];
            for (int i = 0; i < records; i++) {
                rn.seek(i * 21);
                rn.read(byteLine, 0, 21);
                String strLine = new String(byteLine, "UTF-8");

                String strDate = strLine.subSequence(0, 10).toString();
                String strLoc = strLine.subSequence(10, 21).toString().trim();

                String year = strDate.trim().subSequence(0, 4).toString();
                String month = strDate.trim().subSequence(5, 7).toString();

                int hash = hash(Integer.valueOf(year), Integer.valueOf(month));

                List<DateLoc> dateLocs = map.get(hash);
                if(dateLocs == null) {
                    List<DateLoc> list = new ArrayList<>();
                    list.add(new DateLoc(strDate.trim(),Long.valueOf(strLoc)));
                    map.put(hash, new ArrayList<>());
                } else {
                    dateLocs.add(new DateLoc(strDate.trim(),Long.valueOf(strLoc)));
                }

            }
            rn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMap() {
        Set<Integer> integers = map.keySet();
        for (Integer key : integers) {
            System.out.println("===============================================");
            System.out.println("Hash = " + key);
            List<DateLoc> dateLocs = map.get(key);
            dateLocs.forEach(System.out::println);
        }
    }



}
