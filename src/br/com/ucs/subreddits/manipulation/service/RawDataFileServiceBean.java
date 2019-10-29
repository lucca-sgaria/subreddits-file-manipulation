package br.com.ucs.subreddits.manipulation.service;

import br.com.ucs.subreddits.manipulation.model.RawDataFile;
import br.com.ucs.subreddits.manipulation.model.Subreddit;
import br.com.ucs.subreddits.manipulation.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class RawDataFileServiceBean {

    private RawDataFile rawDataFile = null;

    public void createAndSetRawDataFile(String path) {
        System.out.println("createAndSetRawDataFile()");

        File file = new File(path);
        BufferedReader reader = FileUtil.getReader(file);

        rawDataFile = new RawDataFile();
        rawDataFile.setFile(file);
        rawDataFile.setReader(reader);

        System.out.println("createAndSetRawDataFile() - end");
    }

    public void updateFileSubreddits() {
        System.out.println("updateFileSubreddits()");

        BufferedReader reader = rawDataFile.getReader();
        rawDataFile.setSubredditList(new ArrayList<>());

        try {
            String line = reader.readLine();
            while (line != null) {
                Subreddit sub = new Subreddit();
                String[] columns = line.split(";");

                for (int i = 0; i < columns.length; i++) {
                    String[] data = columns[i].split("=");

                    switch (i) {
                        case 0:
                            sub.setId(Long.valueOf(data[1]));
                            break;
                        case 1:
                            sub.setIdReddit(data[1]);
                            break;
                        case 2:
                            sub.setDisplayName(data[1]);
                            break;
                        case 3:
                            sub.setCreatedUtc(Double.valueOf(data[1]));
                            sub.setCreated(new Date(sub.getCreatedUtc().longValue() * 1000));
                            break;
                        case 4:
                            sub.setSubscribers(Long.valueOf(data[1]));
                            break;
                        case 5:
                            sub.setLanguage(data[1]);
                            break;
                        case 6:
                            sub.setPath(data[1]);
                            break;
                        case 7:
                            sub.setUrl(data[1]);
                            break;
                    }
                }

                line = reader.readLine();

//                System.out.println("[ADDED] " + sub.getId() + " - " + sub.getDisplayName() + " - " + sub.getCreated().toString());
                rawDataFile.addSub(sub);
            }

            reader.close();

            System.out.println("[UPDATED LIST]");
        } catch (IOException e) {

        }
        System.out.println("updateFileSubreddits() - end");
    }

    public void printRawComplete() {
        System.out.println("printRawComplete()");
        rawDataFile.setReader(FileUtil.getReader(rawDataFile.getFile()));
        BufferedReader reader = rawDataFile.getReader();
        try {
            String readLine = reader.readLine();
            while (readLine != null) {
                System.out.println(readLine);
                readLine = reader.readLine();
            }
        } catch (IOException e) {
        }

        System.out.println("printRawComplete() - end");
    }

    public void printSubredditList() {
        System.out.println("printSubredditList()");
        rawDataFile.getSubredditList().forEach(System.out::println);
        System.out.println("printSubredditList() - end");
    }

    public RawDataFile getRawDataFile() {
        return rawDataFile;
    }
}
