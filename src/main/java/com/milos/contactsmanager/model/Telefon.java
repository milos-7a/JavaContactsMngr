package com.milos.contactsmanager.model;

public class Telefon extends KontaktDetalji{
    private int broj;

    //Konstruktori
    public Telefon(){
        super(0,0,null);
        broj = 0;
    }
    public Telefon(int id, int kontakt_id, String tip, int broj){
        super(id, kontakt_id, tip);
        this.broj = broj;
    }

    public int getBroj() {
        return broj;
    }
    public void setBroj(int broj) {
        this.broj = broj;
    }

    @Override
    public String vratiVrednosti() {
        return String.valueOf(broj);
    }

    @Override
    public String toString() {
        return String.valueOf(broj) + " - " + getTip();
    }
}
