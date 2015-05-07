package de.i3mainz.jaxb.level3;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "resources")
public class Resources2 {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private ArrayList<Resource> resource;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    
    public ArrayList<Resource> getResource() {
        return resource;
    }

    public void setResource(ArrayList<Resource> resource) {
        this.resource = resource;
    }

}
