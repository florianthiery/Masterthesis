package de.i3mainz.jaxb.level4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "collection")
public class CollResResResFragment {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private ResourcesFragment resources;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    
    public ResourcesFragment getResources() {
        return resources;
    }

    public void setResources(ResourcesFragment resources) {
        this.resources = resources;
    }

}
