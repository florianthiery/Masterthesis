package de.i3mainz.dbutils.event;

import javax.persistence.*;

@Entity
public class Potter {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Potter() {
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
}