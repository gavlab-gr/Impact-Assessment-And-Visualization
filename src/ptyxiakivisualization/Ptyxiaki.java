package ptyxiakivisualization;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;

public class Ptyxiaki extends javax.swing.JFrame {

    private final static HashMap<ArrayList<String>, JournalScienceMatch> authorJournal = new HashMap<>(); //String[]:pinakas 2 thesewn.stin prwti thesi exei ton author kai stin 2h exei ton titlo tou paper.  JournalScienceMatch einai antikeimeno
    private static ArrayList<JournalScienceMatch> journalObjects;
    private static ArrayList<Papers> paperObject;
    private static HashMap<String, Double> authorScienceFreq;
    private static HashSet<String> ListNameAuthors;
    private static HashMap<ArrayList<String>, Double> persentageLines;

    public Ptyxiaki() {
        initComponents();

        ReadXMLFILES read = new ReadXMLFILES();
        read.ReadJournalScienceMatchXML();
        read.ReadPapersXML();
        journalObjects = read.getJournalScienceMatchArray();
        paperObject = read.getPapersList();
        ListNameAuthors = read.getListNameAuthors();
        displayJournalNames();
        displayAuthors();
        findAuthorJournals();

    }

    //display authors in combobox
    public static void displayAuthors() {
        List list = new ArrayList(ListNameAuthors);
        Collections.sort(list);//taksinomimena ola ta onomata twn syggrafewn   
        Iterator iterSet = list.iterator();
        while (iterSet.hasNext()) {
            Ptyxiaki.listAuthors.addItem(iterSet.next());
        }
    }

    //display journalNames in Form
    public static void displayJournalNames() {
        int i = 1;
        for (JournalScienceMatch j : journalObjects) {//gia kathe antikeimeno journal
            {
                journals.addItem(" " + i + ")" + "  " + j.getTitle());
                journals.addItem("\n");
                i++;
            }
        }
    }

    public static void findPercentageForLines(String author) {
        persentageLines = new HashMap<>();
        Set mapSet = (Set) authorJournal.entrySet();
        Iterator it = mapSet.iterator();
        ArrayList<String> key;
        String keyScience;
        JournalScienceMatch j;
        HashMap<String, ArrayList<String>> scienceFields;

        ArrayList<String> fields;

        HashMap<String, Integer> sciencesFreq = new HashMap<>();

        HashMap<ArrayList<String>, Integer> scienceFieldsFreq = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            key = (ArrayList) mapEntry.getKey();//periexei (author,title,uniqueNumber)
            if (key.get(0).trim().equals(author)) {
                j = (JournalScienceMatch) mapEntry.getValue();
                // System.out.println("journal=" + j.getTitle());
                scienceFields = j.getScienceFieldMap();
                Set mapSetScienceFilds = (Set) scienceFields.entrySet();
                Iterator itScField = mapSetScienceFilds.iterator();

                while (itScField.hasNext()) {
                    ArrayList<String> temp = new ArrayList<>();
                    Map.Entry mapEntryScF = (Map.Entry) itScField.next();
                    keyScience = (String) mapEntryScF.getKey();//periexei string
                    //System.out.println("science="+keyScience);
                    temp.add(keyScience);//science
                    fields = (ArrayList) mapEntryScF.getValue();

                    for (String s : fields) {
                        //System.out.println("field="+s);
                        temp.add(s);
                    }
                    //System.out.println();
                    if (scienceFieldsFreq.containsKey(temp)) {
                        Integer i = scienceFieldsFreq.get(temp);
                        scienceFieldsFreq.put(temp, ++i);
                    } else {
                        scienceFieldsFreq.put(temp, 1);
                    }
                    if (sciencesFreq.containsKey(keyScience)) {
                        Integer count = sciencesFreq.get(keyScience);
                        sciencesFreq.put(keyScience, ++count);
                    } else {
                        sciencesFreq.put(keyScience, 1);
                    }
                }
            }
        }

//        System.out.println("HELLO!");
//        Iterator iter = scienceFieldsFreq.entrySet().iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next());
//        }
        Set map = (Set) scienceFieldsFreq.entrySet();
        Iterator iterator = map.iterator();
        ArrayList<String> keyScienceFields;
        double perc, tempPerc;
        double syxnotitaFields, syxnotitaScience;
        while (iterator.hasNext()) {
            Map.Entry mapEnt = (Map.Entry) iterator.next();
            keyScienceFields = (ArrayList) mapEnt.getKey();
            syxnotitaFields = (Integer) mapEnt.getValue();
            syxnotitaScience = sciencesFreq.get(keyScienceFields.get(0));
            ArrayList<String> tempFie ;

            if (keyScienceFields.size() == 2) {
                tempFie = new ArrayList<>();
                // System.out.println("hellohello!"+syxnotitaFields+"syxnotitaScience="+syxnotitaScience);
                perc = syxnotitaFields ;//=============================allazw 
                //System.out.println(perc);
                tempFie.add(keyScienceFields.get(0));//prosthiki science
                tempFie.add(keyScienceFields.get(1));//prosthiki field
                if (persentageLines.containsKey(tempFie)) {
                    tempPerc = persentageLines.get(tempFie);
                    double tem = tempPerc + perc;
//                    tempFie.add(keyScienceFields.get(0));//prosthiki science
//                    tempFie.add(keyScienceFields.get(1));
                    persentageLines.put(tempFie, tem);
                } else {
                    //tempFie.add(keyScienceFields.get(1));
                    persentageLines.put(tempFie, perc);
                }
            } else {
                perc = (0.7 * syxnotitaFields) / (keyScienceFields.size() - 1) ;
                for (int i = 1; i < keyScienceFields.size(); i++) {
                    tempFie = new ArrayList<>();
                    tempFie.add(keyScienceFields.get(0));//prosthiki science
                    tempFie.add(keyScienceFields.get(1));//prosthiki field
                    if (persentageLines.containsKey(tempFie)) {
                        tempPerc = persentageLines.get(tempFie);
                        double tem = tempPerc + perc;
                        persentageLines.put(tempFie, tem);
                    } else {
                        persentageLines.put(tempFie, perc);
                    }
                }
            }
        }
        System.out.println("FIELDS");
        Iterator iter = persentageLines.entrySet().iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    public static void findPercentage(String author) {//pososto gia to istogramma kai ta trigwna
        authorScienceFreq = new HashMap<>();

        Set mapSet = (Set) authorJournal.entrySet();
        Iterator it = mapSet.iterator();
        ArrayList<String> key;
        String science;
        JournalScienceMatch j;
        ArrayList<String> scienceList;
        int sumJournal = 0;

        HashMap<ArrayList<String>, Integer> freqMap = new HashMap<>();//ta science kai poses fores fores emfanizontai 

        int tempFreq;
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            key = (ArrayList) mapEntry.getKey();
            if (key.get(0).trim().equals(author)) {
                sumJournal++;//plithos twn journal pou exei symmetasxei o author
                j = (JournalScienceMatch) mapEntry.getValue();
                scienceList = j.getSciencesList();//science tou journal

                //System.out.println(scienceList.size());
                // System.out.println("journal:"+j.getTitle());
                //System.out.println("journal:"+j.getTitle());
                //Set mapSetscienceFields = (Set) scienceFieldMap.entrySet();
                //Iterator itSciences = mapSetscienceFields.iterator();
                //Map.Entry mapEntry1 = (Map.Entry) itSciences.next();
                ArrayList<String> temp = new ArrayList<>();

                if (scienceList.size() == 1) {
                    //science = mapEntry1.getKey().toString();
                    science = scienceList.get(0);
                    //System.out.println("science:"+science);
                    temp.add(science);
                    if (freqMap.containsKey(temp)) {
                        tempFreq = freqMap.get(temp);
                        freqMap.put(temp, ++tempFreq);
                    } else {
                        freqMap.put(temp, 1);
                    }
                } else {
                    //while (itSciences.hasNext()) {
                    for (String sc : scienceList) {
                        // Map.Entry mapEntry2 = (Map.Entry) itSciences.next();
                        //science = mapEntry2.getKey().toString();
                        science = sc;
                        //System.out.println("science:"+science);
                        temp.add(science);
                    }
                    if (freqMap.containsKey(temp)) {
                        tempFreq = freqMap.get(temp);
                        freqMap.put(temp, ++tempFreq);
                    } else {
                        freqMap.put(temp, 1);
                    }
                }
            }
        }
        // System.out.println("sumJournal=" + sumJournal);
        //Iterator itt = freqMap.entrySet().iterator();
        Set mapSetReq = (Set) freqMap.entrySet();
        Iterator itt = mapSetReq.iterator();
        ArrayList<String> name;
        double value;
        double perc = 0;
        while (itt.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) itt.next();
            name = (ArrayList) mapEntry.getKey();//name of science
            value = (int) mapEntry.getValue();//req of science
            //System.out.println("name=" + name.size());
            //System.out.println("value=" + value);
            if (name.size() == 1) {
                perc = value ;
                if (authorScienceFreq.containsKey(name.get(0))) {
                    double temp = authorScienceFreq.get(name.get(0));
                    double t1 = temp + perc;
                    authorScienceFreq.put(name.get(0), t1);
                } else {
                    authorScienceFreq.put(name.get(0), perc);
                }
            } else {
                perc = ((value * 0.7) / name.size());
                for (String n : name) {
                    if (authorScienceFreq.containsKey(n)) {
                        double temp = authorScienceFreq.get(n);
                        double t1 = temp + perc;
                        authorScienceFreq.put(n, t1);
                    } else {
                        authorScienceFreq.put(n, perc);
                    }
                }
            }
//            Iterator iterator = authorScienceFreq.entrySet().iterator();
//            while(iterator.hasNext()){
//                System.out.println(iterator.next());
//            }

        }
    }

    public void showHistogram(String author) {
        Iterator iterator = authorScienceFreq.entrySet().iterator();
        String countNames[] = new String[authorScienceFreq.size()];
        int i = 0;
        double count[] = new double[authorScienceFreq.size()];
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            countNames[i] = (String) mapEntry.getKey();
            // System.out.println("category=" + countNames[i]);
            count[i] = (double) mapEntry.getValue();
            //System.out.println("category=" + count[i]);
            i++;
        }

        Histogram h = new Histogram();
        h.showHistogram(count, countNames);
        JFrame frame = new JFrame("HISTOGRAM:    " + author);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(h);
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    //area representation
    public void showTriangles(String author) {
        
        Iterator iterator = authorScienceFreq.entrySet().iterator();
        String countNames[] = new String[authorScienceFreq.size()];
        int i = 0;
        double lengths[] = new double[5];
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            countNames[i] = (String) mapEntry.getKey();
           // System.out.println("category=" + countNames[i]);
            lengths[i] = (double) mapEntry.getValue();
            //System.out.println("category=" + lengths[i]);
            i++;
        }

        for (int j = 0; j < countNames.length; j++) {
            if (countNames[j].equals("natural science")) {
                lengths[0] = 100.0 * lengths[j];
                //System.out.println("timi="+lengths[0]);
            } else if (countNames[j].equals("applied science")) {
                lengths[1] = 100.0 * lengths[j];
            } else if (countNames[j].equals("social science")) {
                lengths[2] = 100.0 * lengths[j];
            } else if (countNames[j].equals("universe science")) {
                lengths[3] = 100.0 * lengths[j];
            } else if (countNames[j].equals("human science")) {
                lengths[4] = 100.0 * lengths[j];
            }
        }

        // for(int k=0; k<lengths.length; k++){
        // System.out.println(lengths[k]);
        // }
        // double lengths[] = {50.1,45,65.0,98,32};
        Graph1 g1 = new Graph1(lengths);
        JFrame frame = new JFrame("Triangles:    " + author);
        frame.add(g1);
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }

    //second level field representation
    public void showLines(String author) {
        Iterator iterator = persentageLines.entrySet().iterator();
        String countNamesScience[] = new String[persentageLines.size()];
        String countNamesFields[] = new String[persentageLines.size()];
        int i = 0;
        ArrayList<String> temp;
        double lengths[] = new double[5];
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            temp = (ArrayList) mapEntry.getKey();
            countNamesScience[i] = temp.get(0).toLowerCase();
            countNamesFields[i] = temp.get(1).toLowerCase();
            //System.out.println("category=" + countNames[i]);
            lengths[i] = (double) mapEntry.getValue() * 100.0;
            //System.out.println("category=" + lengths[i]);
            i++;
        }

        double lengths1[] = new double[7];
        double lengths2[] = new double[5];
        double lengths3[] = new double[6];
        double lengths4[] = new double[4];
        double lengths5[] = new double[6];

        for (int j = 0; j < countNamesScience.length; j++) {
            if (countNamesScience[j].equals("natural science")) {
                if (countNamesFields[j].equals("physics")) {
                    lengths1[0] = lengths[j];
                }
                if (countNamesFields[j].equals("chemistry")) {
                    lengths1[1] = lengths[j];
                }
                if (countNamesFields[j].equals("earth and planetary science")) {
                    lengths1[2] = lengths[j];
                }
                if (countNamesFields[j].equals("life science")) {
                    lengths1[3] = lengths[j];
                }
                if (countNamesFields[j].equals("mathematics")) {
                    lengths1[4] = lengths[j];
                }
                if (countNamesFields[j].equals("statistics")) {
                    lengths1[5] = lengths[j];
                }
                if (countNamesFields[j].equals("computer science")) {
                    lengths1[6] = lengths[j];
                }
                //System.out.println("timi="+lengths[0]);
            } else if (countNamesScience[j].equals("applied science")) {
                if (countNamesFields[j].equals("mechanical engineering")) {
                    lengths2[0] = lengths[j];
                }
                if (countNamesFields[j].equals("health science")) {
                    lengths2[1] = lengths[j];
                }
                if (countNamesFields[j].equals("geotechnical science")) {
                    lengths2[2] = lengths[j];
                }
                if (countNamesFields[j].equals("science of management")) {
                    lengths2[3] = lengths[j];
                }
            } else if (countNamesScience[j].equals("social science")) {
                if (countNamesFields[j].equals("psychology")) {
                    lengths3[0] = lengths[j];
                }
                if (countNamesFields[j].equals("politics")) {
                    lengths3[1] = lengths[j];
                }
                if (countNamesFields[j].equals("law & order")) {
                    lengths3[2] = lengths[j];
                }
                if (countNamesFields[j].equals("sociology")) {
                    lengths3[3] = lengths[j];
                }
                if (countNamesFields[j].equals("economics")) {
                    lengths3[4] = lengths[j];
                }
                if (countNamesFields[j].equals("geography")) {
                    lengths3[5] = lengths[j];
                }
            } else if (countNamesScience[j].equals("universe science")) {
                if (countNamesFields[j].equals("cosmology")) {
                    lengths4[2] = lengths[j];
                }
                if (countNamesFields[j].equals("metaphysics")) {
                    lengths4[3] = lengths[j];
                }
                if (countNamesFields[j].equals("theology")) {
                    lengths4[4] = lengths[j];
                }
                if (countNamesFields[j].equals("astronomy")) {
                    lengths4[5] = lengths[j];
                }
            } else if (countNamesScience[j].equals("human science")) {
                if (countNamesFields[j].equals("philology")) {
                    lengths5[0] = lengths[j];
                }
                if (countNamesFields[j].equals("history")) {
                    lengths5[1] = lengths[j];
                }
                if (countNamesFields[j].equals("archeology")) {
                    lengths5[2] = lengths[j];
                }
                if (countNamesFields[j].equals("anthropology")) {
                    lengths5[3] = lengths[j];
                }
                if (countNamesFields[j].equals("philosophy")) {
                    lengths5[4] = lengths[j];
                }
                if (countNamesFields[j].equals("anatomy")) {
                    lengths5[5] = lengths[j];
                }
            }
        }

        
        JFrame frame;
        Graph2 g2 = new Graph2(lengths1, lengths2, lengths3, lengths4, lengths5);
        frame = new JFrame("Triangles:    " + author);
        frame.add(g2);
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

//        for(int k=0; k<lengths.length; k++){
//            System.out.println(lengths[k]);
//        }
    }

    //tha vazei se hashMap tous author kai ta journal pou exoun symmetasxei.journal:to synedrio me ta science,field,subfield....
    public static void findAuthorJournals() {

        String nameJournal;
        for (Papers p : paperObject) {
            nameJournal = p.getNameJournal();

            //String authorTitle[] = new String[2];//periexei ton author kai to title tou paper
            ArrayList<String> authors = p.getAuthorsList();

            //System.out.println("authors="+authors);
            String i = "0";
            Integer ti;
            for (JournalScienceMatch j : journalObjects) {//gia kathe antikeimeno journal
                if (nameJournal.equals(j.getTitle())) {
                    //ArrayList<String> authorTitle = new ArrayList<>();

                    for (String a : authors) {
                        ti = (Integer.parseInt(i) + 1);
                        i = ti.toString();

                        ArrayList<String> authorTitle = new ArrayList<>();
                        authorTitle.add(a.trim());
                        //System.out.println("author:"+a);
                        authorTitle.add(p.getTitle().trim());//titlos tou paper
                        authorTitle.add(i);
//                        System.out.println("title:"+p.getTitle());
//                        System.out.println("sciences:"+j.getSciencesList());
                        authorJournal.put(authorTitle, j);
                    }
                    // System.out.println();
                }
            }
        }
//        Set mapSet = (Set) authorJournal.entrySet();
//        Iterator it = mapSet.iterator();
//        ArrayList key;
//
//        while (it.hasNext()) {
//            Map.Entry mapEntry = (Map.Entry) it.next();
//            key = (ArrayList) mapEntry.getKey();
//            //System.out.println("author="+key.get(0));
//            if (key.get(0).equals("N PLATIS")) {
//                System.out.println("author:" + key.get(0));
//                System.out.println("title:" + key.get(1));
//                JournalScienceMatch js = (JournalScienceMatch) mapEntry.getValue();
//                System.out.println("journal=" + js.getTitle());
//                System.out.println("scienceList=" + js.getSciencesList());
//            }
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        journals = new java.awt.List();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        listAuthors = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        panelSchemes = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("LOAD FILES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        journals.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 255));
        jButton2.setText("ADD JOURNAL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Clear List");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Journals:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(journals, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(journals, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("JOURNALS", jPanel1);

        listAuthors.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("Authors:");

        javax.swing.GroupLayout panelSchemesLayout = new javax.swing.GroupLayout(panelSchemes);
        panelSchemes.setLayout(panelSchemesLayout);
        panelSchemesLayout.setHorizontalGroup(
            panelSchemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
        );
        panelSchemesLayout.setVerticalGroup(
            panelSchemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 204));
        jButton4.setText("CREATE SCHEMES");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(listAuthors, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelSchemes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listAuthors, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(panelSchemes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("AUTHORS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        AddJournal add = new AddJournal();
        add.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        journals.clear();
    }//GEN-LAST:event_jButton3ActionPerformed


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String author = listAuthors.getSelectedItem().toString();
        //String author = "J HORTON";
        System.out.println("author:" + author);
        findPercentage(author);
        showHistogram(author);
        showTriangles(author);
        findPercentageForLines(author);
        showLines(author);
    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ptyxiaki.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ptyxiaki.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ptyxiaki.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ptyxiaki.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ptyxiaki().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static java.awt.List journals;
    protected static javax.swing.JComboBox listAuthors;
    private javax.swing.JPanel panelSchemes;
    // End of variables declaration//GEN-END:variables
}
