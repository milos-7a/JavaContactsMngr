package com.milos.contactsmanager.model;
import java.time.LocalDate;
import java.util.List;

public class Kontakt {
    private int id;
    private String Ime;
    private String Prezime;
    private String Kompanija;
    private String adresa;
    private LocalDate rodjendan;
    private String napomene;
    private String slika;
    private List<KontaktDetalji> detalji;

    //Konstruktori
    public Kontakt(){
        id=0;
        Ime=null;
        Prezime=null;
        adresa=null;
        Kompanija=null;
        rodjendan=null;
        slika=null;
        detalji = null;
    }
    public Kontakt(int id, String Ime, String Prezime, String Kompanija, String adresa, LocalDate rodjendan, String napomene, String slika){
        this.id = id;
        this.Ime = Ime;
        this.Prezime = Prezime;
        this.Kompanija = Kompanija;
        this.adresa = adresa;
        this.rodjendan = rodjendan;
        this.napomene = napomene;
        this.slika = slika;
        this.detalji = null;
    }

    //Get
    public int getId(){
        return id;
    }
    public String getIme(){
        return Ime;
    }
    public String getPrezime(){
        return Prezime;
    }
    public String getKompanija(){
        return Kompanija;
    }
    public LocalDate getRodjendan(){
        return rodjendan;
    }
    public String getNapomene(){
        return napomene;
    }
    public String getSlika(){
        return slika;
    }
    public String getAdresa() {return adresa;}
    public List<KontaktDetalji> getDetalji(){ return detalji;}

    //Set
    public void setId(int id){
        this.id = id;
    }
    public void setIme(String ime){
        this.Ime = ime;
    }
    public void setPrezime(String prezime){
        this.Prezime = prezime;
    }
    public void setKompanija(String kompanija){
        this.Kompanija = kompanija;
    }
    public void setRodjendan(LocalDate rodjendan){
        this.rodjendan = rodjendan;
    }
    public void setNapomene(String napomena){
        this.napomene = napomena;
    }
    public void setSlika(String slika){
        this.slika = slika;
    }
    public void setAdresa (String adresa) { this.adresa = adresa;}
    public void setDetalji(List<KontaktDetalji> detalji) { this.detalji = detalji;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================\n");
        sb.append("Kontakt: ").append(Ime).append(" ").append(Prezime).append("\n");
        sb.append("----------------------------------\n");
        sb.append(String.format("ID: %d\n", id));
        if (Kompanija != null && !Kompanija.isEmpty()) {
            sb.append(String.format("Kompanija: %s\n", Kompanija));
        }
        if (rodjendan != null) {
            sb.append(String.format("Rođendan: %s\n", rodjendan));
        }
        if (napomene != null && !napomene.isEmpty()) {
            sb.append(String.format("Napomene: %s\n", napomene));
        }
        if (adresa != null && !adresa.isEmpty()) {
            sb.append(String.format("Adresa: %s\n", adresa));
        }
        if (slika != null && !slika.isEmpty()) {
            sb.append(String.format("Slika: %s\n", slika));
        }

        if (detalji != null && !detalji.isEmpty()) {
            sb.append("\nDetalji:\n");
            sb.append("----------------------------------\n");
            for (KontaktDetalji detalj : detalji) {
                sb.append(String.format("%s\n", detalj));
            }
        }
        sb.append("==================================\n");
        return sb.toString();
    }
}
