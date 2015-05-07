package de.i3mainz.dbutils.event;

import com.vividsolutions.jts.geom.Point;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class Findspot implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private double lat;
    private double lon;
    private int datemin;
    private int datemax;
    private boolean kilnsite;
    @Type(type = "org.hibernate.spatial.GeometryType")
    @Column(name = "geom")
    private Point location;

    public Findspot() {
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setDate(String name) {
        this.name = name;
    }
    
    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    
    public int getDatemin() {
        return datemin;
    }

    public void setDatemin(int datemin) {
        this.datemin = datemin;
    }
    
    public int getDatemax() {
        return datemax;
    }

    public void setDatemax(int datemax) {
        this.datemax = datemax;
    }
    
    public boolean getKilnsite() {
        return kilnsite;
    }

    public void setKilnsite(boolean kilnsite) {
        this.kilnsite = kilnsite;
    }
    
    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}