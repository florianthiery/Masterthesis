package de.i3mainz.jaxb.level4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse Potter
 * @author Florian Thiery
 */

@XmlRootElement(name = "resource")
public class PotterRes {
    
    private @XmlAttribute String id;
    private @XmlAttribute String href;
    
    private String pottername;

    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    public String getPottername() {
        return pottername;
    }
    
    public void setPottername(String pottername) {
    this.pottername = pottername;
  }

}
