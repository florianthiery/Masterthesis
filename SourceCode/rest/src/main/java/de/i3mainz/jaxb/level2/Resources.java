package de.i3mainz.jaxb.level2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse Collection
 * @author Florian Thiery
 */

@XmlRootElement(name = "resources")
public class Resources {

    public Resources() {
        super();
    }
    
    private @XmlAttribute String id;
    private @XmlAttribute String href;

    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }

}