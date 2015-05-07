package de.i3mainz.dbutils.event;

import javax.persistence.*;

@Entity
public class Pleiadesmatch {

    @Id
    @GeneratedValue
    private String findspot;
    private String pleiadesplace;

    public Pleiadesmatch() {
    }

    public String getFindspot() {
        return findspot;
    }

    private void setFindspot(String findspot) {
        this.findspot = findspot;
    }

    public String getPleiadesPlace() {
        return pleiadesplace;
    }

    public void setPleiadesPlace(String pleiadesplace) {
        this.pleiadesplace = pleiadesplace;
    }
}