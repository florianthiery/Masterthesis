package de.i3mainz.jaxb.level4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "collection")
public class CollResResResFindspot {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private ResourcesFindspot resources;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    
    public ResourcesFindspot getResources() {
        return resources;
    }

    public void setResources(ResourcesFindspot resources) {
        this.resources = resources;
    }

}
