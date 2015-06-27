package ptyxiakivisualization;

import java.util.ArrayList;

public class Papers {

    private String NameJournal;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    private ArrayList<String> authorsList;

    public Papers(String NameJournal, String title, ArrayList<String> authorsList) {
        this.NameJournal = NameJournal;
        this.title = title;
        this.authorsList = authorsList;
    }

    public void setAuthorsList(ArrayList<String> authorsList) {
        this.authorsList = authorsList;
    }

    public ArrayList<String> getAuthorsList() {
        return authorsList;
    }

    public void setNameJournal(String NameJournal) {
        this.NameJournal = NameJournal;
    }

    public String getNameJournal() {
        return NameJournal;
    }

}
