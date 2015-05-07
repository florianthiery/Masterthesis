package de.i3mainz.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Triples
 *
 * @author Florian Thiery
 */
public class Triple {

    private List<String> triplelist = new ArrayList<String>();
    private List<String> entitylist = new ArrayList<String>();
    private List<String[]> coordlist = new ArrayList<String[]>();
    private int x = 20; // jahre durch 2 und 4 teilbar ohne fließkommazahl
    //private int ref = 10000; // referenzjahr
    private int ref = 1900; // referenzjahr

    /**
     * Konstruktor
     */
    public Triple() throws SQLException, IOException {

//        triplelist.add("site:A time:before site:B .");
//        triplelist.add("site:C time:before site:A .");
//        triplelist.add("site:G time:after site:A .");
//        triplelist.add("site:D time:after site:B .");
//        triplelist.add("site:E time:meets site:C .");
//        triplelist.add("site:F time:overlapped-by site:C .");

//        Eine Linie
//        ----------
//        triplelist.add("site:B time:meets site:A .");
//        triplelist.add("site:C time:meets site:B .");
//        triplelist.add("site:D time:met-by site:A .");
//        triplelist.add("site:E time:met-by site:D .");

//        triplelist.add("site:B time:contains site:A .");
//        triplelist.add("site:C time:meets site:B .");
//        triplelist.add("site:D time:met-by site:A .");
//        triplelist.add("site:E time:met-by site:D .");

        PostGIS pg = new PostGIS();
        triplelist = pg.getTriples();

    }

    /**
     * Create Koordinates and fill Array
     */
    private void createCoordinates() {

        List<String> triplelist_tmp = new ArrayList<String>();
        triplelist_tmp = sortTriplelist(triplelist);
        
        try {
        
        for (int i = 0; i < triplelist_tmp.size(); i++) {
            System.out.println(triplelist_tmp.get(i));
        }

        for (int i = 0; i < triplelist_tmp.size(); i++) {

            // Triple parsen und einzelne Elemente zwischenspeichern
            String temp = triplelist_tmp.get(i);
            temp = temp.replace("site:", "");
            temp = temp.replace("time:", "");
            temp = temp.replace(" .", "");
            String[] temparray = temp.split(" "); //[0]subject/site [1]prädikat/time [2]object/site

            // Bei erstem Aufruf, das Objekt als Referenzdatum nutzen
            if (i == 0) {
                String[] nae = new String[4];
                nae[0] = temparray[2];
                nae[1] = String.valueOf(ref);
                nae[2] = String.valueOf(ref + x);
                nae[3] = "ref";
                coordlist.add(nae);
            }

            boolean match = false;
            // Nach Objektname in der Liste suchen und "Koordinaten" berechnen
            for (int j = 0; j < coordlist.size(); j++) {

                String[] temparray2 = coordlist.get(j);

                // wenn Objekt schon vorhanden
                if (temparray2[0].equals(temparray[2])) {

                    String[] nae = new String[4];
                    nae[0] = temparray[0];
                    nae[3] = temparray[1];
                    int sa = 0;
                    int se = 0;
                    int tmp = 0;
                    int tmp2 = 0;

                    if (temparray[1].equals("before")) { // <              
                        // start start-0.5x | ende -x
                        se = Integer.parseInt(temparray2[1]) - new Double(0.5 * x).intValue();
                        sa = se - x;
                    } else if (temparray[1].equals("after")) { // >
                        // start ende+0.5x | ende +x
                        sa = Integer.parseInt(temparray2[2]) + new Double(0.5 * x).intValue();
                        se = sa + x;
                    } else if (temparray[1].equals("during")) { // d
                        // start mitte-0.25x | ende mitte+0.25x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        sa = tmp - new Double(0.25 * tmp2).intValue();
                        se = tmp + new Double(0.25 * tmp2).intValue();
                    } else if (temparray[1].equals("contains")) { // di
                        // start mitte-x | ende mitte+x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        sa = tmp - (tmp2/2) - new Double(0.25 * tmp2).intValue();
                        se = tmp + (tmp2/2) + new Double(0.25 * tmp2).intValue();
                    } else if (temparray[1].equals("overlaps")) { // o
                        // start mitte | ende -x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        se = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2;
                        //sa = se - x;
                        sa = se - tmp2;
                    } else if (temparray[1].equals("overlapped-by")) { // oi
                        // start mitte | ende +x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        sa = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2;
                        //se = sa + x;
                        se = sa + tmp2;
                    } else if (temparray[1].equals("meets")) { // m
                        // start anfang | ende -x
                        se = Integer.parseInt(temparray2[1]);
                        sa = se - x;
                    } else if (temparray[1].equals("met-by")) { // mi
                        // start ende | ende +x
                        sa = Integer.parseInt(temparray2[2]);
                        se = sa + x;
                    } else if (temparray[1].equals("starts")) { // s
                        // start start | ende +0.5x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        sa = Integer.parseInt(temparray2[1]);
                        //se = sa + new Double(0.5 * x).intValue();
                        se = sa + new Double(0.5 * tmp2).intValue();
                    } else if (temparray[1].equals("started-by")) { // si
                        // start start | ende +1.5x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        sa = Integer.parseInt(temparray2[1]);
                        //se = sa + new Double(1.5 * x).intValue();
                        se = sa + new Double(1.5 * tmp2).intValue();
                    } else if (temparray[1].equals("finishes")) { // f
                        // start ende | ende -0.5x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        se = Integer.parseInt(temparray2[2]);
                        //sa = se - new Double(0.5 * x).intValue();
                        sa = se - new Double(0.5 * tmp2).intValue();
                    } else if (temparray[1].equals("finished-by")) { // fi
                        // start ende | ende -1.5x
                        tmp = (Integer.parseInt(temparray2[1]) + Integer.parseInt(temparray2[2])) / 2; //mitte
                        tmp2 = (Integer.parseInt(temparray2[2]) - Integer.parseInt(temparray2[1])); //länge des vorhandenen
                        se = Integer.parseInt(temparray2[2]);
                        //sa = se - new Double(1.5 * x).intValue();
                        sa = se - new Double(1.5 * tmp2).intValue();
                    } else if (temparray[1].equals("equals")) { // =
                        // start start | ende ende
                        sa = Integer.parseInt(temparray2[1]);
                        se = Integer.parseInt(temparray2[2]);
                    } else { // =
                        // start start | ende ende
                        sa = Integer.parseInt(temparray2[1]);
                        se = Integer.parseInt(temparray2[2]);
                    }

                    nae[1] = String.valueOf(sa);
                    nae[2] = String.valueOf(se);
                    coordlist.add(nae);
                    match = true;
//                    System.out.println(temparray[2] + "--" + match);
                    break;

                }

            } // end for
            


        }
        
        } catch (Exception e) {
            int z = 0;
        }
    }

    /**
     * Sortiert eine Liste von Tripeln nach Vorkommen des Objekts erst nach
     * Vorkommen als Subjekt
     *
     * @param TripleList
     * @return triplelist
     */
    private List<String> sortTriplelist(List<String> TripleList) {

        List<String> triplelist_tmp = new ArrayList<String>();
        triplelist_tmp = TripleList;
        List<String> entitylist_tmp = new ArrayList<String>();

        // Ausgabe vor dem Sortieren
        for (int i = 0; i < triplelist_tmp.size(); i++) {
            System.out.println(triplelist_tmp.get(i));
        }

        // Sortieren
        boolean match = false;
        boolean match2 = false;
        while (match2 == false) {
            for (int i = 0; i < triplelist_tmp.size(); i++) {

                String temp = triplelist_tmp.get(i);
                temp = temp.replace("site:", "");
                temp = temp.replace("time:", "");
                temp = temp.replace(" .", "");
                String[] temparray = temp.split(" "); //[0]subject/site [1]prädikat/time [2]object/site

                // Bei erstem Aufruf, das Objekt als Referenzdatum nutzen
                if (i == 0) {
                    entitylist_tmp.add(temparray[2]);
                }

                // Nach Objektname in der Entity-Liste suchen
                for (int j = 0; j < entitylist_tmp.size(); j++) {

                    String temparray2 = entitylist_tmp.get(j);

                    // wenn Objekt schon vorhanden
                    if (temparray2.equals(temparray[2])) {
                        entitylist_tmp.add(temparray[0]);
                        match = true;
                        break;
                    } else {
                        match = false;
                        match2 = true;
                    }

                } // end for j

                if (match == false) {
                    //System.out.println("i: " + i + " " + temp);
                    triplelist_tmp.remove(i);
                    triplelist_tmp.add(temp);
                    match2 = false;
                    entitylist_tmp.clear();
                    break;
                }
            } // end for i

        } // end while

        for (int i = 0; i < triplelist_tmp.size(); i++) {
            System.out.println("in sort: " + triplelist_tmp.get(i));
        }
//
//        for (int i = 0; i < entitylist_tmp.size(); i++) {
//            System.out.println(entitylist_tmp.get(i));
//        }

        return triplelist_tmp;

    }

    private void getEntities() {

        for (int i = 0; i < triplelist.size(); i++) {

            // Triple parsen und einzelne Elemente zwischenspeichern
            String temp = triplelist.get(i);
            temp = temp.replace("site:", "");
            temp = temp.replace("time:", "");
            temp = temp.replace(" .", "");
            String[] temparray = temp.split(" "); //[0]subject/site [1]prädikat/time [2]object/site

            // Bei erstem Aufruf, das Objekt nutzen
            if (i == 0) {
                entitylist.add(temparray[2]);
            }

            entitylist.add(temparray[0]);

        }

    }

    /**
     * Get List of Triples
     *
     * @return TripleStringList
     */
    public List<String> getTripleList() {
        return triplelist;
    }

    /**
     * Get List of Time Koordinates
     *
     * @return TimeCoordinateListArrayNameBeginEnd
     */
    public List<String[]> getCoordinateList() {
        createCoordinates();
        return coordlist;
    }

    /**
     * Get List of Entitys
     *
     * @return
     */
    public List<String> getEntityList() {
        getEntities();
        return entitylist;
    }
}
