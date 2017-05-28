package com.example.a15017395.fyptestapp;

public class Contact {
    // Create the attributes for your class
    private String role;
    private String name;
    private String email;
    private String fb;
    private String insta;

    public Contact() {
        super();
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFb() {
        return fb;
    }
    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getInsta() {
        return insta;
    }
    public void setInsta(String insta) {
        this.insta = insta;
    }

    @Override
    public String toString() {
        return "Contact [role=" + role
                + ", name=" + name
                + ", email=" + email
                + ", fb=" + fb
                + ", insta=" + insta
                + "]";
    }
}

