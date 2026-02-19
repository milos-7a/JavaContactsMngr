package com.milos.contactsmanager.model;

public class Telefon extends KontaktDetalji{
    private String broj;

    //Konstruktori
    public Telefon(){
        super(0,0,null);
        broj = "";
    }
    public Telefon(int id, int kontakt_id, String tip, String broj){
        super(id, kontakt_id, tip);
        this.broj = broj;
    }

    public String getBroj() {
        return broj;
    }
    public void setBroj(String broj) {
        this.broj = broj;
    }

    @Override
    public String vratiVrednosti() {
        return broj;
    }

}
