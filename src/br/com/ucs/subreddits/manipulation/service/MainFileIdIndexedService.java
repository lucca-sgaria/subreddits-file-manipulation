package br.com.ucs.subreddits.manipulation.service;

import br.com.ucs.subreddits.manipulation.model.MainFile;
import br.com.ucs.subreddits.manipulation.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class MainFileIdIndexedService {
    private MainFile mainFile;
    private File indexedFile;

    public MainFileIdIndexedService(MainFile mainFile) {
        this.mainFile = mainFile;
    }

    @SuppressWarnings("Duplicates")
    public void createAndPopulateIdFile(String path) {
        System.out.println("createAndPopulateIdFile()");
        try {

            indexedFile = new File(path);
            BufferedWriter writer = FileUtil.getWriter(indexedFile);

            RandomAccessFile rn = new RandomAccessFile(mainFile.getFile(), "r");
            double records = rn.length() / 84;

            byte[] b = new byte[90];
            for (int i = 0; i < records; i++) {
                rn.seek(i * 84);
                rn.read(b, 0, 10);
                String line = new String(b, "UTF-8").trim();

                long ll = i*84;
                line = stringToFixedSize(line,10) + stringToFixedSize(String.valueOf(ll),10);

                writer.write(line + "\n");
            }
            rn.close();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("createAndPopulateIdFile() - end");

    }

    @SuppressWarnings("Duplicates")
    public String binarySearchById(String key) {
        System.out.println("binarySearchById()");

        try {
            RandomAccessFile rn = new RandomAccessFile(indexedFile, "r");
            long records = rn.length()/21;
            System.out.println("rn size " + records);

            long start = 0;
            long limit = records-1;
            long half;

            byte[] line = new byte[90];
            while(start <= limit) {
                half = (start+limit)/2;

                rn.seek(half*21);
                rn.read(line, 0, 21);

                byte[] b = Arrays.copyOfRange(line, 0, 10);

                String string = new String(b, "UTF-8");
                string = string.trim();
                long id = Long.valueOf(string);

                if(key.equals(string)) {
                    System.out.println("binarySearchById() - [FOUND]");

                    byte[] address = new byte[90];
                    rn.read(address, 11, 20);
                    String addressString = new String(address, "UTF-8").trim();

                    System.out.println("binarySearchById() - [addressString ] - " + addressString);
                    return addressString;
                } else if(Long.valueOf(key) < id) {
                    limit = half -1;
                } else {
                    start = half+1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("binarySearchById() - [NOT FOUND]");
        return "";
    }

    private String stringToFixedSize(String string, int width) {
        return String.format("%1$" + width + "s", string);
    }
}
