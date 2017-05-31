package com.example.a15017395.fyptestapp;
public class Outlet {
    private String id;
    private String name;
    private String location;
    private String postalCode;

    public Outlet() {
        super();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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
        return "Outlet [id=" + id
                + ", name=" + name
                + ", location=" + location
                + ", postalCode=" + postalCode
                + "]";
    }
}
