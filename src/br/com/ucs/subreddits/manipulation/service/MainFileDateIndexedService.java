package br.com.ucs.subreddits.manipulation.service;

import br.com.ucs.subreddits.manipulation.model.MainFile;
import br.com.ucs.subreddits.manipulation.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainFileDateIndexedService {
    private MainFile mainFile;
    private File indexedFile;

    public MainFileDateIndexedService(MainFile mainFile) {
        this.mainFile = mainFile;
    }

    @SuppressWarnings("Duplicates")
    public void createAndPopulateDateFile(String path) {
        System.out.println("createAndPopulateDateFile()");
        try {

            indexedFile = new File(path);
            BufferedWriter writer = FileUtil.getWriter(indexedFile);

            RandomAccessFile rn = new RandomAccessFile(mainFile.getFile(), "r");
            double records = rn.length() / 84;
            System.out.println("rn " + rn.length());


            byte[] b = new byte[90];
            String actualDate = "";
            for (int i = 0; i < records; i++) {
                rn.seek((i * 84) + 55);
                rn.read(b, 0, 10);

                String line = new String(b, "UTF-8").trim();
                if (!line.equals(actualDate)) {
                    long ll = i * 84;
                    actualDate = line;
                    line = stringToFixedSize(line, 10) + stringToFixedSize(String.valueOf(ll), 10);
                    writer.write(line + "\n");
                }

            }
            rn.close();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("createAndPopulateDateFile() - end");

    }

    @SuppressWarnings("Duplicates")
    public String binarySearchByData(Date key) {
        System.out.println("binarySearchByData()");


        try {
            RandomAccessFile rn = new RandomAccessFile(indexedFile, "r");
            long records = rn.length() / 21;
            System.out.println("rn size " + records);

            long start = 0;
            long limit = records - 1;
            long half;

            byte[] line = new byte[90];
            while (start <= limit) {
                half = (start + limit) / 2;

                rn.seek(half * 21);
                rn.read(line, 0, 21);

                byte[] b = Arrays.copyOfRange(line, 0, 10);

                String string = new String(b, "UTF-8").trim();
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(string);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = dateFormat.format(key);

                if (format.equals(string)) {

                    System.out.println("binarySearchByData() - [FOUND]");
                    byte[] addressByte = Arrays.copyOfRange(line, 11, 20);
                    String addressString = new String(addressByte, "UTF-8");
                    System.out.println("binarySearchById() - [addressString ] - " + addressString);

                    byte[] line2 = new byte[90];
                    rn.seek((half * 21) + 21);
                    rn.read(line2, 0, 21);
                    byte[] address2 = Arrays.copyOfRange(line2, 11, 20);
                    String address2String = new String(address2, "UTF-8");

                    return addressString.trim() + ";" + address2String.trim();

                } else if (key.after(date1)) {
                    limit = half - 1;
                } else {
                    start = half + 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("binarySearchByData() - [NOT FOUND]");
        return "";
    }

    private String stringToFixedSize(String string, int width) {
        return String.format("%1$" + width + "s", string);
    }
}
