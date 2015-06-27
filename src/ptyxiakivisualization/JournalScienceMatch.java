package ptyxiakivisualization;

import java.util.ArrayList;
import java.util.HashMap;

public class JournalScienceMatch {

    private String title;
    // private String categoryScience;
    
    private ArrayList<String> sciencesList;
    
    private HashMap<String, ArrayList<String>> scienceFieldMap;
    private HashMap<String, ArrayList<String>> FieldSubFieldMap;
    private HashMap<String, ArrayList<String>> FieldSubSubFieldMap;

    public JournalScienceMatch(String title, ArrayList<String> sciencesList, HashMap<String, ArrayList<String>> scienceFieldMap, HashMap<String, ArrayList<String>> FieldSubFieldMap, HashMap<String, ArrayList<String>> FieldSubSubFieldMap) {
        this.title = title;
        this.sciencesList = sciencesList;
        this.scienceFieldMap = scienceFieldMap;
        this.FieldSubFieldMap = FieldSubFieldMap;
        this.FieldSubSubFieldMap = FieldSubSubFieldMap;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setFieldSubFieldMap(HashMap<String, ArrayList<String>> FieldSubFieldMap) {
        this.FieldSubFieldMap = FieldSubFieldMap;
    }

    public HashMap<String, ArrayList<String>> getFieldSubFieldMap() {
        return FieldSubFieldMap;
    }

    public HashMap<String, ArrayList<String>> getFieldSubSubFieldMap() {
        return FieldSubSubFieldMap;
    }

    public HashMap<String, ArrayList<String>> getScienceFieldMap() {
        return scienceFieldMap;
    }

    public void setFieldSubSubFieldMap(HashMap<String, ArrayList<String>> FieldSubSubFieldMap) {
        this.FieldSubSubFieldMap = FieldSubSubFieldMap;
    }

    public void setScienceFieldMap(HashMap<String, ArrayList<String>> scienceFieldMap) {
        this.scienceFieldMap = scienceFieldMap;
    }    

    public void setSciencesList(ArrayList<String> sciencesList) {
        this.sciencesList = sciencesList;
    }

    public ArrayList<String> getSciencesList() {
        return sciencesList;
    }
}
