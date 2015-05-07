package de.i3mainz.jaxb.level4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse Collection
 *
 * @author Florian Thiery
 */
@XmlRootElement(name = "resource")
public class FragmentRes {

    private @XmlAttribute
    String id;
    private @XmlAttribute
    String href;
    private String die;
    private String potform;
    private PotterRes potter;
    private FindspotRes findspot;

    public void setID(String id) {
        this.id = id;
    }

    public void setHREF(String href) {
        this.href = href;
    }

    public String getDie() {
        return die;
    }

    public void setDie(String die) {
        this.die = die;
    }

    public String getPotform() {
        return potform;
    }

    public void setPotform(String potform) {
        this.potform = potform;
    }
    
    public PotterRes getPotter() {
        return potter;
    }

    public void setPotter(PotterRes potter) {
        this.potter = potter;
    }
    
    public FindspotRes getFindspot() {
        return findspot;
    }

    public void setFindspot(FindspotRes findspot) {
        this.findspot = findspot;
    }
}
