package com.afm.suppliermanagementsystem.model;

public class Agence {

    private String agence;
    private String address;
    private String telephone;

    public Agence() {
    }

    public Agence(String agence, String address, String telephone) {
        this.agence = agence;
        this.address = address;
        this.telephone = telephone;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}