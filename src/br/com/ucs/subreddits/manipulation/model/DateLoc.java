package br.com.ucs.subreddits.manipulation.model;

public class DateLoc {
    private String date;
    private long loc;

    public DateLoc(String date, long loc) {
        this.date = date;
        this.loc = loc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getLoc() {
        return loc;
    }

    public void setLoc(long loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "date= " + date + " ; endereço=" + loc;
    }
}
