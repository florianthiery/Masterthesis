package de.i3mainz.jaxb.level4;

import de.i3mainz.jaxb.level4.PotterRes;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Klasse CollllectionAttrib
 * @author Florian Thiery
 */

@XmlRootElement(name = "resources")
public class ResourcesFragment {

    private @XmlAttribute String id;
    private @XmlAttribute String href;
    private FragmentRes resource;
    
    public void setID(String id) {
        this.id = id;
    }
    
    public void setHREF(String href) {
        this.href = href;
    }
    
    public FragmentRes getResource() {
        return resource;
    }

    public void setResource(FragmentRes resource) {
        this.resource = resource;
    }

}
