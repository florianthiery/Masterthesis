package de.i3mainz.jaxb.level4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse Findspot
 * @author Florian Thiery
 */

@XmlRootElement(name = "resource")
public class FindspotRes {
    
    private @XmlAttribute String id;
    private @XmlAttribute String href;
    
    private String name;
    private int datemin;
    private int datemax;
    private boolean kilnsite;
    private String location;
    private String gml;

    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
//    public int getDateMin() {
//        return datemin;
//    }
//    
//    public void setDateMin(int datemin) {
//        this.datemin = datemin;
//    }
//    
//    public int getDateMax() {
//        return datemax;
//    }
//    
//    public void setDateMax(int datemax) {
//        this.datemax = datemax;
//    }
    
    public boolean getKilnsite() {
        return kilnsite;
    }
    
    public void setKilnsite(boolean kilnsite) {
        this.kilnsite = kilnsite;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getGML() {
        return gml;
    }
    
    public void setGML(String gml) {
        this.gml = gml;
    }

}