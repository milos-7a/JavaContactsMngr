package com.milos.contactsmanager.model;

public class Link extends KontaktDetalji{
    private String url;

    //Konstruktor
    public Link(){
        super(0,0,null);
        url = null;
    }
    public Link(int id, int kontakt_id, String tip, String url){
        super(id,kontakt_id,tip);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String vratiVrednosti() {
        return url;
    }

}
