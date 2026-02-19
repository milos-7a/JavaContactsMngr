package com.milos.contactsmanager.model;

public class Email extends KontaktDetalji{
    private String email;

    //Konstruktor
    public Email(){
        super(0,0,null);
        this.email = null;
    }
    public Email(int id, int kontakt_id, String tip, String email){
        super(id,kontakt_id,tip);
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String vratiVrednosti() {
        return email;
    }

}
