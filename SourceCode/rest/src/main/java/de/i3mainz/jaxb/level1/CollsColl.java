package de.i3mainz.jaxb.level1;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollsColl
 * @author Florian Thiery
 */

@XmlRootElement(name = "collections")
public class CollsColl {

    private Collection collection;
    
    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}
