package com.milos.contactsmanager.model;

public abstract class KontaktDetalji {
    private int id;
    private int kontakt_id;
    private String tip;
    public KontaktDetalji(int id, int kontakt_id, String tip){
        this.id = id;
        this.kontakt_id = kontakt_id;
        this.tip = tip;
    }
    //Get
    public int getId() {
        return id;
    }
    public int getKontakt_id() {
        return kontakt_id;
    }
    public String getTip() { return tip;}
    //Set
    public void setKontakt_id(int kontakt_id) {
        this.kontakt_id = kontakt_id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTip(String tip){ this.tip = tip;}

    public abstract String vratiVrednosti();
    @Override
    public String toString() {
        return String.valueOf(id) + kontakt_id + tip;
    }
}
