package de.i3mainz.jaxb.level3;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "collection")
public class CollResRes {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private Resources2 resources;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    
    public Resources2 getResources() {
        return resources;
    }

    public void setResources(Resources2 resources) {
        this.resources = resources;
    }

}
