package br.com.ucs.subreddits.manipulation.model;

import java.io.File;
import java.io.Writer;

public class MainFile {
    private File file;
    private String path;
    private Writer writer;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
