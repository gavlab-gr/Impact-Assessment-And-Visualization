package ptyxiakivisualization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLFILES {

    //fields for JournalScienceMatch.xml
    private String title;
    private HashMap<String, ArrayList<String>> scienceFieldMap;
    private HashMap<String, ArrayList<String>> FieldSubFieldMap;
    private HashMap<String, ArrayList<String>> FieldSubSubFieldMap;
    private ArrayList<String> sciencesList = new ArrayList<>();

    private final ArrayList<JournalScienceMatch> journalScienceMatchArray = new ArrayList<>();

    //fields for papers.xml
    //private String nameJournal;
    private ArrayList<JournalScienceMatch> authorsList = new ArrayList<>();
    private ArrayList<Papers> papersList = new ArrayList<>();

    private HashSet<String> ListNameAuthors = new HashSet<>();

    public HashSet<String> getListNameAuthors() {
        return ListNameAuthors;
    }

    public ArrayList<JournalScienceMatch> getJournalScienceMatchArray() {
        return journalScienceMatchArray;
    }

    public ArrayList<Papers> getPapersList() {
        return papersList;
    }

    public void ReadJournalScienceMatchXML() {
        try {
            //Βήμα 1: Δημιουργία Parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //Βήμα 2: Parsing
            Document doc = dBuilder.parse("JournalScienceMatch.xml");
            //Βήμα 3: Ανάγνωση κόμβων
            //Διαβάσμα του root κόμβου
            //Element rootElement = doc.getDocumentElement();
            //Παίρνουμε όλα τα elements <journal>
            NodeList nList = doc.getElementsByTagName("journal");

            //Βήμα 4: Διάσχιση child
            String science, field, subField, subsubField;
            for (int n = 0; n < nList.getLength(); n++) {
                Node nNode = nList.item(n);
                NodeList childs = nNode.getChildNodes();
                sciencesList = new ArrayList<>();
                scienceFieldMap = new HashMap<>();
                FieldSubSubFieldMap = new HashMap<>();
                FieldSubFieldMap = new HashMap<>();
                for (int i = 0; i < childs.getLength(); i++) {
                    Node child = childs.item(i);
                    Element e;

                    if (child instanceof Element) {
                        e = (Element) child;
                        if (e.getNodeName().trim().equals("title")) {

                            title = e.getTextContent().trim();
                            //System.out.println("title:" + title);
                        }
                        if (e.getNodeName().trim().equals("science")) {
                            science = e.getAttribute("category").trim();
                            //System.out.println("science:" + science);
                            NodeList childsScience = e.getChildNodes();
                            ArrayList<String> fields = new ArrayList<>();
                            //--------node field-------------------

                            for (int k = 0; k < childsScience.getLength(); k++) {
                                Node childScience = childsScience.item(k);//field
                                if (childScience instanceof Element) {
                                    Element e1 = (Element) childScience;
                                    field = e1.getAttribute("category").trim();
                                    fields.add(field);
                                    //System.out.println("childOfScience:" + e1.getAttribute("category"));
                                    NodeList childsFields = e1.getChildNodes();//ta paidia tou field. dld ta subfields
                                    ArrayList<String> subFields = new ArrayList<>();

                                    for (int a = 0; a < childsFields.getLength(); a++) {//subField of field
                                        Node childSubField = childsFields.item(a);//subField
                                        if (childSubField instanceof Element) {
                                            Element eSubField = (Element) childSubField;
                                            subField = eSubField.getAttribute("category").trim();
                                            subFields.add(subField);//add subField
                                            // System.out.println("subField:" + eSubField.getAttribute("category"));

                                            NodeList childsSubFields = eSubField.getChildNodes();//ta paidia tou subField. dld ta subsubfields tou subField
                                            ArrayList<String> subsubFields = new ArrayList<>();

                                            for (int j = 0; j < childsSubFields.getLength(); j++) {
                                                Node childSubsubField = childsSubFields.item(j);

                                                if (childSubsubField instanceof Element) {
                                                    Element eSubSubField = (Element) childSubsubField;
                                                    subsubField = eSubSubField.getAttribute("category").trim();
                                                    subsubFields.add(subsubField);//add subField
                                                    // System.out.println("subsubField:" + subsubField);

                                                }
                                            }
                                           // System.out.println("subsubFields="+subsubFields);
                                            FieldSubSubFieldMap.put(subField, subsubFields);
                                        }
                                    }
                                   // System.out.println("subFields="+subFields);
                                    FieldSubFieldMap.put(field, subFields);
                                }
                            }
                            scienceFieldMap.put(science, fields);
//                          System.out.println("science="+science);
//                          System.out.println("fields="+fields);
                            sciencesList.add(science);
                        }
                    }
                }
                //System.out.println();
                JournalScienceMatch j = new JournalScienceMatch(title, sciencesList, scienceFieldMap, FieldSubFieldMap, FieldSubSubFieldMap);
                journalScienceMatchArray.add(j);
            }
            //System.out.println(journalScienceMatchArray.size());
        } catch (ParserConfigurationException | SAXException | IOException | DOMException s) {
            System.out.println(s.toString());
        }
    }

    //reading of paper.xml
    public void ReadPapersXML() {
        try {
            //Βήμα 1: Δημιουργία Parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //Βήμα 2: Parsing
            Document doc = dBuilder.parse("papers.xml");
            //Βήμα 3: Ανάγνωση κόμβων
            //Διαβάσμα του root κόμβου
            //Element rootElement = doc.getDocumentElement();
            //Παίρνουμε όλα τα elements <journal>
            NodeList nList = doc.getElementsByTagName("paper");
            String nameAuthor, titleOfPaper = "",nameJournal="";
            for (int i = 0; i < nList.getLength(); i++) {
                Node paper = nList.item(i);
                NodeList childsOfPaper = paper.getChildNodes();
                ArrayList<String> tempAuthors = new ArrayList<>();
                for (int j = 0; j < childsOfPaper.getLength(); j++) {
                    Node child = childsOfPaper.item(j);
                    if (child instanceof Element) {
                        Element e = (Element) child;
                        if (e.getNodeName().trim().equals("journal")) {
                            nameJournal = e.getTextContent().trim();
                            // System.out.println("Journal:" + nameJournal);
                        }
                        if (e.getNodeName().trim().equals("author")) {
                            nameAuthor = e.getTextContent().trim().toUpperCase();
                            if (!nameAuthor.equals("")) {
                                ListNameAuthors.add(nameAuthor);
                                tempAuthors.add(nameAuthor);
                            }
                            // System.out.println("author:" + nameAuthor);
                        }
                        if (e.getNodeName().trim().equals("title")) {
                            titleOfPaper = e.getTextContent().trim();

                            // System.out.println("titleOfPaper:" +titleOfPaper );
                        }
                    }
                }
                Papers p = new Papers(nameJournal, titleOfPaper, tempAuthors);
                papersList.add(p);
            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException s) {
            System.out.println(s.toString());
        }
    }

}
