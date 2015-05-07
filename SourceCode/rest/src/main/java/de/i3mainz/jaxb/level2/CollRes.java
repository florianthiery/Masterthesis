package de.i3mainz.jaxb.level2;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "collection")
public class CollRes {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private ArrayList<Resources> resources;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    
    public ArrayList<Resources> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resources> resources) {
        this.resources = resources;
    }

}