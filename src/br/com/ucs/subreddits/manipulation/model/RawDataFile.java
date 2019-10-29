package br.com.ucs.subreddits.manipulation.model;


import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RawDataFile {
    private BufferedReader reader;
    private File file;
    private List<Subreddit> subredditList = new ArrayList<>();


    public void addSub(Subreddit sub) {
        subredditList.add(sub);
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Subreddit> getSubredditList() {
        return subredditList;
    }

    public void setSubredditList(List<Subreddit> subredditList) {
        this.subredditList = subredditList;
    }
}
