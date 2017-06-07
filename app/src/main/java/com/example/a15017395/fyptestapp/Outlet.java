package com.example.a15017395.fyptestapp;

import java.io.Serializable;

public class Outlet implements Serializable {
    private int id;
    private String name;
    private String location;
    private String postalCode;

    public Outlet() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Outlet [outlet_id=" + id
                + ", name=" + name
                + ", location=" + location
                + ", postalCode=" + postalCode
                + "]";
    }
}
