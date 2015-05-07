package de.i3mainz.jaxb.level1;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse Collection
 * @author Florian Thiery
 */

@XmlRootElement(name = "collection")
public class Collection {

    private @XmlAttribute String name;
    private @XmlAttribute String href;

    public void setName(String name) {
        this.name = name;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }

}
