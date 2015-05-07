package de.i3mainz.dbutils.event;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Fragment implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String die;
    private String potform;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="f_pid")
    private Potter potter;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="f_fid")
    private Findspot findspot;
    private int number;

    public Fragment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Potter getPotter() {
        return potter;
    }

    public void setPotter(Potter potter) {
        this.potter = potter;
    }

    public Findspot getFindspot() {
        return findspot;
    }

    public void setFindspot(Findspot findspot) {
        this.findspot = findspot;
    }
}