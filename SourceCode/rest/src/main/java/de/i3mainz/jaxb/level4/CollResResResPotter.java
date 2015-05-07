package de.i3mainz.jaxb.level4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "collection")
public class CollResResResPotter {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private ResourcesPotter resources;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    
    public ResourcesPotter getResources() {
        return resources;
    }

    public void setResources(ResourcesPotter resources) {
        this.resources = resources;
    }

}
