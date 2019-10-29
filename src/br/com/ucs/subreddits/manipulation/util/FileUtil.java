package br.com.ucs.subreddits.manipulation.util;

import java.io.*;

public class FileUtil {

    public static BufferedReader getReader(File file)  {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("[FILE NOT FOUND]" + file.getPath());
            return null;
        }
        Reader reader = new InputStreamReader(inputStream);
        return new BufferedReader(reader);
    }

    public static BufferedWriter getWriter(File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer writer = new OutputStreamWriter(out);
        return new BufferedWriter(writer);
    }
}
