package br.com.ucs.subreddits.manipulation.service;

import br.com.ucs.subreddits.manipulation.model.MainFile;
import br.com.ucs.subreddits.manipulation.model.Subreddit;
import br.com.ucs.subreddits.manipulation.util.FileUtil;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MainFileServiceBean {
    private MainFile mainFile;

    public void createAndPopulateFile(String path, List<Subreddit> list) {
        System.out.println("createAndPopulateFile()");

        File file = new File(path);
        mainFile = new MainFile();
        mainFile.setFile(file);
        mainFile.setPath(path);

        BufferedWriter writer = FileUtil.getWriter(file);
        try {
            for (Subreddit subreddit : list) {
                String toInsert = subreddit.toInsertString();

                writer.write(toInsert);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("createAndPopulateFile() - end");
    }

    public void printMainFileComplete() {
        System.out.println("printMainFileComplete()");
        try {
            RandomAccessFile rn = new RandomAccessFile(mainFile.getFile(), "r");
            double records = rn.length() / 84;
            System.out.println(records);

            byte[] b = new byte[90];
            for (int i = 0; i < records; i++) {
                rn.seek(i * 84);
                rn.read(b, 0, 85);
                String r = new String(b, "UTF-8");
                System.out.println(r);
            }
            rn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("printMainFileComplete() - end");
    }

    @SuppressWarnings("Duplicates")
    public void printMainFileInterval(int pos,int posFinal) {
        System.out.println("printMainFileComplete()");

        int differential = posFinal - pos;
        double records = differential / 84;


        try {
            RandomAccessFile rn = new RandomAccessFile(mainFile.getFile(), "r");
            System.out.println(records);

            byte[] b = new byte[90];
            for (int i = pos; i < posFinal; i+=84) {
                rn.seek(i);
                rn.read(b, 0, 85);
                String r = new String(b, "UTF-8");
                System.out.println(r);
            }
            rn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("printMainFileComplete() - end");
    }

    @SuppressWarnings("Duplicates")
    public String binarySearchById(String key) {
        System.out.println("binarySearchById()");

        try {
            RandomAccessFile rn = new RandomAccessFile(mainFile.getFile(), "r");
            long records = rn.length()/84;
            System.out.println("rn size " + records);

            long start = 0;
            long limit = records-1;
            long half;

            byte[] line = new byte[90];
            while(start <= limit) {
                half = (start+limit)/2;

                rn.seek(half*84);
                rn.read(line, 0, 85);

                byte[] b = Arrays.copyOfRange(line, 0, 10);

                String string = new String(b, "UTF-8");
                string = string.trim();
                long id = Long.valueOf(string);

                if(key.equals(string)) {
                    System.out.println("binarySearchById() - [FOUND]");
                    return new String(line, "UTF-8");
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

    public MainFile getMainFile() {
        return mainFile;
    }

    @SuppressWarnings("Duplicates")
    public void findByAdress(int pos) throws IOException {
        byte[] b = new byte[90];
        RandomAccessFile rn = new RandomAccessFile(mainFile.getFile(), "r");
        System.out.println(rn.length());
        rn.seek(pos);
        rn.read(b, 0, 85);
        String r = new String(b, "UTF-8");
        System.out.println(r);
        rn.close();
    }



}
