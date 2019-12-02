package br.com.ucs.subreddits.manipulation.model;

import br.com.ucs.subreddits.manipulation.constants.RecordWidths;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Subreddit implements Serializable{
	private static final long serialVersionUID = 6056453798648924933L;
	
	private Long id;
    private String idReddit;
    private String displayName;
    private Date created;
    private Double createdUtc;
    private Long subscribers;
    private String language;
    private String path;
    private String url;
    private String date;

    public Subreddit(Long id, String idReddit, String displayName, Date created,
                     Double createdUtc, Long subscribers, String language,
                     String path, String url) {
        this.id = id;
        this.idReddit = idReddit;
        this.displayName = displayName;
        this.created = created;
        this.createdUtc = createdUtc;
        this.subscribers = subscribers;
        this.language = language;
        this.path = path;
        this.url = url;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        date= dateFormat.format(created);
    }

    public Subreddit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdReddit() {
        return idReddit;
    }

    public void setIdReddit(String idReddit) {
        this.idReddit = idReddit;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Double getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Double createdUtc) {
        this.createdUtc = createdUtc;
    }

    public Long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Long subscribers) {
        this.subscribers = subscribers;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Subreddit{" +
                "id=" + id +
                ", idReddit=" + idReddit +
                ", displayName='" + displayName + '\'' +
                ", created=" + created +
                ", createdUtc=" + createdUtc +
                ", subscribers=" + subscribers +
                ", language='" + language + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String toInsertString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String insertString = "";

        insertString += stringToFixedSize(id.toString(), RecordWidths.ID);
        insertString += stringToFixedSize(displayName, RecordWidths.NAME);
        insertString += stringToFixedSize(idReddit, RecordWidths.IDREDDIT);
        insertString += stringToFixedSize(dateFormat.format(created), RecordWidths.DATA);
        insertString += stringToFixedSize(subscribers.toString(), RecordWidths.SUBS);
        insertString += stringToFixedSize(language, RecordWidths.LANGUAGE);

        insertString += "\n";

        return insertString;
    }

    private String stringToFixedSize(String string, int width) {
        return String.format("%1$" + width + "s", string);
    }
}
